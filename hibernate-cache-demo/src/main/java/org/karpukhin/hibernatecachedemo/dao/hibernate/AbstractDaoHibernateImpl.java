package org.karpukhin.hibernatecachedemo.dao.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.karpukhin.hibernatecachedemo.dao.EntityDao;
import org.karpukhin.hibernatecachedemo.model.Entity;

import java.lang.reflect.ParameterizedType;

/**
 * @author Pavel Karpukhin
 * @since 25.12.14
 */
public class AbstractDaoHibernateImpl<T extends Entity> implements EntityDao<T> {

    private SessionFactory sessionFactory;

    private final Class<T> type = getType();

    public AbstractDaoHibernateImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(T entity) {
        getSession().save(entity);
    }

    @Override
    public void update(T entity) {
        getSession().update(entity);
    }

    @Override
    public T getById(int id) {
        return (T)getSession().load(type, id);
    }

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings("unchecked")
    private Class<T> getType() {
        return (Class<T>) ((ParameterizedType)getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }
}
