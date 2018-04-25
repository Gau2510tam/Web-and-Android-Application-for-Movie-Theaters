

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.lang.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.json.JSONArray;
/**
 * Servlet implementation class Searching
 */
@WebServlet("/SearchAuto")
public class SearchAuto extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchAuto() {
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
	
	     // Time an event in a program to nanosecond precision
	        long startTime = System.nanoTime();
	        /////////////////////////////////
	        /// ** part to be measured ** ///
	        /////////////////////////////////
	       

	        try
	           {
	              //Class.forName("org.gjt.mm.mysql.Driver");
	        	long a1 = System.nanoTime();
	        	Class.forName("com.mysql.jdbc.Driver").newInstance();
				
				Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
				 Statement statement = dbcon.createStatement();
	              // Declare our statement
	        	long a2 = System.nanoTime();

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

	              
		          		          
		          PreparedStatement query = null,query1=null ;
		          try {
		        	  
			          query = dbcon.prepareStatement("SELECT id,title FROM movies WHERE MATCH (title) AGAINST (?  IN BOOLEAN MODE) LIMIT 5");
			          query1 = dbcon.prepareStatement("SELECT id,name FROM stars WHERE MATCH (name) AGAINST (?  IN BOOLEAN MODE) LIMIT 5");
			          StringBuilder query3 = new StringBuilder();
			          StringBuilder query2 = new StringBuilder();

			          if(title.length()>1)
			        	  { query3.append("'");
			        	    query2.append("'");
			        	   String []split = title.split(" ");
			        	  for(int i=0;i<split.length;i++)
			        	  {
			        		  
			        	        			  
			        	  query3.append( " +" + split[i] );
			        	  query2.append( " +" + split[i] );
			        	  
			        	  }
			        	  query3.append("*' ");
			        	  query2.append("*' ");
                          
			        	  query.setString(1, query3.toString());
			        	  query1.setString(1, query2.toString());

			         
		          } 
		          }catch(java.lang.Exception ex) {System.out.println("Exception in query builder");}
		       // Time an event in a program to nanosecond precision
		          long tj1 = 0;
		          long s1 = System.nanoTime();
		          /////////////////////////////////
		          /// ** part to be measured ** ///
		          /////////////////////////////////
		   
		          rs = (ResultSet) query.executeQuery(); 
		          long e1 = System.nanoTime();
		          tj1 += e1 - s1; // elapsed time in nano seconds. Note: print the values in nano seconds  
	              JSONArray jsonArray = new JSONArray();

	              while (rs.next())
	              {
	            	  JSONObject obj = new JSONObject();
	                  String m_ID = rs.getString("id");
	                  obj.put("id", m_ID);
	                  obj.put("title", rs.getString("title"));

					jsonArray.put(obj);  

	              } //big while
            	  JSONObject obj = new JSONObject();
            	  obj.put("id", "entry");
            	  jsonArray.put(obj);
		          long s2 = System.nanoTime();
		          /////////////////////////////////
		          /// ** part to be measured ** ///
		          /////////////////////////////////
		   
		          rs = (ResultSet) query1.executeQuery(); 
		          long e2 = System.nanoTime();
		          tj1 += e2 - s2; // elapsed time in nano seconds. Note: print the values in nano seconds  
	              while (rs.next())
	              {
	            	  obj = new JSONObject();
	                  String s_ID = rs.getString("id");
	                  obj.put("id", s_ID);
	                  obj.put("name", rs.getString("name"));

					jsonArray.put(obj);  

	              }

	              out.write(jsonArray.toString());	
	              //System.out.print(jsonArray.toString());
	              rs.close();
	              query.close();
	              query1.close();
	              dbcon.close();
	              long endTime = System.nanoTime();
	  	          long elapsedTime = endTime - startTime; // elapsed time in nano seconds. Note: print the values in nano seconds 
	  	          
	  	      //List<String> lines = Arrays.asList( title + ";" + elapsedTime + ";" + (tj1/2));

	  	      //Path file = Paths.get("/Project2/Performance.txt");
	  	      //Files.write(file, lines, Charset.forName("UTF-8"));
	  	      //Files.write(file, lines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);

	  	    BufferedWriter bw = null; 
	  	    FileWriter fw = null; 
	  	    try 
	  	    { String data = " This is new content"; 
	  	    File file = new File("/var/lib/tomcat7/webapps/Project2/Performance.txt"); 
	  	  //   File file = new File("C:/Users/gauta/Desktop/PROJECT WORKSPACE/Performance.txt"); 
	  	    if (!file.exists()) { file.createNewFile(); }
	  	    fw = new FileWriter(file.getAbsoluteFile(), true);
	  	    bw = new BufferedWriter(fw);
	  	    bw.write(title + ";" + elapsedTime + ";" + (tj1/2) +  ";" + (a2-a1) + "\n" ); 
	  	    }
	  	    catch (IOException e) { e.printStackTrace(); }
	  	    finally 
	  	    {
	  	    	try { if (bw != null) bw.close();
	  	    	    if (fw != null) fw.close(); 
	  	    	    } 
	  	    	catch (IOException ex)
	  	    	{ ex.printStackTrace(); } }
	  	        
	           
	
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
