/**
 * ADVANDB MP1
 * Chua, Livelo, Ver, Yao
 */

/**
 * This is the class that holds the main function of the program.
 */
public class MainClass {
    
    public static void main(String args[])
    {
        Connector c = new Connector();
        
        System.out.println("++ORIGINAL++");
        c.issueQuery(SQLEnum.ORIGINAL1);
        System.out.println("++OPTIMIZED++");
        c.issueQuery(SQLEnum.OPTIMIZED1);
        c.createStoredProcedure("query_a", SQLEnum.STORED);
        c.executeStoredProcedure("query_a", "");
    }
    
}
