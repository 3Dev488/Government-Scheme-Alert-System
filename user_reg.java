/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.HTMLDocument;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import pack.DbConnection;

/**
 *
 * @author Pramod
 */
public class user_reg extends HttpServlet {

    private boolean isMultipart;
    private String filePath;
    private int maxFileSize = 50 * 1024;
    private int maxMemSize = 50 * 1024;
    private File file;
    public String iris = "";
    public String fp = "";

    public void init() {
        // Get the file location where it would be stored.
        filePath = getServletContext().getInitParameter("file-upload");
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            String uid = " ";
            String password = " ";
            String name = " ";
            String paddress = " ";
            String caddress = " ";
            String email = " ";
            String mobile = " ";
            String wmobile = " ";
            String birth = " ";
            String age = " ";
            String gender = " ";
            String state = " ";
            String adhar = " ";
            String cast = " ";
            String category = " ";
            String education = " ";
            String income = " ";
            String savefile;
            String contentType = request.getContentType();
            DiskFileItemFactory factory = new DiskFileItemFactory();

// Set factory constraints
            factory.setSizeThreshold(4012);
//factory.setRepository("c:");

// Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload(factory);

// Set overall request size constraint
            //upload.setSizeMax(10024);
// Parse the request
            List items = null;
            try {
                items = upload.parseRequest(request);
                byte[] data = null;
                String fileName = null;
// Process the uploaded items
                Iterator iter = items.iterator();
                while (iter.hasNext()) {
                    FileItem item = (FileItem) iter.next();

                    if (item.isFormField()) {
                        //processFormField(item);
                        String name1 = item.getFieldName();
                        String value = item.getString();

                        if (name1.equalsIgnoreCase("userid")) {
                            uid = value;
                        } else if (name1.equalsIgnoreCase("password")) {
                            password = value;
                        } else if (name1.equalsIgnoreCase("name1")) {
                            name = value;
                        } else if (name1.equalsIgnoreCase("paddress")) {
                            paddress = value;
                        } else if (name1.equalsIgnoreCase("caddress")) {
                            caddress = value;
                        } else if (name1.equalsIgnoreCase("mobile")) {
                            mobile = value;
                        } else if (name1.equalsIgnoreCase("wmobile")) {
                            wmobile = value;
                        } else if (name1.equalsIgnoreCase("gender")) {
                            gender = value;
                        } else if (name1.equalsIgnoreCase("aadhar")) {
                            adhar = value;
                        } else if (name1.equalsIgnoreCase("age")) {
                            age = value;
                        } else if (name1.equalsIgnoreCase("email")) {
                            email = value;
                        } else if (name1.equalsIgnoreCase("state")) {
                            state = value;
                        } else if (name1.equalsIgnoreCase("caste")) {
                            cast = value;
                        } else if (name1.equalsIgnoreCase("details")) {
                            education = value;
                        } else if (name1.equalsIgnoreCase("datepicker")) {
                            birth = value;
                        } else if (name1.equalsIgnoreCase("income")) {
                            income = value;
                        } else if (name1.equalsIgnoreCase("category")) {
                            category = value;
                        }
                    } else {
                        data = item.get();
                        fileName = item.getName();
                    }
                }
                savefile = fileName;
                String path = request.getSession().getServletContext().getRealPath("/");
                //JOptionPane.showMessageDialog(null, "Path--" + path);
                String patt = path.replace("\\build", "");

                File ff = new File(patt + "\\Profile\\" + savefile);
                // File ff = new File(path + saveFile);
                FileOutputStream fileOut = new FileOutputStream(ff);
                fileOut.write(data, 0, data.length);
                fileOut.flush();
                fileOut.close();
                FileInputStream fis;
                File f = new File(patt + "\\Profile\\" + savefile);

                Connection con = DbConnection.getConn();
                Statement st = con.createStatement();
                PreparedStatement ps = null;

                String sql = "select * from user_reg where userid = '" + uid + "'";
                ResultSet rs = st.executeQuery(sql);

                if (rs.next()) {
                    out.println("<script>");
                    out.println("alert('This User is already exist !')");
                    out.println("location='UserRegistration.jsp'");
                    out.println("</script>");

                } else {
                    PreparedStatement psmnt = null;

                    String sql1 = "insert into user_reg values(0,?,?, ?, ?, ?,?, ?, ?, ?, ?, ?,?,?,?, ?, ?, ?, ?,?)";

                    psmnt = con.prepareStatement(sql1);

                    fis = new FileInputStream(f);
                    psmnt.setString(1, uid);
                    psmnt.setString(2, password);
                    psmnt.setString(3, name);
                    psmnt.setString(4, paddress);
                    psmnt.setString(5, caddress);
                    psmnt.setString(6, mobile);
                    psmnt.setString(7, wmobile);
                    psmnt.setString(8, birth);
                    psmnt.setString(9, gender);
                    psmnt.setString(10, state);
                    psmnt.setString(11, cast);
                    psmnt.setString(12, category);
                    psmnt.setString(13, education);
                    psmnt.setString(14, adhar);
                    psmnt.setString(15, email);
                    psmnt.setString(16, income);
                    psmnt.setBinaryStream(17, (InputStream) fis, (int) (f.length()));
                    psmnt.setString(18, age);
                    psmnt.setString(19, savefile);
                    int i = psmnt.executeUpdate();

                    if (i != 0) {

                        out.println("<script>");
                        out.println("alert('New User Registered Successfully !')");
                        out.println("location='index.html'");
                        out.println("</script>");
                    } else {
                        out.println("<script>");
                        out.println("alert('Something went wrong!')");
                        out.println("location='index.html'");
                        out.println("</script>");
                    }
                }
            } catch (FileUploadException e) {
                e.printStackTrace();
            }

        } catch (Exception ee) {
            ee.printStackTrace();
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
