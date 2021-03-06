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
    private String pPass = "1234";
    
    /*Number of trials for accurate measurement of time*/
    private final int trials = 1000;
    
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
            long duration = 0;
            double fDuration = 0;
            int i;
            for(i = 0; i < trials; i++)
            {
                long start = System.nanoTime(); //start time
                rs = s.executeQuery(se.toString());
                long end = System.nanoTime(); //end tme
                if(i != 0) //ignore first trial due to possible spike
                    duration += (end - start);
                Statement temp = conn.createStatement();
                temp.executeQuery("flush query cache;");
            }
            fDuration = duration / (trials - 1);
            
            /*Print out time of execution*/
            System.out.println("==================");
            System.out.println("Query executed:");
            System.out.println(se.toString() + "\n");
            System.out.println("Time of execution:");
            System.out.println("Nanoseconds - " + fDuration);
            System.out.println("Milliseconds - " + fDuration / 1000000);
            System.out.println("Seconds - " + fDuration / 1000000000);
            System.out.println("==================");
            
            return rs; //return result
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
            return null;
        }
    }
    
    public ResultSet issueQuery2(String se)
    {
        try
        {
            long duration = 0;
            double fDuration = 0;
            int i = 0;
            
            for(i = 0; i < trials; i++)
            {
                long start = System.nanoTime(); //start time
                rs = s.executeQuery(se);
                long end = System.nanoTime(); //end tme
                if(i != 0) //ignore first trial due to possible spike
                    duration += (end - start);
                Statement temp = conn.createStatement();
                temp.executeQuery("flush query cache;");
            }
            fDuration = duration / (trials - 1);
            
            /*Print out time of execution*/
            System.out.println("==================");
            System.out.println("Query executed:");
            System.out.println(se + "\n");
            System.out.println("Time of execution:");
            System.out.println("Nanoseconds - " + fDuration);
            System.out.println("Milliseconds - " + fDuration / 1000000);
            System.out.println("Seconds - " + fDuration / 1000000000);
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
     */
    public void createStoredProcedure(String procName, SQLEnum SE)
    {
        try
        {
            s.execute("DROP PROCEDURE IF EXISTS " + procName);
            s.executeUpdate("CREATE PROCEDURE " +
                procName +
                SE.toString());
            System.out.println("==================");
            System.out.println("Created stored procedure:");
            System.out.println(procName);
            System.out.println("==================");
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }
    
    /**
     * Function to call a stored procedure in the database.
     * @param procName Name of procedure to be called.
     * @param procParam Parameters to be passed to the stored procedure.
     * @return Result of the procedure.
     */
    public ResultSet executeStoredProcedure(String procName, String procParam)
    {
        try
        {
            cs = conn.prepareCall("{call " + procName + "(" + procParam + ")}");
            
            long duration = 0;
            double fDuration = 0;
            
            for(int i = 0; i < trials; i++)
            {
                long start = System.nanoTime(); //start time
                rs = cs.executeQuery();
                long end = System.nanoTime(); //end tme
                if(i != 0) //ignore first trial due to possible spike
                    duration += (end - start);
                Statement temp = conn.createStatement();
                temp.executeQuery("flush query cache;");
            }
            fDuration = duration / (trials-1);
            
            /*Print out time of execution*/
            System.out.println("==================");
            System.out.println("Procedure executed:");
            System.out.println(procName + "\n");
            System.out.println("Time of execution:");
            System.out.println("Nanoseconds - " + fDuration);
            System.out.println("Milliseconds - " + fDuration / 1000000);
            System.out.println("Seconds - " + fDuration / 1000000000);
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
