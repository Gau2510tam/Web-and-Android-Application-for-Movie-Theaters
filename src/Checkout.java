import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/Checkout")
public class Checkout extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("In getttt");
		
	   // response.getWriter().append("Served at: ").append(request.getContextPath());
	    String loginUser = "root";
        String loginPasswd = "GautamSQL123";
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
        PrintWriter out= response.getWriter();
        response.setContentType("text/html");  
        // Response mime type
        try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			
			Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
			 Statement statement = dbcon.createStatement();
			 System.out.println("Before");
        String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String email = request.getParameter("email");
		String ccid =  request.getParameter("ccid");
		String address = request.getParameter("address");
	    
		String movies = request.getParameter("movieIDs");
		String []split = movies.split(" ");
		//System.out.println(movies);
		//DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		//Date Date = Date.now();
		//System.out.println(dtf.format(localDate));
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = new java.util.Date();
        String strdate=df.format(date);
        java.util.Date sale=df.parse(strdate);
        java.sql.Date saledate = new java.sql.Date(sale.getTime());
		
		PreparedStatement query = dbcon.prepareStatement(" SELECT firstName,lastName, email, ccId, id, address from customers where firstName = ? AND lastName = ? AND ccId = ? AND email = ? AND address = ? ");
		query.setString(1,fname );
		query.setString(2,lname );
		query.setString(3,email );
		query.setString(4,ccid );
		query.setString(5,address );
		ResultSet rs;
		rs =query.executeQuery();
		
		rs.next();
		System.out.println("After");
		String Fname = rs.getString("firstName");
		String Lname = rs.getString("lastName");
		String Email = rs.getString("email");
		String CCID =  rs.getString("ccId");
		String Address = rs.getString("address");
		String Cid =    rs.getString("id");
		
		
		if(Fname.equals(fname) && Lname.equals(lname) && Email.equals(email) && CCID.equals(ccid) && Address.equals(address))
		{
			System.out.println("YES");
			for(String s:split)
			{
				PreparedStatement stmt=dbcon.prepareStatement("insert into sales values(DEFAULT,?,?,?)");  
				stmt.setString(1,Cid);//1 specifies the first parameter in the query  
				stmt.setDate(3, saledate);
				stmt.setString(2,s);
				System.out.println(s);
			
				int i=stmt.executeUpdate();  
				stmt.close();
				System.out.println(i);
			}
			
			
			/*Cookie loginCookie = new Cookie("user",email);
			//setting cookie to expiry in 30 mins
			request.getSession();
			loginCookie.setMaxAge(30*60);
			response.addCookie(loginCookie);*/
			out.write("1");
			//response.sendRedirect("/Project2/order.html");
		}/*else{
			System.out.println("asd");
			RequestDispatcher rd = getServletContext().getRequestDispatcher("index.html");
			
			out.println("<font color=red>Either user name or password is wrong.</font>");
			rd.include(request, response);
		}*/
		rs.close();
        query.close();
        dbcon.close();
		}
	        
        catch (SQLException ex) {
            //while (ex != null) {
              //    System.out.println ("SQL Exception:  " + ex.getMessage ());
                //  ex = ex.getNextException ();
        	
            //RequestDispatcher rd = getServletContext().getRequestDispatcher("/checkout.html");
            out.write("0");
            System.out.println("not success");
			//out.println("<font color=red> The entered details are incorrect </font>");
			//rd.include(request, response);
                // end while
          }  // end catch SQLException

      catch(java.lang.Exception ex)
          {
              out.println("<HTML>" +
                          "<HEAD><TITLE>" +
                          "MovieDB: Error" +
                          "</TITLE></HEAD>\n<BODY>" +
                          "<P>SQL error in doGet: " +
                          ex.getMessage() + "</P></BODY></HTML>");
              return;
          }
        out.close();
}
}