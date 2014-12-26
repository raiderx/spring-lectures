package org.karpukhin.hibernatecachedemo.dao.hibernate;

import org.hibernate.SessionFactory;
import org.karpukhin.hibernatecachedemo.dao.GroupDao;
import org.karpukhin.hibernatecachedemo.model.Group;

import java.util.List;

/**
 * @author Pavel Karpukhin
 * @since 25.12.14
 */
public class GroupDaoHibernateImpl extends AbstractDaoHibernateImpl<Group> implements GroupDao {

    public GroupDaoHibernateImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Group> getAll() {
        return getSession().getNamedQuery("query.group.get.all").setCacheable(true).list();
    }
}
