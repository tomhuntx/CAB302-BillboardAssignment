package assignment1.billboard.controlPanel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
    JTextPane billboardText;

    // Name of billboard
    JTextField billName;
    String name_input;

    // RGB colours of billboard background
    ArrayList<JTextField> rgb;

    // Saved hex colour of billboard
    String billColour = "#32A852";

    // Saved font for billboards
    Font billboardFont;

    // Scheduling billboards items
    JComboBox<String> dateBox;
    JSpinner timeSpinner;
    JTextField durationText;
    ButtonGroup bg;
    ArrayList<JRadioButton> repeatRbs;
    JRadioButton rb0;
    JRadioButton rb1;
    JRadioButton rb2;
    JRadioButton rb3;
    JTextField rb3time;

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
     * Provides four buttons for access to the control panel features.
     * Create Billboards, List Billboards, Schedule Users, and Edit Users.
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
            // Move screen to schedule billboards section
            getContentPane().remove(hubContainer);
            ScheduleBillboards();

            // Revalidate content pane
            getContentPane().revalidate();
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
        billboardText = new JTextPane();
        billboardText.setBorder(new LineBorder(Color.BLACK, 1));
        billboardText.setPreferredSize(new Dimension(640,360));
        billboardFont = titleLabel.getFont().deriveFont(Font.PLAIN, 40f);
        billboardText.setFont(billboardFont);

        // Center billboard text
        StyledDocument doc = billboardText.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        // Font size panel
        JPanel fontPanel = new JPanel(new GridLayout(3,1));
        fontPanel.setPreferredSize(new Dimension(75,150));
        JLabel fontLabel = new JLabel("Font Size", SwingConstants.CENTER);
        fontPanel.add(fontLabel);
        JPanel upPanel = new JPanel();
        JPanel downPanel = new JPanel();
        upPanel.add(fontUP);
        downPanel.add(fontDown);
        fontUP.setPreferredSize(new Dimension(45,35));
        fontDown.setPreferredSize(new Dimension(45,35));
        fontPanel.add(upPanel);
        fontPanel.add(downPanel);

        // RGB Panel
        JPanel RGBPanel = new JPanel(new GridLayout(5,1));
        JLabel RGBLabel = new JLabel("<html>Background<br>Colour</html>", SwingConstants.CENTER);
        RGBPanel.add(RGBLabel);
        RGBPanel.setPreferredSize(new Dimension(80,200));

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

        // Layout Font Panel
        springlayout.putConstraint(SpringLayout.VERTICAL_CENTER, fontPanel, 0, SpringLayout.VERTICAL_CENTER,
                CBContainer);
        springlayout.putConstraint(SpringLayout.WEST, fontPanel, 30, SpringLayout.WEST, CBContainer);

        // Layout Billboard
        springlayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, billboardText, 0,
                SpringLayout.HORIZONTAL_CENTER, CBContainer);
        springlayout.putConstraint(SpringLayout.VERTICAL_CENTER, billboardText, -90,
                SpringLayout.HORIZONTAL_CENTER, CBContainer);

        CBContainer.add(fontPanel);
        CBContainer.add(RGBPanel);
        CBContainer.add(titleLabel);
        CBContainer.add(billboardText, SwingConstants.CENTER);
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

    // Button used to increase font size
    JButton fontUP = new JButton( new AbstractAction("▲") {
        @Override
        public void actionPerformed( ActionEvent e ) {
            int newSize = billboardFont.getSize() + 4;

            if (newSize < 200) {
                billboardFont = billboardFont.deriveFont(Font.PLAIN, newSize);
                billboardText.setFont(billboardFont);
            }
        }
    });

    // Button used to lower font size
    JButton fontDown = new JButton( new AbstractAction("▼") {
        @Override
        public void actionPerformed( ActionEvent e ) {
            int newSize = billboardFont.getSize() - 4;

            if (newSize > 5) {
                billboardFont = billboardFont.deriveFont(Font.PLAIN, newSize);
                billboardText.setFont(billboardFont);
            }
        }
    });

    // Button used import an .xml file
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

    // Button used export an .xml file
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
        springlayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, titleLabel, 0, SpringLayout.HORIZONTAL_CENTER,
                LBContainer);
        springlayout.putConstraint(SpringLayout.SOUTH, scrollPane, 0, SpringLayout.SOUTH, LBContainer);
        springlayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, scrollPane, 0, SpringLayout.HORIZONTAL_CENTER,
                LBContainer);

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

        // Title label
        JLabel titleLabel = new JLabel("Schedule Billboards", JLabel.CENTER );
        titleLabel.setPreferredSize(new Dimension(230,100));
        Font largerFont = titleLabel.getFont().deriveFont(Font.PLAIN, 25f);
        titleLabel.setFont(largerFont);

        // Hub button
        returnHub.setPreferredSize(new Dimension(100,50));

        // Weekly Calender
        JPanel calPanel = new JPanel(new GridLayout(1,7));
        calPanel.setPreferredSize(new Dimension(700,180));
        String[] days = new String[7];

        // Get the current week
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");

        // Keep track of the day panels
        ArrayList<JPanel> dayPanels = new ArrayList<>();

        // Create a font for the days
        Font dayFont = titleLabel.getFont().deriveFont(Font.ITALIC, 18f);

        // Create a day panel for each day of the week and a corresponding time
        for (int i = 0; i < days.length; i++) {
            days[i] = sdf.format(cal.getTime());
            cal.add(Calendar.DAY_OF_MONTH, 1);

            // Create a label and panel for each day of the week
            JLabel dayLabel = new JLabel();
            dayLabel.setText(days[i]);
            dayLabel.setHorizontalAlignment(JLabel.CENTER);
            dayLabel.setFont(dayFont);

            JPanel dayPanel = new JPanel(new GridLayout(5,1));
            dayPanel.setBorder(new LineBorder(Color.BLACK, 1));

            dayPanel.add(dayLabel);
            dayPanels.add(dayPanel);
            calPanel.add(dayPanel);
        }

        // Date Scheduling panel
        JPanel schPanel = new JPanel(new GridLayout(1,4));
        schPanel.setPreferredSize(new Dimension(500,50));
        Font dateFont = titleLabel.getFont().deriveFont(Font.PLAIN, 18f);

        // Date combo box
        JPanel datePanel = new JPanel();
        JLabel dateLabel = new JLabel("Date:");
        dateLabel.setFont(dateFont);
        datePanel.add(dateLabel);
        dateBox = new JComboBox<>(days);
        dateBox.setPreferredSize(new Dimension(80,36));
        dateBox.setFont(dayFont);
        datePanel.add(dateBox);
        schPanel.add(datePanel);

        // Time JSpinner
        JPanel timePanel = new JPanel();
        JLabel timeLabel = new JLabel("Time:");
        timeLabel.setFont(dateFont);
        timePanel.add(timeLabel);
        timeSpinner = new JSpinner( new SpinnerDateModel() );
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
        timeSpinner.setEditor(timeEditor);
        timeSpinner.setValue(new Date()); // set to the today's date
        timeSpinner.setPreferredSize(new Dimension(70,40));
        timeSpinner.setFont(dateFont);
        timePanel.add(timeSpinner);
        schPanel.add(timePanel);

        // Duration text input
        JPanel durationPanel = new JPanel();
        JLabel durationLabel = new JLabel("Duration:");
        durationLabel.setFont(dateFont);
        durationText = new JTextField("0");
        durationText.setPreferredSize(new Dimension(32,40));
        durationText.setFont(dateFont);
        JLabel minutesLabel = new JLabel("mins");
        minutesLabel.setFont(dateFont);
        durationPanel.add(durationLabel);
        durationPanel.add(durationText);
        durationPanel.add(minutesLabel);
        schPanel.add(durationPanel);

        // Date Repeating panel
        JPanel repeatPanel = new JPanel();
        repeatPanel.setPreferredSize(new Dimension(550,50));
        JLabel repeatLabel = new JLabel("Repeat:");
        repeatLabel.setFont(dateFont);

        // Radio buttons
        Dimension rbSize = new Dimension(60,40);
        rb0 = new JRadioButton("None", true);
        JPanel rb0Panel = new JPanel();
        rb0Panel.setPreferredSize(rbSize);
        rb1 = new JRadioButton("Daily");
        JPanel rb1Panel = new JPanel();
        rb1Panel.setPreferredSize(rbSize);
        rb2 = new JRadioButton("Hourly");
        JPanel rb2Panel = new JPanel();
        rb2Panel.setPreferredSize(rbSize);

        // Minutes radio button panel
        JPanel rb3Panel = new JPanel();
        rb3Panel.setPreferredSize(new Dimension(160,40));
        rb3 = new JRadioButton("Every");
        rb3time = new JTextField("0");
        rb3time.setPreferredSize(new Dimension(25,25));
        JLabel rb3Label = new JLabel("minutes");

        // Only allow one button to be pressed at any time
        bg = new ButtonGroup();
        bg.add(rb0);
        bg.add(rb1);
        bg.add(rb2);
        bg.add(rb3);
        repeatRbs = new ArrayList<>();
        repeatRbs.add(rb0);
        repeatRbs.add(rb1);
        repeatRbs.add(rb2);
        repeatRbs.add(rb3);

        // Add radio buttons to their panels and then to the main panel
        rb0Panel.add(rb0);
        rb1Panel.add(rb1);
        rb2Panel.add(rb2);
        rb3Panel.add(rb3);
        rb3Panel.add(rb3time);
        rb3Panel.add(rb3Label);
        repeatPanel.add(repeatLabel);
        repeatPanel.add(rb0Panel);
        repeatPanel.add(rb1Panel);
        repeatPanel.add(rb2Panel);
        repeatPanel.add(rb3Panel);

        // Confirm button
        scheduleConfirm.setPreferredSize(new Dimension(80,45));

        // Layout elements with spring layout
        SpringLayout springlayout = new SpringLayout();
        SBContainer.setLayout(springlayout);
        springlayout.putConstraint(SpringLayout.NORTH, returnHub, 30, SpringLayout.NORTH, SBContainer);
        springlayout.putConstraint(SpringLayout.WEST, returnHub, 30, SpringLayout.WEST, SBContainer);
        springlayout.putConstraint(SpringLayout.NORTH, titleLabel, 10, SpringLayout.NORTH, SBContainer);
        springlayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, titleLabel, 0, SpringLayout.HORIZONTAL_CENTER,
                SBContainer);
        springlayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, calPanel, 0, SpringLayout.HORIZONTAL_CENTER,
                SBContainer);
        springlayout.putConstraint(SpringLayout.VERTICAL_CENTER, calPanel, -20, SpringLayout.VERTICAL_CENTER,
                SBContainer);
        springlayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, schPanel, 0, SpringLayout.HORIZONTAL_CENTER,
                SBContainer);
        springlayout.putConstraint(SpringLayout.SOUTH, schPanel, -75, SpringLayout.SOUTH, SBContainer);
        springlayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, repeatPanel, 0, SpringLayout.HORIZONTAL_CENTER,
                SBContainer);
        springlayout.putConstraint(SpringLayout.SOUTH, repeatPanel, -20, SpringLayout.SOUTH, SBContainer);
        springlayout.putConstraint(SpringLayout.SOUTH, scheduleConfirm, -55, SpringLayout.SOUTH, SBContainer);
        springlayout.putConstraint(SpringLayout.EAST, scheduleConfirm, -35, SpringLayout.EAST, SBContainer);

        // Add components to the container
        SBContainer.add(calPanel);
        SBContainer.add(schPanel);
        SBContainer.add(repeatPanel);
        SBContainer.add(scheduleConfirm);
        SBContainer.add(returnHub);
        SBContainer.add(titleLabel);
        getContentPane().add(SBContainer);

        // Change the size of the window
        setSize(new Dimension(800, getHeight()));

        // Set the current complete container
        currentContainer = SBContainer;
    }

    // Button used to confirm the chosen scheduled billboard
    JButton scheduleConfirm = new JButton( new AbstractAction("Confirm") {
        @Override
        public void actionPerformed( ActionEvent e ) {
            try {
                // Date
                String date = (String) dateBox.getSelectedItem();

                // Time
                Date selectedTime = (Date)timeSpinner.getValue();
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                String time = format.format(selectedTime);

                // Duration
                String duration = durationText.getText();
                int durationInt = Integer.parseInt(duration);
                if (durationInt <= 0) {
                    throw new IndexOutOfBoundsException();
                }

                int repeat;
                String repeatOutput = "not repeat";
                for (JRadioButton button : repeatRbs) {
                    if (button.isSelected()) {
                        if (button == rb0) {

                            repeatOutput = "not repeat";
                        } else if (button == rb1) {

                            repeatOutput = "repeat every day";

                        } else if (button == rb2) {

                            repeatOutput = "repeat every hour";
                        } else if (button == rb3) {
                            String repeatTime = rb3time.getText();

                            repeat = Integer.parseInt(repeatTime);
                            repeatOutput = "repeat every " + repeat + " minute(s)";

                            if (repeat == 0) {
                                repeatOutput = "not repeat";
                            }
                            else if (repeat < 0) {
                                throw new IndexOutOfBoundsException();
                            }
                        } else throw new Exception("Could not find selected radio button.");
                    }
                }

                // Output
                String output = "Are you sure you wish to schedule a billboard on the\n " + date + " at " + time +
                        " for " + duration + " minutes, that will " + repeatOutput + "?";

                int outputDialog = JOptionPane.showConfirmDialog(null, output,
                        "Scheduling Billboard", JOptionPane.YES_NO_OPTION);

                if (outputDialog == JOptionPane.YES_OPTION){

                    System.out.println("Billboard scheduled.");
                }
                else {
                    System.out.println("User has chosen to cancel scheduling.");
                }
            }
            catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null,
                        "Time durations must be valid numbers only.",
                        "Schedule Billboard Failed", JOptionPane.WARNING_MESSAGE);
            }
            catch (IndexOutOfBoundsException oob) {
                JOptionPane.showMessageDialog(null,
                        "The duration cannot be 0, and no values can be less than 0.",
                        "Schedule Billboard Failed", JOptionPane.WARNING_MESSAGE);
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(null,
                        "There has been a problem, please try again.",
                        "Schedule Billboard Failed", JOptionPane.ERROR_MESSAGE);
            }

        }
    });

    /**
     * Display the Edit Users section
     * ...
     */
    public void EditUsers() {
        // Create new container and layout
        EUContainer = new JPanel();

        // Title label
        JLabel titleLabel = new JLabel("Edit Users", JLabel.CENTER );
        titleLabel.setPreferredSize(new Dimension(230,100));
        Font largerFont = titleLabel.getFont().deriveFont(Font.PLAIN, 25f);
        titleLabel.setFont(largerFont);

        // Hub button
        returnHub.setPreferredSize(new Dimension(100,50));

        // Layout button and title with spring layout
        SpringLayout springlayout = new SpringLayout();
        EUContainer.setLayout(springlayout);
        springlayout.putConstraint(SpringLayout.NORTH, returnHub, 30, SpringLayout.NORTH, EUContainer);
        springlayout.putConstraint(SpringLayout.WEST, returnHub, 30, SpringLayout.WEST, EUContainer);
        springlayout.putConstraint(SpringLayout.NORTH, titleLabel, 10, SpringLayout.NORTH, EUContainer);
        springlayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, titleLabel, 0, SpringLayout.HORIZONTAL_CENTER,
                EUContainer);

        // Add components to the container
        EUContainer.add(returnHub);
        EUContainer.add(titleLabel);
        getContentPane().add(SBContainer);

        // Set the current complete container
        currentContainer = EUContainer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}