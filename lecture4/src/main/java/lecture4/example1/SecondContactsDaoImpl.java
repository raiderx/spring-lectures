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
public class SecondContactsDaoImpl implements ContactsDao {

    private final RowMapper<Contact> rowMapper = new ContactRowMapper();

    private NamedParameterJdbcTemplate jdbcTemplate;

    public SecondContactsDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
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
        SqlParameterSource params = new MapSqlParameterSource("id", id);
        return jdbcTemplate.queryForObject(
                "SELECT ID, FIRST_NAME, LAST_NAME, EMAIL, BIRTH_DATE " +
                "FROM CONTACTS WHERE ID = :id", params, rowMapper);
    }

    @Override
    public void create(Contact contact) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", contact.getId());
        params.addValue("firstName", contact.getFirstName());
        params.addValue("lastName", contact.getLastName());
        params.addValue("email", contact.getEmail(), Types.VARCHAR);
        params.addValue("birthDate", contact.getBirthDate(), Types.DATE);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rows = jdbcTemplate.update(
                "INSERT INTO CONTACTS (FIRST_NAME, LAST_NAME, EMAIL, BIRTH_DATE)" +
                "VALUES (:firstName, :lastName, :email, :birthDate)",
                params, keyHolder);
        if (rows != 1) {
            throw new RuntimeException("Expected 1 but got " + rows);
        }
        contact.setId(keyHolder.getKey().intValue());
    }

    @Override
    public void update(Contact contact) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", contact.getId());
        params.addValue("firstName", contact.getFirstName());
        params.addValue("lastName", contact.getLastName());
        params.addValue("email", contact.getEmail(), Types.VARCHAR);
        params.addValue("birthDate", contact.getBirthDate(), Types.DATE);
        int rows = jdbcTemplate.update(
                "UPDATE CONTACTS " +
                "SET FIRST_NAME = :firstName, LAST_NAME = :lastName, " +
                    "EMAIL = :email, BIRTH_DATE = :birthDate " +
                "WHERE ID = :id", params);
        if (rows != 1) {
            throw new RuntimeException("Expected 1 but got " + rows);
        }
    }

    @Override
    public void removeById(int id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        int rows = jdbcTemplate.update(
                "DELETE FROM CONTACTS WHERE ID = :id", params);
        if (rows != 1) {
            throw new RuntimeException("Expected 1 but got " + rows);
        }
    }
}
