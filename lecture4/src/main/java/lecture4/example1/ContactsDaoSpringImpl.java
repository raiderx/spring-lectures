package lecture4.example1;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class ContactsDaoSpringImpl implements ContactsDao {

    private final RowMapper<Contact> rowMapper =
            new ContactRowMapper();

    private JdbcTemplate jdbcTemplate;

    public ContactsDaoSpringImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int getContactsCount() {
        final String sql = "SELECT COUNT(*) FROM CONTACTS";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public List<Contact> getContacts() {
        final String sql =
                "SELECT ID, FIRST_NAME, LAST_NAME, EMAIL, " +
                "BIRTH_DATE FROM CONTACTS";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Contact getContactById(int id) {
        final String sql =
                "SELECT ID, FIRST_NAME, LAST_NAME, EMAIL, " +
                "BIRTH_DATE FROM CONTACTS WHERE ID = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    @Override
    public void create(final Contact contact) {
        final String sql =
                "INSERT INTO CONTACTS (FIRST_NAME, LAST_NAME, " +
                "EMAIL, BIRTH_DATE) VALUES (?, ?, ?, ?)";
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con)
                    throws SQLException {
                PreparedStatement statement = con.prepareStatement(
                        sql, Statement.RETURN_GENERATED_KEYS);
                int i = 0;
                statement.setString(++i, contact.getFirstName());
                statement.setString(++i, contact.getLastName());
                statement.setString(++i, contact.getEmail());
                statement.setDate(++i, new Date(contact.getBirthDate().getTime()));
                return statement;
            }
        };
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rows = jdbcTemplate.update(psc, keyHolder);
        if (rows != 1) {
            throw new RuntimeException("Expected 1 but got " + rows);
        }
        contact.setId(keyHolder.getKey().intValue());
    }

    @Override
    public void update(Contact contact) {
        final String sql =
                "UPDATE CONTACTS SET FIRST_NAME = ?, LAST_NAME = ?, " +
                "EMAIL = ?, BIRTH_DATE = ? WHERE ID = ?";
        Object[] values = new Object[] {
                contact.getFirstName(),
                contact.getLastName(),
                contact.getEmail(),
                contact.getBirthDate(),
                contact.getId()
        };
        int rows = jdbcTemplate.update(sql, values);
        if (rows != 1) {
            throw new RuntimeException("Expected 1 but got " + rows);
        }
    }

    @Override
    public void removeById(int id) {
        final String sql =
                "DELETE FROM CONTACTS WHERE ID = ?";
        int rows = jdbcTemplate.update(sql, id);
        if (rows != 1) {
            throw new RuntimeException("Expected 1 but got " + rows);
        }
    }
}
