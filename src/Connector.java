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
    private CallableStatement cs = null;
    
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
            System.out.println("Nanoseconds - " + duration);
            System.out.println("Milliseconds - " + (float) duration / 1000000);
            System.out.println("Seconds - " + (float) duration / 1000000000);
            System.out.println("==================");
            
            return rs; //return result
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
            return null;
        }
    }
    
    /**
     * Function to create a stored procedure in the database.
     * @param procName Name of the procedure to be created.
     * @param SE SQL statements to be placed in the procedure.
     * @return Result of the procedure call.
     */
    public ResultSet createStoredProcedure(String procName, SQLEnum SE)
    {
        try
        {
            s.execute("DROP PROCEDURE IF EXISTS " + procName);
            s.executeUpdate("CREATE PROCEDURE " +
                procName +
                " BEGIN " +
                SE.toString() +
                "; END");
            return executeStoredProcedure(procName);
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
            return null;
        }
    }
    
    /**
     * Function to call a stored procedure in the database.
     * @param procName Name of procedure to be called.
     * @return Result of the procedure.
     */
    public ResultSet executeStoredProcedure(String procName)
    {
        try
        {
            cs = conn.prepareCall("{call " + procName + "()}");
            
            long start = System.nanoTime(); //start time
            rs = cs.executeQuery();
            long end = System.nanoTime(); //end tme
            long duration  = end - start;
            
            /*Print out time of execution*/
            System.out.println("==================");
            System.out.println("Procedure executed:");
            System.out.println(procName + "\n");
            System.out.println("Time of execution:");
            System.out.println("Nanoseconds - " + duration);
            System.out.println("Milliseconds - " + (float) duration / 1000000);
            System.out.println("Seconds - " + (float) duration / 1000000000);
            System.out.println("==================");
            return rs;
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
            return null;
        }
    }
}
