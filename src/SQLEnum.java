/**
 * Enum to contain SQL Queries for faster accessing.
 */
public enum SQLEnum {
    ORIGINAL, OPTIMIZED, STORED, OPTSTORED;
    
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
                return "() " +
                    "BEGIN " +
                    "DECLARE test INT; " +
                    "END";
            case OPTSTORED:
                return "SELECT * FROM employees";
            default:
                return "SELECT * FROM employees";
        }
    }
}
