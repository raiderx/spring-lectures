package lecture4.example1;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactRowMapper implements RowMapper<Contact> {

    @Override
    public Contact mapRow(ResultSet rs, int rowNum)
            throws SQLException {
        Contact contact = new Contact();
        contact.setId(rs.getInt("ID"));
        contact.setFirstName(rs.getString("FIRST_NAME"));
        contact.setLastName(rs.getString("LAST_NAME"));
        contact.setEmail(rs.getString("EMAIL"));
        contact.setBirthDate(rs.getDate("BIRTH_DATE"));
        return contact;
    }
}
