package assignment1.billboard.Viewer;

import assignment1.billboard.Server.DBConnection;

import javax.swing.SwingUtilities;

public class DisplayBillboard {

    /**
     * Create the GUI.
     */
    private static void createAndShowGUI() {
        Display billboard = new Display();

        //new BillboardUI(new DBConnection());
    }

    /**
     * Main function of viewer package
     * @param args
     */
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
