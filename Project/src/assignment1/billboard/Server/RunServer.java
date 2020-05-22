package assignment1.billboard.Server;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class RunServer {
    public static void main(String[] args) {
        Connection connection = DBConnection.getInstance();
        try {
            Statement st = connection.createStatement();
            CreateBillboardsTable(st);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    /**
     * @param st Used for basic SQL Statements.
     *           The method itself is run when the server initializes and generates the billboard table
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
}
