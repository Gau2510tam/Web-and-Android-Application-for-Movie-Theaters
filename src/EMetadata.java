

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * Servlet implementation class EMetadata
 */
@WebServlet("/EMetadata")
public class EMetadata extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EMetadata() {
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
			 java.sql.DatabaseMetaData databaseMetaData = dbcon.getMetaData();
             String   catalog          = null;
             String   schemaPattern    = null;
             String   tableNamePattern = null;
             String[] types            = null;
        ResultSet result = databaseMetaData.getTables(catalog, schemaPattern, tableNamePattern, types );
        out.println("<div style='display:inline-flex;'>");
        while(result.next()) 
        {
            String tableName = result.getString(3);
            System.out.println(tableName);
            String   table_catalog           = null;
            String   table_schemaPattern     = null;
            String   tableNameGiven  = result.getString(3);
            String   columnNamePattern = null;
            ResultSet result2 = databaseMetaData.getColumns(table_catalog, table_schemaPattern, tableName, columnNamePattern);
            out.println("<div style='margin:15px;'><div>" +  tableNameGiven + "</div><br/>");
            while(result2.next())
            {
                String columnName = result2.getString(4);
                int    columnType = result2.getInt(5);
                String columnDataType="";
                if(columnType==4) columnDataType="Integer";
                else if(columnType==12) columnDataType="String";
                else if(columnType==91) columnDataType="Date";
                out.println("<div>" +  columnName  + ":" +   columnDataType + "</div>");

            }
            out.println("</div>");
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
