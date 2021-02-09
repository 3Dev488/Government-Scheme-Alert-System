/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Properties;

/**
 *
 * @author admin
 */
public class SmsCode {

    String postData = "";
    String retval = "";
    String Username = "richhelps1234";
    String Password = "richhelps1234";
    String MobileNo = "9503986854";
    String Message = "Testing message";
    String SenderID = "VSNHLP";
    String priority = "ndnd";
    String stype = "normal";

    public void sendMessage(String phone, String message) {
        try {

            this.Message = message;
            this.MobileNo = phone;
            postData += "user=" + Username + "&pass=" + Password + "&phone="
                    + MobileNo + "&sender=" + SenderID + "&text=" + Message + "&priority=" + priority + "&stype=" + stype;
            URL url = new URL("http://bhashsms.com/api/sendmsg.php?");
            HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
            urlconnection.setRequestMethod("POST");
            urlconnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlconnection.setDoOutput(true);
            OutputStreamWriter out = new OutputStreamWriter(urlconnection.getOutputStream());
            out.write(postData);
            out.close();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlconnection.getInputStream()));
            String decodedString;
            while ((decodedString = in.readLine()) != null) {
                retval += decodedString;
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append("hello");
        System.err.println("" + hash("Hello"));

    }

    private static String hash(String sb) {
        //Implement hash (MD5)

        String md5 = null;
        if (null == sb) {
            return null;
        }
        try {

            //Create MessageDigest object for MD5
            MessageDigest digest = MessageDigest.getInstance("MD5");

            //Update input string in message digest
            digest.update(sb.toString().getBytes(), 0, sb.length());

            //Converts message digest value in base 16 (hex)
            md5 = new BigInteger(1, digest.digest()).toString(16);

        } catch (Exception e) {

            e.printStackTrace();
        }

        return md5.toString();
    }

}
