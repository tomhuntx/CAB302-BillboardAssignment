package assignment1.billboard.controlPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReadBillboard {

    /**
     * Read the billboard
     */
    private void initialize() {

        JTextArea textArea = new JTextArea();
        textArea.setBounds(56, 73, 346, 100);

        JButton btnGetFile = new JButton("Get file");
        btnGetFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                OpenXML open = new OpenXML();

                try {
                    open.SelectFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                textArea.setText(open.builder.toString());
            }
        });
    }
}