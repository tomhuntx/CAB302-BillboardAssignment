package assignment1.billboard.ControlPanel;

import static org.junit.jupiter.api.Assertions.*;
import assignment1.billboard.Server.DBConnection;
import org.junit.jupiter.api.*;
import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

/**
 * ControlPanelTest Class
 * JUnit testing for the control panel login, users, and permissions
 * Covers testing for the ControlPanelManager and the ControlPanelUI classes
 */
public class ControlPanelTest {

    // Control panel UI
    ControlPanelUI testUI;

    /* Test 0: Ensure the control panel can be instantiated - Passes */
    @BeforeAll @Test
    static void constructControlPanel() {
        ControlPanelManager.main(null);
    }

    /* Test 1: Ensure the login menu activates - Passes */
    @BeforeEach @Test
    public void constructGUI() throws InvocationTargetException, InterruptedException {
        SwingUtilities.invokeAndWait(() -> {
            // Open and run the login window
            testUI = new ControlPanelUI();
            SwingUtilities.invokeLater(testUI::loginGUI);

            // Reset current user between each test
            ControlPanelUI.current_user = null;
        });
    }

    /* Test 2: Ensure that the server exists - Passes */
    @Test
    public void connectToServer() { new DBConnection(); }

    @Test
    /* Test 3: Ensure a direct login with a user succeeds - Passes */
    public void login() {
        boolean[] perms = new boolean[] {true, true, true, true};

        // Directly run the login method with a newly created user
        ControlPanelManager.login(new User("username", "password", perms));

        // Ensure the program has a current user
        ControlPanelUI.current_user = ControlPanelUI.admin;
    }

    @Test
    /* Test 4: Ensure a physical login with known username "username" and password "password" succeeds - Passes */
    public void loginCorrectAdmin() throws InvocationTargetException, InterruptedException {
        SwingUtilities.invokeAndWait(() -> {
            // Enter the details and press the login button
            testUI.username_input.setText("username");
            testUI.password_input.setText("password");
            testUI.submitLogin.doClick();

            // The current user should now be "admin"
            assertEquals(ControlPanelUI.current_user, ControlPanelUI.admin);
        });
    }

    @Test
    /* Test 5: Ensure a login known username "dummy" and password "12345" succeeds - Passes */
    public void loginCorrectDummy() throws InvocationTargetException, InterruptedException {
        SwingUtilities.invokeAndWait(() -> {
            // Login as dummy
            testUI.username_input.setText("dummy");
            testUI.password_input.setText("12345");
            testUI.submitLogin.doClick();

            // The current user should now be be "dummy"
            assertEquals(ControlPanelUI.current_user, ControlPanelUI.dummy);
        });
    }

    @Test
    /* Test 6: Ensure a login with an unknown username and password does not succeed - Passes */
    public void loginIncorrect() throws InvocationTargetException, InterruptedException {
        SwingUtilities.invokeAndWait(() -> {
            // Login as an unknown user
            testUI.username_input.setText("hacker");
            testUI.password_input.setText("1234");
            testUI.submitLogin.doClick();

            // Expect the output text to change - indicating unsuccessful login attempt
            assertEquals(" Incorrect username or password. Please try again.", testUI.outputMessage.getText());
        });
    }

    @Test
    /* Test 7: Ensure a dummy has the appropriate permissions - Passes */
    public void dummyPerms() throws InvocationTargetException, InterruptedException {
        SwingUtilities.invokeAndWait(() -> {
            // Login as dummy
            testUI.username_input.setText("dummy");
            testUI.password_input.setText("12345");
            testUI.submitLogin.doClick();

            // Permissions should be true, true, false, false
            assertArrayEquals(ControlPanelUI.current_user.getPermissions(), new boolean[] {true, true, false, false});
        });
    }

    @Test
    /* Test 8: Ensure an admin has the appropriate permissions - Passes */
    public void adminPerms() throws InvocationTargetException, InterruptedException {
        SwingUtilities.invokeAndWait(() -> {
            // Login as dummy
            testUI.username_input.setText("username");
            testUI.password_input.setText("password");
            testUI.submitLogin.doClick();

            // Permissions should be true, true, false, false
            assertArrayEquals(ControlPanelUI.current_user.getPermissions(), new boolean[] {true, true, true, true});
        });
    }

    @Test
    /* Test 9: Ensure a failed login does not create a new user - Passes */
    public void noCurrentUser() throws InvocationTargetException, InterruptedException {
        SwingUtilities.invokeAndWait(() -> {
            // Login as dummy
            testUI.username_input.setText("hackerman");
            testUI.password_input.setText("password");
            testUI.submitLogin.doClick();

            // Should be no current user
            assertNull(ControlPanelUI.current_user);
        });
    }
}
