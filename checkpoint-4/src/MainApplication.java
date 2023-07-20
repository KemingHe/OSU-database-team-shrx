/**
 * SU23 CSE3241 Team SHRX's database main program,
 * with Java 8 front-end, JDBC integration,
 * and SQLite database back-end.
 * 
 * @author Keming (he.1537)
 * @version 20230719
 */

import java.util.*;
import java.sql.*;

public class MainApplication {
	
	/*
	 * File path of Team SHRX's SQLite database.
	 */
	private static final String DBPATH = "TeamSHRX_Database_Binary.db";

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
		Connection conn = MainAppHelpers.initConnection(DBPATH);
		
		while (true) {
			//Start at the main menu.
			MainAppHelpers.printMainMenu();
						
			String user_selection = consoleIn.nextLine();
			if (user_selection.equals(MainAppHelpers.OPT_INSERT)) {
				/**
                 * Option 0: insert a new row.
                 * 
                 * First,   select the table to insert into;
                 * Second,  obtain all column values for the new row;
                 * Third,   insert new row into table;
                 * Fourth,  print the entire table as confirmation;
                 * Finally, return to main menu.
                 */
				MainAppHelpers.printOptionHeader(MainAppHelpers.OPT_INSERT);
				
				String insertTName = MainAppHelpers.getTableName(consoleIn, "insert into");
				
				if (insertTName != null) {
					insertNewRecord(consoleIn, conn, insertTName);
				} 
				
				MainAppHelpers.printReturnToMain();
				
			} else if (user_selection.equals(MainAppHelpers.OPT_SEARCH)) {
				/**
                 * Option 1: search for a row.
                 * 
                 * First,   select the table to search from;
                 * Second,  obtain all key attribute values for the search;
                 * Third,   search for and return the results (row value or not found);
                 * Finally, return to main menu.
                 */			
				MainAppHelpers.printOptionHeader(MainAppHelpers.OPT_SEARCH);
				
				String searchTName = MainAppHelpers.getTableName(consoleIn, "search from");
				
				if (searchTName != null) {
					searchExistingRecord(consoleIn, conn, searchTName);
				}
				
				MainAppHelpers.printReturnToMain();
				
			} else if (user_selection.equals(MainAppHelpers.OPT_UPDATE)) {
				/*
                 * Option 2: update the value of a row.
                 * 
                 * First,	select the table to update to;
                 * Second, 	select (using search) the row to update;
                 * Third, 	obtain all new column values for that row;
                 * Fourth, 	update the row with all new values;
                 * Fifth,	print the entire table as confirmation;
                 * Finally, return to main menu.
                 */
				MainAppHelpers.printOptionHeader(MainAppHelpers.OPT_UPDATE);
				
				String updateTName = MainAppHelpers.getTableName(consoleIn, "update to");
				
				if (updateTName != null) {
					updateExistingRecord(consoleIn, conn, updateTName);
				}
				
				MainAppHelpers.printReturnToMain();
				
			} else if (user_selection.equals(MainAppHelpers.OPT_DELETE)) {
				/*
                 * Option 3: delete a row.
                 * 
                 * First,	select the table to delete from;
                 * Second, 	select (using search) the row to delete;
                 * Third, 	delete the row;
                 * Fifth,	print the entire table as confirmation;
                 * Finally, return to main menu.
                 */
				MainAppHelpers.printOptionHeader(MainAppHelpers.OPT_PRINT_REP);
				
				String deleteTName = MainAppHelpers.getTableName(consoleIn, "delete from");
				
				if (deleteTName != null) {
					deleteExistingRecord(consoleIn, conn, deleteTName);					
				}
				
				MainAppHelpers.printReturnToMain();
				
			} else if (user_selection.equals(MainAppHelpers.OPT_PRINT_REP)) {
				/*
				 * Option 4: print a list of useful reports.
				 */
				MainAppHelpers.printOptionHeader(MainAppHelpers.OPT_PRINT_REP);
				
				printAllUsefulReports(conn);
				
				MainAppHelpers.printReturnToMain();
				
			} else {
				/*
				 * Option 5 (or default): to exit the main program (loop).
				 */
				break;
			}
		}
		
		/*
		 * Main loop exited, print exit message;
		 * close connection, and close scanner.
		 */
		MainAppHelpers.printExitMessage();
		MainAppHelpers.closeConnection(conn);
		consoleIn.close();				
	}
	
	/**
	 * Method (procedure, no return values) to insert a new record
	 * into a given table in the given SQLite database.
	 * 
	 * @param consoleIn
	 * 		The user input stream.
	 * @param conn
	 * 		The connection object to the SQLite database.
	 * @param tableName
	 * 		The name of the table to make a insertion.
	 * 
	 * @requires consoleIn != null
	 * @requires conn != null
	 * @requires tableName is one of the four tables.
	 */
	private static void insertNewRecord(Scanner consoleIn, Connection conn, String tableName) {
		//Check method requirements are met.
		assert consoleIn != null && conn != null && tableName != null;
		
		try {
			PreparedStatement pStat= null;
			
			//Initialize PreparedStatement according to given table.
			String newInsertSQL = MainAppHelpers.buildSQLStringInsert(tableName);
			pStat = conn.prepareStatement(newInsertSQL);
			
			//Set user parameters for the PreparedStatement according to given table.
			String [] newRow = MainAppHelpers.getRowColumnValues(consoleIn, tableName);
			MainAppHelpers.setParamInsert(pStat, tableName, newRow);
			
			//If PreparedStatement is valid, execute update and print confirmation.
			if (pStat != null) {
				pStat.executeUpdate();
				MainAppHelpers.printSuccessInsert(conn, tableName);
			}
			
			//Close PreparedStatement object when done with insert (update).
			//Handle and print if an exception is thrown.
			try {
				if(pStat != null) pStat.close();
			} catch (SQLException sE) {
				SQLHelpers.printSQLExceptionData(sE, 
						"closing the insert PreparedStatement.");
			}
			
			//Handle and print if an exception is thrown
			//during executeUpdate().
		} catch (SQLException sE) {
			SQLHelpers.printSQLExceptionData(sE, 
					"inserting via PreparedStatement.");
		}
	}
	
	/**
	 * Method (procedure, no return values) to search (generally/substring)
	 * whether a given table contains any rows 
	 * that partially match the primary key values given by the user.
	 * 
	 * @param consoleIn
	 * 		The user input stream.
	 * @param conn
	 * 		The connection object to the SQLite database.
	 * @param tableName
	 * 		The name of the table to generally search from.
	 * 
	 * @requires consoleIn != null
	 * @requires conn != null
	 * @requires tableName is one of the four tables.
	 */
	private static void searchExistingRecord(Scanner consoleIn, Connection conn, String tableName) {
		//Check method requirements are met.
		assert consoleIn != null && conn != null && tableName != null;
		
		try {
			PreparedStatement pStat= null;
			
			//Initialize PreparedStatement according to given table.
			String newSearchSQL = MainAppHelpers.buildSQLStringSearchGeneral(tableName);
			pStat = conn.prepareStatement(newSearchSQL);
			
			if (pStat != null) {
				//Set user parameters for the PreparedStatement according to given table.
				String [] userPK = MainAppHelpers.getPrimaryKeyValues(consoleIn, tableName);
				MainAppHelpers.setParamSearchGeneral(pStat, tableName, userPK);
				
				SQLHelpers.checkAndPrintResultSet(pStat, true);
			}
			
			//Close PreparedStatement object when done with search.
			//Handle and print if an exception is thrown.
			try {
				if(pStat != null) pStat.close();
			} catch (SQLException sE) {
				SQLHelpers.printSQLExceptionData(sE, 
						"closing the search PreparedStatement.");
			}
			
			//Handle and print if an exception is thrown
			//during executeUpdate().
		} catch (SQLException sE) {
			SQLHelpers.printSQLExceptionData(sE, 
					"searching via PreparedStatement.");
		}
	}
	
	/**
	 * 
	 * @param consoleIn
	 * @param conn
	 * @param tableName
	 */
	private static void updateExistingRecord(Scanner consoleIn, Connection conn, String tableName) {
		
		try {
			PreparedStatement pStat= null;
			PreparedStatement pCheck = null;
			
			String [] userPK = MainAppHelpers.getPrimaryKeyValues(consoleIn, tableName);
			
			String searchSQL = MainAppHelpers.buildSQLStringSearchSpecific(tableName);
			pCheck = conn.prepareStatement(searchSQL);
			
			if(pCheck != null) {
				
				MainAppHelpers.setParamSearchSpecific(pCheck, tableName, userPK);
				
				if (SQLHelpers.checkAndPrintResultSet(pCheck, false)) {
					
					String updateSQL = MainAppHelpers.buildSQLStringUpdate(tableName);
					pStat = conn.prepareStatement(updateSQL);
					
					if (pStat != null) {
						String [] userRow = MainAppHelpers.getUpdateRowValues(consoleIn, tableName);
						MainAppHelpers.setParamUpdate(pStat, tableName, userPK, userRow);
						pStat.executeUpdate();
						MainAppHelpers.printSuccessUpdate(conn, tableName);
						
					} else {
						//Handle case where pStat is null without exceptions thrown.
						System.out.println("Err: The update PreparedStatment is null.");
					}
					
					//Close PreparedStatement object when done with update.
					//Handle and print if an exception is thrown.
					try {
						if(pStat != null) pStat.close();
					} catch (SQLException sE) {
						SQLHelpers.printSQLExceptionData(sE, 
								"closing the update PreparedStatement.");
					}
					
				} else {
					//Handle case where the given primary key does not match any record.
					System.out.println("Sorry. The given primary key does not match any row in the table.");
					System.out.println("...Thus, no update operation has been performed.");
					System.out.println();
				}
				
			} else {
				//Handle case where pCheck is null without exceptions thrown.
				System.out.println("Err: The check PreparedStatment is null.");
			}
			
			//Close PreparedStatement object when done with update.
			//Handle and print if an exception is thrown.
			try {
				if(pCheck != null) pCheck.close();
			} catch (SQLException sE) {
				SQLHelpers.printSQLExceptionData(sE, 
						"closing the pre-update check PreparedStatement.");
			}
			
		//Handle and print if an exception is thrown
		//during executeUpdate().
		} catch (SQLException sE) {
			SQLHelpers.printSQLExceptionData(sE, 
					"updating via PreparedStatement.");
		}
	}
	
	/**
	 * Method (procedure, no return values) to delete an existing record
	 * from a given table in the given SQLite database.
	 * The record is located via the primary key value(s)
	 * obtained from the user.
	 * 
	 * @param consoleIn
	 * 		The user input stream.
	 * @param conn
	 * 		The connection object to the SQLite database.
	 * @param tableName
	 * 		The name of the table to make a deletion.
	 * 
	 * @requires consoleIn != null
	 * @requires conn != null
	 * @requires tableName is one of the four tables.
	 */
	private static void deleteExistingRecord(Scanner consoleIn, Connection conn, String tableName) {
		//Check method requirements are met.
		assert consoleIn != null && conn != null && tableName != null;
		
		try {
			PreparedStatement pStat= null;
			PreparedStatement pCheck = null;
			
			String [] userPK = MainAppHelpers.getPrimaryKeyValues(consoleIn, tableName);
			
			String searchSQL = MainAppHelpers.buildSQLStringSearchSpecific(tableName);
			pCheck = conn.prepareStatement(searchSQL);
			
			if(pCheck != null) {
				
				MainAppHelpers.setParamSearchSpecific(pCheck, tableName, userPK);
				
				if (SQLHelpers.checkAndPrintResultSet(pCheck, false)) {
					
					String deleteSQL = MainAppHelpers.buildSQLStringDelete(tableName);
					pStat = conn.prepareStatement(deleteSQL);
					
					if (pStat != null) {
						MainAppHelpers.setParamDelete(pStat, tableName, userPK);
						pStat.executeUpdate();
						MainAppHelpers.printSuccessDelete(conn, tableName);
						
					} else {
						//Handle case where pStat is null without exceptions thrown.
						System.out.println("Err: The delete PreparedStatment is null.");
					}
					
					//Close PreparedStatement object when done with delete.
					//Handle and print if an exception is thrown.
					try {
						if(pStat != null) pStat.close();
					} catch (SQLException sE) {
						SQLHelpers.printSQLExceptionData(sE, 
								"closing the delete PreparedStatement.");
					}
					
				} else {
					//Handle case where the given primary key does not match any record.
					System.out.println("Sorry. The given primary key does not match any row in the table.");
					System.out.println("...Thus, no delete (update) operation has been performed.");
					System.out.println();
				}
				
			} else {
				//Handle case where pCheck is null without exceptions thrown.
				System.out.println("Err: The check PreparedStatment is null.");
			}
			
			//Close PreparedStatement object when done with delete.
			//Handle and print if an exception is thrown.
			try {
				if(pCheck != null) pCheck.close();
			} catch (SQLException sE) {
				SQLHelpers.printSQLExceptionData(sE, 
						"closing the pre-delete check PreparedStatement.");
			}
			
		//Handle and print if an exception is thrown
		//during executeUpdate().
		} catch (SQLException sE) {
			SQLHelpers.printSQLExceptionData(sE, 
					"deleting via PreparedStatement.");
		}
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
			SQLHelpers.simpleQueryAndPrint(conn, allReportSQL[idx], exLabel);
			
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
