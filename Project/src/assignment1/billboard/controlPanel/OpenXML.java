package assignment1.billboard.ControlPanel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class OpenXML {

    // Opens a choose file dialogue from the user's home directory
    JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

    // Builds strings of billboard file
    StringBuilder builder = new StringBuilder();

    // Scanner of input file
    Scanner input;

    // Billboard file's title
    String filepath = null;

    /**
     * Opens a file chooser window for the CreateBillboards window
     * Accepts .xml files selected by the user
     * Catches when users select a file that does not exist or if they cancel the search
     */

    public void SelectFile() {
        // Set up the file chooser
        chooser.setDialogTitle("Open an .xml billboard file.");
        // Search for xml files only
        FileNameExtensionFilter xml_filter = new FileNameExtensionFilter(
                ".xml Files", "xml");
        chooser.setFileFilter(xml_filter);

        // Opens the file dialog
        int dialog = chooser.showOpenDialog(null);

        // Scan a file if "Open" is selected
        if (dialog == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();

            try {
                input = new Scanner(file);

                while(input.hasNext()) {
                    // Use a regex to replace code
                    builder.append(input.nextLine().replaceAll("<[^>]+>", ""));
                    builder.append("\n");
                }

                // Record the file's path
                filepath = chooser.getSelectedFile().getName();

                // Close scanner when finished
                input.close();
            }
            // If wrong file chosen, re-opens select file
            catch (FileNotFoundException fnf) {
                JOptionPane.showMessageDialog(null,
                        "Could not find the file specified.",
                        "Attention", JOptionPane.WARNING_MESSAGE);
                SelectFile();
                filepath = null;
            }
            // If unexpected problem, closes search window
            catch (Exception other) {
                JOptionPane.showMessageDialog(null,
                        "There was a problem. Please try again",
                        "Attention", JOptionPane.ERROR_MESSAGE);
                filepath = null;
            }
        }
        // Otherwise, the search has been cancelled
        else {
            System.out.println("User has cancelled the search.");
            filepath = null;
        }
    }

    public String getName() {
        return filepath;
    }
}