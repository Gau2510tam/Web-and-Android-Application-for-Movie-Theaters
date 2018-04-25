

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Emp
 */
@WebServlet("/Emp")
public class Emp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Emp() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		   
		// TODO Auto-generated method stub
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
				 ResultSet rs;
			String sname = request.getParameter("sname");
			String sbyear = request.getParameter("sbyear");
		
			PreparedStatement  query1 = dbcon.prepareStatement("select id from stars ORDER BY id desc LIMIT 1");
		  
			rs = query1.executeQuery();
			rs.next();
	        String sid = rs.getString("id");
 			String id = sid.substring(0,2) + Integer.toString((Integer.parseInt(sid.substring(2,9))+1));
		
			PreparedStatement query = dbcon.prepareStatement("INSERT INTO stars(id, name, birthYear) values(? , ? , ?)") ;
            query.setString(1, id);
            query.setString(2, sname);
            query.setString(3, sbyear);
			
			query.executeUpdate();
			
		out.println("<td>" +  "New Star Inserted Sucessfully" + "</td>");
	      }
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
	    
	    /**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
