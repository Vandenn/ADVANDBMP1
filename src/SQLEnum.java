/**
 * Enum to contain SQL Queries for faster accessing.
 */
public enum SQLEnum {
    ORIGINAL, OPTIMIZED, STORED;
    
    @Override
    public String toString()
    {
        switch(this)
        {
            case ORIGINAL:
                return "SELECT * FROM employees";
            case OPTIMIZED:
                return "SELECT * FROM employees";
            case STORED:
                return "SELECT * FROM employees";
            default:
                return "SELECT * FROM employees";
        }
    }
}
