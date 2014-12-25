package org.karpukhin.hibernatecachedemo.dao.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.stat.SecondLevelCacheStatistics;
import org.hibernate.stat.Statistics;
import org.junit.Before;
import org.junit.Test;
import org.karpukhin.hibernatecachedemo.dao.GroupDao;
import org.karpukhin.hibernatecachedemo.model.Group;
import org.karpukhin.hibernatecachedemo.model.User;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * @author Pavel Karpukhin
 * @since 25.12.14
 */
public class GroupDaoHibernateImplTest {

    private SessionFactory sessionFactory;
    private GroupDao groupDao;

    @Before
    public void setUp() throws IOException {
        Properties properties = new Properties();
        properties.load(getClass().getResourceAsStream("/app.properties"));
        properties.load(getClass().getResourceAsStream("/test.properties"));

        sessionFactory = new Configuration()
                .addAnnotatedClass(Group.class)
                .addAnnotatedClass(User.class)

                .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect")
                .setProperty("hibernate.show_sql", "true")
                .setProperty("hibernate.format_sql", "true")
                .setProperty("hibernate.generate_statistics", "true")
                .setProperty("hibernate.use_sql_comments", "true")
                // Hibernate Cache Properties
                .setProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory")
                .setProperty("hibernate.cache.use_query_cache", "true")
                .setProperty("hibernate.cache.use_second_level_cache", "true")
                .setProperty("hibernate.cache.use_structured_entries", "true")
                // Miscellaneous Properties
                .setProperty("hibernate.current_session_context_class", "thread")
                .setProperty("hibernate.hbm2ddl.auto", "create")
                .setProperty("hibernate.bytecode.use_reflection_optimizer", "true")
                .addProperties(properties)
                .buildSessionFactory();
        groupDao = new GroupDaoHibernateImpl(sessionFactory);


    }

    @Test
    public void testAll() {
        getAll();

        // Creating
        Group group = new Group();
        group.setName("New group");
        create(group);

        getById(group.getId());
    }

    private void getAll() {
        System.out.println("getAll");

        sessionFactory.openSession();
        Session session = sessionFactory.getCurrentSession();

        Transaction tx = session.beginTransaction();

        List<Group> result = groupDao.getAll();
        assertThat(result, is(not(nullValue())));

        tx.commit();

        printStatistics(sessionFactory.getStatistics());
    }

    private void create(Group group) {
        System.out.println("create");

        sessionFactory.openSession();
        Session session = sessionFactory.getCurrentSession();

        Transaction tx = session.beginTransaction();

        groupDao.create(group);

        tx.commit();

        printStatistics(sessionFactory.getStatistics());
    }

    private void getById(int id) {
        System.out.println("getById");

        sessionFactory.openSession();
        Session session = sessionFactory.getCurrentSession();

        Transaction tx = session.beginTransaction();

        Group group = groupDao.getById(id);
        System.out.println("Result: " + group);

        tx.commit();

        printStatistics(sessionFactory.getStatistics());
    }

    private void printStatistics(Statistics statistics) {
        System.out.println("  Hits: " + statistics.getSecondLevelCacheHitCount());
        System.out.println("Misses: " + statistics.getSecondLevelCacheMissCount());
        System.out.println("  Puts: " + statistics.getSecondLevelCachePutCount());
        for (String region : statistics.getSecondLevelCacheRegionNames()) {
            System.out.println("       Region name: " + region);
            SecondLevelCacheStatistics stat = statistics.getSecondLevelCacheStatistics(region);
            System.out.println("              Hits: " + stat.getHitCount());
            System.out.println("            Misses: " + stat.getMissCount());
            System.out.println("              Puts: " + stat.getPutCount());
            System.out.println("Elements in memory: " + stat.getElementCountInMemory());
            System.out.println("  Elements on disk: " + stat.getElementCountOnDisk());
            System.out.println("    Size in memory: " + stat.getSizeInMemory());
        }
    }

}
