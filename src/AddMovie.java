

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AddMovie
 */
@WebServlet("/AddMovie")
public class AddMovie extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddMovie() {
        super();
        // TODO Auto-generated constructor stub
    }

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
	        String title = request.getParameter("title");
			String sname = request.getParameter("sname");
			String genre = request.getParameter("genre");
		
			String  query1 = "select id from movies ORDER BY id desc LIMIT 1";
			rs = statement.executeQuery(query1);
			rs.next();
	        String mid = rs.getString("id");
 			String id1 = mid.substring(0,3) + Integer.toString((Integer.parseInt(mid.substring(3,9))+1));
 			
 			String  query2 = "select id from stars ORDER BY id desc LIMIT 1";
			rs = statement.executeQuery(query2);
			rs.next();
	        String sid = rs.getString("id");
 			String id2 = sid.substring(0,3) + Integer.toString((Integer.parseInt(sid.substring(3,9))+1));

 			String  query3 = "select id from genres ORDER BY id desc LIMIT 1";
			rs = statement.executeQuery(query3);
			rs.next();
	        String gid = rs.getString("id");
 			String id3 = Integer.toString((Integer.parseInt(gid)+1));

CallableStatement cs = null;
	        try {
	            cs = dbcon.prepareCall("{call add_movie(?,?,?,?,?,?,?,?,?)}");
	            cs.setString(1,title);
	            cs.setString(2,sname);
	            cs.setString(3,genre);
	            cs.setString(4,id1);
	            cs.setString(5,id2);
	            cs.setString(6,id3);
	            cs.registerOutParameter(7, Types.VARCHAR);
	            cs.registerOutParameter(8, Types.VARCHAR);
	            cs.registerOutParameter(9, Types.VARCHAR);
	            boolean result=cs.execute();
	            String str1 = cs.getString(7);
	            String str2 = cs.getString(8);
	            String str3 = cs.getString(9);
		out.println("<div>" + str1  + "<br>" +  str2 + "<br>" +  str3  + "</div>" );
	            //out.println("<td>" +  "New Star Inserted Sucessfully" + "</td>");
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