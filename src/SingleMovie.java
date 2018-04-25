

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

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.json.JSONArray;

/**
 * Servlet implementation class Searching
 */
@WebServlet("/SingleMovie")
public class SingleMovie extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SingleMovie() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		    String loginUser = "root";
	        String loginPasswd = "GautamSQL123";
	        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";

	        response.setContentType("application/json");    // Response mime type

	        // Output stream to STDOUT
	        PrintWriter out = response.getWriter();

	        //out.println("<HTML><HEAD><TITLE>MovieDB: Found Records</TITLE></HEAD>");
	        //out.println("<BODY><H1>MovieDB: Found Records</H1>");


	        try
	           {
	              //Class.forName("org.gjt.mm.mysql.Driver");
	              Class.forName("com.mysql.jdbc.Driver").newInstance();

	              Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
	              // Declare our statement

		          String movieid = request.getParameter("movieId");
		          out.print(movieid);
		          ResultSet rs;
	              
	              PreparedStatement query = dbcon.prepareStatement("SELECT movies.id,movies.title,movies.year,movies.Director, ratings.rating from movies,ratings where movies.id=ratings.movieId AND movies.id  = ?");
	 		      query.setString(1, movieid);                 
	              rs = query.executeQuery();
	              
	              

	              // Perform the query
	             
	           	             
	              
	              //out.println("<TABLE border>");

	              // Iterate through each row of rs
		      /*out.println("<tr>" +
				  "<td>" + "ID" + "</td>" +
				  "<td>" + "Title" + "</td>" +
				  "<td>" + "Year" + "</td>" +
				  "<td>" + "Director" + "</td>" +
				  "<td>" + "Rating" + "</td>"+
				  "<td>" + "Genres" + "</td>"+
				  "<td>" + "Stars" + "</td>"+
				  "</tr>");*/
	              JSONArray jsonArray = new JSONArray();

	              while (rs.next())
	             {
	            	  JSONObject obj = new JSONObject();
	            	  String m_ID = rs.getString("id");
	                  obj.put("id", m_ID);
	                  obj.put("title", rs.getString("title"));
	            	  obj.put("year", rs.getString("year"));
	            	  obj.put("director", rs.getString("Director"));
	            	  obj.put("rating", rs.getString("rating"));

	                  /*String m_ID = rs.getString("id");
	                  String m_Title = rs.getString("title");
	                  String m_Year = rs.getString("year");
	                  String m_Director = rs.getString("Director");
	                  String m_Rating = rs.getString("rating");*/
	                  /*out.println("<tr>" +
	                              "<td>" + m_ID + "</td>" +
	                              "<td>" + m_Title + "</td>" +
	                              "<td>" + m_Year + "</td>" +
	                              "<td>" + m_Director + "</td>" +
	                              "<td>" + m_Rating + "</td>" +
	                              "<td style='width:20%'>");*/
	                  
	                  PreparedStatement gen = dbcon.prepareStatement("Select genres.name FROM genres, genres_in_movies,movies where genres.id = genres_in_movies.genreID AND genres_in_movies.movieID=movies.id AND movies.id = ?");
	                	
	                  gen.setString(1, m_ID);
	                  
	  
	                  
	                  ResultSet gs = null;
	                  
	  
	                           
	                  gs = (ResultSet) gen.executeQuery();   
	                  JSONArray gjson = new JSONArray();
	                  while(gs.next()) 
	                  {   JSONObject gobj = new JSONObject();
	                	  gobj.put("genre",gs.getString(1));
                	      gjson.put(gobj);
	                     //out.print(gs.getObject(1)+ " ");     
	  				  }
	                  gen.close();
	                  obj.put("genres", gjson);
	  				// out.print("</td>");
	  				// out.print("<td>");
	                PreparedStatement SString = dbcon.prepareStatement("Select stars.name,stars.id FROM stars, stars_in_movies,movies where stars.id = stars_in_movies.starId AND stars_in_movies.movieId=movies.id AND movies.id =  ? ") ;
			         
	                SString.setString(1, m_ID);
					ResultSet ss = null;
	
					ss = (ResultSet) SString.executeQuery();
					JSONArray sjson = new JSONArray();
					while(ss.next()) 
					{   JSONObject sobj = new JSONObject();
					    sobj.put("star",ss.getString(1));
					    sobj.put("starId",ss.getString(2));
					    sjson.put(sobj);
					    //obj.put("stars",ss.getObject(1)+ " ");

						//out.print(ss.getObject(1)+ " ");
					}
					obj.put("stars", sjson);		  
					//out.print("</td>");		  
					
	         		//out.print("</tr>");
					jsonArray.put(obj);  
					SString.close();
	             }
	              
	              
	             // out.println("</TABLE>");
	             /* String s = jsonArray.toString();
	              request.getSession().setAttribute("jsonArray",s);
	              response.sendRedirect(" ");*/
	               out.write(jsonArray.toString());
	              //System.out.println(jsonArray.toString());
	              rs.close();
	              query.close();
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
		doGet(request, response);
	}

}