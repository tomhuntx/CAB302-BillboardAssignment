package assignment1.billboard.Viewer;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.xml.sax.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.Properties;

public class Display extends JFrame implements Runnable{
    private Properties properties = new Properties();

    private JPanel displayPanel = new JPanel();
    private JPanel messagePanel = new JPanel();
    private JPanel informationPanel = new JPanel();
    private JPanel picturePanel = new JPanel();
    private JPanel errorPanel = new JPanel();
    private JLabel messageLabel = new JLabel("", SwingConstants.CENTER);
    private JLabel informationLabel = new JLabel("", SwingConstants.CENTER);
    private JLabel pictureLabel = new JLabel();
    private JLabel errorLabel = new JLabel();
    boolean msg;
    boolean inf;
    boolean pic;

    public Display(){
        setTitle("Billboard");
        msg = false;
        inf = false;
        pic = false;

        exitEvents();

        connectToServer();

        displayPanel.setLayout(new GridBagLayout());
        add(displayPanel, BorderLayout.CENTER);
        setUndecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        setVisible(true);
    }

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

    public void connectToServer(){

    }

    public static void main(String[] args){
        Display display = new Display();
        display.showDisplay();
    }

    private void showDisplay(){
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<billboard background=\"#555555\">\n" +
                "    <message colour=\"#FFFFFF\">The information text is always smaller than the message text</message>\n" +
                "    <information colour=\"#DDDDDD\">The information text is always smaller than the message text</information>\n" +
                "</billboard>";
        displayPanel.setBackground(Color.white);
        updateBillboard(xmlString);
        labelPlacement();
        pack();
        setExtendedState(MAXIMIZED_BOTH);
        setVisible(true);
    }

    private void updateBillboard(String xmlString){
        try {
            DocumentBuilderFactory dBFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dBFactory.newDocumentBuilder();
            InputSource iSource = new InputSource(new StringReader(xmlString));
            Document document = dBuilder.parse(iSource);

            String bg = document.getDocumentElement().getAttribute("background");
            if(bg != null){
                Color bgColour = Color.getColor(bg);
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

    public void updateAttributes(Node node){
        String key = node.getNodeName();
        String value = node.getTextContent();
        if(key == "message"){
            msg = true;
            messageLabel.setText(value);
            Node colour = node.getAttributes().getNamedItem("colour");
            if(colour != null){
                messageLabel.setForeground(Color.getColor(colour.getTextContent()));
            }
        }else if(key == "information"){
            inf = true;
            informationLabel.setText(value);
            Node colour = node.getAttributes().getNamedItem("colour");
            if(colour != null){
                informationLabel.setForeground(Color.getColor(colour.getTextContent()));
            }
        }else if(key == "picture"){
            pic = true;
            Node picURL = node.getAttributes().getNamedItem("url");
            Node picDATA  = node.getAttributes().getNamedItem("data");
            if(picURL != null && picDATA == null){
                try{
                    URL url = new URL(picURL.getTextContent());
                    pictureLabel.setIcon(new ImageIcon(url));
                }
                catch (MalformedURLException e) {
                    errorLabel.setText(e.getMessage());
                    displayPanel.add(errorLabel);
                }
            } else if(picURL == null && picDATA != null){
                value = picDATA.getTextContent();
                pictureLabel.setIcon(new ImageIcon(Base64.getDecoder().decode(value)));
            }
        }
    }

    public void labelPlacement(){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        if(!pic){
            if(!inf){
                if(!msg){
                    /*
                     * No contents
                     * Error Message
                     */
                    errorLabel.setText("Empty");
                    errorLabel.setFont(new Font("Arial", Font.BOLD, 40));
                    errorLabel.setForeground(Color.red);
                    errorPanel.add(errorLabel);
                    displayPanel.add(errorPanel);
                }else if(msg){
                    /*
                     * Message Only
                     *
                     */
                    messageLabel.setFont(new Font("Arial", Font.BOLD, 40));
                    messagePanel.add(messageLabel);
                    messagePanel.setPreferredSize(screen);
                    displayPanel.add(messagePanel);
                }
            }else if(inf){
                if(!msg){
                    /*
                     * Information Only
                     *
                     */
                    informationLabel.setFont(new Font("Arial", Font.PLAIN, 40));
                    informationPanel.add(informationLabel);
                    informationPanel.setPreferredSize(screen);
                    displayPanel.add(informationPanel);
                }else if(msg){
                    /*
                     * Message And Information
                     *
                     */
                    messageLabel.setFont(new Font("Arial", Font.BOLD, 40));
                    informationLabel.setFont(new Font("Arial", Font.PLAIN, 34));
                    messagePanel.add(messageLabel);
                    informationPanel.add(informationLabel);
                    messagePanel.setPreferredSize(new Dimension(screen.width, screen.height / 2));
                    messagePanel.setPreferredSize(new Dimension(screen.width, screen.height / 2));
                    displayPanel.add(messagePanel);
                    displayPanel.add(informationPanel);
                }
            }
        }else if(pic){
            if(!inf){
                if(!msg){
                    /*
                     * Picture Only
                     *
                     */
                    picturePanel.add(pictureLabel);
                    picturePanel.setPreferredSize(screen);
                    displayPanel.add(picturePanel);
                }else if(msg) {
                    /*
                     * Message And Picture
                     *
                     */
                }
            }else if(inf){
                if(!msg){
                    /*
                     * Information An Picture
                     *
                     */
                }else if(msg){
                    /*
                     * Message And Information And Picture
                     *
                     */
                }
            }
        }
    }

    public void run(){
        showDisplay();
    }
}
