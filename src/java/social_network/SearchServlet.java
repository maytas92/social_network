/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package social_network;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
 *
 * @author Piyush
 */
@WebServlet(name = "SearchServlet", urlPatterns = {"/SearchServlet"})
public class SearchServlet extends HttpServlet {
    private DoctorSearch createDoctorSearchObject(HttpServletRequest request) {
        
        DoctorSearch docSearch = new DoctorSearch();
        if(request.getParameter("firstname") != null) {
            String firstName = (String)request.getParameter("firstname");
            if(!firstName.isEmpty())
            docSearch.setFirstName(firstName);
        }
        
        if(request.getParameter("middlename") != null) {
            String middleName = (String)request.getParameter("middlename");
            if(!middleName.isEmpty())
            docSearch.setMiddleName(middleName);
        }
        if(request.getParameter("lastname") != null ) {
            String lastName = (String)request.getParameter("lastname");
            if(!lastName.isEmpty())
            docSearch.setLastName(lastName);
        }      
       
        if(request.getParameter("gender") != null) {
            String gender = (String)request.getParameter("gender");
            if(!gender.isEmpty()) {
                docSearch.setGender(gender);
            }
        }
        
        if(request.getParameter("numyears") != null) {
            Calendar now = Calendar.getInstance();
            java.util.Date curTime = now.getTime();
            String numYears = (String)request.getParameter("numyears");
            if(!numYears.isEmpty()) {
                int num_years_ago = Integer.parseInt(request.getParameter("numyears"));
                now.add(Calendar.YEAR, -num_years_ago);
                java.sql.Date licenseYear;
                licenseYear = new java.sql.Date(now.getTime().getTime());
                //Date licenseYear = Date.parse(request.getParameter("num_"))
                docSearch.setLicense_year(licenseYear);
            }
        }
        /*
        if(request.getParameter("specialisation") != null) {
            docSearch.setSpecialisation(request.getParameter("specialisation"));
        }
        if(request.getParameter("street_number") != null) {
            docSearch.setStreetNumber(Integer.parseInt(request.getParameter("street_number")));
        }
        if(request.getParameter("street_name") != null) {
            docSearch.setStreetName(request.getParameter("street_name"));
        }
        if(request.getParameter("postal_code") != null) {
            docSearch.setPostalCode(request.getParameter("postal_code"));
        }
        if(request.getParameter("house_number") != null) {
            docSearch.setHouseNumber(request.getParameter("house_number"));
        }
        if(request.getParameter("city") != null) {
            docSearch.setCity(request.getParameter("city"));
        }
        if(request.getParameter("province") != null) {
            docSearch.setProvince(request.getParameter("province"));
        }
        */
        return docSearch;
    }
    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String url = null;
        try {
            
            DoctorSearch ds = createDoctorSearchObject(request);

            ArrayList<Doctor> listOfDoctors = SearchDBAO.getSearchDoctors(ds);

            request.setAttribute("listOfDoctors", listOfDoctors);
            url = "/doctor_search_results.jsp";
            

            request.getServletContext().getRequestDispatcher(url).forward(request, response);
             /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SearchServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SearchServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SearchServlet.class.getName()).log(Level.SEVERE, null, ex);
            
                request.setAttribute("exception", ex);
            url = "/error.jsp";
            request.getServletContext().getRequestDispatcher(url).forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(SearchServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("exception", ex);
            url = "/error.jsp";
            request.getServletContext().getRequestDispatcher(url).forward(request, response);
        } catch (Exception e) {
                request.setAttribute("exception", e);
                url = "/error.jsp";
                request.getServletContext().getRequestDispatcher(url).forward(request, response);
        }finally {
            out.close();
        } 
        
    }
/**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
