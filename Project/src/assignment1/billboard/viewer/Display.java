package assignment1.billboard.Viewer;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;

/**
 * Class Display
 * performs the creation of viewer GUI
 * will also perform connection to server
 * contains example xml strings
 */
public class Display{
    private Properties properties = new Properties();
    private static Connection instance = null;
    public BillboardContents billboardContents;

    /**
     * Display constructor
     */
    public Display(){
        /**
         * Server connection timer not functional
         */
        //timedServerConnection();
        billboardContents = null;
    }

    public void runDisplay(){
        billboardContents = new BillboardContents(newXML());
        billboardContents.showGUI();
    }

    public void delayDisplay(int delay){
        try {
            Thread.sleep(delay);
            billboardContents.resetGUI();
        }catch(InterruptedException e) {
            System.out.println(e);
        }
    }

    /**
     * Server connection timer
     */
    public void timedServerConnection(int delay){
        connectToServer();
        new Timer(delay, e ->{
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

    /**
     * Function to return string of random XML billboard
     * was placeholder for until server worked
     * @return returns string of XML
     */
    public String newXML() {
        String out = null;
        Random random = new Random();
        String[] billboards = new String[]{"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<billboard>\n" +
                "    <message>Basic message-only billboard</message>\n" +
                "</billboard>",
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<billboard>\n" +
                        "    <information>Billboard with an information tag and nothing else. Note that the text is word-wrapped. The quick brown fox jumped over the lazy dogs.</information>\n" +
                        "</billboard>",
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<billboard>\n" +
                        "    <picture url=\"https://cloudstor.aarnet.edu.au/plus/s/62e0uExNviPanZE/download\" />\n" +
                        "</billboard>",
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<billboard>\n" +
                        "    <message>Billboard with message and picture with data attribute</message>\n" +
                        "    <picture data=\"iVBORw0KGgoAAAANSUhEUgAAACAAAAAQCAIAAAD4YuoOAAAAKXRFWHRDcmVhdGlvbiBUaW1lAJCFIDI1IDMgMjAyMCAwOTowMjoxNyArMDkwMHlQ1XMAAAAHdElNRQfkAxkAAyQ8nibjAAAACXBIWXMAAAsSAAALEgHS3X78AAAABGdBTUEAALGPC/xhBQAAAS5JREFUeNq1kb9KxEAQxmcgcGhhJ4cnFwP6CIIiPoZwD+ALXGFxj6BgYeU7BO4tToSDFHYWZxFipeksbMf5s26WnAkJki2+/c03OzPZDRJNYcgVwfsU42cmKi5YjS1s4p4DCrkBPc0wTlkdX6bsG4hZQOj3HRDLHqh08U4Adb/zgEMtq5RuH3Axd45PbftdB2wO5OsWc7pOYaOeOk63wYfdFtL5qldB34W094ZfJ+4RlFldTrmW/ZNbn2g0of1vLHdZq77qSDCaSAsLf9kXh9w44PNoR/YSPHycEmbIOs5QzBJsmDHrWLPeF24ZkCe6ZxDCOqHcmxmsr+hsicahss+n8vYb8NHZPTJxi/RGC5IqbRwqH6uxVTX+5LvHtvT/V/R6PGh/iF4GHoBAwz7RD26spwq6Amh/AAAAAElFTkSuQmCC\" />\n" +
                        "</billboard>",
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<billboard>\n" +
                        "    <picture url=\"https://cloudstor.aarnet.edu.au/plus/s/vYipYcT3VHa1uNt/download\" />\n" +
                        "    <information>Billboard with picture (with URL attribute) and information text only. The picture is now centred within the top 2/3 of the image and the information text is centred in the remaining space below the image.</information>\n" +
                        "</billboard>",
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<billboard>\n" +
                        "    <message>Billboard with message, GIF and information</message>\n" +
                        "    <picture url=\"https://cloudstor.aarnet.edu.au/plus/s/A26R8MYAplgjUhL/download\" />\n" +
                        "    <information>This billboard has a message tag, a picture tag (linking to a URL with a GIF image) and an information tag. The picture is drawn in the centre and the message and information text are centred in the space between the top of the image and the top of the page, and the space between the bottom of the image and the bottom of the page, respectively.</information>\n" +
                        "</billboard>",
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<billboard>\n" +
                        "    <message>Billboard with message and info</message>\n" +
                        "    <information>Billboard with a message tag, an information tag, but no picture tag. The message is centred within the top half of the screen while the information is centred within the bottom half.</information>\n" +
                        "</billboard>",
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<billboard>\n" +
                        "    <message colour=\"#FFC457\">Billboard with default background and custom-coloured message</message>\n" +
                        "</billboard>",
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<billboard background=\"#7F3FBF\">\n" +
                        "    <message>Billboard with custom background and default-coloured message</message>\n" +
                        "</billboard>",
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<billboard>\n" +
                        "    <message>Default-coloured message</message>\n" +
                        "    <picture url=\"https://cloudstor.aarnet.edu.au/plus/s/X79GyWIbLEWG4Us/download\" />\n" +
                        "    <information colour=\"#60B9FF\">Custom-coloured information text</information>\n" +
                        "</billboard>",
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<billboard background=\"#6800C0\">\n" +
                        "    <message colour=\"#FF9E3F\">All custom colours</message>\n" +
                        "    <information colour=\"#3FFFC7\">All custom colours</information>\n" +
                        "</billboard>",
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<billboard background=\"#555555\">\n" +
                        "    <message colour=\"#FFFFFF\">The information text is always smaller than the message text</message>\n" +
                        "    <information colour=\"#DDDDDD\">The information text is always smaller than the message text</information>\n" +
                        "</billboard>",
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<billboard background=\"#FFC457\">\n" +
                        "    <picture url=\"https://cloudstor.aarnet.edu.au/plus/s/EvYVdlUNx72ioaI/download\" />\n" +
                        "</billboard>",
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<billboard background=\"#FF38C3\">\n" +
                        "    <picture url=\"https://cloudstor.aarnet.edu.au/plus/s/a2IioOedKQgQwvQ/download\" />\n" +
                        "</billboard>",
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<billboard background=\"#8996FF\">\n" +
                        "    <picture url=\"https://cloudstor.aarnet.edu.au/plus/s/5fhToroJL0nMKvB/download\" />\n" +
                        "</billboard>"};
        out = billboards[random.nextInt(billboards.length)];
        return out;
    }

}
