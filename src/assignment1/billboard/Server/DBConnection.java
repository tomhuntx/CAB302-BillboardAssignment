package assignment1.billboard.Server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

//from Lecture 7 - Data Connectivity
public class DBConnection {
    /**
     * The instance of the database connection session.
     */
    private static Connection instance = null;

    /**
     * Constructor initializes the connection.
     */
    private DBConnection() {

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
            System.out.println("Connection to Database Failed. Terminating Server...");
            System.exit(1);
        } catch (FileNotFoundException fnfe) {
            System.err.println(fnfe);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Provides global access to the singleton instance of the UrlSet.
     *
     * @return a handle to the singleton instance of the UrlSet.
     */
    public static Connection getInstance() {
        if (instance == null) {
            new DBConnection();
        }
        return instance;
    }
}