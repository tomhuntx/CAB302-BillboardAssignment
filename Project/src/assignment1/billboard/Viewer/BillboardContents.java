package assignment1.billboard.Viewer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.xml.sax.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;

public class BillboardContents extends JFrame{
    private String xmlString;
    private Node node = null;
    private Dimension screen;
    private JPanel displayPanel = new JPanel();
    private JLabel messageLabel = new JLabel("", SwingConstants.CENTER);
    private JLabel informationLabel = new JLabel("", SwingConstants.CENTER);
    private JLabel pictureLabel = new JLabel("", SwingConstants.CENTER);
    private JLabel errorLabel = new JLabel("", SwingConstants.CENTER);
    private Boolean msg;
    private Boolean inf;
    private Boolean pic;
    private BufferedImage image;

    /**
     * Initialises variables and frame
     *
     * @param xmlInput string of xml code
     */
    public BillboardContents(String xmlInput){
        super("Billboard");
        xmlString = xmlInput;
        msg = false;
        inf = false;
        pic = false;
        screen = Toolkit.getDefaultToolkit().getScreenSize();
        int DEF_SIZE = 46;
        int DEF_INF_SIZE = 30;
        String DEF_FONT = "Arial";
        Font defFont = new Font(DEF_FONT, Font.BOLD, DEF_SIZE);
        messageLabel.setFont(defFont);
        Font defInfFont = new Font(DEF_FONT, Font.PLAIN, DEF_INF_SIZE);
        informationLabel.setFont(defInfFont);
        setUndecorated(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(screen.width, screen.height);
    }

    /**
     * Shows GUI of billboard
     */
    public void showGUI(){
        exitEvents();
        updateBillboard();
        labelPlacement();
        add(displayPanel, BorderLayout.CENTER);
        pack();
        setVisible(true);
    }

    /**
     * Closes GUI
     */
    public void resetGUI(){
        dispose();
    }

    /**
     * Adds adapter for events to exit
     */
    private void exitEvents(){
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
            }
        });
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
    }

    /**
     * Updates billboard
     *
     */
    private void updateBillboard(){
        try {
            DocumentBuilderFactory dBFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dBFactory.newDocumentBuilder();
            InputSource iSource = new InputSource(new StringReader(xmlString));
            Document document = dBuilder.parse(iSource);
            String bg = document.getDocumentElement().getAttribute("background");
            if(bg.length() != 0){
                Color bgColour = Color.decode(bg);
                displayPanel.setBackground(bgColour);
            }
            NodeList nodeList = document.getDocumentElement().getChildNodes();
            for(int i = 0; i < nodeList.getLength(); i++) {
                node = nodeList.item(i);
                updateAttributes();
            }
        } catch (ParserConfigurationException e) {
            errorLabel.setText(e.getMessage());
            displayPanel.add(errorLabel);
        } catch (SAXException e) {
            errorLabel.setText(e.getMessage());
            displayPanel.add(errorLabel);
        } catch (IOException e) {
            errorLabel.setText(e.getMessage());
            displayPanel.add(errorLabel);
        }
    }

    /**
     * Updates frame attributes
     *
     */
    private void updateAttributes(){
        String key = node.getNodeName();
        String value = node.getTextContent();
        if(key == "message"){
            msg = true;
            messageLabel.setText(value);
            Node colour = node.getAttributes().getNamedItem("colour");
            if(colour != null){
                messageLabel.setForeground(Color.decode(colour.getTextContent()));
            }
        }else if(key == "information"){
            inf = true;
            informationLabel.setText(value);
            Node colour = node.getAttributes().getNamedItem("colour");
            if(colour != null){
                informationLabel.setForeground(Color.decode(colour.getTextContent()));
            }
        }else if(key == "picture"){
            pic = true;
            Node picURL = node.getAttributes().getNamedItem("url");
            Node picDATA  = node.getAttributes().getNamedItem("data");
            if(picURL != null && picDATA == null){
                try{
                    URL url = new URL(picURL.getTextContent());
                    image = ImageIO.read(url);
                }
                catch (MalformedURLException e) {
                    errorLabel.setText(e.getMessage());
                    displayPanel.add(errorLabel);
                    add(displayPanel, BorderLayout.CENTER);
                    pack();
                    setVisible(true);
                } catch (IOException e) {
                    errorLabel.setText(e.getMessage());
                    displayPanel.add(errorLabel);
                    add(displayPanel, BorderLayout.CENTER);
                    pack();
                    setVisible(true);
                }
            } else if(picURL == null && picDATA != null){
                try {
                    value = picDATA.getTextContent();
                    image = ImageIO.read(new ByteArrayInputStream(Base64.getDecoder().decode(value)));
                } catch (IOException e){
                    errorLabel.setText(e.getMessage());
                    displayPanel.add(errorLabel);
                    add(displayPanel, BorderLayout.CENTER);
                    pack();
                    setVisible(true);
                }
            }
        }
    }

    /**
     * Places labels accordingly
     */
    private void labelPlacement(){
        int QUARTER = 4;
        int THIRD = 3;
        Dimension picScreen = new Dimension(screen.width / QUARTER, screen.height / QUARTER);
        Dimension twoScreen = new Dimension(screen.width / THIRD, screen.height / THIRD);

        /*
         * Create new GridLayout
         */
        int ROWS = 3;
        int COLS = 1;
        GridLayout gridLayout = new GridLayout(ROWS,COLS);
        displayPanel.setLayout(gridLayout);

        /*
         * Each case for different combination of attributes
         */
        if(!pic){
            if(!inf){
                if(!msg){
                    /*
                     * No contents
                     * Error Message
                     */
                    errorLabel.setText("No Billboard");
                    errorLabel.setPreferredSize(screen);
                    errorLabel.setForeground(Color.red);
                    displayPanel.add(errorLabel, gridLayout);
                    add(displayPanel, BorderLayout.CENTER);
                    pack();
                    setVisible(true);
                }else if(msg){
                    /*
                     * Message Only
                     */
                    messageLabel.setPreferredSize(screen);
                    messageLabel.setFont(new Font(messageLabel.getName(), Font.BOLD, fontScale(messageLabel)));
                    displayPanel.add(messageLabel, BorderLayout.CENTER);
                }
            }else if(inf){
                if(!msg){
                    /*
                     * Information Only
                     */
                    informationLabel.setPreferredSize(screen);
                    displayPanel.add(informationLabel, BorderLayout.CENTER);
                }else if(msg){
                    /*
                     * Message And Information
                     */
                    messageLabel.setPreferredSize(twoScreen);
                    messageLabel.setText("<html><span style'font-size:" + fontScale(messageLabel) + "px'>"
                        + messageLabel.getText() + "</span></html>");
                    displayPanel.add(messageLabel, BorderLayout.NORTH);

                    informationLabel.setPreferredSize(twoScreen);
                    informationLabel.setText("<html><span style'font-size:" + fontScale(informationLabel) + "px'>"
                            + informationLabel.getText() + "</span></html>");
                    displayPanel.add(informationLabel, BorderLayout.SOUTH);
                }
            }
        }else if(pic){
            if(!inf){
                if(!msg){
                    /*
                     * Picture Only
                     */
                    pictureLabel.setPreferredSize(picScreen);
                    Image img = image.getScaledInstance(picScreen.width, picScreen.height, Image.SCALE_SMOOTH);
                    pictureLabel.setIcon(new ImageIcon(img));
                    displayPanel.add(pictureLabel, BorderLayout.CENTER);
                }else if(msg) {
                    /*
                     * Message And Picture
                     */
                    messageLabel.setPreferredSize(picScreen);
                    messageLabel.setText("<html><span style'font-size:" + fontScale(messageLabel) + "px'>"
                            + messageLabel.getText() + "</span></html>");
                    displayPanel.add(messageLabel, BorderLayout.NORTH);

                    pictureLabel.setPreferredSize(picScreen);
                    Image img = image.getScaledInstance(picScreen.width, picScreen.height, Image.SCALE_SMOOTH);
                    pictureLabel.setIcon(new ImageIcon(img));
                    displayPanel.add(pictureLabel, BorderLayout.CENTER);
                }
            }else if(inf){
                if(!msg){
                    /*
                     * Information And Picture
                     */
                    pictureLabel.setPreferredSize(picScreen);
                    Image img = image.getScaledInstance(picScreen.width, picScreen.height, Image.SCALE_SMOOTH);
                    pictureLabel.setIcon(new ImageIcon(img));
                    displayPanel.add(pictureLabel, BorderLayout.CENTER);

                    informationLabel.setPreferredSize(picScreen);
                    informationLabel.setText("<html><span style'font-size:" + fontScale(informationLabel) + "px'>"
                            + informationLabel.getText() + "</span></html>");
                    displayPanel.add(informationLabel, BorderLayout.SOUTH);
                }else if(msg){
                    /*
                     * Message And Information And Picture
                     */
                    messageLabel.setPreferredSize(picScreen);
                    messageLabel.setText("<html><span style'font-size:" + fontScale(messageLabel) + "px'>"
                            + messageLabel.getText() + "</span></html>");
                    displayPanel.add(messageLabel, BorderLayout.NORTH);

                    pictureLabel.setPreferredSize(picScreen);
                    Image img = image.getScaledInstance(picScreen.width, picScreen.height, Image.SCALE_SMOOTH);
                    pictureLabel.setIcon(new ImageIcon(img));
                    displayPanel.add(pictureLabel, BorderLayout.CENTER);

                    informationLabel.setPreferredSize(picScreen);
                    informationLabel.setText("<html><span style'font-size:" + fontScale(informationLabel) + "px'>"
                            + informationLabel.getText() + "</span></html>");
                    displayPanel.add(informationLabel, BorderLayout.SOUTH);
                }
            }
        }
    }

    /**
     * Scale text size to fit screen
     * @param label the label to be scaled
     * @return the scaled size
     */
    private int fontScale(JLabel label){
        Font font = label.getFont();
        double componentWidth = label.getFontMetrics(font).stringWidth(label.getText());
        double labelWidth = label.getWidth();
        return (int) Math.min(((font.getSize() * componentWidth) / labelWidth), font.getSize());
    }

}
