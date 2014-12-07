package lecture4.other;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

public class EmbeddedTestExample {

    private EmbeddedDatabase db;

    @Before
    public void setUp() {
        // Создает в памяти экземпляр HSQL базы данных и
        // наполняет ее данными из скриптов
        // classpath:schema.sql и classpath:data.sql
        db = new EmbeddedDatabaseBuilder().addDefaultScripts()
                .build();
    }

    @Test
    public void testDataAccess() {
        JdbcTemplate template = new JdbcTemplate(db);
        template.query(...);
    }

    @After
    public void tearDown() {
        db.shutdown();
    }
}
