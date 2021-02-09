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
public class ChangePass extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. */
            HttpSession user = request.getSession();
            String uid = user.getAttribute("userid").toString();

            String passwd = request.getParameter("pass");
            String passnew = request.getParameter("newpass");

            Connection con = DbConnection.getConn();
            Statement st = con.createStatement();

            String sql = "select * from admin where userid = '" + uid + "'";
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                String pass = rs.getString("password");

                if (passwd.equals(pass)) {
                    String sql1 = "update admin set password ='" + passnew + "' where userid = '"+uid+"' ";
                   int update = st.executeUpdate(sql1);
                   
                   if (update != 0) {
                    out.println("<script>");
                    out.println("alert('Password Changed Successfully !')");
                    out.println("location='index.html'");
                    out.println("</script>");
                } else {
                    out.println("<script>");
                    out.println("alert('Re-Enter New Password Correctly!')");
                    out.println("location='change_pass.jsp'");
                    out.println("</script>");
                }
                   
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
