package assignment1.billboard.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;

public class RunServer {
    public static final String INSERTBILLBOARD = "INSERT INTO billboards VALUES (?, ?)";
    public static final String RETRIEVEBILLBOARD = "SELECT * FROM billboards WHERE NAME =";

    public static void main(String[] args) {
        Connection connection = DBConnection.getInstance();
        try {
            Statement st = connection.createStatement();
            CreateBillboardsTable(st);
            CreateScheduleTable(st);
            CreateUsersTable(st);

            PreparedStatement insertStatement = connection.prepareStatement(INSERTBILLBOARD);
            addBillboard(insertStatement, xml2String(new File("C:/Users/Sympil/Desktop/Study/2020_Semester_1/CAB302/billboards/1.xml")), "billboard01");
            /*
            Example of the PreparedStatement being used to add a .xml file to the database, with the name billboard01
             */
            Statement queryStatement = connection.createStatement();
            Object xmlByteData = retrieveBillboard(queryStatement, "billboard01");
            /*
            Example of the retrieveBillboard method, retrieving an object with the name billboard01 in the database
             */
            String xmlString = xmlByteData.toString();  // This can be converted into a string and will be an exact copy of the xml file that was stored.
            st.close();
            insertStatement.close();
            queryStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            for(;;) {
                Socket socket = serverSocket.accept();
                Statement queryStatement = connection.createStatement();
                System.out.println("Connected to " + socket.getInetAddress());

                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();
                ObjectInputStream OIS = new ObjectInputStream(inputStream);
                ObjectOutputStream OOS = new ObjectOutputStream(outputStream);
                /*
                This code wont ever run because the viewer doesn't send anything over the socket.
                 */
                if (OIS.readUTF() == "Requesting Billoard"){
                    OOS.writeUTF("You are requesting a Billboard from Schedule");
                    OOS.flush();
                    Object billboardByteData = retrieveBillboard(queryStatement,"billboard01");
                    //  The fileName was meant to be determine by a method which was given the schedule time could
                    //  extract the fileName from the specific time and return its name.
                }
                /*
                There is no handling for the Control Panel either, because again there are no socket's being initiated
                at any time requesting anything. Handling could not be done.
                 */
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * @param st Used for basic SQL Statements.
     *           The method itself must run when the server successfully initializes and generates the billboard table
     *           if it does not already exist. The billboard table should contain a varchar name, and a medium blob
     *           that contains the byte[] of the xmlFile.
     */

    private static void CreateBillboardsTable(Statement st){
        try {
            st.executeQuery(
                    "CREATE TABLE IF NOT EXISTS Billboards(" +
                            "Name varchar(255) NOT NULL default '', " +
                            "BillboardXML mediumblob);");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param st Used for basic SQL Statements.
     *           The method is also run in conjunction with the other CreateTable methods. The Schedule table should
     *           contain the billboard's name, day and time its starting at, its duration and the billboard's creator.
     *           The day and time value gets stored in HH:MM:SS format, meaning should the date be the 1st, 24hours
     *           will be added to the starting time, for the 7th, 168 hours.
     *
     */
    private static void CreateScheduleTable(Statement st){
        try {
            st.executeQuery(
                    "CREATE TABLE IF NOT EXISTS Schedule(" +
                            "Name varchar(255) NOT NULL default ' '," +
                            "Start time NOT NULL default '0 08:00'," +
                            "Duration int(4) NOT NULL default '0'," +
                            "Creator varchar(255) NOT NULL default ' ');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param st Used for basic SQL Statements
     *           The final Table creation method. The User method contain's the username of users, with their associated
     *           permissions granted to them in a string format being either "true" or "false
     */
    private static void CreateUsersTable(Statement st){
        try {
            st.executeQuery(
                    "CREATE TABLE IF NOT EXISTS Users(" +
                            "Username varchar(255) NOT NULL default ' '," +
                            "CreateBillboards int(2) NOT NULL default '0'," +
                            "EditAllBillboards int(2) NOT NULL default '0'," +
                            "ScheduleBillboards int(2) NOT NULL default '0'," +
                            "EditUsers int(2) NOT NULL default '0'," +
                            "HashedPasswords blob);");
            //  ALso add a User called Admin or something, which has all permissions with the hashed password.

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param xmlFile Takes a .xml File
     * @return Returns the contents of the .xml file as a String
     */
    // To be used for testing, while you have a locally stored .xml file on your system, such as from the assignment examples.
    // To be used from the Control Panel after creating a .xml file, convert to string and send over to billboard Server,
    // to be saved on the database
    // Code referenced from: https://javarevisited.blogspot.com/2015/07/how-to-read-xml-file-as-string-in-java-example.html#axzz6N2xjzbgQ
    public static String xml2String(File xmlFile){
        String xmlString = "";
        try {
            Reader fileReader = new FileReader(xmlFile);
            BufferedReader bufReader = new BufferedReader(fileReader);

            StringBuilder sb = new StringBuilder();
            String line = bufReader.readLine();
            while( line != null){
                sb.append(line).append("\n");
                line = bufReader.readLine();
            }
            xmlString = sb.toString();
            bufReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return xmlString;
    }

    /**
     * @param insertStatement To be used in conjunction with the PreparedStatement INSERTBILLBOARD
     * @param xmlString The String contents of an .xml file
     * @param fileName The name of the .xml file to be stored in the database
     */
    private static void addBillboard(PreparedStatement insertStatement, String xmlString, String fileName){
        try {
            ByteArrayOutputStream BAOS = new ByteArrayOutputStream();
            ObjectOutputStream OOS = new ObjectOutputStream(BAOS);
            OOS.writeObject(xmlString);

            byte[] data = BAOS.toByteArray();

            insertStatement.clearParameters();
            insertStatement.setString(1,fileName);
            insertStatement.setBinaryStream(2, new ByteArrayInputStream(data), data.length);
            int rows = insertStatement.executeUpdate();
            System.out.println("Rows updated: " + rows);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param queryStatement a basic SQL statment, in this case utilizing RETRIEVEBILLBOARD
     * @param fileName The indicator for the SQL statement to locate the specified file.
     */
    private static Object retrieveBillboard(Statement queryStatement, String fileName) {
        Object obj = null;
        try {
            String queryFileName = RETRIEVEBILLBOARD + " '" + fileName + "'";
            ResultSet rs = queryStatement.executeQuery(queryFileName);
            rs.next();

            byte[] data = rs.getBytes("billboardxml");
            ObjectInputStream OIS = new ObjectInputStream(new ByteArrayInputStream(data));
            obj = OIS.readObject();
            //System.out.println(obj.toString());

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
