package assignment1.billboard.ControlPanel;

import assignment1.billboard.Server.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.text.MessageFormat;

public class ControlPanelManager {

    private static ControlPanelUI ui;
    private static DBConnection database;

    /**
     * Start the control panel application and create the login GUI
     */
    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        ui = new ControlPanelUI();

        // Connect to the database
        //database = new DBConnection();

        // Run the first control panel window
        SwingUtilities.invokeLater(ui::loginGUI);
    }

    /**
     * Login when successful
     * Successful if username and password are correct
     * @param user of control panel
     */
    protected static void login(User user) {
        System.out.println(MessageFormat.format("{0} has logged in successfully...", user.getUsername()));

        // Dispose of the current panel and create a new one
        ui.dispose();
        ui = new ControlPanelUI();
        SwingUtilities.invokeLater(ui::ControlPanelHub);

        // Set the location of the UI
        ui.setLocation(new Point(400, 200));
    }

    /**
     * Logout of account and create a new login window
     */
    protected static void logout() {
        // Dispose of the current panel and create a new one
        ui.dispose();
        ui = new ControlPanelUI();

        // Run the first control panel window
        SwingUtilities.invokeLater(ui::loginGUI);
    }
}
