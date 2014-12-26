package org.karpukhin.hibernatecachedemo.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Pavel Karpukhin
 * @since 25.12.14
 */
@javax.persistence.Entity
@Table(name = "GROUPS")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "Groups")
@NamedQuery(name = "query.group.get.all", query = "from Group")
public class Group extends Entity {

    @Column(name = "NAME", nullable = false, length = 50)
    private String name;
    @Column(name = "DESCRIPTION", nullable = true, length = 50)
    private String description;
    @OneToMany(mappedBy = "group")
    @Cascade(CascadeType.SAVE_UPDATE)
    @OrderBy("id")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "Group.Users")
    private List<User> users = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        users.add(user);
        user.setGroup(this);
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
