/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import com.darwinsys.spdf.MoveTo;
import com.darwinsys.spdf.PDF;
import com.darwinsys.spdf.Page;
import com.darwinsys.spdf.Text;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pack.DbConnection;

/**
 *
 * @author admin
 */
public class AddScheme extends HttpServlet {

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
        response.setContentType("application/pdf");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        List<String> data = new ArrayList();
        try {
            /* TODO output your page here. You may use following sample code. */
            String scheme_id = request.getParameter("s_id");
            String scheme_name = request.getParameter("s_name");
            String startdate = request.getParameter("sdate");
            String lastdate = request.getParameter("ldate");
            String caste = request.getParameter("c_condition");
            String income = request.getParameter("s_condition");
            String age1 = request.getParameter("age_from");
            String age2 = request.getParameter("age_to");
            int agecr1 = Integer.parseInt(age1);
            int agecr2 = Integer.parseInt(age2);
            int a_income = Integer.parseInt(income);

            String filename = request.getParameter("s_name");
            String filename1 = filename + ".txt";

            Connection con = DbConnection.getConn();
            Statement st = con.createStatement();
            Statement st1 = con.createStatement();

            ServletContext sc1 = this.getServletContext();
            String sg1 = sc1.getRealPath("/");
            String fname1 = sg1.substring(0, sg1.indexOf("build"));
            String filePath = fname1 + "web/TextFiles/";

            SmsCode sc = new SmsCode();

            String sql = "select * from add_scheme where s_id = '" + scheme_id + "'";
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                out.println("<script>");
                out.println("alert('This Scheme is already exist !')");
                out.println("location='a_scheme.jsp'");
                out.println("</script>");

            } else {
                String sql1 = "insert into add_scheme values (0,'" + scheme_id + "', '" + scheme_name + "', '" + startdate + "', '" + lastdate + "', '" + income + "', '" + age1 + "', '" + age2 + "', '" + caste + "', '" + filename1 + "')";
                int i = st.executeUpdate(sql1);
                if (i != 0) {
                    data.add("Scheme Id : " + scheme_id + "\n Scheme Name : " + scheme_name + "\n Starting Date : " + startdate + "\n Last Date : " + lastdate + "\n Maximum Annual Income : " + income + "\n Minimum Age : " + age1 + "\n Maximum Age : " + age2 + "\n Cast Category" + caste);
                    writeToFile(data, filePath + scheme_name + ".txt");

                    String sql2 = "select * from user_reg where age>='" + agecr1 + "' and age<='" + agecr2 + "' and category='" + caste + "' and income<='" + a_income + "'";
                    ResultSet rs2 = st.executeQuery(sql2);
                    while (rs2.next()) {
                        String mobile = rs2.getString("mobile");
                        String user = rs2.getString("username");

                        String msg = "Dear " + user + " New Scheme " + scheme_name + " Added by Government. It's Starting Date is " + startdate + " and Closing on" + lastdate + " to Know more about Scheme Please Visit Government Scheme Alert System Website.";
                        sc.sendMessage(mobile, msg);

                        String sql3 = "insert into sms(scheme_name, user, mobile)values('" + scheme_name + "', '" + user + "', '" + mobile + "')";
                        int j = st1.executeUpdate(sql3);

                    }
                    out.println("<script>");
                    out.println("alert('New Scheme Added Successfully !')");
                    out.println("location='a_scheme.jsp'");
                    out.println("</script>");
                } else {
                    out.println("<script>");
                    out.println("alert('Something went wrong!')");
                    out.println("location='a_scheme.jsp'");
                    out.println("</script>");
                }
            }
        } catch (Exception e) {
            out.println(e);
        } finally {
            out.close();
        }
    }

    private static void writeToFile(java.util.List list, String path) {
        BufferedWriter out = null;
        try {
            File file = new File(path);
            out = new BufferedWriter(new FileWriter(file, true));
            for (Object s : list) {
                out.write((String) s);
                out.newLine();
            }
            out.close();
        } catch (IOException e) {
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
