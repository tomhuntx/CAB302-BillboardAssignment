package assignment1.billboard.ControlPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.text.MessageFormat;

public class ControlPanelManager {

    public static String current_user;
    public static boolean[] permissions;
    private static ControlPanelUI ui;

    /**
     * Start the control panel application and create the login GUI
     */
    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        ui = new ControlPanelUI();

        // Run the first control panel window
        //SwingUtilities.invokeLater(ui::loginGUI);

        // To skip to and test control panel
        SwingUtilities.invokeLater(ui::ControlPanelHub);
    }

    /**
     * Login when successful
     * Successful if username and password are correct
     * @param userID of current control panel user
     */
    protected static void login(String userID) {
        current_user = userID;
        System.out.println(MessageFormat.format("{0} has logged in successfully...", current_user));

        // Dispose of the current panel and create a new one
        ui.dispose();
        ui = new ControlPanelUI();
        SwingUtilities.invokeLater(ui::ControlPanelHub);

        // Set the location of the UI
        ui.setLocation(new Point(400, 200));
    }

    /**
     * Get all valid permissions from user
     * @return a boolean array of permissions (createBB, listBB, scheduleBB, editPermissions)
     */
    public static boolean[] getPermissions() {

        // would get all valid permissions for the current user
        // four total (createBB, listBB, scheduleBB, editPermissions)

        // currently have one false bool to show disabled control panel hub buttons
        permissions = new boolean[]{true, true, true, false};

        return permissions;
    }
}
