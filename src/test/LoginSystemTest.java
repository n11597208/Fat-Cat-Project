import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


public class LoginSystemTest {

    private LoginSystem loginSystem;

    @BeforeEach
    public void setUp() {
        // initialize the login system and database before running each test
        loginSystem = new LoginSystem();
        SqliteConnection.setupDatabase();
    }

    @Test
    public void testValidLogin() {
        // test valid login credentials
        boolean result = loginSystem.checkLogin("admin", "1234");
        assertTrue(result, "Login should be successful with valid credentials");
    }


    @Test
    public void testInvalidLogin_WrongPassword() {
        // test wrong password
        boolean result = loginSystem.checkLogin("admin", "wrongpassword");
        assertFalse(result, "Login should fail with incorrect password");
    }

    @Test
    public void testInvalidLogin_WrongUsername() {
        // test wrong username
        boolean result = loginSystem.checkLogin("nonexistent", "1234");
        assertFalse(result, "Login should fail with non-existent username");
    }



}
