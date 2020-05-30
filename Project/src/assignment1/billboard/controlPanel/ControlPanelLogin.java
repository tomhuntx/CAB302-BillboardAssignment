package assignment1.billboard.controlPanel;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class ControlPanelLogin implements ActionListener {

    // User input into the login GUI
    private JTextField username_input;
    private JPasswordField password_input;

    // Output message for confirmation/testing
    private JLabel outputMessage;

    /**
     * Create and show the Billboard Control Panel Login GUI.
     * Accepts username and password input and ensures credentials are correct before proceeding.
     */
    private void loginGUI()  {
        JFrame.setDefaultLookAndFeelDecorated(true);

        //Create the control panel window.
        JFrame frame = new JFrame("Control Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Title label
        JLabel titleLabel = new JLabel("Please login...", SwingConstants.CENTER);

        // Username
        JLabel usernameLabel = new JLabel();
        usernameLabel.setText("Username:");
        username_input = new JTextField();
        Font largerFont = username_input.getFont().deriveFont(Font.PLAIN, 20f);
        username_input.setFont(largerFont);

        // Password
        JLabel passwordLabel = new JLabel("Password:");
        password_input = new JPasswordField();
        password_input.setFont(largerFont);

        // Submit button
        JButton submitLogin = new JButton("Submit");
        submitLogin.addActionListener(this);
        submitLogin.setPreferredSize(new Dimension(30, 20));

        // Output message for testing purposes
        outputMessage = new JLabel();

        // Create login panel and add components to it
        JPanel loginPanel = new JPanel(new GridLayout(7, 1, 80,10));
        loginPanel.add(titleLabel, BorderLayout.CENTER);
        loginPanel.add(usernameLabel);
        loginPanel.add(usernameLabel);
        loginPanel.add(username_input);
        loginPanel.add(passwordLabel);
        loginPanel.add(password_input);
        loginPanel.add(submitLogin, BorderLayout.CENTER);
        loginPanel.add(outputMessage);

        //Add the login panel to the frame
        frame.getContentPane().add(loginPanel);

        //Display the frame window
        frame.setPreferredSize(new Dimension(200, 300));
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        ControlPanelLogin controlPanel = new ControlPanelLogin();
        controlPanel.loginGUI();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        // Get username and password input
        String username = username_input.getText();
        String password = String.valueOf(password_input.getPassword());

        // Check if this input is correct and display output
        if (username.trim().equals("username") && password.trim().equals("password")) {
            outputMessage.setText("Opening control panel...");
        } else {
            outputMessage.setText("Incorrect username or password.");
        }
    }
}

