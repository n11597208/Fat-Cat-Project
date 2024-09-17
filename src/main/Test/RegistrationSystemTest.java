import static org.junit.jupiter.api.Assertions.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.cab302project.RegistrationSystem;
import com.example.cab302project.SqliteConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationSystemTest {

    private Connection connection;
    private RegistrationSystem registrationSystem;

    @BeforeEach
    public void setUp() throws SQLException {
        // Create an in-memory database
        connection = SqliteConnection.connect(); // Ensure this connects to an in-memory database

        // Create the 'users' table for testing
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

        registrationSystem = new RegistrationSystem();
    }

    @Test
    public void testAddUser_Success() throws SQLException {
        // Add a user
        registrationSystem.addUser("John", "Doe", "john.doe@example.com", "johndoe", "password");

        // Verify the user was added
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE userName = ?")) {
            statement.setString(1, "johndoe");
            ResultSet resultSet = statement.executeQuery();

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

    @Test
    public void testAddUser_DuplicateEmail() throws SQLException {
        // Add a user
        registrationSystem.addUser("John", "Doe", "john.doe@example.com", "johndoe", "password");

        // Try to add another user with the same email
        registrationSystem.addUser("Jane", "Doe", "john.doe@example.com", "janedoe", "password");

        // Verify the first user still exists and the second one was not added
        try (PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM users WHERE email = ?")) {
            statement.setString(1, "john.doe@example.com");
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            assertEquals(1, count, "Email should be unique.");
        }
    }
}
