
import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DOMParser {

    static List<Actor> myActorls;
    static List<Casts> myCastls;
    static List<Mains> myMainsls;
    Document actordom;
    Document castdom;
    Document mainsdom;

    public DOMParser() {
        //create a list to hold the Actor objects
        myActorls = new ArrayList<>();
        myCastls = new ArrayList<>();
        myMainsls = new ArrayList<>();
        
    }

    public void runExample() {

        //parse the xml file and get the dom object
        parseXmlFile();

        //get each Actor element and create a Actor object
        parseDocument();

        //Iterate through the list and print the data
        

    }

    private void parseXmlFile() {
        //get the factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {

            //Using factory get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();

            //parse using builder to get DOM representation of the XML file
            actordom = db.parse("./stanford-movies/actors63.xml");
            castdom = db.parse("./stanford-movies/casts124.xml");
            mainsdom = db.parse("./stanford-movies/mains243.xml");
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (SAXException se) {
            se.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void parseDocument() {
        //get the root elememt
        Element docEle1 = actordom.getDocumentElement();

        //get a nodelist of <Actor> elements
        NodeList nl = docEle1.getElementsByTagName("actor");
        if (nl != null && nl.getLength() > 0) {
            for (int i = 0; i < nl.getLength(); i++) {

                //get the Actor element
                Element el = (Element) nl.item(i);

                //get the Actor object
                getActor(el);

                //add it to list
                
                
        
                
            }
        }
        
        Element docEle2 = castdom.getDocumentElement();

        //get a nodelist of <Actor> elements
          nl = docEle2.getElementsByTagName("m");
        if (nl != null && nl.getLength() > 0) {
            for (int i = 0; i < nl.getLength(); i++) {

                //get the Actor element
                Element el = (Element) nl.item(i);

                //get the Actor object
                getCast(el);

                //add it to list
         
            }
        }
        
        Element docEle3 = mainsdom.getDocumentElement();

        //get a nodelist of <Actor> elements
          nl = docEle3.getElementsByTagName("directorfilms");
        if (nl != null && nl.getLength() > 0) {
            for (int i = 0; i < nl.getLength(); i++) {

                //get the Actor element
                Element el = (Element) nl.item(i);

                //get the Actor object
                getMains(el);

                //add it to list
         
            }
        }
        
    }

    /**
     * I take an Actor element and read the values in, create
     * an Actor object and return it
     * 
     * @param empEl
     * @return
     */
    
    private void getMains(Element empEl) {

        //for each <Actor> element get text or int values of 
        //name ,id, age and name
        String director = getTextValue(empEl, "dirname");
        
        NodeList nl = empEl.getElementsByTagName("film");
        if (nl != null && nl.getLength() > 0) {
            for (int i = 0; i < nl.getLength(); i++) {

                //get the Actor element
                Element el = (Element) nl.item(i);

                //get the Actor object
                String mname = getTextValue(el, "t");
                String year =  getTextValue(el, "year");
                String genre="";
                NodeList nl1 = empEl.getElementsByTagName("cats");
                if (nl1 != null && nl1.getLength() > 0) {
                    for (int j = 0; j < nl1.getLength(); j++) {

                        //get the Actor element
                        Element el1 = (Element) nl1.item(i);
                        genre = getTextValue(el1, "cat");
                    }
                }
                //add it to list
                if( mname.isEmpty() || year.isEmpty() || director.isEmpty()) {}
                	//System.out.println("Entity doesn't exist");
                else {
                	
                //Create a new Actor with the value read from the xml nodes
	                Mains e = new Mains(mname,director,year,genre);
	                myMainsls.add(e);
                }
            }
        }
        
        
    }

    
    private void getCast(Element empEl) {

        //for each <Actor> element get text or int values of 
        //name ,id, age and name
        String mname = getTextValue(empEl, "t");
        String sname = getTextValue(empEl, "a");
        if( mname.isEmpty() || sname.isEmpty()) {}
        	//System.out.println("Name doesn't exist");
        else {
        	
        //Create a new Actor with the value read from the xml nodes
        Casts e = new Casts(mname,sname);
        myCastls.add(e);
        }
    }
    
    private void getActor(Element empEl) {

        //for each <Actor> element get text or int values of 
        //name ,id, age and name
        String name = getTextValue(empEl, "stagename");
        String dob = getTextValue(empEl, "dob");
        if( name.isEmpty()) {}
        	//System.out.println("Name doesn't exist");
        else {
        	if(dob.isEmpty())
        		dob = "";
        //Create a new Actor with the value read from the xml nodes
        Actor e = new Actor(name,dob);
        myActorls.add(e);
        }
    }

    /**
     * I take a xml element and the tag name, look for the tag and get
     * the text content
     * i.e for <Actor><name>John</name></Actor> xml snippet if
     * the Element points to Actor node and tagName is name I will return John
     * 
     * @param ele
     * @param tagName
     * @return
     */
    private String getTextValue(Element ele, String tagName) {
        String textVal = "";
        NodeList nl = ele.getElementsByTagName(tagName);
        if (nl != null && nl.getLength() > 0) {
            Element el = (Element) nl.item(0);
            try {
            	textVal = el.getFirstChild().getNodeValue();
            	if(textVal == null)
          {
            textVal="";		
	       }
            }
            catch(java.lang.NullPointerException e) {}
        }

        return textVal;
    }

    /**
     * Calls getTextValue and returns a int value
     * 
     * @param ele
     * @param tagName
     * @return
     */


    /**
     * Iterate through the list and print the
     * content to console
     */


    public static void main(String[] args) {
        //create an instance
        DOMParser dpe = new DOMParser();

        //call run example
        dpe.runExample();
        
		// TODO Auto-generated method stub
	    String loginUser = "root";
        String loginPasswd = "GautamSQL123";
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
          
        // Response mime type
    try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			
			 Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
			 Statement statement = dbcon.createStatement();
			 ResultSet rs;
	
		String  query1 = "select id from stars ORDER BY id desc LIMIT 1";
		rs = statement.executeQuery(query1);
		rs.next();
        String sid = rs.getString("id");
        int increment = Integer.parseInt(sid.substring(2,9))+1;
        
 for(Actor A:myActorls)
 {if(A.name.matches("[a-zA-Z0-9 .~,-]+") == false)
 {
	 System.out.print(A.name + "  "+ A.dob);
	 System.out.println("Not a valid name format");
	 
 }
 else {
	    String id = sid.substring(0,2) + Integer.toString((increment));

		System.out.println( A.dob + A.name + id ); 
		String  query2 = "select id from stars where stars.name = '" + A.name + "'";
		rs = statement.executeQuery(query2);
		if(rs.next())
		{
			System.out.println("Star Already Exists");
		}
		else {
			String query = "INSERT INTO stars(id, name, birthYear) values('" + id + "' , '" + A.name + "' , '" + A.dob + "')" ;
			
			if(A.dob.isEmpty() || A.dob.equals("n.a.") || (A.dob.matches("[0-9]+") == false))
				
			{
				query = "INSERT INTO stars(id, name) values('" + id + "' , '" + A.name + "')" ;
			}
		
      
		increment++;
		
		statement.executeUpdate(query);
                System.out.println("New Star Inserted");
           }
		}	
       }
 
 
 String  query2 = "select id from movies ORDER BY id desc LIMIT 1";
	rs = statement.executeQuery(query2);
	rs.next();
 String mid = rs.getString("id");

 int incrementm = Integer.parseInt(mid.substring(3,9))+1;
	String  query3 = "select id from genres ORDER BY id desc LIMIT 1";
	rs = statement.executeQuery(query3);
	rs.next();
    String gid = rs.getString("id");
	int id3 = (Integer.parseInt(gid)+1);
 
for(Mains A:myMainsls)
{System.out.println(A.mname + A.year + A.director + A.genre);
if(A.mname.matches("[a-zA-Z0-9 .~,-]+") == false || (A.year.matches("[0-9]+") == false) || A.director.matches("[a-zA-Z0-9 .~,-]+")==false)
{
//System.out.print(A.name + "  "+ A.dob);
System.out.println("One of the entries doesn't match required format");

}
else {
 String id = mid.substring(0,3) + Integer.toString((incrementm));

	
	String  query4 = "select id from movies where movies.title = '" + A.mname + "'";
	rs = statement.executeQuery(query4);
	if(rs.next())
	{
		System.out.println("Movie Already Exists");
	}
	else {
		String query = "INSERT INTO movies(id, title,year,Director) values('" + id + "' , '" + A.mname + "' , '" + A.year + "', '" + A.director + "' )" ;
		
	incrementm++;
	
	statement.executeUpdate(query);
	System.out.println("New Movie Added");
	if(A.genre.matches("[a-zA-Z0-9 ]+") == false)
	{
		System.out.println("Genre is not according to the required format");
	}
	
	else
 	{  
		String  query6 = "select id from genres where genres.name = '" + A.genre + "'";
		rs = statement.executeQuery(query6);
		int id5;
		if(rs.next())
		{
		    id5=Integer.parseInt(rs.getString("id"));
			System.out.println("Genre Already Exists");
		}
		else 
		{
		String query5 = "INSERT INTO genres(id,name) VALUES ('" + id3 + "' , '" + A.genre + "') "; 
		statement.executeUpdate(query5);
		id5=id3;
		id3++;
		
		
		}
		String query12 = "INSERT INTO genres_in_movies(genreId, movieId) values('" + id5 + "' , '" + id + "')" ;
    						statement.executeUpdate(query12);
    						System.out.println("Genre and Movie are now linked");
	}
    }
	}	
}
			 for(Casts A:myCastls)
			 {System.out.println(A.mname + A.sname);
				 //System.out.println(A.mname);
				 if(A.mname.matches("[a-zA-Z0-9 .~,-]+") == false || A.sname.matches("[a-zA-Z0-9 .~,-]+") == false)
				 {
				  System.out.println("Invalid format for moviename or starname");
				 }	 
				 else
					 {
					 String  query10 = "select id from movies where movies.title = '" + A.mname + "'";
					 
					rs = statement.executeQuery(query10);
					if(rs.next())
					{
						//System.out.println(A.sname);
						String movieid = rs.getString("id");
						String  query11 = "select id from stars where stars.name = '" + A.sname + "'";
						rs = statement.executeQuery(query11);
						
				        if(rs.next())
                        { 	String starID = rs.getString("id");
							String  query13 = "select * from stars_in_movies where starId = '" + starID + "' AND movieId = '" + movieid + "' ";
							rs = statement.executeQuery(query13);
							if(rs.next())
							{
								System.out.println("Movie and Star are already Linked");
								
							}
							else
							{
				        	String query12 = "INSERT INTO stars_in_movies(starId, movieId) values('" + starID + "' , '" + movieid + "')" ;
    						statement.executeUpdate(query12);
    						System.out.println("Star and Movie are now linked");
							}
                        }
                        
                        else {
                        	System.out.println("Unknown Star");
                            }
					

					}
					
					else 
					{
						System.out.println("Movie Doesn't Exist");
					}
				 
			 }
			 
			 }			 
      }
      catch(java.lang.Exception ex)
    {
        System.out.println("<HTML>" +
                    "<HEAD><TITLE>" +
                    "MovieDB: Error" +
                    "</TITLE></HEAD>\n<BODY>" +
                    "<P>SQL error in doGet: " +
                    ex.getMessage() + "</P></BODY></HTML>");
        return;
    }
      
    }

}
