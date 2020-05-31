package assignment1.billboard.viewer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public class Display {
    private JFrame mainFrame;
    private JPanel displayPanel;
    private JLabel messageLabel;

    private Graphics2D graphics;
    private BufferedImage image;

    public Display(){
        initBillboard();
    }

    public static void main(String[] args){
        Display display = new Display();
        display.showDisplay();
    }

    public static void initBillboard() {
        JFrame mainFrame = new JFrame("Billboard");
        mainFrame.setExtendedState(mainFrame.MAXIMIZED_BOTH);
        mainFrame.setUndecorated(true);
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });
        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new FlowLayout());
        mainFrame.add(displayPanel);
    }

    private void showDisplay(){
        JPanel newPanel = new JPanel();
        newPanel.setBackground(Color.white);
        newPanel.setLayout(new FlowLayout());
        updateBillboard();
        newPanel.add(messageLabel);
        displayPanel.add(newPanel);
        mainFrame.setVisible(true);
    }

    public void updateBillboard() {
        JLabel messageLabel = new JLabel();
        messageLabel.setText("Billboard");
    }

}
