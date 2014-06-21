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
    
     public static ArrayList<Review> getAllReviewsForDoctor(String login) 
            throws SQLException, ClassNotFoundException {
        Connection con = null;
        ArrayList<Review> listOfReviews = new ArrayList<Review>();
        PreparedStatement pStmt = null;
        ResultSet resultSet = null;
        try {
            con = getConnection();
            pStmt = con.prepareStatement("SELECT * FROM review_view WHERE doctor_login=? ORDER BY datetime DESC;");
            pStmt.setString(1, login);
            resultSet = pStmt.executeQuery();
            while(resultSet.next()) {
                Review rev = new Review();
                rev.setPatientLogin(resultSet.getString("patient_login"));
                rev.setComments(resultSet.getString("comments"));
                rev.setDatetime(resultSet.getTimestamp("datetime"));
                rev.setRating(resultSet.getInt("rating"));
                listOfReviews.add(rev);
            }
            return listOfReviews;
        }finally {
            if (pStmt != null) {
                pStmt.close();
            }
            if (con != null) {
                con.close();
            }    
        }
    }
    
    public static ArrayList<Review> getAllReviewsByPatient(String login) 
            throws SQLException, ClassNotFoundException {
        Connection con = null;
        ArrayList<Review> listOfReviews = new ArrayList<Review>();
        PreparedStatement pStmt = null;
        ResultSet resultSet = null;
        try {
            con = getConnection();
            pStmt = con.prepareStatement("SELECT * FROM review_view WHERE patient_login=?");
            pStmt.setString(1, login);
            resultSet = pStmt.executeQuery();
            while(resultSet.next()) {
                Review rev = new Review();
                rev.setDoctorLogin(resultSet.getString("doctor_login"));
                rev.setComments(resultSet.getString("comments"));
                rev.setDatetime(resultSet.getTimestamp("datetime"));
                rev.setRating(resultSet.getInt("rating"));
                rev.setPatientLogin(login);
                listOfReviews.add(rev);
            }
            return listOfReviews;
        }finally {
            if (pStmt != null) {
                pStmt.close();
            }
            if (con != null) {
                con.close();
            }    
        }
    }
    
    public static ArrayList<Review> getAllReviews() throws SQLException, ClassNotFoundException {
       Connection con = null;
       ArrayList<Review> listOfReviews = new ArrayList<Review>();
       PreparedStatement pStmt = null;
       try {
           con = getConnection();
           pStmt = con.prepareStatement("SELECT * FROM review_view");
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
    
    public static void createReview(Review review) 
            throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement pStmt = null;
        try {
            con = getConnection();
            pStmt = con.prepareStatement("INSERT INTO Reviews VALUES (?, ?, ?, ?, ?)");
            pStmt.setString(1, review.getPatientLogin());
            pStmt.setString(2, review.getDoctorLogin());
            pStmt.setTimestamp(3, getCurrentTimeStamp());
            pStmt.setDouble(4, review.getRating());
            pStmt.setString(5, review.getComments());
            pStmt.executeUpdate();
        }finally {
            if(pStmt != null) {
                pStmt.close();
            }
            if(con != null) {
                con.close();
            }
        }
    }
    private static java.sql.Timestamp getCurrentTimeStamp() {
 
		java.util.Date today = new java.util.Date();
		return new java.sql.Timestamp(today.getTime());
 
   }
    
     public static boolean deleteReview(String patient_login, String doctor_login, String datetime) 
            throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement pStmt = null;
        ResultSet resultSet = null;
        boolean isDeleted = false;
        try {
            con = getConnection();
            pStmt = con.prepareStatement("SELECT * FROM review_view WHERE patient_login=? and doctor_login=? AND datetime=?");
            pStmt.setString(1, patient_login);
            pStmt.setString(2, doctor_login);
            pStmt.setString(3, datetime);
            resultSet = pStmt.executeQuery();
            if(resultSet.first()) {
                con.setAutoCommit(false);
                con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                pStmt = con.prepareStatement("DELETE FROM Reviews WHERE patient_login=? AND doctor_login=? AND datetime=?");
                pStmt.setString(1, patient_login);
                pStmt.setString(2, doctor_login);
                pStmt.setString(3, datetime);
                pStmt.executeUpdate();
                con.commit();
                con.setAutoCommit(true);
                isDeleted = true;
            }else {
                isDeleted = false;
            }
        }catch(SQLException se) {
            System.out.print(se.toString());
            con.rollback();            
        } finally {
            //con.setAutoCommit(true);
            if (pStmt != null) {
                pStmt.close();
            }
            if (con != null) {
                con.close();
            }    
            return isDeleted;
        }
    }
    
}
