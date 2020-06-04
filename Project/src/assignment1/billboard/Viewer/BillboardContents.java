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
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.Properties;

public class BillboardContents extends JFrame{
    private JPanel displayPanel = new JPanel();
    private JLabel messageLabel = new JLabel("", SwingConstants.CENTER);
    private JLabel informationLabel = new JLabel("", SwingConstants.CENTER);
    private JLabel pictureLabel = new JLabel("", SwingConstants.CENTER);
    private JLabel errorLabel = new JLabel("", SwingConstants.CENTER);
    private Boolean msg;
    private Boolean inf;
    private Boolean pic;

    /**
     * Instantiates new GUI
     *
     * @param xmlString string of xml code
     */
    public BillboardContents(String xmlString){
        /*
         * Initialise variables
         */
        msg = false;
        inf = false;
        pic = false;
        Font defFont = new Font("Arial", Font.BOLD, 46);
        messageLabel.setFont(defFont);

        /*
         * Initialise GUI frame
         */
        setTitle("Billboard");
        setUndecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        exitEvents();

        /*
         * Gets attributes of new billboard
         */
        updateBillboard(xmlString);

        /*
         * Place attributes on displayPanel
         */
        labelPlacement();

        /*
         * Add displayPanel to GUI frame
         */
        add(displayPanel, BorderLayout.CENTER);

        /*
         * Update GUI
         */
        pack();
        setVisible(true);
    }

    /**
     * Adds adapter for events to exit
     */
    public void exitEvents(){
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
     * @param xmlString string of XML code
     */
    private void updateBillboard(String xmlString){
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
                Node node = nodeList.item(i);
                updateAttributes(node);
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
     * @param node attribute
     */
    public void updateAttributes(Node node){
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
                    Image image = ImageIO.read(url);
                    pictureLabel.setIcon(new ImageIcon(image));
                }
                catch (MalformedURLException e) {
                    errorLabel.setText(e.getMessage());
                    displayPanel.add(errorLabel);
                } catch (IOException e) {
                    errorLabel.setText(e.getMessage());
                    displayPanel.add(errorLabel);
                }
            } else if(picURL == null && picDATA != null){
                value = picDATA.getTextContent();
                pictureLabel.setIcon(new ImageIcon(Base64.getDecoder().decode(value)));
            }
        }
    }

    /**
     * Places labels accordingly
     */
    public void labelPlacement(){
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension picScreen = new Dimension(screen.width / 4, screen.height / 4);
        Dimension twoScreen = new Dimension(screen.width / 3, screen.height / 3);

        /*
         * Create new GridBagLayout
         */
        GridLayout gridLayout = new GridLayout(3,1);
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
                    displayPanel.add(pictureLabel, BorderLayout.CENTER);
                }
            }else if(inf){
                if(!msg){
                    /*
                     * Information And Picture
                     */
                    pictureLabel.setPreferredSize(picScreen);
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
