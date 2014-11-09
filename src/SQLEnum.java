/**
 * Enum to contain SQL Queries for faster accessing.
 */
public enum SQLEnum {
    ORIGINAL1, OPTIMIZED1,
    ORIGINAL2, OPTIMIZED2,
    ORIGINAL3, OPTIMIZED3,
    ORIGINAL4, OPTIMIZED4,
    ORIGINAL5, OPTIMIZED5,
    STORED, OPTSTORED;
    
    @Override
    public String toString()
    {
        switch(this)
        {
            case ORIGINAL1:
                return "SELECT DISTINCT customerid\n" +
                        "FROM orders\n" +
                        "WHERE employeeid IN (SELECT employeeid \n" +
                        "			      FROM employees\n" +
                        "			      WHERE reportsto = (SELECT employeeid\n" +
                        "       FROM employees\n" +
                        "       WHERE title = 'Sales Manager'));";
            case OPTIMIZED1:
                return "SELECT DISTINCT O.customerid\n" +
                        "FROM orders O,employees E\n" +
                        "WHERE O.employeeid = E.employeeid and\n" +
                        "	    E.reportsto = (SELECT employeeid\n" +
                        "     FROM employees\n" +
                        "     WHERE title = 'Sales Manager');";
            case ORIGINAL2:
                return "	SELECT productname \n" +
                        "	FROM products\n" +
                        "	WHERE productid IN (SELECT productid\n" +
                        "				FROM products\n" +
                        "				WHERE supplierid IN (SELECT supplierid\n" +
                        "                                                   FROM suppliers\n" +
                        "                                                   WHERE country = 'Japan'))";
            case OPTIMIZED2:
                return "        SELECT productname\n" +
                        "	FROM products\n" +
                        "	WHERE supplierid IN (SELECT supplierid\n" +
                        "                           FROM suppliers\n" +
                        "                           WHERE country = 'Japan')";
            case ORIGINAL3:
                return "SELECT companyname\n" +
                        "	FROM suppliers\n" +
                        "	WHERE supplierid IN (SELECT supplierid\n" +
                        "				    FROM products\n" +
                        "				    WHERE categoryid = (SELECT categoryid\n" +
                        "							    FROM categories\n" +
                        "							    WHERE categoryname = “Seafood”));";
            case OPTIMIZED3:
                return "SELECT S.companyname\n" +
                        "FROM suppliers S, products P\n" +
                        "WHERE P.categoryid = (SELECT categoryid\n" +
                        "			FROM categories C\n" +
                        "                       WHERE C.categoryname = 'Seafood')\n" +
                        "		        and S.supplierid = P.supplierid";
            case ORIGINAL4:
                return "SELECT C.customerid\n" +
                        "FROM customers C\n" +
                        "WHERE C.customerid IN (SELECT O.customerid\n" +
                        "				FROM orders O\n" +
                        "				WHERE O.employeeid IN (SELECT employeeid\n" +
                        "						FROM employees E1\n" +
                        "						WHERE E1.hiredate < (SELECT hiredate\n" +
                        "								FROM employees E2\n" +
                        "								WHERE E2.lastname = 'Suyama'\n" +
                        "                                                               and E2.firstname = 'Michael'))\n" +
                        "				and O.shipvia IN (SELECT shipperid\n" +
                        "					FROM shippers S\n" +
                        "					WHERE S.companyname = 'United Package')); ";
            case OPTIMIZED4:
                return "SELECT DISTINCT O.customerid \n" +
                        "FROM orders O\n" +
                        "WHERE O.employeeid IN (SELECT employeeid\n" +
                        "				FROM employees E1\n" +
                        "				WHERE E1.hiredate < (SELECT hiredate\n" +
                        "							      FROM employees E2\n" +
                        "							      WHERE E2.lastname = 'Suyama'\n" +
                        "                                                               and E2.firstname = 'Michael'))\n" +
                        "	and O.shipvia IN (SELECT shipperid\n" +
                        "			       FROM shippers S\n" +
                        "			       WHERE S.companyname = 'United Package');\n";
            case ORIGINAL5:
                return "SELECT S.companyname, P1.productname\n" +
                        "FROM suppliers S, products P1\n" +
                        "WHERE S.supplierid = P1.supplierid and \n" +
                        "			productname IN (SELECT P2.productname\n" +
                        "					      FROM products P2\n" +
                        "					      WHERE P2.discontinued = 'no' and \n" +
                        "					      P2.unitprice = (SELECT min(unitprice)\n" +
                        "							         FROM products P3\n" +
                        "                                                                WHERE P2.supplierid =  \n" +
                        "                                                                      P3.supplierid));";
            case OPTIMIZED5:
                return "SELECT S.companyname, P1.productname \n" +
                        "FROM suppliers S, products P1\n" +
                        "WHERE S.supplierid = P1.supplierid and \n" +
                        "	 	    P1.discontinued = 'no' and \n" +
                        "	  	     P1.unitprice = (SELECT min(unitprice)\n" +
                        "				        FROM products P2\n" +
                        "				        WHERE P1.supplierid =  P2.supplierid);";
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
