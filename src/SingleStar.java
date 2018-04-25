import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.json.JSONArray;

/**
 * Servlet implementation class Searching
 */
@WebServlet("/SingleStar")
public class SingleStar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SingleStar() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		    System.out.println("In getttt");
		
		    //response.getWriter().append("Served at: ").append(request.getContextPath());
		    String loginUser = "root";
	        String loginPasswd = "GautamSQL123";
	        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";

	        response.setContentType("application/json");    // Response mime type

	        // OutaddProperty stream to STDOUT
	        PrintWriter out = response.getWriter();



	        try
	           {
	              //Class.forName("org.gjt.mm.mysql.Driver");
	              Class.forName("com.mysql.jdbc.Driver").newInstance();

	              Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
	              // Declare our statement
 
		          String starid = request.getParameter("starId");
		          //out.print(starid);
		          ResultSet rs;
	              
	              PreparedStatement query = dbcon.prepareStatement("SELECT stars.id,stars.name, stars.birthYear from stars where stars.id = ?");
	              
	 		      query.setString(1,starid);                
	              rs = query.executeQuery();
	              

	              /*out.println("<TABLE border>");

	              // Iterate through each row of rs
		      out.println("<tr>" +
				  "<td>" + "Star Name" + "</td>" +
				  "<td>" + "Star Birth Year" + "</td>" +
       			  "<td>" + "Movies Appeared" + "</td>"+
				  "</tr>");*/
		      JSONArray jsonArray = new JSONArray();
	              while (rs.next())
	              {   
	            	  JSONObject obj = new JSONObject();
	                  String s_Id = rs.getString("id");	
	                  /*String s_Name = rs.getString("name");
	                  String s_BirthYear = rs.getString("birthYear");*/
	            	  obj.put("name", rs.getString("name"));
	            	  obj.put("birthyear", rs.getString("birthYear"));
	                  /*out.println("<tr>" +
	                              "<td>" + s_Name + "</td>" +
	                              "<td>" + s_BirthYear + "</td>" +
	                              "<td style='width:20%'>");*/
	                  
	                  PreparedStatement gen = dbcon.prepareStatement("Select movies.title,movies.id FROM movies, stars_in_movies,stars where stars.id = stars_in_movies.starID AND stars_in_movies.movieID=movies.id AND stars.id = ?");
	                		 
	                  gen.setString(1,s_Id);
	           
	                  
	                  ResultSet gs = null;
	                  
	   
	                           
	                  gs = (ResultSet) gen.executeQuery();   
	                  //Create a json array for movies and a json object inside the loop. Store this array as an object in the main json object "obj"
	    		      JSONArray mjson = new JSONArray();

	                  while(gs.next()) 
	                  {
		            	  JSONObject mobj = new JSONObject();
                          mobj.put("movie",gs.getString(1));
                          mobj.put("movieId",gs.getString(2));
	                	  mjson.put(mobj);     
	  				  }
	                  gen.close();
	                  obj.put("movies", mjson);
	                  jsonArray.put(obj);
	  				// out.print("</td>");
	  			//out.print("</tr>");
	              }
                  //out.write(jsonArray.toString());
                out.write(jsonArray.toString());

	              // out.print("<td>");
	              //out.print(s);
	              //out.print("</td>");
	             
	              
	              //out.println("</TABLE>");
query.close();
	              rs.close();
	          
	              dbcon.close();
	            }
	        catch (SQLException ex) {
	              while (ex != null) {
	                    System.out.println ("SQL Exception:  " + ex.getMessage ());
	                    ex = ex.getNextException ();
	                }  // end while
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		System.out.println("In posttt");

		doGet(request, response);
	}

}