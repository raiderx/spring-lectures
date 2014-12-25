package org.karpukhin.hibernatecachedemo.model;

import javax.persistence.*;

/**
 * @author Pavel Karpukhin
 * @since 25.12.14
 */
@MappedSuperclass
public abstract class Entity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
