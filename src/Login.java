import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
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
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{   PrintWriter out= response.getWriter();
		System.out.println("In getttt");
		String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
		System.out.println("gRecaptchaResponse=" + gRecaptchaResponse);
		// Verify CAPTCHA.
		boolean valid = VerifyUtils.verify(gRecaptchaResponse);
		if (!valid) {
		    //errorString = "Captcha invalid!";
		    out.println("<HTML>" +
				"<HEAD><TITLE>" +
				"MovieDB: Error" +
				"</TITLE></HEAD>\n<BODY>" +
				"<P>Recaptcha WRONG!!!! </P></BODY></HTML>");
		    return;
		}
		
	   // response.getWriter().append("Served at: ").append(request.getContextPath());
	    String loginUser = "root";
        String loginPasswd = "GautamSQL123";
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
        response.setContentType("text/html");  
        // Response mime type
    try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			
			Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
			 Statement statement = dbcon.createStatement();
			
        String email = request.getParameter("email");
		String pwd = request.getParameter("password");
		String type = request.getParameter("type");
		
		if(type.equals("1"))
	{	
		PreparedStatement query = dbcon.prepareStatement(" SELECT email,Password from customers where email = ? AND Password = ? ");
		query.setString(1, email);
		query.setString(2, pwd);
		ResultSet rs;
		rs = query.executeQuery();
		
		rs.next();
		String userID = rs.getString("email");
		String password = rs.getString("Password");
		
		
		if(userID.equals(email) && password.equals(pwd))
		{
			Cookie loginCookie = new Cookie("user",email);
			//setting cookie to expiry in 30 mins
			request.getSession();
			loginCookie.setMaxAge(30*60);
			response.addCookie(loginCookie);
			response.sendRedirect("/Project2/SortAndSearch.html");
		
		}
		
	}
		
		else if(type.equals("2"))
		{
			PreparedStatement query = dbcon.prepareStatement(" SELECT email,Password from employees where email = ? AND Password = ? ");
			query.setString(1, email);
			query.setString(2, pwd);
			ResultSet rs;
			rs = query.executeQuery();
			
			rs.next();
			String userID = rs.getString("email");
			String password = rs.getString("password");
			
			
			if(userID.equals(email) && password.equals(pwd))
			 {
				Cookie loginCookie = new Cookie("user",email);
				//setting cookie to expiry in 30 mins
				request.getSession();
				loginCookie.setMaxAge(30*60);
				response.addCookie(loginCookie);
				response.sendRedirect("/Project2/Empfun.html");
		     }
		}	
		/*else{
			System.out.println("asd");
			RequestDispatcher rd = getServletContext().getRequestDispatcher("index.html");
			
			out.println("<font color=red>Either user name or password is wrong.</font>");
			rd.include(request, response);
		}*/
		}
	        
        catch (SQLException ex) {
            //while (ex != null) {
              //    System.out.println ("SQL Exception:  " + ex.getMessage ());
                //  ex = ex.getNextException ();
        	
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
			
			out.println("<font color=red>Either user name or password is wrong</font>");
			rd.include(request, response);
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

}
}