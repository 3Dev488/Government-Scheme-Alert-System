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
import pack.MailUtil;

/**
 *
 * @author admin
 */
public class ForgetPass extends HttpServlet {

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
            String userid = request.getParameter("user_id");
            String email = request.getParameter("femail");

            Connection con = DbConnection.getConn();
            Statement st = con.createStatement();

            String sql2 = "select * from user_reg where userid = '" + userid + "'";
            ResultSet rs2 = st.executeQuery(sql2);
            if (rs2.next()) {
                String dmail = rs2.getString("email");
                String passwd = rs2.getString("password");
                if (dmail.equals(email)) {
                    String msg = "Hii " + userid + "...... \n Your Password is : " + passwd;
                    String mail[] = {email};
                    MailUtil mm = new MailUtil();
                    mm.sendMail(mail, mail, "Your Password", msg);
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Mail sent successfully!');");
                    out.println("location='index.html';");
                    out.println("</script>");
                    return;
                } else {
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Email Id is Wrong!');");
                    out.println("location='index.html';");
                    out.println("</script>");
                }
            } else {
                out.println("<script type=\"text/javascript\">");
                out.println("aler('Email ID is wrong!');");
                out.println("location='index.html';");
                out.println("</script>");
                return;
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
