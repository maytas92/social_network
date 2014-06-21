/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package social_network;

/**
 *
 * @author Piyush
 */
import java.sql.*;
import java.util.ArrayList;
import static social_network.DoctorDBAO.getConnection;
import static social_network.SearchDBAO.getConnection;
import static social_network.SearchDBAO.pwd;
import static social_network.SearchDBAO.url;
import static social_network.SearchDBAO.user;

public class SearchDBAO {
    //public static final String url = "jdbc:mysql://eceweb.uwaterloo.ca:3306/";
    //public static final String user = "user_pgadigon";
    //public static final String pwd = "user_pgadigon";
    public static final String url = "jdbc:mysql://localhost:3306/";
    public static final String user = "root";
    public static final String pwd = "";

   public static ArrayList<Patient> getSearchPatients(PatientSearch ps) 
           throws ClassNotFoundException, SQLException {
       Connection con = null;
       PreparedStatement stmt = null;
       String query = null;
       try {
           con = getConnection();
           query = "SELECT distinct login FROM patient_view WHERE ";
           
           if(ps != null) {
               if(ps.getLogin()!= null)
                    query += "login LIKE ? AND ";
                if(ps.getFirstName() != null)
                    query += "first_name LIKE ? AND ";
                if(ps.getMiddleName() != null)
                    query += "middle_name LIKE ? AND ";
                if(ps.getLastName() != null)
                    query += "last_name LIKE ? AND ";
                if(ps.getEmailAddress()!= null)
                    query += "email_address LIKE ? AND ";
                
           }
           query+="1=1;";
           stmt = con.prepareStatement(query);
           int i = 1;
           if (ps != null) {
               if(ps.getLogin()!= null) {
                    stmt.setString(i, "%"+ps.getLogin()+"%");
                    i++;
               }
                if(ps.getFirstName() != null) {
                    stmt.setString(i, "%"+ps.getFirstName()+"%");
                    i++;
                }
                if(ps.getMiddleName() != null) {
                    stmt.setString(i, "%"+ps.getMiddleName()+"%");
                    i++;
                }
                if(ps.getLastName() != null) {
                    stmt.setString(i, "%"+ps.getLastName()+"%");
                    i++;
                }
                if(ps.getEmailAddress()!= null) {
                    stmt.setString(i, "%"+ps.getEmailAddress()+"%");
                    i++;
                }
           }
           ResultSet resultSet = stmt.executeQuery();
           
           ArrayList<Patient> patientList = new ArrayList<Patient>();
           while(resultSet.next()) {
               patientList.add(PatientDBAO.getPatientInfo(resultSet.getString("login")));
           }
           return patientList;
       } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }    
       }
   }
   
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
    
   public static ArrayList<String> getDoctorsRecommendedByFriends(String login) 
        throws ClassNotFoundException, SQLException {
       Connection con = null;
       PreparedStatement pStmt = null;
       try {
           con = getConnection();
           ArrayList<String> doctorList = new ArrayList<String>();
           pStmt = con.prepareStatement("SELECT doctor_login FROM recommended_doctors WHERE patient_login=? ;");
           pStmt.setString(1, login);

           ResultSet resultSet = pStmt.executeQuery();
           while(resultSet.next()) {
               doctorList.add(resultSet.getString("doctor_login"));
           }
           return doctorList;
       } finally {
            if (pStmt != null) {
                pStmt.close();
            }
            if (con != null) {
                con.close();
            }    
       }
   }
   
   public static ArrayList<String> getDoctorsWithAvgRating(double avg_rating) 
                        throws ClassNotFoundException, SQLException {
       Connection con = null;
       PreparedStatement pStmt = null;
       try {
           con = getConnection();
           ArrayList<String> doctorList = new ArrayList<String>();
           pStmt = con.prepareStatement("SELECT doctor_login, avg_rating FROM doctor_rating " +
            "HAVING avg_rating >= ?");
           pStmt.setDouble(1, avg_rating);

           ResultSet resultSet = pStmt.executeQuery();
           while(resultSet.next()) {
               doctorList.add(resultSet.getString("doctor_login"));
           }
           return doctorList;
       } finally {
            if (pStmt != null) {
                pStmt.close();
            }
            if (con != null) {
                con.close();
            }    
       }
   }

   public static ArrayList<Doctor> getSearchDoctors(DoctorSearch ds) 
           throws ClassNotFoundException, SQLException {
       Connection con = null;
       PreparedStatement stmt = null;
       String query = null;
       try {
           con = getConnection();
           query = "SELECT distinct doctor_view.login FROM doctor_view LEFT JOIN specialisation_view ON doctor_view.login = specialisation_view.login"
                   + " LEFT JOIN work_address_view ON doctor_view.login = work_address_view.login WHERE ";
           
           if(ds!=null) {
                /*if(ds.getLogin()!= null)
                    query += "login LIKE '%"+ds.getLogin()+"%' AND ";
                if(ds.getFirstName() != null)
                    query += "first_name LIKE '%"+ds.getFirstName()+"%' AND ";
                if(ds.getMiddleName() != null)
                    query += "middle_name LIKE '%"+ds.getMiddleName()+"%' AND ";
                if(ds.getLastName() != null)
                    query += "last_name LIKE '%"+ds.getLastName()+"%' AND ";
                if(ds.getGender()!= null)
                    query += "gender LIKE '%"+ds.getGender()+"%' AND ";
                if(ds.getLicense_year()!= null)
                    query += "license_year < '"+ds.getLicense_year()+"' AND ";
                if(ds.getSpecialisation()!= null)
                    query += "area_of_specialisation LIKE '%"+ds.getSpecialisation()+"%' AND ";
                if(ds.getStreetNumber()!= 0)
                    query += "street_number LIKE "+ds.getStreetNumber()+" AND ";
                if(ds.getStreetName()!= null)
                    query += "street_name LIKE '%"+ds.getStreetName()+"%' AND ";
                if(ds.getPostalCode()!= null)
                    query += "postal_code LIKE '%"+ds.getPostalCode()+"%' AND ";
                if(ds.getHouseNumber()!= null)
                    query += "house_number LIKE '%"+ds.getHouseNumber()+"%' AND ";
                if(ds.getCity()!= null)
                    query += "city LIKE '"+ds.getCity()+"' AND ";
                if(ds.getProvince()!= null)
                    query += "province LIKE '"+ds.getProvince()+"' AND ";
                }*/
                if(ds.getLogin()!= null)
                    query += "login LIKE ? AND ";
                if(ds.getFirstName() != null)
                    query += "first_name LIKE ? AND ";
                if(ds.getMiddleName() != null)
                    query += "middle_name LIKE ? AND ";
                if(ds.getLastName() != null)
                    query += "last_name LIKE ? AND ";
                if(ds.getGender()!= null)
                    query += "gender LIKE ? AND ";
                if(ds.getLicense_year()!= null)
                    query += "license_year < ? AND ";
                if(ds.getSpecialisation()!= null)
                    query += "area_of_specialisation LIKE ? AND ";
                if(ds.getStreetNumber()!= 0)
                    query += "street_number LIKE ? AND ";
                if(ds.getStreetName()!= null)
                    query += "street_name LIKE ? AND ";
                if(ds.getPostalCode()!= null)
                    query += "postal_code LIKE ? AND ";
                if(ds.getHouseNumber()!= null)
                    query += "house_number LIKE ? AND ";
                if(ds.getCity()!= null)
                    query += "city LIKE ? AND ";
                if(ds.getProvince()!= null)
                    query += "province LIKE ? AND ";
            }
               
           
            query+="1=1;";
            stmt = con.prepareStatement(query);
            int i = 1;
            if(ds != null) {
                if(ds.getLogin()!= null) {
                    stmt.setString(i, "%"+ds.getLogin()+"%");
                    i++;
                }
                if(ds.getFirstName() != null){
                    stmt.setString(i, "%"+ds.getFirstName()+"%");
                    i++;
                }
                if(ds.getMiddleName() != null){
                    stmt.setString(i, "%"+ds.getMiddleName()+"%");
                    i++;
                }
                if(ds.getLastName() != null){
                    stmt.setString(i, "%"+ds.getLastName()+"%");
                    i++;
                }
                if(ds.getGender()!= null){
                    stmt.setString(i, "%"+ds.getGender()+"%");
                    i++;
                }
                if(ds.getLicense_year()!= null){
                    stmt.setDate(i, ds.getLicense_year());
                    i++;
                }
                if(ds.getSpecialisation()!= null){
                    stmt.setString(i, "%"+ds.getSpecialisation()+"%");
                    i++;
                }
                if(ds.getStreetNumber()!= 0){
                    stmt.setInt(i, ds.getStreetNumber());
                    i++;
                }
                if(ds.getStreetName()!= null){
                    stmt.setString(i, "%"+ds.getStreetName()+"%");
                    i++;
                }
                if(ds.getPostalCode()!= null){
                    stmt.setString(i, "%"+ds.getPostalCode()+"%");
                    i++;
                }
                if(ds.getHouseNumber()!= null){
                    stmt.setString(i, "%"+ds.getHouseNumber()+"%");
                    i++;
                }
                if(ds.getCity()!= null){
                    stmt.setString(i, "%"+ds.getCity()+"%");
                    i++;
                }
                if(ds.getProvince()!= null){
                    stmt.setString(i, "%"+ds.getProvince()+"%");
                    i++;
                }
           }
            
           ResultSet resultSet = stmt.executeQuery();
           ArrayList<String> doctorLoginList = new ArrayList<String>();
           while(resultSet.next()) {
               doctorLoginList.add(resultSet.getString("login"));
           }
           
           ArrayList<String> tmpList = null;
           if(ds.getRating()>=0) {
             tmpList = getDoctorsWithAvgRating(ds.getRating());  
             for(i = 0; i < doctorLoginList.size();) {
                 if(!exists(tmpList,doctorLoginList.get(i))) {
                     doctorLoginList.remove(i);
                 }
                 else {
                     i++;
                 }
             }
           }
           
           tmpList = null;
           if(ds.isRecommendedByFriend()) {
             tmpList = getDoctorsRecommendedByFriends(ds.getPatientLogin());  
             for(i = 0; i < doctorLoginList.size();) {
                 if(!exists(tmpList,doctorLoginList.get(i))) {
                     doctorLoginList.remove(i);
                 }
                 else {
                     i++;
                 }
             }
           }
           
           ArrayList<Doctor> doctorList = new ArrayList<Doctor>();
           for(String s : doctorLoginList) {
               doctorList.add(DoctorDBAO.getDoctorInfo(s));
           }
           return doctorList;
       } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }    
       }
   }
   
   public static ArrayList<Review> getSearchReviews(ReviewSearch rs) 
           throws ClassNotFoundException, SQLException {
       Connection con = null;
       PreparedStatement stmt = null;
       String query = null;
       try {
           con = getConnection();
           query = "SELECT * FROM review_view WHERE ";
           
           if(rs!=null) {
               if(rs.getComments() != null) {
                    query += "comments LIKE ? AND ";
                }
                if(rs.getDoctorLogin() != null) {
                    query += "doctor_login LIKE ? AND ";
                }
                if(rs.getPatientLogin()!= null) {
                    query += "patient_login LIKE ? AND ";
                }
                if(rs.getMinRatings() >= 0) {
                    query += "rating >= ? AND ";
                }
                if(rs.getMaxRatings() >= 0) {
                    query += "rating <= ? AND ";
                }
                if(rs.getMinDateTime()!=null) {
                    query += "datetime >= ? AND ";
                }
                if(rs.getMaxDateTime()!=null) {
                    query += "datetime <= ? AND ";
                }
           }
           query+="1=1;";
           
           stmt = con.prepareStatement(query);
           int i = 1;
           if(rs != null) {
                if(rs.getComments() != null) {
                    stmt.setString(i,"%"+rs.getComments()+"%");
                    i++;
                }
                if(rs.getDoctorLogin() != null) {
                    stmt.setString(i,"%"+rs.getDoctorLogin()+"%");
                    i++;
                }
                if(rs.getPatientLogin()!= null) {
                    stmt.setString(i,"%"+rs.getPatientLogin()+"%");
                    i++;
                }
                if(rs.getMinRatings() >= 0) {
                    stmt.setInt(i,rs.getMinRatings());
                    i++;
                }
                if(rs.getMaxRatings() >= 0) {
                    stmt.setInt(i,rs.getMaxRatings());
                    i++;
                }
                if(rs.getMinDateTime()!=null) {
                    stmt.setDate(i,rs.getMinDateTime());
                    i++;
                }
                if(rs.getMaxDateTime()!=null) {
                    stmt.setDate(i,rs.getMaxDateTime());
                    i++;
                }
           }
           ResultSet resultSet = stmt.executeQuery();
           ArrayList<Review> reviewList = new ArrayList<Review>();
           while(resultSet.next()) {
               Review review = new Review();
               review.setDoctorLogin(resultSet.getString("doctor_login"));
               review.setPatientLogin(resultSet.getString("patient_login"));
               review.setRating(resultSet.getInt("rating"));
               review.setComments(resultSet.getString("comments"));
               review.setDatetime(resultSet.getTimestamp("datetime"));
               reviewList.add(review);
           }
           return reviewList;
       } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }    
       }
   }
   
   public static boolean exists(ArrayList<String> list, String str) {
       for(String a : list) {
           if(str.equals(a)) return true;
       }
       return false;
   }
}
