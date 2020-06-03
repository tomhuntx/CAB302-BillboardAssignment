package assignment1.billboard.controlPanel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
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

    // Text for billboard
    JTextArea billboardText;

    // Name of billboard
    JTextField billName;
    String name_input;

    // RGB colours of billboard background
    ArrayList<JTextField> rgb;

    // Saved hex colour of billboard
    String billColour = "#32A852";

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
     * Display the Create Billboards tool
     * Users can:
     * -Change the background colour with RGB text inputs
     * -Input text onto the canvas
     * -Import and export .xml files
     */
    public void CreateBillboards() {
        // Create new container and layout
        CBContainer = new JPanel();

        // Change the size of the window
        setSize(new Dimension(900, 660));

        // Title label
        JLabel titleLabel = new JLabel("Create Billboards", JLabel.CENTER );
        titleLabel.setPreferredSize(new Dimension(190,100));
        Font largerFont = titleLabel.getFont().deriveFont(Font.PLAIN, 25f);
        titleLabel.setFont(largerFont);

        // Name Panel
        JPanel namePanel = new JPanel();
        JLabel nameLabel = new JLabel();
        nameLabel.setText("Billboard Title: ");
        billName = new JTextField();
        Font inputFont = titleLabel.getFont().deriveFont(Font.PLAIN, 16f);
        billName.setFont(inputFont);
        billName.setPreferredSize(new Dimension(150,25));
        namePanel.add(nameLabel);
        namePanel.add(billName);

        // Billboard editor
        billboardText = new JTextArea();
        billboardText.setBorder(new LineBorder(Color.BLACK, 1));
        billboardText.setPreferredSize(new Dimension(640,360));

        // RGB Panel
        JPanel RGBPanel = new JPanel(new GridLayout(5,1));
        JLabel RGBLabel = new JLabel("<html><div style='text-align: center;'>" +
                                          "Background<br>Colour</div></html>");
        RGBPanel.add(RGBLabel);
        RGBPanel.setBackground(Color.lightGray);
        RGBPanel.setPreferredSize(new Dimension(70,200));

        // R, G, and B text input for RGB Panel
        String[] texts = new String[] {"R:", "G:", "B:"};
        rgb = new ArrayList<>();
        for (String s : texts)
        {
            JPanel panel = new JPanel();
            JTextField tf = new JTextField();
            JLabel label = new JLabel(s);
            label.setFont(inputFont);
            tf.setFont(inputFont);
            tf.setPreferredSize(new Dimension(35,25));
            tf.setText("255");
            panel.add(label);
            panel.add(tf);
            panel.setBackground(Color.lightGray);
            RGBPanel.add(panel);

            // Limit input field to three characters and numbers
            // Thanks to Dr. Payne: https://stackoverflow.com/a/35393356
            tf.addKeyListener(new KeyAdapter() {
                public void keyTyped(KeyEvent e) {
                    if (tf.getText().length() >= 3) {
                        e.consume();
                    }
                    // Only allow numbers to be added
                    char c = e.getKeyChar();
                    if (!((c >= '0') && (c <= '9') ||
                            (c == KeyEvent.VK_BACK_SPACE) ||
                            (c == KeyEvent.VK_DELETE))) {
                        e.consume();
                    }
                }
            });

            // Safe text fields for later use
            rgb.add(tf);
        }
        // Add confirm button to RGB Panel
        JPanel confirmPanel = new JPanel();
        confirmRGB.setPreferredSize(new Dimension(60,25));
        Font confirmFont = titleLabel.getFont().deriveFont(Font.PLAIN, 10f);
        confirmRGB.setFont(confirmFont);
        confirmPanel.add(confirmRGB);
        RGBPanel.add(confirmPanel);

        // Hub button
        returnHub.setPreferredSize(new Dimension(100,50));

        // XML buttons
        importXML.setPreferredSize(new Dimension(110,40));
        exportXML.setPreferredSize(new Dimension(110,40));

        // Layout buttons with spring layout
        SpringLayout springlayout = new SpringLayout();
        CBContainer.setLayout(springlayout);
        springlayout.putConstraint(SpringLayout.NORTH, returnHub, 30, SpringLayout.NORTH, CBContainer);
        springlayout.putConstraint(SpringLayout.WEST, returnHub, 30, SpringLayout.WEST, CBContainer);
        springlayout.putConstraint(SpringLayout.SOUTH, importXML, -30, SpringLayout.SOUTH, CBContainer);
        springlayout.putConstraint(SpringLayout.WEST, importXML, 30, SpringLayout.WEST, CBContainer);
        springlayout.putConstraint(SpringLayout.SOUTH, exportXML, -30, SpringLayout.SOUTH, CBContainer);
        springlayout.putConstraint(SpringLayout.EAST, exportXML, -30, SpringLayout.EAST, CBContainer);

        // Layout Title
        springlayout.putConstraint(SpringLayout.NORTH, titleLabel, 10, SpringLayout.NORTH, CBContainer);
        springlayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, titleLabel, 0,
                SpringLayout.HORIZONTAL_CENTER, CBContainer);

        // Layout Name Panel
        springlayout.putConstraint(SpringLayout.NORTH, namePanel, 120, SpringLayout.NORTH, CBContainer);
        springlayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, namePanel, 0,
                SpringLayout.HORIZONTAL_CENTER, CBContainer);

        // Layout RGB Panel
        springlayout.putConstraint(SpringLayout.VERTICAL_CENTER, RGBPanel, 0, SpringLayout.VERTICAL_CENTER,
                CBContainer);
        springlayout.putConstraint(SpringLayout.EAST, RGBPanel, -25, SpringLayout.EAST, CBContainer);

        // Layout Billboard
        springlayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, billboardText, 0,
                SpringLayout.HORIZONTAL_CENTER, CBContainer);
        springlayout.putConstraint(SpringLayout.VERTICAL_CENTER, billboardText, -90,
                SpringLayout.HORIZONTAL_CENTER, CBContainer);


        CBContainer.add(RGBPanel);
        CBContainer.add(titleLabel);
        CBContainer.add(billboardText);
        CBContainer.add(namePanel);
        CBContainer.add(returnHub);
        CBContainer.add(importXML);
        CBContainer.add(exportXML);
        getContentPane().add(CBContainer);

        // Set the current complete container
        currentContainer = CBContainer;
    }

    // Button used to return to the hub (used by each control panel section)
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

    // Button used to change the background colour of the billboard
    JButton confirmRGB = new JButton( new AbstractAction("Set") {
        @Override
        public void actionPerformed( ActionEvent e ) {
            int[] col = new int[3];

            try {
                for (int i = 0; i < rgb.size(); i++) {
                    col[i] = Integer.parseInt(rgb.get(i).getText());
                }

                Color bgColour = new Color(col[0], col[1], col[2]);
                billboardText.setBackground(bgColour);

                // Save colour as a hexadecimal
                billColour = "#"+Integer.toHexString(bgColour.getRGB()).substring(2);
                System.out.println("Colour set to (hex): " + billColour);
            }
            catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null,
                        "Please enter a valid RGB colour.",
                        "Set Background Failed", JOptionPane.WARNING_MESSAGE);
            }
            catch (IllegalArgumentException iae) {
                JOptionPane.showMessageDialog(null,
                        "Each number must be between 0 and 255.",
                        "Set Background Failed", JOptionPane.WARNING_MESSAGE);
            }
        }
    });

    // Button used to return to the hub
    JButton importXML = new JButton( new AbstractAction("Import .xml") {
        @Override
        public void actionPerformed( ActionEvent ae )  {
            OpenXML open = new OpenXML();

            // Try to open a file select menu
            open.SelectFile();

            // If successful, change the billboard name to the name of the new file
            if (open.filepath != null) {
                name_input = open.getName();

                // Remove the xml tag from the string if it exists
                if (name_input.contains(".xml")) {
                    name_input = name_input.substring(0, name_input.lastIndexOf(".xml"));
                }

                billName.setText(name_input);

                // Set the billboard text
                billboardText.setText(open.builder.toString());
            }
        }
    });

    // Button used to return to the hub
    JButton exportXML = new JButton( new AbstractAction("Export .xml") {
        @Override
        public void actionPerformed( ActionEvent e ) {

        }
    });

    /**
     * Display the List Billboards section
     * Lists billboard info in a JTable
     * Table includes billboard name, creator, preview button, edit button, and delete button
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

        // Set up table row sorting
        table.setRowSorter(new TableRowSorter<>(model));
        table.setAutoCreateRowSorter(true);

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