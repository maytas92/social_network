/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package social_network;

/**
 *
 * @author Piyush
 */
import java.sql.Timestamp;
public class Review {
    private String patientLogin;
    private String doctorLogin;
    private Timestamp datetime;
    private double rating;
    private String comments;

    /**
     * @return the patientLogin
     */
    public String getPatientLogin() {
        return patientLogin;
    }

    /**
     * @param patientLogin the patientLogin to set
     */
    public void setPatientLogin(String patientLogin) {
        this.patientLogin = patientLogin;
    }

    /**
     * @return the doctorLogin
     */
    public String getDoctorLogin() {
        return doctorLogin;
    }

    /**
     * @param doctorLogin the doctorLogin to set
     */
    public void setDoctorLogin(String doctorLogin) {
        this.doctorLogin = doctorLogin;
    }

    /**
     * @return the datetime
     */
    public Timestamp getDatetime() {
        return datetime;
    }

    /**
     * @param datetime the datetime to set
     */
    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    /**
     * @return the rating
     */
    public double getRating() {
        return rating;
    }

    /**
     * @param rating the rating to set
     */
    public void setRating(double rating) {
        this.rating = rating;
    }

    /**
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(String comments) {
        if(comments == null) comments = "";
        this.comments = comments;
    }
}
