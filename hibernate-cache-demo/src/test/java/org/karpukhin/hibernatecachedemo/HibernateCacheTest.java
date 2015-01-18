package org.karpukhin.hibernatecachedemo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.stat.SecondLevelCacheStatistics;
import org.hibernate.stat.Statistics;
import org.junit.Before;
import org.junit.Test;
import org.karpukhin.hibernatecachedemo.model.Group;
import org.karpukhin.hibernatecachedemo.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * @author Pavel Karpukhin
 * @since 18.01.15
 */
public class HibernateCacheTest {

    private static final Logger logger = LoggerFactory.getLogger(HibernateCacheTest.class);

    private SessionFactory sessionFactory;

    @Before
    public void setUp() throws IOException {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        properties.setProperty("hibernate.show_sql", "true");
        //properties.setProperty("hibernate.format_sql", "true");
        properties.setProperty("hibernate.generate_statistics", "true");
        //properties.setProperty("hibernate.use_sql_comments", "true");
        // Hibernate Cache Properties
        properties.setProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory");
        properties.setProperty("hibernate.cache.use_query_cache", "true");
        properties.setProperty("hibernate.cache.use_second_level_cache", "true");
        properties.setProperty("hibernate.cache.use_structured_entries", "true");
        // Miscellaneous Properties
        properties.setProperty("hibernate.current_session_context_class", "thread");
        properties.setProperty("hibernate.hbm2ddl.auto", "create");
        properties.setProperty("hibernate.bytecode.use_reflection_optimizer", "true");
        properties.load(getClass().getResourceAsStream("/app.properties"));
        properties.load(getClass().getResourceAsStream("/test.properties"));

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(properties)
                .build();
        sessionFactory = new Configuration()
                .addAnnotatedClass(Group.class)
                .addAnnotatedClass(User.class)
                .addProperties(properties)
                .buildSessionFactory(serviceRegistry);
    }

    @Test
    public void testAll() {
        getAll();

        // Creating
        User user = new User();
        user.setFirstName("Michael");
        user.setLastName("Jordan");

        Group group = new Group();
        group.setName("New group");
        group.addUser(user);

        create(group);

        getById(group.getId());

        getAll();

        //Group anotherGroup = getById(group.getId());

        User anotherUser = new User();
        anotherUser.setFirstName("Patrick");
        anotherUser.setLastName("Ewing");
        anotherUser.setGroup(group);

        group.addUser(anotherUser);

        create(anotherUser);

        //update(anotherGroup);

        getAll();

        getById(group.getId());
    }

    private void getAll() {
        logger.info("\n\n====================== GetAll ======================");

        sessionFactory.openSession();
        Session session = sessionFactory.getCurrentSession();

        Transaction tx = session.beginTransaction();

        List<Group> result = session.getNamedQuery("query.group.get.all").list();
        assertThat(result, is(not(nullValue())));

        String message = "\nResult:";
        for (Group group : result) {
            message += "\n" + groupToString(group);
        }
        logger.info(message);

        tx.commit();

        printStatistics(sessionFactory.getStatistics());
    }

    private void create(Group group) {
        logger.info("\n\n====================== Create group ======================");

        sessionFactory.openSession();
        Session session = sessionFactory.getCurrentSession();

        Transaction tx = session.beginTransaction();

        session.save(group);

        tx.commit();

        printStatistics(sessionFactory.getStatistics());
    }

    private void create(User user) {
        logger.info("\n\n====================== Create user ======================");

        sessionFactory.openSession();
        Session session = sessionFactory.getCurrentSession();

        Transaction tx = session.beginTransaction();

        session.save(user);

        tx.commit();

        printStatistics(sessionFactory.getStatistics());
    }

    private void update(Group group) {
        logger.info("\n\n====================== Update ======================");

        sessionFactory.openSession();
        Session session = sessionFactory.getCurrentSession();

        Transaction tx = session.beginTransaction();

        session.update(group);

        tx.commit();

        printStatistics(sessionFactory.getStatistics());
    }

    private Group getById(int id) {
        logger.info("\n\n====================== GetById ======================");

        sessionFactory.openSession();
        Session session = sessionFactory.getCurrentSession();

        Transaction tx = session.beginTransaction();

        Group group = (Group)session.get(Group.class, id);
        logger.info("\nResult:\n" + groupToString(group));

        tx.commit();

        printStatistics(sessionFactory.getStatistics());

        return group;
    }

    private static String groupToString(Group group) {
        String message = group.toString();
        for (Iterator<User> it = group.getUsers().iterator(); it.hasNext(); ) {
            message += "\n" + it.next();
        }
        return message;
    }

    private static void printStatistics(Statistics statistics) {
        String message = "Statistics:" +
                "\n     Hits: " + statistics.getSecondLevelCacheHitCount() +
                "\n   Misses: " + statistics.getSecondLevelCacheMissCount() +
                "\n     Puts: " + statistics.getSecondLevelCachePutCount();
        String format =
                "\nRegion name: %s" +
                "\n     Hits: %5d      Elements in memory: %5d" +
                "\n   Misses: %5d        Elements on disk: %5d" +
                "\n     Puts: %5d          Size in memory: %5d"/* +
                "\n  Entries: %5d"*/;
        String[] regionNames = statistics.getSecondLevelCacheRegionNames();
        Arrays.sort(regionNames);
        for (String regionNAme : regionNames) {
            SecondLevelCacheStatistics stat = statistics.getSecondLevelCacheStatistics(regionNAme);
            message += String.format(format, regionNAme,
                    stat.getHitCount(), stat.getElementCountInMemory(),
                    stat.getMissCount(), stat.getElementCountOnDisk(),
                    stat.getPutCount(), stat.getSizeInMemory()/*,
                    stat.getEntries().size()*/);
        }
        logger.info(message);
        statistics.clear();
    }
}
