/**
 * SQL-specific helper methods for
 * SU23 CSE3241 Team SHRX's database main program
 * and main program helper methods.
 * 
 * @author Keming (he.1537)
 * @version 20230719
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class SQLHelpers {

	/**
	 * Method (procedure, no return values) to print 
	 * where the SQLException happened,
	 * the message of the SQLException,
	 * and the stack track of the SQLException.
	 * 
	 * @param sE
	 * 		The SQLException object thrown.
	 * @param exLabel
	 * 		A string describing where the exception is thrown.
	 * 
	 * @requires sE != null
	 * @requires exLabel is descriptive of where the exception is thrown.
	 */
	public static void printSQLExceptionData(SQLException sE, String exLabel) {
		//Check method requirement that the exception object
		//and the exception label are valid.
		assert sE != null && exLabel != null;
		
		System.out.println();
		System.out.println("Err: Exception thrown when " + exLabel);

		System.out.println();
		System.out.println("Printing message for exception below:");
		System.out.println(sE.getMessage());
		
		System.out.println();
		System.out.println("Printing stack trace for exception below:");
		sE.printStackTrace();
	}
	
	/**
	 * Method for checking (and printing, toggle-able) 
	 * whether the ResultSet obtained from
	 * executing the given PreparedStatement
	 * contains any rows.
	 * 
	 * @param pStat
	 * 		A valid query type PreparedStatement to execute.
	 * @param printResult
	 * 		If true print either the ResultSet content (has rows)
	 * 		or message that the ResultSet has no rows;
	 * 		if false print nothing.
	 * @return hasResult
	 * 		True if ResultSet (from executing pStat) has at least on row;
	 * 		false if ResultSet has no rows.
	 * 
	 * @requires pStat != null
	 */
	public static boolean checkAndPrintResultSet(PreparedStatement pStat, 
			boolean printResult) {
		
		//Check method requirement that given PreparedStatement is valid.
		assert pStat != null;
		
		//Declare return boolean, default to false (no result).
		boolean hasResults = false;
		
		try {
			ResultSet rSet = pStat.executeQuery();
			
			if (rSet != null) {
				
				/*
				 * boolean isBeforeFirst()
				 * 
				 * Returns true if cursor is before the first row;
				 * returns false if cursor is at any other position,
				 * or the result set contains no rows.
				 */
				
				//Before moving the cursor using next(),
				//use isBeforeFirst to check if result is empty.
				//Print result if result set is not empty.
				if (rSet.isBeforeFirst()) {
					
					//Result exists; set return boolean to true.
					hasResults = true;
					
					if (printResult) {
						//Obtain column names from ResultSet's meta data.
						ResultSetMetaData rSMeta = rSet.getMetaData();
						
						//Print the column names of the query result.
						System.out.print("Columns: ");
						int cCount = rSMeta.getColumnCount();
						for (int cIdx = 1; cIdx <= cCount; cIdx++) {
							System.out.print(rSMeta.getColumnName(cIdx));
							if (cIdx < cCount) System.out.print(", ");
						}
						
						System.out.println();
						System.out.println();
						
						//Print each row of the query result.
						int tmpRowNum = 1;
						while (rSet.next()) {	
							
							System.out.print("Row " + String.valueOf(tmpRowNum) + ": ");
							
							for (int rIdx = 1; rIdx <= cCount; rIdx++) {
								System.out.print(rSet.getString(rIdx));
								if (rIdx < cCount) System.out.print(", ");
							}
							System.out.println();
							System.out.println();
							
							tmpRowNum++;
						}
						
					} //Else don't print result.
					
				} else {
					//Else ResultSet is empty (no rows, but has meta data).
					hasResults = false;
					
					//Print no result message if printResult is true.
					if (printResult) System.out.println("Your query returned no rows.");
				}
								
				//Close ResultSet when done.
				try {
					rSet.close();
				} catch (SQLException sE) {
					printSQLExceptionData(sE, "closing ResultSet.");
				}
				
			} else {
				//No need to close ResultSet if it is null.
				System.out.println("Err: ResultSet is null.");
			}
			
		} catch (SQLException sQLE) {
			printSQLExceptionData(sQLE, "obtaining ResultSet by executeQuery()");
		}
		
		return hasResults;
	}

	/**
	 * Helper method (procedure, no return values) to print the result of
	 * an SQL query with no parameters. The printed result includes 
	 * all the column names and all the rows with values.
	 * 
	 * @param conn
	 * 		The Connection object to the SQLite database.
	 * @param sQLCode
	 * 		The SQL query code to be run on the database, no parameters.
	 * @param sELabel
	 * 		The description for if running query code throws an exception.
	 * 
	 * @requires conn != null
	 * @requires sQLCode is valid.
	 * @requires sELabel is valid and descriptive.
	 */
	public static void simpleQueryAndPrint(Connection conn, String sQLCode, String sELabel) {
		//Check method requirement that connection is not null,
		//and query code and exception label is valid.
		assert conn != null && sQLCode != null && sELabel != null;
		
		try {
			//Obtain ResultSet and meta data using PreparedStatement
			//for better security.
			PreparedStatement pStat = conn.prepareStatement(sQLCode);
			ResultSet rSet = pStat.executeQuery();
			ResultSetMetaData rSMeta = rSet.getMetaData();
			
			//Print the column names of the query result.
			System.out.print("Columns: ");
			
			int cCount = rSMeta.getColumnCount();
			for (int cIdx = 1; cIdx <= cCount; cIdx++) {
				System.out.print(rSMeta.getColumnName(cIdx));
				if (cIdx < cCount) System.out.print(", ");
			}
			
			System.out.println();
			System.out.println();
			
			//Print each row of the query result.
			int tmpRowNum = 1;
			while (rSet.next()) {	
				
				System.out.print("Row " + String.valueOf(tmpRowNum) + ": ");
				
				for (int rIdx = 1; rIdx <= cCount; rIdx++) {
					System.out.print(rSet.getString(rIdx));
					if (rIdx < cCount) System.out.print(", ");
				}
				System.out.println();
				System.out.println();
				
				tmpRowNum++;
			}
			
			
			//Close ResultSet and PreparedStatement when done.
			try {
				if (rSet != null) rSet.close();
			} catch (SQLException sE) {
				String tmpLabel = "closing ResultSet.";
				printSQLExceptionData(sE, tmpLabel);
			}
			
			try {
				if (pStat != null) pStat.close();
			} catch (SQLException sE) {
				String tmpLabel = "closing PreparedStatement.";
				printSQLExceptionData(sE, tmpLabel);
			}
			
		} catch (SQLException sQLE) {
			printSQLExceptionData(sQLE, sELabel);
		}
	}
	
	/**
	 * Method (procedure, no return value) to print a given, existing, full
	 * table from the connected SQLite database.
	 * 
	 * @param conn
	 * 		The Connection object to the SQLite database.
	 * @param tableName
	 * 		The name of an existing table from the database.
	 * 
	 * @requires conn != null
	 * @requires tableName exist in the database
	 */
	public static void printTable(Connection conn, String tableName) {
		//Check method requirement that Connection cannot be null.
		assert conn != null;
		
		String printTableSQL = "SELECT * FROM " + tableName + ";";
		String printTableELabel = "printing table: " + tableName + ".";
		simpleQueryAndPrint(conn, printTableSQL, printTableELabel);
	}

}
