package assignment1.billboard.controlPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.text.MessageFormat;

public class ControlPanelManager {

    public static String current_user;
    public static boolean[] permissions;
    private static ControlPanelUI ui;

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
     * ...
     */
    protected static void login(String userID) {
        current_user = userID;
        System.out.println(MessageFormat.format("{0} has logged in successfully...", current_user));

        // Dispose of the current panel and create a new one
        ui.dispose();
        ui = new ControlPanelUI();
        ui.ControlPanelHub();

        // Set the location of the UI
        ui.setLocation(new Point(400, 200));
    }

    public static boolean[] getPermissions() {

        // would get all valid permissions for the current user
        // four total (createBB, listBB, scheduleBB, editPermissions)

        // currently have one false bool to show disabled control panel hub buttons
        permissions = new boolean[]{true, true, true, false};

        return permissions;
    }
}
