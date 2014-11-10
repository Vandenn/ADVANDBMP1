/**
 * Enum to contain SQL Queries for faster accessing.
 */
public enum SQLEnum {
    ORIGINAL1, OPTIMIZED1,
    ORIGINAL2, OPTIMIZED2,
    ORIGINAL3, OPTIMIZED3,
    ORIGINAL4, OPTIMIZED4,
    ORIGINAL5, OPTIMIZED5,
    STORED1, OPTSTORED,
    STORED2, STORED3,
    STORED4, STORED5;
    
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
                        "							    WHERE categoryname = 'Seafood'));";
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
            case STORED1:
                return "(position VARCHAR(50))\n" +
"BEGIN\n" +
"	DECLARE employee_done, employee_id INT;\n" +
"	DECLARE cur_employee_id CURSOR FOR SELECT employeeid\n" +
"		FROM employees\n" +
"		WHERE title = position;\n" +
"	DECLARE CONTINUE HANDLER FOR NOT FOUND SET employee_done = 1;\n" +
"\n" +
"	OPEN cur_employee_id;\n" +
"\n" +
"	SET employee_done = 0;\n" +
"	SET employee_id = 0;\n" +
"\n" +
"	WHILE employee_done = 0 DO\n" +
"		FETCH cur_employee_id INTO employee_id;\n" +
"		SET employee_done = 1;\n" +
"	END WHILE;\n" +
"\n" +
"	CLOSE cur_employee_id;\n" +
"	\n" +
"	SELECT DISTINCT O.customerid\n" +
"FROM orders O,employees E\n" +
"WHERE O.employeeid = E.employeeid and\n" +
"	    E.reportsto = employee_id;\n" +
"END";
            case STORED2:
                return "(countryName VARCHAR(30))\n" +
"BEGIN\n" +
"\n" +
"DROP TABLE IF EXISTS Temp;\n" +
"\n" +
"CREATE TABLE Temp\n" +
"(\n" +
"sup_id		integer NOT NULL 	\n" +
");	\n" +
"\n" +
"INSERT INTO Temp (sup_id)\n" +
"SELECT supplierid\n" +
"FROM suppliers\n" +
"WHERE country = countryName;\n" +
"	\n" +
"SELECT productname\n" +
"FROM products p, Temp t\n" +
"WHERE p.supplierid = t.sup_id;\n" +
"\n" +
"END";
            case STORED3:
                return "(category VARCHAR(20))\n" +
"	BEGIN\n" +
"	DECLARE cat_done, cat_id INT;\n" +
"	DECLARE cur_cat_id CURSOR FOR SELECT categoryid\n" +
"		FROM categories\n" +
"		WHERE categoryname = category;\n" +
"	DECLARE CONTINUE HANDLER FOR NOT FOUND SET cat_done = 1;\n" +
"\n" +
"	OPEN cur_cat_id;\n" +
"\n" +
"	SET cat_done = 0;\n" +
"	SET cat_id = 0;\n" +
"\n" +
"	WHILE cat_done = 0 DO\n" +
"		FETCH cur_cat_id INTO cat_id;\n" +
"		SET cat_done = 1;\n" +
"	END WHILE;\n" +
"\n" +
"	CLOSE cur_cat_id;\n" +
"	\n" +
"	SELECT distinct companyname\n" +
"		FROM suppliers s, products p\n" +
"		WHERE s.supplierid = p.supplierid AND\n" +
"			p.categoryid = cat_id; \n" +
"\n" +
"	END";
            case STORED4:
                return "(last_name VARCHAR(50), first_name VARCHAR(50), company_name VARCHAR(50))\n" +
"\n" +
"BEGIN\n" +
"\n" +
"	SELECT DISTINCT O.customerid \n" +
"	FROM orders O\n" +
"	WHERE O.employeeid IN (SELECT employeeid\n" +
"					FROM employees E1\n" +
"					WHERE E1.hiredate < (SELECT hiredate\n" +
"								FROM employees E2\n" +
"								WHERE E2.lastname = \n" +
"last_name\n" +
"								and E2.firstname = \n" +
"first_name))\n" +
"		    and O.shipvia IN (SELECT shipperid\n" +
"					FROM shippers S\n" +
"					WHERE S.companyname = company_name);\n" +
"\n" +
"	END ";
                
            case STORED5:
                return "() " +
"	BEGIN\n" +
"\n" +
"SELECT S.companyname, P1.productname \n" +
"FROM suppliers S, products P1\n" +
"WHERE S.supplierid = P1.supplierid and \n" +
"	 	    P1.discontinued = 'no' and \n" +
"	  	     P1.unitprice = (SELECT min(unitprice)\n" +
"				        FROM products P2\n" +
"				        WHERE P1.supplierid =  P2.supplierid);\n" +
"\n" +
"	END";
 
            case OPTSTORED:
                return "CALL query_c('Seafood');";
            default:
                return "SELECT * FROM employees";
        }
    }
}
