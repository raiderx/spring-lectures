package lecture4.example1;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Pavel Karpukhin
 * @since 10.12.14
 */
public class ContactsDaoAnotherSpringImpl implements ContactsDao {

    private final RowMapper<Contact> rowMapper =
            new ContactRowMapper();

    private NamedParameterJdbcTemplate jdbcTemplate;

    public ContactsDaoAnotherSpringImpl(
            NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int getContactsCount() {
        final String sql = "SELECT COUNT(*) FROM CONTACTS";
        Map<String, Object> params = new HashMap<>();
        return jdbcTemplate.queryForObject(sql, params, Integer.class);
    }

    @Override
    public List<Contact> getContacts() {
        final String sql =
                "SELECT ID, FIRST_NAME, LAST_NAME, EMAIL, BIRTH_DATE " +
                "FROM CONTACTS";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Contact getContactById(int id) {
        final String sql =
                "SELECT ID, FIRST_NAME, LAST_NAME, EMAIL, BIRTH_DATE " +
                "FROM CONTACTS WHERE ID = :id";
        SqlParameterSource params = new MapSqlParameterSource("id", id);
        return jdbcTemplate.queryForObject(sql, params, rowMapper);
    }

    @Override
    public void create(Contact contact) {
        final String sql =
                "INSERT INTO CONTACTS (FIRST_NAME, LAST_NAME, EMAIL, BIRTH_DATE)" +
                "VALUES (:firstName, :lastName, :email, :birthDate)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("firstName", contact.getFirstName());
        params.addValue("lastName", contact.getLastName());
        params.addValue("email", contact.getEmail(), Types.VARCHAR);
        params.addValue("birthDate", contact.getBirthDate(), Types.DATE);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rows = jdbcTemplate.update(sql, params, keyHolder);
        if (rows != 1) {
            throw new RuntimeException("Expected 1 but got " + rows);
        }
        contact.setId(keyHolder.getKey().intValue());
    }

    @Override
    public void update(Contact contact) {
        final String sql =
                "UPDATE CONTACTS " +
                "SET FIRST_NAME = :firstName, LAST_NAME = :lastName, " +
                "EMAIL = :email, BIRTH_DATE = :birthDate " +
                "WHERE ID = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", contact.getId());
        params.put("firstName", contact.getFirstName());
        params.put("lastName", contact.getLastName());
        params.put("email", contact.getEmail());
        params.put("birthDate", contact.getBirthDate());
        int rows = jdbcTemplate.update(sql, params);
        if (rows != 1) {
            throw new RuntimeException("Expected 1 but got " + rows);
        }
    }

    @Override
    public void removeById(int id) {
        final String sql = "DELETE FROM CONTACTS WHERE ID = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        int rows = jdbcTemplate.update(sql, params);
        if (rows != 1) {
            throw new RuntimeException("Expected 1 but got " + rows);
        }
    }
}
