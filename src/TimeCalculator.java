import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
@WebServlet("/TimeCalculator")
public class TimeCalculator extends HttpServlet
{

    public String getServletInfo()
    {
        return "Servlet connected";
    }
    
    public void doGet(HttpServletRequest request , HttpServletResponse response)
        throws IOException, ServletException
    {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        String filePath = "/var/lib/tomcat7/webapps/Project2/Performance.txt";

        try{

            BufferedReader in = new BufferedReader(new FileReader(filePath));
            String str;
            long valTS=0,valTJ=0,count=0,valTC=0;
            while ((str = in.readLine()) != null){
                count++;
                int strc=0;
                for (String retval: str.split(";")) {
                    strc++;
                    if(strc==1) out.println("<div>"+str+"</div>");
                    // System.out.println(retval);    out.println("<div>Parsed"+Integer.parseInt(retval.substring(3))+" NP:"+retval+"</div>"); 
                    if(strc==2){ valTS+=Integer.parseInt(retval); }
                    else if(strc==3) valTJ+=Integer.parseInt(retval);
                    else if (strc == 4) valTC+= Integer.parseInt(retval);
                }  
            }
            out.println("Count:"+count);
            float avgTS=valTS/(count*1000000), avgTJ= valTJ/(count*1000000), avgTC= valTC/(count*1000000);
            out.println("Average TS:"+avgTS+"ms Average TJ:"+avgTJ+"ms + Average TC:"+avgTC+"ms");
            in.close();
        } catch (IOException e) {
        }

        // long endTime   = System.currentTimeMillis();
        // long totalTime = endTime - startTime;
        // long mins=(totalTime/1000)/60;
        // long secs=(totalTime/1000)%60;
        // System.out.println("\nTotal Time Taken by the time to parse and populate the database: "+mins+"mins "+secs+"secs.");

    }

}