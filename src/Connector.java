import java.util.*;
import java.sql.*;

/**
 * This is the class that will connect to the SQL Database.
 */
public class Connector {
    
    /*SQL Variables*/
    private Connection conn = null;
    private Statement s = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    
    /*String Variables for Connecting to SQL*/
    private String pUrl = "jdbc:mysql://localhost:3306/";
    private String pName = "northwind";
    private String pDriver = "com.mysql.jdbc.Driver";
    private String pUser = "root";
    private String pPass = "p@ssword";
    
    /**
     * Constructor.
     */
    public Connector()
    {
        try
        {
            /*Useful strings to connect to the database*/
            Class.forName(pDriver);
            conn = DriverManager.getConnection (pUrl +
                        pName +
                        "?user=" +
                        pUser +
                        "&password=" +
                        pPass);
            s = conn.createStatement();
        }
        catch (Exception e)
        {
            /*If an error is found (mainly with connecting), show the error.*/
            System.err.println(e.getMessage());
        }
    }
    
    /**
     * Function used to issue standard queries to DB.
     * @param se SQL query/statement from SQLEnum to be executed.
     * @return Result from the SQL query execution.
     */
    public ResultSet issueQuery(SQLEnum se)
    {
        try
        {
            long start = System.nanoTime(); //start time
            rs = s.executeQuery(se.toString());
            long end = System.nanoTime(); //end tme
            long duration  = end - start;
            
            /*Print out time of execution*/
            System.out.println("==================");
            System.out.println("Query executed:");
            System.out.println(se.toString() + "\n");
            System.out.println("Time of execution:");
            System.out.println(duration);
            System.out.println("==================");
            
            return rs; //return result
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
            return null;
        }
    }
    
}
