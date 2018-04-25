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
@WebServlet("/AndroidLogin")
public class AndroidLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{   PrintWriter out= response.getWriter();
		System.out.println("In getttt");
	
		
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
		
		String query = " SELECT email,Password from customers where email = '" + email + "' AND Password = '" + pwd + "' ";
		
		ResultSet rs;
		rs = statement.executeQuery(query);
		
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
			out.write("True");
		
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
        	
       		
			out.write("False");
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