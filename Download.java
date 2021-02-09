/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author admin
 */
public class Download extends javax.servlet.http.HttpServlet implements
        javax.servlet.Servlet {

    static final long serialVersionUID = 1L;
    private static final int BUFSIZE = 4096;
    private String filePath;

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        //PrintWriter out = response.getWriter();

        ServletContext sc = this.getServletContext();
        String sg1 = sc.getRealPath("/");
        String fname1 = sg1.substring(0, sg1.indexOf("build"));
        filePath = fname1 + "web/Application Form/";

        String fileName = "Government Application Form.pdf";

        int length = 0;
        ServletOutputStream outStream = response.getOutputStream();

        // sets response content type
        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition", "attachment;fileName=\"" + fileName + "\"");

        byte[] byteBuffer = new byte[BUFSIZE];
        DataInputStream in = new DataInputStream(new FileInputStream(filePath + fileName));

        // reads the file's bytes and writes them to the response stream
        while ((in != null) && ((length = in.read(byteBuffer)) != -1)) {
            outStream.write(byteBuffer, 0, length);
            outStream.println("<script>");
            outStream.println("alert('Patient Registered Successfully !')");
            outStream.println("location='HealthRecord.jsp'");
            outStream.println("</script>");
        }

        in.close();
        outStream.close();
    }
}
