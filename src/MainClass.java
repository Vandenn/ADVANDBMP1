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
        c.issueQuery(SQLEnum.ORIGINAL4);
        c.issueQuery(SQLEnum.OPTIMIZED4);
    }
    
}
