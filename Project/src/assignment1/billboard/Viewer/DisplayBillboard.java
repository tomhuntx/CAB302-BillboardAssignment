package assignment1.billboard.Viewer;

/**
 * Main Class DisplayBillboard
 * Creates threads within thread to maintain the program
 * main thread creates new thread to cycle through billboards
 * without exiting program
 */
public class DisplayBillboard {
    /*
     * Number of cycles through billboards
     */
    private static final int MAX = 15;
    /*
     * Delay in milliseconds between switching
     */
    private static final int DELAY = 15000;

    /*
     * Display class object
     */
    private static Display display;

    /**
     * Create the GUI.
     */
    private static void createAndShowGUI() {
        display = new Display();
        for (int i = 0; i < MAX; i++){
            display.runDisplay();
            display.delayDisplay(DELAY);
        }
    }

    /**
     * Main function of viewer package
     * @param args string arguments of main function
     */
    public static void main(String[] args) {
        createAndShowGUI();
    }
}
