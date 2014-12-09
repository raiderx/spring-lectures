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
import java.sql.Types;
import java.util.List;

public class ContactsDaoImpl implements ContactsDao {

    private final RowMapper<Contact> rowMapper = new ContactRowMapper();

    private JdbcTemplate jdbcTemplate;

    public ContactsDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Contact> getContacts() {
        return jdbcTemplate.query(
                "SELECT ID, FIRST_NAME, LAST_NAME, EMAIL, BIRTH_DATE " +
                "FROM CONTACTS", rowMapper);
    }

    @Override
    public Contact getContactById(int id) {
        return jdbcTemplate.queryForObject(
                "SELECT ID, FIRST_NAME, LAST_NAME, EMAIL, BIRTH_DATE " +
                        "FROM CONTACTS WHERE ID = ?", rowMapper, id);
    }

    @Override
    public void create(final Contact contact) {
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con)
                    throws SQLException {
                PreparedStatement statement = con.prepareStatement(
                        "INSERT INTO CONTACTS (FIRST_NAME, LAST_NAME, EMAIL, BIRTH_DATE) " +
                                "VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
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
        Object[] values = new Object[] {
                contact.getFirstName(),
                contact.getLastName(),
                contact.getEmail(),
                contact.getBirthDate(),
                contact.getId()
        };
        int[] types = new int[] {
                Types.VARCHAR,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.DATE,
                Types.INTEGER
        };
        int rows = jdbcTemplate.update(
                "UPDATE CONTACTS " +
                "SET FIRST_NAME = ?, LAST_NAME = ?, EMAIL = ?, BIRTH_DATE = ? " +
                "WHERE ID = ?", values, types);
        if (rows != 1) {
            throw new RuntimeException("Expected 1 but got " + rows);
        }
    }

    @Override
    public void removeById(int id) {
        int rows = jdbcTemplate.update(
                "DELETE FROM CONTACTS " +
                "WHERE ID = ?", id);
        if (rows != 1) {
            throw new RuntimeException("Expected 1 but got " + rows);
        }
    }
}
