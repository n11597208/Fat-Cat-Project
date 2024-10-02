import static org.junit.jupiter.api.Assertions.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.cab302project.Model.RegistrationSystem;
import com.example.cab302project.Model.SqliteConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationSystemTest {

    private Connection connection;
    private RegistrationSystem registrationSystem;

    @BeforeEach
    public void setUp() throws SQLException {
        connection = SqliteConnection.connect();
        registrationSystem = new RegistrationSystem();

        try (PreparedStatement statement = connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS users (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "firstName TEXT NOT NULL," +
                        "lastName TEXT NOT NULL," +
                        "email TEXT NOT NULL," +
                        "userName TEXT NOT NULL," +
                        "password TEXT NOT NULL," +
                        "followers INTEGER DEFAULT 0," +
                        "numberOfPosts INTEGER DEFAULT 0" +
                        ")")) {
            statement.execute();
        }
    }

    @Test
    public void testAddUser_Success() throws SQLException {
        registrationSystem.addUser("John", "Doe", "john.doe@example.com", "johndoe", "password");

        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE userName = ?")) {
            statement.setString(1, "johndoe");
            try (ResultSet resultSet = statement.executeQuery()) {
                assertTrue(resultSet.next(), "User should be found in the database");
                assertEquals("John", resultSet.getString("firstName"));
                assertEquals("Doe", resultSet.getString("lastName"));
                assertEquals("john.doe@example.com", resultSet.getString("email"));
                assertEquals("johndoe", resultSet.getString("userName"));
                assertEquals("password", resultSet.getString("password"));
                assertEquals(0, resultSet.getInt("followers"));
                assertEquals(0, resultSet.getInt("numberOfPosts"));
            }
        }
    }

    @Test
    public void testAddUser_Empty() throws SQLException {
        registrationSystem.addUser("", "", "", "", "");

        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE userName = ?")) {
            statement.setString(1, "");
            try (ResultSet resultSet = statement.executeQuery()) {
                assertFalse(resultSet.next(), "Empty post should not be added to the database");
            }
        }
    }

    @Test
    public void testAddUser_DuplicateEmail() throws SQLException {

        registrationSystem.addUser("John", "Doe", "john.doe@example.com", "johndoe", "password");

        registrationSystem.addUser("Jane", "Doe", "john.doe@example.com", "janedoe", "password");

        try (PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM users WHERE email = ?")) {
            statement.setString(1, "john.doe@example.com");
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                int count = resultSet.getInt(1);
                assertEquals(1, count, "Email should be unique.");
            }
        }
    }
}
