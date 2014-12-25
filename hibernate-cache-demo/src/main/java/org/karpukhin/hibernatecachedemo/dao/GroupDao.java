package org.karpukhin.hibernatecachedemo.dao;

import org.karpukhin.hibernatecachedemo.model.Group;

import java.util.List;

/**
 * @author Pavel Karpukhin
 * @since 25.12.14
 */
public interface GroupDao extends EntityDao<Group> {

    List<Group> getAll();
}
