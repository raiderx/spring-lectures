package lecture4.example1;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ContactsDaoJdbcImpl implements ContactsDao {

    private DataSource dataSource;

    public ContactsDaoJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public int getContactsCount() {
        final String sql = "SELECT COUNT(*) FROM CONTACTS";
        int count = 0;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            Connection connection = dataSource.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                count = resultSet.getInt(1);
                if (resultSet.next()) {
                    throw new RuntimeException("Expected 1 row but got more");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error occurred while executing SQL query", e);
        } finally {
            close(statement, resultSet);
        }
        return count;
    }

    @Override
    public List<Contact> getContacts() {
        final String sql =
                "SELECT ID, FIRST_NAME, LAST_NAME, EMAIL, " +
                "BIRTH_DATE FROM CONTACTS";
        List<Contact> result = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            Connection connection = dataSource.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                result.add(mapRow(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error occurred while executing SQL query", e);
        } finally {
            close(statement, resultSet);
        }
        return result;
    }

    @Override
    public Contact getContactById(int id) {
        final String sql =
                "SELECT ID, FIRST_NAME, LAST_NAME, EMAIL, " +
                "BIRTH_DATE FROM CONTACTS WHERE ID = ?";
        Contact contact = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            Connection connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                contact = mapRow(resultSet);
                if (resultSet.next()) {
                    throw new RuntimeException("Expected 1 row but got more");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error occurred while executing SQL query", e);
        } finally {
            close(statement, resultSet);
        }
        return contact;
    }

    @Override
    public void create(Contact contact) {
        final String sql =
                "INSERT INTO CONTACTS (FIRST_NAME, LAST_NAME, " +
                "EMAIL, BIRTH_DATE) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            Connection connection = dataSource.getConnection();
            statement = connection.prepareStatement(
                    sql, Statement.RETURN_GENERATED_KEYS);
            int i = 0;
            statement.setString(++i, contact.getFirstName());
            statement.setString(++i, contact.getLastName());
            statement.setString(++i, contact.getEmail());
            statement.setDate(++i, new Date(contact.getBirthDate().getTime()));
            int rows = statement.executeUpdate();
            if (rows != 1) {
                throw new RuntimeException("Expected 1 but got " + rows);
            }
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                contact.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error occurred while executing SQL query", e);
        } finally {
            close(statement, resultSet);
        }
    }

    @Override
    public void update(Contact contact) {
        final String sql =
                "UPDATE CONTACTS SET FIRST_NAME = ?, LAST_NAME = ?, " +
                "EMAIL = ?, BIRTH_DATE = ? WHERE ID = ?";
        PreparedStatement statement = null;
        try {
            Connection connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            int i = 0;
            statement.setString(++i, contact.getFirstName());
            statement.setString(++i, contact.getLastName());
            statement.setString(++i, contact.getEmail());
            statement.setDate(++i, new Date(contact.getBirthDate().getTime()));
            statement.setInt(++i, contact.getId());
            int rows = statement.executeUpdate();
            if (rows != 1) {
                throw new RuntimeException("Expected 1 but got " + rows);
            }
        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error occurred while executing SQL query", e);
        } finally {
            close(statement, null);
        }
    }

    @Override
    public void removeById(int id) {
        final String sql =
                "DELETE FROM CONTACTS WHERE ID = ?";
        PreparedStatement statement = null;
        try {
            Connection connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            int rows = statement.executeUpdate();
            if (rows != 1) {
                throw new RuntimeException("Expected 1 but got " + rows);
            }
        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error occurred while executing SQL query", e);
        } finally {
            close(statement, null);
        }
    }

    private static Contact mapRow(ResultSet rs) throws SQLException {
        Contact contact = new Contact();
        contact.setId(rs.getInt("ID"));
        contact.setFirstName(rs.getString("FIRST_NAME"));
        contact.setLastName(rs.getString("LAST_NAME"));
        contact.setEmail(rs.getString("EMAIL"));
        contact.setBirthDate(rs.getDate("BIRTH_DATE"));
        return contact;
    }

    private static void close(Statement statement, ResultSet resultSet) {
        try {
            if (statement != null) {
                statement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error occurred while closing statement", e);
        }
    }
}
