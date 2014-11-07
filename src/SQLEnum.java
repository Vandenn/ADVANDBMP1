/**
 * Enum to contain SQL Queries for faster accessing.
 */
public enum SQLEnum {
    ORIGINAL, OPTIMIZED, ORIGINAL4, OPTIMIZED4;
    
    @Override
    public String toString()
    {
        switch(this)
        {
            case ORIGINAL:
                return "SELECT * FROM employees";
            case OPTIMIZED:
                return "SELECT * FROM employees";
            case ORIGINAL4:
                return "SELECT productname FROM products WHERE productid IN (SELECT productid FROM products WHERE supplierid IN (SELECT supplierid FROM suppliers WHERE country = \"Japan\"))";
            case OPTIMIZED4:
                return "SELECT productname FROM products WHERE supplierid IN (SELECT supplierid FROM suppliers WHERE country = \"Japan\")";
            default:
                return "SELECT * FROM employees";
        }
    }
}
