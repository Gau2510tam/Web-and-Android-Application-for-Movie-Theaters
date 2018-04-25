

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.lang.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.json.JSONArray;
/**
 * Servlet implementation class Searching
 */
@WebServlet("/AndroidSearch")
public class AndroidSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AndroidSearch() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		  //  response.getWriter().append("Served at: ").append(request.getContextPath());
		    String loginUser = "root";
	        String loginPasswd = "GautamSQL123";
	        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";

	        response.setContentType("application/json");    // Response mime type

	        // Output stream to STDOUT
	        PrintWriter out = response.getWriter();

	        // out.println("<HTML><HEAD><TITLE>MovieDB: Found Records</TITLE></HEAD>");
	       // out.println("<BODY><H1>MovieDB: Found Records</H1>");
	        String query1="" ;
	        String query3="" ;

	        try
	           {
	              //Class.forName("org.gjt.mm.mysql.Driver");
	              Class.forName("com.mysql.jdbc.Driver").newInstance();

	              Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
	              // Declare our statement
	              Statement statement = dbcon.createStatement();

		          String title = "";
		          try {
		        	  title = request.getParameter("title");
		        	  
		          } catch(java.lang.Exception exe) {
		        	  
		          }

		          //out.print(title);
		          //out.print(director);
		          //out.print(year);
		          //out.print(starname);
		          ResultSet rs = null;

	              
		          		          
		          StringBuilder query = new StringBuilder();
		          StringBuilder query2 = new StringBuilder();
		          try {
		        	  
			          query.append("SELECT * FROM movies WHERE MATCH (title) AGAINST (' ");
			      			          if(title.length()>1)
			        	  { String []split = title.split(" ");
			        	  for(int i=0;i<split.length;i++)
			        	  {
			        		  
			        	        			  
			        	  query.append( " +" + split[i] );
			        
			        	  
			        	  }
			        	  query.append("*' IN BOOLEAN MODE)");
			        	
			        	 query1  = query.toString();
			    
			          

			         
		          } 
		          }catch(java.lang.Exception ex) {System.out.println("Exception in query builder");}
		          rs = (ResultSet) statement.executeQuery(query1); 
		          
		          JSONArray jsonArray = new JSONArray();

	              while (rs.next())
	              {
	            	  JSONObject obj = new JSONObject();
	                  String m_ID = rs.getString("id");
	                  obj.put("id", m_ID);
	                  obj.put("title", rs.getString("title"));
	            	  obj.put("year", rs.getString("year"));
	            	  obj.put("director", rs.getString("Director"));
	            	  
	                 
                      
	                  //String m_Title = rs.getString("title");
	                  //String m_Year = rs.getString("year");
	                  //String m_Director = rs.getString("Director");
	                  /*out.println("<tr>" +
	                              "<td>" + m_ID + "</td>" +
	                              "<td>" + m_Title + "</td>" +
	                              "<td>" + m_Year + "</td>" +
	                              "<td>" + m_Director + "</td>" +
	                              "<td style='width:20%'>");*/
	                  
	                  String gen = "Select genres.name FROM genres, genres_in_movies,movies where genres.id = genres_in_movies.genreID AND genres_in_movies.movieID=movies.id AND movies.id = '"+ m_ID +"'";
	                		 
	                  
	                  Statement gstatement = null;
	                  
	                  ResultSet gs = null;
	                  System.out.println("Check Point 2");
	                  gstatement = (Statement) dbcon.createStatement();
	                  JSONArray gjson = new JSONArray();
	                  try {         
	                	  gs = (ResultSet) gstatement.executeQuery(gen);  
	                	  while(gs.next()) 
		                  {   JSONObject gobj = new JSONObject();
		                	  gobj.put("genre",gs.getString(1));
	                	      gjson.put(gobj);
		  
		  				  }
	                  }catch(SQLException ex) {}
	                  
	                  
	                  obj.put("genres", gjson);
	  			//	 out.print("</td>");
	  				// out.print("<td>");
	  				String SString = "Select stars.name,stars.id FROM stars, stars_in_movies,movies where stars.id = stars_in_movies.starId AND stars_in_movies.movieId=movies.id AND movies.id = '" + m_ID + "'" ;
					Statement sstatement = null;
					ResultSet ss = null;
					System.out.println("Check Point 3");
					sstatement = (Statement) dbcon.createStatement();
					JSONArray sjson = new JSONArray();
					try {
						ss = (ResultSet) sstatement.executeQuery(SString);
						
						while(ss.next()) 
						{   JSONObject sobj = new JSONObject();
						    sobj.put("star",ss.getString(1));
						    sobj.put("starId",ss.getString(2));
						    sjson.put(sobj);
	
						}
	              	}catch(SQLException ex) {}
					obj.put("stars", sjson);	
	                		  
					//out.print("</td>");		  
					
	         		//out.print("</tr>");
					jsonArray.put(obj);  

	              } //big while
	              out.write(jsonArray.toString());	
	              //System.out.print(jsonArray.toString());
	              rs.close();
	              statement.close();
	              dbcon.close();
	            }
	           
	        catch (SQLException ex) {
	              while (ex != null) {
	                    System.out.println ("SQL Exception:  " + ex.getMessage ());
	                    ex = ex.getNextException ();
	                }  // end while
	            }  // end catch SQLException

	        catch(java.lang.Exception exe)
	            {
	        		//System.out.println(exe.getMessage());
	                out.println("<HTML>" +
	                            "<HEAD><TITLE>" +
	                            "MovieDB: Error" +
	                            "</TITLE></HEAD>\n<BODY>" +
	                            "<P>SQL error in doGet: " +
	                            exe.getMessage() + "</P></BODY></HTML>");
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
