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
import pack.DbConnection;

/**
 *
 * @author admin
 */
public class ChangeStatus extends HttpServlet {

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
            Connection con = DbConnection.getConn();
            Statement st = con.createStatement();
            
            String appid = request.getParameter("id");
            //String application_id1 = request.getParameter("id1");
            //String application_id2 = request.getParameter("id2");
            
            SmsCode sc = new SmsCode();
            
            if (request.getParameter("Approved") != null) {
                String sql2 = "select * from apply_scheme where applicationno = '" + appid + "'";
                ResultSet rs = st.executeQuery(sql2);
                if (rs.next()) {
                    String mobile = rs.getString("mobileno");
                    String username = rs.getString("username");
                    String scheme = rs.getString("schemename");
                    
                    String sql1 = "update apply_scheme set app_status = 'Approved' where applicationno='" + appid + "'";
                    st.executeUpdate(sql1);
                    
                    String msg = "Dear " + username + " your Application for " + scheme + " Approved by Admin.";
                    
                    sc.sendMessage(mobile, msg);
                    
                    response.sendRedirect("a_report.jsp");
                }
                
            } else if (request.getParameter("Rejected") != null) {
                String sql4 = "select * from apply_scheme where applicationno = '" + appid + "'";
                ResultSet rs1 = st.executeQuery(sql4);
                if (rs1.next()) {
                    String mobile = rs1.getString("mobileno");
                    String username = rs1.getString("username");
                    String scheme = rs1.getString("schemename");
                    
                    String sql3 = "update apply_scheme set app_status = 'Rejected' where applicationno='" + appid + "'";
                    st.executeUpdate(sql3);
                    
                    String msg = "Dear " + username + " your Application for " + scheme + " Rejected by Admin.";
                    
                    sc.sendMessage(mobile, msg);
                    
                    response.sendRedirect("a_report.jsp");
                }
                
            }            
        } catch (Exception e) {
            e.printStackTrace();
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
