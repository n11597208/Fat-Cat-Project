import com.example.cab302project.LoginSystem;
import com.example.cab302project.SqliteConnection;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class LoginSystemTesting {
    private LoginSystem loginSystem;


    @BeforeEach
    public void SetUp() {
        // initialise login system
        loginSystem = new LoginSystem();
        SqliteConnection.setupDatabase();
    }

    @Test
    public void TestValidLogin() {
        // Test valid login credentials
        boolean result = loginSystem.checkLogin("admin", "1234");
        assertTrue(result, "Login should be successful with valid credentials");
    }

    @Test
    public void TestWrongPassword() {
        // Test incorrect password
        boolean result = loginSystem.checkLogin("admin", "wrongpassword");
        assertFalse(result, "Login should fail with incorrect password");
    }

    @Test
    public void TestWrongUsername() {
        // Test wrong username
        boolean result = loginSystem.checkLogin("nonexistent", "1234");
        assertFalse(result, "Login should fail with non-existent username");
    }

}
