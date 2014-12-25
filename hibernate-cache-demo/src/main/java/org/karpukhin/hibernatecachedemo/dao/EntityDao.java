package org.karpukhin.hibernatecachedemo.dao;

import org.karpukhin.hibernatecachedemo.model.Entity;

/**
 * @author Pavel Karpukhin
 * @since 25.12.14
 */
public interface EntityDao<T extends Entity> {

    void create(T entity);

    void update(T entity);

    T getById(int id);
}
