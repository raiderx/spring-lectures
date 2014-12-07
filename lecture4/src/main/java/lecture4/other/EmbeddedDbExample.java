package lecture4.other;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

public class EmbeddedDbExample {

    public static void main(String[] args) {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase db = builder.setType(EmbeddedDatabaseType.H2)
                .addScript("my-schema.sql").addScript("my-test-data.sql")
                .build();

        // Выполнить работу с базой данных
        // (EmbeddedDatabase расширяет javax.sql.DataSource)

        db.shutdown();
    }
}
