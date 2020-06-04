package assignment1;

import assignment1.billboard.ControlPanel.ControlPanelManager;
import assignment1.billboard.Server.DBConnection;
import assignment1.billboard.Viewer.Display;

public class Main {

    private Display billboardPanel = new Display();
    private ControlPanelManager controlPanel = new ControlPanelManager();
    private DBConnection database = new DBConnection();

    public static void main(String[] args) {
	// write your code here

        System.out.println("Test");
    }
}
