/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pack.DbConnection;

/**
 *
 * @author admin
 */
public class Login extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
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
        try {
            String usertype = request.getParameter("usertype");
            String uid = request.getParameter("userid");
            String pass = request.getParameter("password");

            Connection con = DbConnection.getConn();
            Statement st = con.createStatement();
            Statement st1 = con.createStatement();

            if (usertype.equals("admin")) {
                if (uid.equals("admin")) {
                    ResultSet rt1 = st1.executeQuery("select * from admin where userid='" + uid + "'");
                    if (rt1.next()) {
                        String p1 = rt1.getString("password");
                        if (pass.equals(p1)) {
                            HttpSession user = request.getSession(true);
                            user.setAttribute("userid", "admin");
                            response.sendRedirect("admin_page.jsp");
                        } else {
                            out.println("<script>");
                            out.println("alert('Incorrect User Id or Password !')");
                            out.println("location='index.html'");
                            out.println("</script>");
                        }
                    }

                }
            } else {

                ResultSet rt = st.executeQuery("select * from user_reg where userid='" + uid + "'");
                if (rt.next()) {
                    String p = rt.getString("password");
                    String name = rt.getString("userid");
                    if (pass.equals(p)) {
                        HttpSession user = request.getSession(true);
                        user.setAttribute("userid", name);
                        response.sendRedirect("user_page.jsp");
                    } else {
                        out.println("<script>");
                        out.println("alert('Incorrect Password !')");
                        out.println("location='index.html'");
                        out.println("</script>");
                    }
                } else {
                    out.println("<script>");
                    out.println("alert('Something Went Wrong !')");
                    out.println("location='index.html'");
                    out.println("</script>");
                }
            }
        } catch (Exception e) {
            out.println(e);
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
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
     * Handles the HTTP <code>POST</code> method.
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
