package assignment1.billboard.controlPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class ControlPanelUI extends JFrame implements ActionListener {

    // User input into the login GUI
    private JTextField username_input;
    private JPasswordField password_input;

    // Output message for confirmation/testing
    private JLabel outputMessage;

    /**
     * Display the Control Panel Login GUI
     * Accepts username and password input and ensures credentials are correct before proceeding.
     */
    public void loginGUI()  {
        //Create the control panel window.
        setTitle("Control Panel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
        submitLogin.setPreferredSize(new Dimension(30, 20));

        // Output message
        outputMessage = new JLabel();

        // Create login panel and add components to it
        JPanel loginPanel = new JPanel(new GridLayout(7, 1, 80,10));
        loginPanel.add(titleLabel, BorderLayout.CENTER);
        loginPanel.add(usernameLabel);
        loginPanel.add(username_input);
        loginPanel.add(passwordLabel);
        loginPanel.add(password_input);
        loginPanel.add(submitLogin, BorderLayout.CENTER);
        loginPanel.add(outputMessage);

        //Add the login panel to the frame
        getContentPane().add(loginPanel);

        //Display the frame window
        setPreferredSize(new Dimension(300, 400));
        setLocation(new Point(400, 200));
        pack();
        setVisible(true);
    }

    // Login submit button
    JButton submitLogin = new JButton( new AbstractAction("Submit") {
        @Override
        public void actionPerformed(ActionEvent e) {

            // Get username and password input
            String username = username_input.getText();
            String password = String.valueOf(password_input.getPassword());

            // Check if this input is correct and display output
            if (username.trim().equals("username") && password.trim().equals("password")) {
                outputMessage.setText("Opening control panel...");
                ControlPanelManager.login(username);
            } else {
                outputMessage.setText("Incorrect username or password.");
            }
        }
    });

    /**
     * Display the Control Panel Hub GUI
     * ...
     */
    public void ControlPanelHub() {
        // Initialise control panel hub
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Control Panel");

        // Create hub container and layout
        JPanel hubContainer = new JPanel();
        FlowLayout layout = new FlowLayout();
        layout.setHgap(0);
        layout.setVgap(10);
        hubContainer.setLayout(layout);

        // Title label
        JLabel titleLabel = new JLabel("Control Panel Hub", JLabel.CENTER );
        titleLabel.setPreferredSize(new Dimension(300,60));
        Font largerFont = titleLabel.getFont().deriveFont(Font.PLAIN, 25f);
        titleLabel.setFont(largerFont);

        // Options panel for buttons
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(2,2,10,10));

        // Add buttons to options panel
        Dimension optionsButtonSize = new Dimension(200,120);
        ArrayList<JButton> buttonList = new ArrayList<>();
        buttonList.add(createBB);
        buttonList.add(listBB);
        buttonList.add(scheduleBB);
        buttonList.add(editPermissions);

        // Add buttons to options panel
        boolean[] perms = ControlPanelManager.getPermissions();
        for (int i = 0; i < buttonList.size(); i++) {
            JButton button = buttonList.get(i);
            button.setPreferredSize(optionsButtonSize);
            optionsPanel.add(button);

            // Disable button if lacks permissions
            if (perms[i] == false) {
                button.setEnabled(false);
                button.setToolTipText("You do not have permission to " + button.getText());
            }
        }

        // Add components to frame
        hubContainer.add(titleLabel);
        hubContainer.add(optionsPanel);
        getContentPane().add(hubContainer);

        // Display the window
        setPreferredSize(new Dimension(600, 400));
        setResizable(false);
        setLocation(new Point(400, 200));
        pack();
        setVisible(true);
    }

    // Hub buttons to open new GUI sections
    JButton createBB = new JButton( new AbstractAction("Create Billboard") {
        @Override
        public void actionPerformed( ActionEvent e ) {
            // add Action
        }
    });
    JButton listBB = new JButton( new AbstractAction("List Billboard") {
        @Override
        public void actionPerformed( ActionEvent e ) {
            // add Action
        }
    });
    JButton scheduleBB = new JButton( new AbstractAction("Schedule Billboard") {
        @Override
        public void actionPerformed( ActionEvent e ) {
            // add Action
        }
    });
    JButton editPermissions = new JButton( new AbstractAction("Edit Permissions") {
        @Override
        public void actionPerformed( ActionEvent e ) {
            // add Action
        }
    });

    /**
     * Display the Create Billboards section
     * ...
     */
    public void CreateBillboards() {
    }

    /**
     * Display the List Billboards section
     * ...
     */
    public void ListBillboards() {
    }

    /**
     * Display the Schedule Billboards section
     * ...
     */
    public void ScheduleBillboards() {
    }

    /**
     * Display the Edit Users section
     * ...
     */
    public void EditUsers() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}