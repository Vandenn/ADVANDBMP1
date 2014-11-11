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
        c.issueQuery(SQLEnum.ORIGINAL3);
        System.out.println("++OPTIMIZED++");
        c.issueQuery(SQLEnum.OPTIMIZED3);
        System.out.println("++OPTSTORED++");
        c.issueQuery(SQLEnum.OPTSTORED);
    }
    
}
