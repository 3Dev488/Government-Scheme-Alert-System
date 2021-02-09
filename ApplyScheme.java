/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pack.DbConnection;

/**
 *
 * @author admin
 */
public class ApplyScheme extends HttpServlet {

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
            String userid = request.getParameter("id");
            String schemename = request.getParameter("id1");
            Connection con = DbConnection.getConn();
            Statement st = con.createStatement();

            String sql = "select * from user_reg where userid = '" + userid + "'";
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                String username = rs.getString("username");
                String paddress = rs.getString("p_address");
                String caddress = rs.getString("c_address");
                String mobile = rs.getString("mobile");
                String whatsapp = rs.getString("whatsappno");
                String birthdate = rs.getString("birthdate");
                String gender = rs.getString("gender");
                String state = rs.getString("state");
                String caste = rs.getString("cast");
                String category = rs.getString("category");
                String aadhar = rs.getString("aadharno");
                String edetails = rs.getString("education");

                String sql1 = "select * from add_scheme where scheme_name = '" + schemename + "'";
                ResultSet rs1 = st.executeQuery(sql1);

                if (rs1.next()) {
                    // int appid = 101;
                    Date date = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    String applieddate = formatter.format(date);

                    String schemeid = rs1.getString("s_id");
                    String condition = rs1.getString("condition");
                    String lastdate = rs1.getString("last_date");

                    String sql2 = "select * from apply_scheme where schemeid = '" + schemeid + "' and userid='" + userid + "'";
                    ResultSet rs2 = st.executeQuery(sql2);

                    if (rs2.next()) {
                        out.println("<script>");
                        out.println("alert('You have Already Applied for This Scheme !')");
                        out.println("location='u_schemes.jsp'");
                        out.println("</script>");
                    } else {
                        int aid = 0;
                        String sql4 = "select max(applicationno) as total from apply_scheme ";
                        ResultSet rs4 = st.executeQuery(sql4);
                        if (rs4.next()) {
                            String appid = rs4.getString("total");
                            if (appid == null) {
                                aid = 100;
                            } else {
                                aid = Integer.parseInt(appid);
                                aid++;
                            }
                            String sql3 = "insert into apply_scheme values(0,'" + userid + "', '" + username + "', '" + paddress + "', '" + caddress + "', '" + mobile + "', '" + whatsapp + "', '" + birthdate + "', '" + gender + "', '" + state + "', '" + caste + "', '" + category + "', '" + edetails + "', '" + aadhar + "', '" + schemeid + "', '" + schemename + "', '" + condition + "', '" + applieddate + "','" + aid + "','" + lastdate + "','applied')";
                            int i = st.executeUpdate(sql3);
                            if (i != 0) {
                                out.println("<script>");
                                out.println("alert('Applied Successfully !')");
                                out.println("location='u_schemes.jsp'");
                                out.println("</script>");
                            } else {
                                out.println("<script>");
                                out.println("alert('Something went wrong!')");
                                out.println("location='u_schemes.jsp'");
                                out.println("</script>");
                            }
                        }
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
