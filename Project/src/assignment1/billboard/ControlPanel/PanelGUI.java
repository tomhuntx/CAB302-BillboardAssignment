package assignment1.billboard.controlPanel;

import javax.swing.*;
import java.awt.*;

public class PanelGUI {
    private JFrame frame;
    private JPanel controls;
    private Graphics2D graphics;
    private JButton button;


    public static void initPanel() {
        JFrame frame = new JFrame("Billboard");
        frame.setSize(frame.WIDTH, frame.HEIGHT);
        JFrame.setDefaultLookAndFeelDecorated(true);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void initButtons(JPanel controls) {
        button = new JButton("Update");
        //button.addActionListener(this);
        controls.add(button);
        controls.setLayout(new GridLayout(0,1));
    }
}
