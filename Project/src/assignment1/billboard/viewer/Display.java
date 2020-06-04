package assignment1.billboard.Viewer;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.xml.sax.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Properties;


public class Display implements Runnable{
    private Properties properties = new Properties();
    private static Connection instance = null;
    private BillboardContents billboardContents;
    private String xmlString;

    /**
     * Display constructor
     */
    public Display(){
        xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<billboard>\n" +
                "    <message>Default-coloured message</message>\n" +
                "    <picture url=\"https://cloudstor.aarnet.edu.au/plus/s/X79GyWIbLEWG4Us/download\" />\n" +
                "    <information colour=\"#60B9FF\">Custom-coloured information text</information>\n" +
                "</billboard>";
        timedServerConnection();
        billboardContents = new BillboardContents(xmlString);
    }

    /**
     * Server connection timer
     */
    public void timedServerConnection(){
        connectToServer();
        new Timer(15000, e ->{
            connectToServer();
        }).start();
    }

    /**
     * Connects to server
     */
    public void connectToServer(){
        FileInputStream in = null;
        try {
            Properties props = new Properties();
            in = new FileInputStream("./db.props");
            props.load(in);
            in.close();

            // specify the data source, username and password
            String url = props.getProperty("jdbc.url");
            String username = props.getProperty("jdbc.username");
            String password = props.getProperty("jdbc.password");
            String schema = props.getProperty("jdbc.schema");

            // get a connection
            instance = DriverManager.getConnection(url + "/" + schema, username, password);
        } catch (SQLException sqle) {
            System.exit(0);
        } catch (FileNotFoundException fnfe) {
            System.err.println(fnfe);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void run(){

    }
}
