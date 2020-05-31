package assignment1.billboard.Server;

import java.io.*;
import java.sql.*;

public class RunServer {
    public static final String INSERTBILLBOARD = "INSERT INTO billboards VALUES (?, ?)";
    public static final String RETRIEVEBILLBOARD = "SELECT * FROM billboards WHERE NAME =";

    public static void main(String[] args) {
        Connection connection = DBConnection.getInstance();
        try {
            Statement st = connection.createStatement();
            CreateBillboardsTable(st);

            PreparedStatement insertStatement = connection.prepareStatement(INSERTBILLBOARD);
            addBillboard(insertStatement, xml2String(new File("C:/Users/Sympil/Desktop/Study/2020_Semester_1/CAB302/billboards/1.xml")), "billboard01");
            /*
            Example of the PreparedStatement being used to add a .xml file to the database, with the name billboard01
             */
            Statement queryStatement = connection.createStatement();
            retrieveBillboard(queryStatement, "billboard01");
            /*
            Example of the retrieveBillboard method, retrieving an object with the name billboard01 in the database, which can be toString() to display the contents of the xml.
            This should be further implemented and turned back into a File object, such that the billboard.Viewer can parse through the File.
             */

            st.close();
            insertStatement.close();
            queryStatement.close();
            connection.close();
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
                    "CREATE TABLE IF NOT EXISTS billboards(" +
                            "name varchar(255) NOT NULL default '', " +
                            //"age int(10) unsigned NOT NULL default '0');");
                            "billboardxml mediumblob);");
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
    private static void retrieveBillboard(Statement queryStatement, String fileName){
        try {
            String queryFileName = RETRIEVEBILLBOARD + " '" + fileName + "'";
            ResultSet rs = queryStatement.executeQuery(queryFileName);
            rs.next();

            byte[] data =  rs.getBytes("billboardxml");
            ObjectInputStream OIS = new ObjectInputStream(new ByteArrayInputStream(data));
            Object obj = OIS.readObject();
            System.out.println(obj.toString());

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
