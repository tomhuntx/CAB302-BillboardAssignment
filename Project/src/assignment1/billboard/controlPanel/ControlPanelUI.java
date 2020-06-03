package assignment1.billboard.controlPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ControlPanelUI extends JFrame implements ActionListener {

    // User input into the login GUI
    private JTextField username_input;
    private JPasswordField password_input;

    // Output message for confirmation/testing
    private JLabel outputMessage;

    // Main panels of each control panel section
    JPanel hubContainer;
    JPanel CBContainer;
    JPanel LBContainer;
    JPanel SBContainer;
    JPanel EUContainer;
    JPanel currentContainer;

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
        Font largerFont = titleLabel.getFont().deriveFont(Font.PLAIN, 20f);
        titleLabel.setFont(largerFont);

        // Username
        JPanel usernamePanel = new JPanel();
        JLabel usernameLabel = new JLabel();
        usernameLabel.setText("Username: ");
        username_input = new JTextField();
        username_input.setFont(largerFont);
        username_input.setPreferredSize(new Dimension(170,30));
        usernamePanel.add(usernameLabel);
        usernamePanel.add(username_input);

        // Password
        JPanel passwordPanel = new JPanel();
        JLabel passwordLabel = new JLabel("Password: ");
        password_input = new JPasswordField();
        password_input.setFont(largerFont);
        password_input.setPreferredSize(new Dimension(170,30));
        passwordPanel.add(passwordLabel);
        passwordPanel.add(password_input);

        // Submit button panel
        JPanel submitButtonPanel = new JPanel();
        submitLogin.setPreferredSize(new Dimension(150,40));
        submitButtonPanel.add(submitLogin, BorderLayout.CENTER);

        // Output message
        outputMessage = new JLabel();

        // Create login panel and add components to it
        JPanel loginPanel = new JPanel(new GridLayout(5, 1, 0,10));
        loginPanel.add(titleLabel, BorderLayout.CENTER);
        loginPanel.add(usernamePanel);
        loginPanel.add(passwordPanel);
        loginPanel.add(submitButtonPanel);
        loginPanel.add(outputMessage, BorderLayout.CENTER);

        //Add the login panel to the frame
        getContentPane().add(loginPanel);

        //Display the frame window
        setPreferredSize(new Dimension(300, 300));
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
                outputMessage.setText(" Incorrect username or password. Please try again.");
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
        hubContainer = new JPanel();
        FlowLayout layout = new FlowLayout();
        layout.setHgap(0);
        layout.setVgap(10);
        hubContainer.setLayout(layout);

        // Title label
        JLabel titleLabel = new JLabel("Control Panel Hub", JLabel.CENTER);
        titleLabel.setPreferredSize(new Dimension(300,100));
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
            if (!perms[i]) {
                button.setEnabled(false);
                button.setToolTipText("You do not have permission to " + button.getText());
            }
        }

        // Add components to frame
        hubContainer.add(titleLabel);
        hubContainer.add(optionsPanel);
        getContentPane().add(hubContainer);

        // Disable resizability
        setResizable(false);

        // Display the window
        setPreferredSize(new Dimension(600, 450));
        pack();
        setVisible(true);

        // Set the current complete container
        currentContainer = hubContainer;
    }

    // Hub buttons to open new GUI sections
    JButton createBB = new JButton( new AbstractAction("Create Billboard") {
        @Override
        public void actionPerformed( ActionEvent e ) {
            // Move screen to create billboards section
            getContentPane().remove(hubContainer);
            CreateBillboards();

            // Revalidate content pane
            getContentPane().revalidate();
        }
    });
    JButton listBB = new JButton( new AbstractAction("List Billboard") {
        @Override
        public void actionPerformed( ActionEvent e ) {
            // Move screen to list billboards section
            getContentPane().remove(hubContainer);
            ListBillboards();

            // Revalidate content pane
            getContentPane().revalidate();
        }
    });
    JButton scheduleBB = new JButton( new AbstractAction("Schedule Billboard") {
        @Override
        public void actionPerformed( ActionEvent e ) {
        }
    });
    JButton editPermissions = new JButton( new AbstractAction("Edit Permissions") {
        @Override
        public void actionPerformed( ActionEvent e ) {

        }
    });

    /**
     * Display the Create Billboards section
     * ...
     */
    public void CreateBillboards() {
        // Create new container and layout
        CBContainer = new JPanel();

        // Change the size of the window
        setSize(new Dimension(800, 600));

        // Title label
        JLabel titleLabel = new JLabel("Create Billboards", JLabel.CENTER );
        titleLabel.setPreferredSize(new Dimension(190,100));
        Font largerFont = titleLabel.getFont().deriveFont(Font.PLAIN, 25f);
        titleLabel.setFont(largerFont);

        // Hub button
        returnHub.setPreferredSize(new Dimension(100,50));

        // Layout buttons with spring layout
        SpringLayout springlayout = new SpringLayout();
        CBContainer.setLayout(springlayout);
        springlayout.putConstraint(SpringLayout.NORTH, returnHub, 30, SpringLayout.NORTH, CBContainer);
        springlayout.putConstraint(SpringLayout.WEST, returnHub, 30, SpringLayout.WEST, CBContainer);
        springlayout.putConstraint(SpringLayout.NORTH, titleLabel, 10, SpringLayout.NORTH, CBContainer);
        springlayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, titleLabel, 0, SpringLayout.HORIZONTAL_CENTER, CBContainer);

        CBContainer.add(returnHub);
        CBContainer.add(titleLabel);
        getContentPane().add(CBContainer);

        // Set the current complete container
        currentContainer = CBContainer;
    }

    // Button used to return to the hub
    JButton returnHub = new JButton( new AbstractAction("← Hub") {
        @Override
        public void actionPerformed( ActionEvent e ) {
            // Move screen to create billboards section

            // Remove hub container and
            getContentPane().remove(currentContainer);
            ControlPanelHub();

            // Revalidate content pane
            getContentPane().revalidate();
        }
    });

    /**
     * Display the List Billboards section
     * ...
     */
    public void ListBillboards() {
        // Create new container and layout
        LBContainer = new JPanel();

        // Title label
        JLabel titleLabel = new JLabel("List Billboards", JLabel.CENTER );
        titleLabel.setPreferredSize(new Dimension(190,100));
        Font largerFont = titleLabel.getFont().deriveFont(Font.PLAIN, 25f);
        titleLabel.setFont(largerFont);

        // Hub button
        returnHub.setPreferredSize(new Dimension(100,50));

        // Initialise list Table
        JTable table = new JTable();
        TableModel model = new BillboardListTable("billboard list");
        table.setModel(model);

        // Set button columns as such
        table.getColumn("Preview").setCellRenderer(new BillboardListTable.ButtonRenderer());
        table.getColumn("Preview").setCellEditor(new BillboardListTable.ButtonEditor(new JCheckBox()));

        table.getColumn("Edit").setCellRenderer(new BillboardListTable.ButtonRenderer());
        table.getColumn("Edit").setCellEditor(new BillboardListTable.ButtonEditor(new JCheckBox()));

        table.getColumn("Delete").setCellRenderer(new BillboardListTable.ButtonRenderer());
        table.getColumn("Delete").setCellEditor(new BillboardListTable.ButtonEditor(new JCheckBox()));

        table.setBounds(30, 40, 200, 200);

        // Set table font
        Font tableFont = titleLabel.getFont().deriveFont(Font.PLAIN, 12f);
        table.setFont(tableFont);

        // Customise the table spacing
        table.setRowHeight(30);
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(150);
        columnModel.getColumn(1).setPreferredWidth(100);
        columnModel.getColumn(2).setPreferredWidth(80);

        // Disable table editing
        table.setDefaultEditor(Object.class, null);

        // Put the table in a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(400, 300));

        // Layout button and title with spring layout
        SpringLayout springlayout = new SpringLayout();
        LBContainer.setLayout(springlayout);
        springlayout.putConstraint(SpringLayout.NORTH, returnHub, 30, SpringLayout.NORTH, LBContainer);
        springlayout.putConstraint(SpringLayout.WEST, returnHub, 30, SpringLayout.WEST, LBContainer);
        springlayout.putConstraint(SpringLayout.NORTH, titleLabel, 10, SpringLayout.NORTH, LBContainer);
        springlayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, titleLabel, 0, SpringLayout.HORIZONTAL_CENTER, LBContainer);
        springlayout.putConstraint(SpringLayout.SOUTH, scrollPane, 0, SpringLayout.SOUTH, LBContainer);
        springlayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, scrollPane, 0, SpringLayout.HORIZONTAL_CENTER, LBContainer);

        LBContainer.add(scrollPane);
        LBContainer.add(returnHub);
        LBContainer.add(titleLabel);
        getContentPane().add(LBContainer);

        // Set the current complete container
        currentContainer = LBContainer;
    }

    /**
     * Display the Schedule Billboards section
     * ...
     */
    public void ScheduleBillboards() {
        // Create new container and layout
        SBContainer = new JPanel();
        FlowLayout layout = new FlowLayout();
        layout.setHgap(0);
        layout.setVgap(10);
        SBContainer.setLayout(layout);

        // Title label
        JLabel titleLabel = new JLabel("Schedule Billboards", JLabel.CENTER );
        titleLabel.setPreferredSize(new Dimension(300,60));
        Font largerFont = titleLabel.getFont().deriveFont(Font.PLAIN, 25f);
        titleLabel.setFont(largerFont);

        SBContainer.add(titleLabel);
        getContentPane().add(SBContainer);

        // Set the current complete container
        currentContainer = SBContainer;
    }

    /**
     * Display the Edit Users section
     * ...
     */
    public void EditUsers() {
        // Create new container and layout
        EUContainer = new JPanel();
        FlowLayout layout = new FlowLayout();
        layout.setHgap(0);
        layout.setVgap(10);
        EUContainer.setLayout(layout);

        // Title label
        JLabel titleLabel = new JLabel("Edit Permissions", JLabel.CENTER );
        titleLabel.setPreferredSize(new Dimension(300,60));
        Font largerFont = titleLabel.getFont().deriveFont(Font.PLAIN, 25f);
        titleLabel.setFont(largerFont);

        EUContainer.add(titleLabel);
        getContentPane().add(EUContainer);

        // Set the current complete container
        currentContainer = EUContainer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}