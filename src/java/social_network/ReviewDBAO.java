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
public class ReviewDBAO {
    
    public static final String url = "jdbc:mysql://eceweb.uwaterloo.ca:3306/";
    public static final String user = "user_s52gupta";
    public static final String pwd = "user_s52gupta";

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
            stmt.execute("USE ece356db_s52gupta;");
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return con;
    }
    
    public static ArrayList<Review> getAllReviews() throws SQLException, ClassNotFoundException {
       Connection con = null;
       ArrayList<Review> listOfReviews = new ArrayList<Review>();
       PreparedStatement pStmt = null;
       try {
           con = getConnection();
           pStmt = con.prepareStatement("SELECT * FROM Reviews");
           ResultSet resultSet = pStmt.executeQuery();
           
           while(resultSet.next()) {
               Review rev = new Review();
               rev.setPatientLogin(resultSet.getString("patient_login"));
               rev.setDoctorLogin(resultSet.getString("doctor_login"));
               rev.setDatetime(resultSet.getTimestamp("datetime"));
               rev.setRating(resultSet.getInt("rating"));
               rev.setComments(resultSet.getString("comments"));
               listOfReviews.add(rev);
           }
           //Reviews rev = new Reviews();
           //rev.setDoctorLogin("1");
           //listOfReviews.add(rev);
           return listOfReviews;
       } finally {
           if (pStmt != null) {
                pStmt.close();
            }
            if (con != null) {
                con.close();
            }    
       }
       
   }
    
}
