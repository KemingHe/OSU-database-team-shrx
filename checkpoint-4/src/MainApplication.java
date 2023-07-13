/**
 * SU23 CSE3241 Team SHRX's Database program
 * with Java 8 front-end, JDBC integration,
 * and SQLite database back-end.
 * 
 * @author Keming He
 * @version 20230712
 */

import java.util.*;
import java.sql.*;

public class MainApplication {
	
	//File path of Team SHRX's SQLite database.
	private static final String DBPATH = "AllRecords.db";
	
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
	private static void printSQLExceptionData(SQLException sE, String exLabel) {
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
	 * Method to return a connection object to a SQLite 
	 * database from a given path.
	 * 
	 * @param dBPath
	 * 		The path to a SQLite database.
	 * @return conn
	 * 		The opened (or null) connection object to the database.
	 * 
	 * @requires dBPath is a valid path to a SQLite database.
	 */
	private static Connection initConnection(String dBPath) {
		//Check method requirement that path is valid.
		assert dBPath != null;
		
		//Initialize connection path and declare connection object.
		String dBUrl = "jdbc:sqlite:" + dBPath;
		Connection conn = null;
		
		//Try to open a database connection through the API.
		//Handle and print if null connection or an exception is thrown.
		try {
			conn = DriverManager.getConnection(dBUrl);
			
			if (conn != null) {
				System.out.println();
				System.out.println("Connection to database successfully established.");
			} else {
				System.out.println();
				System.out.println("Connection is null, but no exception is thrown.");
			}
		} catch (SQLException sE) {
			String exLabel = "opening connection.";
			printSQLExceptionData(sE, exLabel);
		} 
		return conn;
	}
	
	/**
	 * Method (procedure, no return values) to close 
	 * an initially opened database connection object.
	 * 
	 * @param conn
	 * 		The previously opened connection to the SQLite database.
	 */
	private static void closeConnection(Connection conn) {
		//Try to close the database connection if 
		//connect is not already null.
		//Handle and print if exception is thrown.
		try {
			if (conn != null) conn.close();
		} catch (SQLException sE) {
			String exLabel = "closing connection.";
			printSQLExceptionData(sE, exLabel);
		}
	}

	/**
	 * Main method of Team SHRX's Java program.
	 * No return values.
	 * 
	 * @param args
	 * 		Command line passing argument array. Unused.
	 */
	public static void main(String[] args) {

		//Initialize console input stream.
		//Remember to close input stream at the very end.
		Scanner consoleIn = new Scanner(System.in);
		
		//Initialize connection to the database.
		//Remember to close connection at the very end.
		Connection conn = initConnection(DBPATH);
		
		while (true) {
			//Start at the main menu.
			printMainMenu();
			
			String mainOptInsert = "0";
			String mainOptSearch = "1";
			String mainOptUpdate = "2";
			String mainOptDelete = "3";
			String mainOptPrintRep = "4";
			
			String userSelection = consoleIn.nextLine();
			
			if (userSelection.equals(mainOptInsert)) {
				
				/**
                 * Option 0: insert a new row.
                 * 
                 * First,   select the table to insert into;
                 * Second,  obtain all column values for the new row;
                 * Third,   insert new row into table;
                 * Fourth,  print the entire table as confirmation;
                 * Finally, return to main menu.
                 */
				System.out.println();
				System.out.println("[Option " 
						+ mainOptInsert 
						+ ": Insert a New Record]");
				
				String insertTName = getTableName(consoleIn, "insert into");
				
				if (insertTName != null) {
					insertNewRecord(consoleIn, conn, insertTName);
				} 
				
				System.out.println("Returning to Main Menu.");
				System.out.println();
				
			} else if (userSelection.equals(mainOptSearch)) {
				
				/**
                 * Option 1: search for a row.
                 * 
                 * First,   select the table to search from;
                 * Second,  obtain all key attribute values for the search;
                 * Third,   search for and return the results (row value or not found);
                 * Finally, return to main menu.
                 */
								
				System.out.println();
				System.out.println("[Option " 
						+ mainOptSearch 
						+ ": Search Existing Records]");
				
				String searchTName = getTableName(consoleIn, "search from");
				
				if (searchTName != null) {
					searchExistingRecord(consoleIn, conn, searchTName);
				}
				
				System.out.println("Returning to Main Menu.");
				System.out.println();
				
			} else if (userSelection.equals(mainOptUpdate)) {
				
				/**
                 * Option 2: update the value of a row.
                 * 
                 * First,	select the table to update to;
                 * Second, 	select (using search) the row to update;
                 * Third, 	obtain all new column values for that row;
                 * Fourth, 	update the row with all new values;
                 * Fifth,	print the entire table as confirmation;
                 * Finally, return to main menu.
                 */
				
				System.out.println();
				System.out.println("[Option " 
						+ mainOptUpdate 
						+ ": Update An Existing Record]");
				
				String updateTName = getTableName(consoleIn, "update to");
				
				if (updateTName != null) {
					updateExistingRecord(consoleIn, conn, updateTName);
				}
				
				System.out.println("Returning to Main Menu.");
				System.out.println();
				
			} else if (userSelection.equals(mainOptDelete)) {
				
				/**
                 * Option 3: delete a row.
                 * 
                 * First,	select the table to delete from;
                 * Second, 	select (using search) the row to delete;
                 * Third, 	delete the row;
                 * Fifth,	print the entire table as confirmation;
                 * Finally, return to main menu.
                 */
				
				System.out.println();
				System.out.println("[Option " 
						+ mainOptDelete 
						+ ": Delete An Existing Record]");
				
				String deleteTName = getTableName(consoleIn, "delete from");
				
				if (deleteTName != null) {
					deleteExistingRecord(consoleIn, conn, deleteTName);					
				}
				
				System.out.println("Returning to Main Menu.");
				System.out.println();
				
			} else if (userSelection.equals(mainOptPrintRep)) {
			
				//Option 4: to print a list of useful reports.
				System.out.println();
				System.out.println("[Option " 
						+ mainOptPrintRep
						+ ": Print All Useful Reports]");
				
				printAllUsefulReports(conn);
				
				System.out.println("Returning to Main Menu.");
				System.out.println();
				
			} else {
				//Option 5 (or default): to exit the main program (loop).
				break;
			}
		}
		
		//Main loop exited, print exit message and close scanner.
		String exitMessage = "Thank you for using Team SHRX's Java frontend."
				+ " Goodbye.";
		
		System.out.println();
		System.out.println(exitMessage);
		
		//Close connection and input stream.
		closeConnection(conn);
		consoleIn.close();				
	}

	/**
	 * Method (procedure, no return value) to print
	 * all five main menu options and to prompt
	 * user selection.
	 */
	private static void printMainMenu() {
		System.out.println();
		
		System.out.println("[Main Menu]");
		System.out.println();
		
		System.out.println("Select an option"
				+" by entering the corresponding index below:");
		System.out.println();

		System.out.println("0. Insert a new record.");
		System.out.println("1. Search for existing records.");
		System.out.println("2. Update an existing record.");
		System.out.println("3. Delete an existing record.");
		System.out.println("4. Print a list of useful reports.");
		System.out.println();
		System.out.println("5. Exit.");
		System.out.println();

		System.out.print("Enter your selection: ");
	}
	
	/**
	 * Method to prompt user (with a given reason) to choose
	 * from one of four tables from the SQLite database; and
	 * to return the capitalize table name.
	 * 
	 * @param consoleIn
	 * 		The user input scanner object.
	 * @param purposeStr
	 * 		String explaining why choose a table.
	 * @return selectedTName
	 * 		A valid database table name (or null) chosen by the user.
	 * 
	 * @requires consoleIn != null
	 * @requires purposeStr explains reason for choosing a table.
	 */
	private static String getTableName(Scanner consoleIn, String purposeStr) {
		//Check method requirement that input scanner is not null.
		assert consoleIn != null && purposeStr != null;
		
		System.out.println();
		System.out.println("Select an table to " + purposeStr);
		System.out.println("by entering the corresponding index below:");
		System.out.println();

		System.out.println("0. CUSTOMER");
		System.out.println("1. ANIME");
		System.out.println("2. STUDIO");
		System.out.println("3. CREATOR");
		System.out.println();
		System.out.println("4. Exit.");
		System.out.println();

		System.out.print("Enter your selection: ");
		
		//Initialize return string, default to null (exit).
		String selectedTName = null;
		switch (consoleIn.nextLine()) {
			case "0": 	selectedTName = "CUSTOMER";
						break;
			case "1": 	selectedTName = "ANIME";
						break;
			case "2":	selectedTName = "STUDIO";
						break;
			case "3": 	selectedTName = "CREATOR";
						break;
			default: 	selectedTName = null;
						break;
						
		}
		return selectedTName;
	}
	
	// TODO
	private static final String [] COL_CUSTOMER = {
			"Username (type text, prime, not null)",
			"Password (type text, not null)",
			"First_name (type text, not null)",
			"Last_name (type text, not null)",
			"Email (type text, not null)",
			"Creation_date (type text, not null)",
			"Billing_info (type text, not null)",
			"DOB (type text, not null)"
	};
	
	private static final String [] COL_ANIME = {
			"Title (type text, prime, not null)",
			"Description (type text)",
			"Genre (type text)",
			"Price (type integer, not null)",
			"Release_year (type integer, not null)"
	};
	
	private static final String [] COL_STUDIO = {
			"Name (type text, prime, not null)",
			"Description (type text)",
			"Website (type text)",
			"Address (type text)"
	};
	
	private static final String [] COL_CREATOR = {
			"Anime_title (type text, prime, foreign key, not null)",
			"Studio_name (type text, prime, foreign key, not null)"
	};
	
	private static String [] getNewRowColumnValues(Scanner consoleIn, String tableName, 
			String[] columnNames) {
		
		System.out.println();
		System.out.println("Please enter column values for new row in table: "
				+ tableName);
		System.out.println();
		
		int columnNum = columnNames.length;
		String [] newRow = new String [columnNum];
		
		for (int idx = 0; idx < columnNum; idx++) {
			
			System.out.print(columnNames[idx] + ": ");
			newRow[idx] = consoleIn.nextLine();
		}
		
		System.out.println();
		
		return newRow;
	}
	
	/**
	 * TODO
	 * 
	 * @param consoleIn
	 * @param conn
	 * @param tableName
	 */
	private static void insertNewRecord(Scanner consoleIn, Connection conn, String tableName) {
		
		assert consoleIn != null && conn != null && tableName != null;
		
		try {
			PreparedStatement pStat= null;
			
			if (tableName.equals("CUSTOMER")) {
				
				String [] newRow = getNewRowColumnValues(consoleIn, tableName, COL_CUSTOMER);
				
				String newInsertSQL = "INSERT INTO " + tableName + " VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
				pStat = conn.prepareStatement(newInsertSQL);
				
				for(int i = 0; i < newRow.length; i++) {
					pStat.setString((i+1), newRow[i]);
				}
								
				
			} else if (tableName.equals("ANIME")) {
				
				String [] newRow = getNewRowColumnValues(consoleIn, tableName, COL_ANIME);
				
				String newInsertSQL = "INSERT INTO " + tableName + " VALUES(?, ?, ?, ?, ?);";
				pStat = conn.prepareStatement(newInsertSQL);
				
				//The first three columns of ANIME is type text;
				//and the last two columns of ANIME is type integer.
				for(int i = 0; i < newRow.length; i++) {
					
					int textIntCutoff = 3;
					if(i < textIntCutoff) {
						pStat.setString((i+1), newRow[i]);
						
					} else {
						//Parse user string to integer.
						//Handle and print if exception is thrown during parsing.
						try {
							int tmpVal = Integer.parseInt(newRow[i]);
							pStat.setInt((i+1), tmpVal);
							
						} catch (NumberFormatException numE) {
							System.out.println(numE.getMessage());
						}
					}
				}
				
			} else if (tableName.equals("STUDIO")) {
				
				String [] newRow = getNewRowColumnValues(consoleIn, tableName, COL_STUDIO);
								
				String newInsertSQL = "INSERT INTO " + tableName + " VALUES(?, ?, ?, ?);";
				pStat = conn.prepareStatement(newInsertSQL);
				
				for(int i = 0; i < newRow.length; i++) {
					pStat.setString((i+1), newRow[i]);
				}
				
			} else if (tableName.equals("CREATOR")) {

				String [] newRow = getNewRowColumnValues(consoleIn, tableName, COL_CREATOR);
				
				String newInsertSQL = "INSERT INTO " + tableName + " VALUES(?, ?);";
				pStat = conn.prepareStatement(newInsertSQL);
				
				for(int i = 0; i < newRow.length; i++) {
					pStat.setString((i+1), newRow[i]);
				}
				
			} else {
				//Handle if table name is out of bounds.
				//Nullifies PreparedStatement and exit.
				System.out.println("Err: Invalid table name. Nullifying PreparedStatement.");
				pStat = null;
			}
			
			//If PreparedStatement is valid, execute update and print confirmation.
			if (pStat != null) {
				
				pStat.executeUpdate();
				
				System.out.println("New data successfully inserted into table: " + tableName +".");
				System.out.println("...Printing updated table content as confirmation.");
				System.out.println();
				
				printTable(conn, tableName);
			}
			
			
			//Close PreparedStatement object when done with insert (update).
			//Handle and print if an exception is thrown.
			try {
				if(pStat != null) pStat.close();
			} catch (SQLException sE) {
				printSQLExceptionData(sE, "closing the insert PreparedStatement.");
			}
			
			//Handle and print if an exception is thrown
			//during executeUpdate().
		} catch (SQLException sE) {
			printSQLExceptionData(sE, "inserting via PreparedStatement.");
		}
	}
	
	private static void searchExistingRecord(Scanner consoleIn, Connection conn, String tableName) {
		//TODO
	}
	
	private static void updateExistingRecord(Scanner consoleIn, Connection conn, String tableName) {
		//TODO
	}
	
	private static void deleteExistingRecord(Scanner consoleIn, Connection conn, String tableName) {
		//TODO
	}
	
	/**
	 * Helper method (procedure, not return value) to print the result of
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
	private static void printSQLResult(Connection conn, String sQLCode, String sELabel) {
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
			
		} catch (SQLException sE) {
			printSQLExceptionData(sE, sELabel);
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
	private static void printTable(Connection conn, String tableName) {
		//Check method requirement that Connection cannot be null.
		assert conn != null;
		
		String printTableSQL = "SELECT * FROM " + tableName + ";";
		String printTableELabel = "printing table: " + tableName + ".";
		printSQLResult(conn, printTableSQL, printTableELabel);
	}
	
	/**
	 * Method (procedure, no return value) to print 
	 * all six (currently) "Useful Report" queries.
	 * 
	 * @param conn 
	 * 		The Connection object to the SQLite database.
	 * @requires conn != null
	 */
	private static void printAllUsefulReports(Connection conn) {
		//Check method requirement that Connection cannot be null.
		assert conn != null;
		
		//Initialize all English queries and code,
		//store in two separate arrays for iterative access.
		String report1English = "Find the total number of anime "
				+ "purchased by customer 'plapwood3'.";
		String report1SQL = "SELECT COUNT(Anime_title) "
				+ "AS Total_purchased "
				+ "FROM PURCHASES "
				+ "WHERE Customer = 'plapwood3';";
		
		String report2English = "Find the most popular anime "
				+ "in the database (use the number of times "
				+ "the item has been purchased to calculate).";
		String report2SQL = "SELECT a.Title, COUNT(p.Anime_title) "
				+ "as Total_Purchased "
				+ "FROM ANIME a "
				+ "LEFT JOIN PURCHASES p "
				+ "ON a.Title = p.Anime_title "
				+ "GROUP BY a.Title "
				+ "ORDER BY Total_Purchased DESC "
				+ "LIMIT 1;";
		
		String report3English = "Find the most popular studio "
				+ "in the database (i.e. the one "
				+ "who has had the most purchased anime).";
		String report3SQL = "SELECT Studio_name, COUNT(*) "
				+ "AS num_purchases "
				+ "FROM PURCHASES p "
				+ "JOIN CREATOR c "
				+ "ON p.Anime_title = c.Anime_title "
				+ "GROUP BY Studio_name "
				+ "ORDER BY num_purchases DESC "
				+ "LIMIT 1;";
		
		String report4English = "Find the most watched anime "
				+ "in the database.";
		String report4SQL = "SELECT ANIME.Title, COUNT(*) "
				+ "AS WatchCount "
				+ "FROM ANIME "
				+ "JOIN CUSTOMER_WATCHES "
				+ "ON ANIME.Title = CUSTOMER_WATCHES.Anime "
				+ "GROUP BY ANIME.Title "
				+ "HAVING COUNT(*) = ( "
				+ "SELECT MAX(WatchCount) "
				+ "FROM ( "
				+ "SELECT COUNT(*) AS WatchCount "
				+ "FROM ANIME "
				+ "JOIN CUSTOMER_WATCHES "
				+ "ON ANIME.Title = CUSTOMER_WATCHES.Anime "
				+ "GROUP BY ANIME.Title));";
		
		String report5English = "Find the customer "
				+ "who has purchased the most anime "
				+ "and the total number of anime "
				+ "they have purchased.";
		String report5SQL = "SELECT Customer, "
				+ "COUNT(Customer) AS Total "
				+ "FROM PURCHASES "
				+ "GROUP BY CUSTOMER "
				+ "ORDER BY COUNT(CUSTOMER) DESC "
				+ "LIMIT 1;";
		
		String report6English = "Find all anime "
				+ "released before 2023.";
		String report6SQL = "SELECT Title, Genre, "
				+ "Release_year "
				+ "FROM ANIME "
				+ "WHERE Release_year < 2023 "
				+ "ORDER BY Genre;";
		
		String [] allReportEnglish = {
				report1English,
				report2English,
				report3English,
				report4English,
				report5English,
				report6English		
		};
		
		String [] allReportSQL = {
				report1SQL,
				report2SQL,
				report3SQL,
				report4SQL,
				report5SQL,
				report6SQL
		};
		
		//Iterate through the array of queries, 
		//print both the English query,
		//and the query result.
		for (int idx = 0; idx < allReportEnglish.length; idx++) {
			
			System.out.println();
			System.out.println("---- Report " 
					+ String.valueOf(idx+1) 
					+ ". ----");
			System.out.println(allReportEnglish[idx]);
			System.out.println();
			
			String exLabel = "executing SQL query for Report "
					+ String.valueOf(idx+1)
					+ ".";
			printSQLResult(conn, allReportSQL[idx], exLabel);
			
			System.out.println("---- End of Report " 
					+ String.valueOf(idx+1) 
					+ ". ----");
			System.out.println();
		}
		
		System.out.println("All " 
				+ String.valueOf(allReportEnglish.length)
				+ " reports have been successfully printed.");
		System.out.println();
	}
}
