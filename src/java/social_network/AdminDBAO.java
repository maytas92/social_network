/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package social_network;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Satyam
 */
public class AdminDBAO {
    
    //public static final String url = "jdbc:mysql://eceweb.uwaterloo.ca:3306/";
    //public static final String user = "user_pgadigon";
    //public static final String pwd = "user_pgadigon";
    public static final String url = "jdbc:mysql://localhost:3306/";
    public static final String user = "root";
    public static final String pwd = "";

    public static void testConnection()
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        try {
            con = getConnection();
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }

    public static Connection getConnection()
            throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(url, user, pwd);
        Statement stmt = null;
        try {
            con.createStatement();
            stmt = con.createStatement();
            stmt.execute("USE ece356db_pgadigon;");
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return con;
    }
    
    public static boolean isAdmin (String login) 
           throws ClassNotFoundException, SQLException {
       Connection con = null;
       Statement stmt = null;
       boolean isAdmin = false;
       try {
           con = getConnection();
           stmt = con.createStatement();
           PreparedStatement pStmt = con.prepareStatement("SELECT login FROM Admin WHERE login=?");
           pStmt.setString(1, login);
           
           ResultSet resultSet = pStmt.executeQuery();
           isAdmin = resultSet.first();
           return isAdmin;
       } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }    
       }
   }
    
    public static Admin getAdminInfo (String login) 
           throws ClassNotFoundException, SQLException {
       Connection con = null;
       Statement stmt = null;
       Admin admin = new Admin();
       try {
           con = getConnection();
           stmt = con.createStatement();
           PreparedStatement pStmt = con.prepareStatement("SELECT * FROM Admin WHERE login=?");
           pStmt.setString(1, login);
           
           ResultSet resultSet = pStmt.executeQuery();
          
           while (resultSet.next()) {
                admin.setFirst_name(resultSet.getString("first_name"));
                admin.setLast_name(resultSet.getString("last_name"));
                admin.setMiddle_name(resultSet.getString("middle_name"));
                admin.setLogin(resultSet.getString("login"));
           }
                                 
           resultSet = pStmt.executeQuery();
           return admin;
       } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }    
       }
   }
    
}
