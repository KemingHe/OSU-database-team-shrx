/**
 * General helper methods and constants for
 * SU23 CSE3241 Team SHRX's database main program.
 * 
 * @author Keming (he.1537)
 * @version 20230719
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class MainAppHelpers {

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
	public static Connection initConnection(String dBPath) {
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
			SQLHelpers.printSQLExceptionData(sE, exLabel);
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
	public static void closeConnection(Connection conn) {
		//Try to close the database connection if 
		//connect is not already null.
		//Handle and print if exception is thrown.
		try {
			if (conn != null) conn.close();
		} catch (SQLException sE) {
			String exLabel = "closing connection.";
			SQLHelpers.printSQLExceptionData(sE, exLabel);
		}
	}
	
	/*
	 * Below are constants for:
	 * Main menu option indexes;
	 * the four valid table names;
	 * and the option indexes for each table.
	 */
	public static final String OPT_INSERT = "0";
	public static final String OPT_SEARCH = "1";
	public static final String OPT_UPDATE = "2";
	public static final String OPT_DELETE = "3";
	public static final String OPT_PRINT_REP = "4";
	
	public static final String TNAME_CUS = "CUSTOMER";
	public static final String TNAME_ANI = "ANIME";
	public static final String TNAME_STU = "STUDIO";
	public static final String TNAME_CRE = "CREATOR";
	
	public static final String TNUM_CUS = "0";
	public static final String TNUM_ANI = "1";
	public static final String TNUM_STU = "2";
	public static final String TNUM_CRE = "3";
	
	/**
	 * Method (procedure, no return value) to print
	 * all five main menu options and to prompt
	 * user selection.
	 */
	public static void printMainMenu() {
		System.out.println();
		
		System.out.println("[Main Menu]");
		System.out.println();
		
		System.out.println("Select an option"
				+" by entering the corresponding index below:");
		System.out.println();

		System.out.println(OPT_INSERT + ". Insert a new record.");
		System.out.println(OPT_SEARCH + ". Search for existing records.");
		System.out.println(OPT_UPDATE + ". Update an existing record.");
		System.out.println(OPT_DELETE + ". Delete an existing record.");
		System.out.println(OPT_PRINT_REP + ". Print a list of useful reports.");
		System.out.println();
		System.out.println("5. Exit.");
		System.out.println();

		System.out.print("Enter your selection: ");
	}
	
	/**
	 * Method (procedure, no return values) to print 
	 * the header/indicator of the currently selected option.
	 * 
	 * @param optionStr
	 * 		The index string of the currently selected option.
	 * 
	 * @requires optionStr != null
	 */
	public static void printOptionHeader(String optionStr) {
		//Check method requirement that optionStr is not null.
		assert optionStr != null;
		
		System.out.println();
		switch(optionStr) {
			case OPT_INSERT:
				System.out.println("[Option " + OPT_INSERT 
					+ ": Insert a New Record]");
				break;
			case OPT_SEARCH:
				System.out.println("[Option " + OPT_SEARCH 
					+ ": Search Existing Records]");
				break;
			case OPT_UPDATE:
				System.out.println("[Option " + OPT_UPDATE 
					+ ": Update An Existing Record]");
				break;
			case OPT_DELETE:
				System.out.println("[Option " + OPT_DELETE
					+ ": Delete An Existing Record]");
				break;
			case OPT_PRINT_REP:
				System.out.println("[Option " + OPT_PRINT_REP 
					+ ": Print All Useful Reports]");
				break;
			default:
				System.out.println("Err: Invalid menu option.");
				break;
		}
	}
	
	/**
	 * Method (procedure, no return values) to print
	 * and let the user know that the program is returning to
	 * the main menu.
	 */
	public static void printReturnToMain() {
		System.out.println("Returning to Main Menu.");
		System.out.println();
	}
	
	/**
	 * Method (procedure, no return values) to print
	 * and let the user know that the program is exiting.
	 */
	public static void printExitMessage() {
		String exitMessage = "Thank you for "
				+ "using Team SHRX's Java frontend. "
				+ "Goodbye.";
		System.out.println();
		System.out.println(exitMessage);
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
	public static String getTableName(Scanner consoleIn, String purposeStr) {
		//Check method requirement that input scanner is not null.
		assert consoleIn != null && purposeStr != null;
		
		System.out.println();
		System.out.println("Select an table to " + purposeStr);
		System.out.println("by entering the corresponding index below:");
		System.out.println();

		System.out.println(TNUM_CUS + ". " + TNAME_CUS);
		System.out.println(TNUM_ANI + ". " + TNAME_ANI);
		System.out.println(TNUM_STU + ". " + TNAME_STU);
		System.out.println(TNUM_CRE + ". " + TNAME_CRE);
		System.out.println();
		System.out.println("4. Exit.");
		System.out.println();

		System.out.print("Enter your selection: ");
		
		//Initialize return string, default to null (exit).
		String selectedTName = null;
		switch (consoleIn.nextLine()) {
			case TNUM_CUS: 	selectedTName = TNAME_CUS;
						break;
			case TNUM_ANI: 	selectedTName = TNAME_ANI;
						break;
			case TNUM_STU:	selectedTName = TNAME_STU;
						break;
			case TNUM_CRE: 	selectedTName = TNAME_CRE;
						break;
			default: 	selectedTName = null;
						break;
						
		}
		return selectedTName;
	}
	
	/*
	 * The following four string arrays
	 * contains column names and constraints
	 * for tables CUSTOMER, ANIME, STUDIO, and CREATOR.
	 * They are used for column value input prompts.
	 */
	private static final String [] COL_CUS = {
			"Username (type text, prime, not null)",
			"Password (type text, not null)",
			"First_name (type text, not null)",
			"Last_name (type text, not null)",
			"Email (type text, not null)",
			"Creation_date (type text, not null)",
			"Billing_info (type text, not null)",
			"DOB (type text, not null)"
	};
		
	private static final String [] COL_ANI = {
			"Title (type text, prime, not null)",
			"Description (type text)",
			"Genre (type text)",
			"Price (type integer, not null)",
			"Release_year (type integer, not null)"
	};
		
	private static final String [] COL_STU = {
			"Name (type text, prime, not null)",
			"Description (type text)",
			"Website (type text)",
			"Address (type text)"
	};
		
	private static final String [] COL_CRE = {
			"Anime_title (type text, prime, foreign key, not null)",
			"Studio_name (type text, prime, foreign key, not null)"
	};
		
	/**
	 * Method to prompt and obtain from the user 
	 * all column values of a row
	 * to be inserted/updated into a given table.
	 * 
	 * @param consoleIn
	 * 		The user input stream.
	 * @param tableName
	 * 		The name of the table the user wish to insert/update into.
	 * 
	 * @return newRow
	 * 		A string array of column values by user input.
	 * 		Null if tableName is invalid.
	 * 
	 * @requires consoleIn != null
	 * @requires tableName is one of the four valid tables.
	 */
	public static String [] getRowColumnValues(Scanner consoleIn, String tableName) {
		//Check method requirements are met.
		assert consoleIn != null && tableName != null;
		
		//Declare return string array.
		String [] newRow = null;
		
		//Determine column name prompt based on given table name.
		String [] columnNames = null;
		switch(tableName) {
			case TNAME_CUS:	
				columnNames = COL_CUS; 
				break;
			case TNAME_ANI: 		
				columnNames = COL_ANI; 
				break;
			case TNAME_STU:  	
				columnNames = COL_STU; 
				break;
			case TNAME_CRE: 	
				columnNames = COL_CRE; 
				break;
			default: 		
				columnNames = null; 
				break;
		}
		
		if(columnNames != null) {
			
			System.out.println();
			System.out.println("Please enter the new column values for row in table: "
					+ tableName);
			System.out.println();
			
			int columnNum = columnNames.length;
			newRow = new String [columnNum];
			
			//Obtain each column value from the user
			//and store in the return string array.
			for (int idx = 0; idx < columnNum; idx++) {
				
				System.out.print(columnNames[idx] + ": ");
				newRow[idx] = consoleIn.nextLine();
			}
			
		} else {
			//Else given table name is invalid. 
			//Print error and return null.
			System.out.println("Err: invalid table name.");
			System.out.println("...Table must be either CUSTOMER, ANIME, STUDIO, or CREATOR.");
			System.out.println("...Method call returning null.");
		}
			
		System.out.println();
		
		return newRow;
	}
	
	/*The following four string arrays contains primary key name(s)
	 * for tables CUSTOMER, ANIME, STUDIO, and CREATOR.
	 * They are used for primary key value input prompts.
	 */
	private static final String [] PK_CUS = {"Username"};
	private static final String [] PK_ANI = {"Title"};
	private static final String [] PK_STU = {"Name"};
	private static final String [] PK_CRE = {"Anime_title", "Studio_name"};
	
	/**
	 * Method to prompt and obtain from the user 
	 * the primary key values for a row in a given table.
	 * Used to later locate a row to update or delete.
	 * 
	 * @param consoleIn
	 * 		The user input stream.
	 * @param tableName
	 * 		The name of the table the user wish to search from.
	 * 
	 * @return userPK
	 * 		A string array of primary key value(s) by user input.
	 * 
	 * @requires consoleIn != null
	 * @requires tableName is one of the four valid tables.
	 */
	public static String [] getPrimaryKeyValues(Scanner consoleIn, String tableName) {
		//Check method requirements are met.
		assert consoleIn != null && tableName != null;
		
		//Declare return string array.
		String [] userPK = null;
		
		//Determine column name prompt based on given table name.
		String [] pKNames = null;
		switch(tableName) {
			case TNAME_CUS:		
				pKNames = PK_CUS; 
				break;
			case TNAME_ANI: 	
				pKNames = PK_ANI; 
				break;
			case TNAME_STU:  	
				pKNames = PK_STU; 
				break;
			case TNAME_CRE: 	
				pKNames = PK_CRE; 
				break;
			default: 			
				pKNames = null; 
				break;
		}
		
		if(pKNames != null) {
			
			System.out.println();
			System.out.println("In order to locate the row you need in table: "
					+ tableName);
			System.out.println("...Please enter its primary key value(s): ");
			System.out.println();
			
			int pKNum = pKNames.length;
			userPK = new String [pKNum];
			
			//Obtain each column value from the user
			//and store in the return string array.
			for (int idx = 0; idx < pKNum; idx++) {
				
				System.out.print(pKNames[idx] + ": ");
				userPK[idx] = consoleIn.nextLine();
			}
			
		} else {
			//Else given table name is invalid. 
			//Print error and return null.
			System.out.println("Err: invalid table name.");
			System.out.println("...Table must be either CUSTOMER, ANIME, STUDIO, or CREATOR.");
			System.out.println("...Method call returning null.");
		}
			
		System.out.println();
		
		return userPK;
	}
	
	/**
	 * Method to prompt and obtain from the user 
	 * the updated column values for a row in a given table.
	 * Used to later update such row.
	 * 
	 * @param consoleIn
	 * 		The user input stream.
	 * @param tableName
	 * 		The name of the table the user wish to update a row to.
	 * 
	 * @return updatedRow
	 * 		A string array of update column values by user input.
	 * 
	 * @requires consoleIn != null
	 * @requires tableName is one of the four valid tables.
	 */
	public static String [] getUpdateRowValues(Scanner consoleIn, String tableName) {
		//Check method requirements are met.
		assert consoleIn != null && tableName != null;
		
		//Declare return string array.
		String [] updatedRow = null;
		
		//Determine column name prompt based on given table name.
		String [] columnNames = null;
		switch(tableName) {
			case TNAME_CUS:	
				columnNames = COL_CUS; 
				break;
			case TNAME_ANI: 		
				columnNames = COL_ANI; 
				break;
			case TNAME_STU:  	
				columnNames = COL_STU; 
				break;
			case TNAME_CRE: 	
				columnNames = COL_CRE; 
				break;
			default: 		
				columnNames = null; 
				break;
		}
		
		if(columnNames != null) {
						
			//The starting index of non-key columns for each table.
			int pKOffset = 0;
			
			switch(tableName) {
				case TNAME_CUS:	
				case TNAME_ANI: 		
				case TNAME_STU:  	
					System.out.println("Please enter the updated values "
							+ "for non-key columns/attributes of your chosen row:");
					pKOffset = 1;
					break;
					
				case TNAME_CRE: 	
					System.out.println("Please enter the updated foreign key pair "
							+ "of your chosen row:");
					pKOffset = 0;
					break;
				default: 
					System.out.println("Err: Invalid table name.");
					break;
			}
			
			System.out.println();
			
			int updatedRowSize = columnNames.length - pKOffset;
			updatedRow = new String [updatedRowSize];
			
			//Obtain each column value from the user
			//and store in the return string array.
			for (int idx = 0; idx < updatedRowSize; idx++) {
				
				System.out.print(columnNames[idx + pKOffset] + ": ");
				updatedRow[idx] = consoleIn.nextLine();
			}
			
		} else {
			//Else given table name is invalid. 
			//Print error and return null.
			System.out.println("Err: Invalid table name.");
			System.out.println("...Table must be either CUSTOMER, ANIME, STUDIO, or CREATOR.");
			System.out.println("...Method call returning null.");
		}
			
		System.out.println();
		
		return updatedRow;
	}
	
	/**
	 * Method to build a String object used to later create a
	 * PreparedStatement to insert a new row. Future parameter positions
	 * are marked using a questions mark.
	 * 
	 * @param tableName
	 * 		The name of the table the user wishes to insert into.
	 * @return deleteSQL
	 * 		The plain SQL update query string with parameters marked.
	 * 
	 * @requires tableName is one of the four valid tables.
	 */
	public static String buildSQLStringInsert(String tableName) {
		//Check method requirement that table name is valid.
		assert tableName != null;
		
		String insertSQL = null;
		switch (tableName) {
			case TNAME_CUS: 
				insertSQL = "INSERT INTO " + tableName 
					+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
				break;
			case TNAME_ANI: 
				insertSQL = "INSERT INTO " + tableName + " VALUES(?, ?, ?, ?, ?);";
				break;
			case TNAME_STU: 
				insertSQL = "INSERT INTO " + tableName + " VALUES(?, ?, ?, ?);";
				break;
			case TNAME_CRE: 
				insertSQL = "INSERT INTO " + tableName + " VALUES(?, ?);";
				break;
			default: 
				insertSQL = null;
				break;
		}
		return insertSQL;
	}
	
	/**
	 * Method to build a String object used to later create a
	 * PreparedStatement to specifically search a row. Future parameter positions
	 * are marked using a questions mark.
	 * 
	 * @param tableName
	 * 		The name of the table the user wishes to search from.
	 * @return deleteSQL
	 * 		The plain SQL select query string with parameters marked.
	 * 
	 * @requires tableName is one of the four valid tables.
	 */
	public static String buildSQLStringSearchSpecific(String tableName) {
		//Check method requirement that table name is valid.
		assert tableName != null;
		
		String searchSQL = null;
		switch (tableName) {
			case TNAME_CUS: 
				searchSQL = "SELECT * FROM " + tableName + " WHERE Username = ?;";
				break;
			case TNAME_ANI: 
				searchSQL = "SELECT * FROM " + tableName + " WHERE Title = ?;";
				break;
			case TNAME_STU: 
				searchSQL = "SELECT * FROM " + tableName + " WHERE Name = ?;";
				break;
			case TNAME_CRE: 
				searchSQL = "SELECT * FROM " + tableName 
					+ " WHERE Anime_title = ? AND Studio_name = ?;";
				break;
			default: 
				searchSQL = null;
				break;
		}
		return searchSQL;
	}
	
	/**
	 * Method to build a String object used to later create a
	 * PreparedStatement to generally search rows. Future parameter positions
	 * are marked using a questions mark.
	 * 
	 * @param tableName
	 * 		The name of the table the user wishes to search from.
	 * @return deleteSQL
	 * 		The plain SQL select query string with parameters marked.
	 * 
	 * @requires tableName is one of the four valid tables.
	 */
	public static String buildSQLStringSearchGeneral(String tableName) {
		//Check method requirement that table name is valid.
		assert tableName != null;
		
		String searchSQL = null;
		switch (tableName) {
			case TNAME_CUS: 
				searchSQL = "SELECT * FROM " + tableName + " WHERE Username LIKE ?;";
				break;
			case TNAME_ANI: 
				searchSQL = "SELECT * FROM " + tableName + " WHERE Title LIKE ?;";
				break;
			case TNAME_STU: 
				searchSQL = "SELECT * FROM " + tableName + " WHERE Name LIKE ?;";
				break;
			case TNAME_CRE: 
				searchSQL = "SELECT * FROM " + tableName 
					+ " WHERE Anime_title LIKE ? AND Studio_name LIKE ?;";
				break;
			default: 
				searchSQL = null;
				break;
		}
		return searchSQL;
	}
	
	/**
	 * Method to build a String object used to later create a
	 * PreparedStatement to update a row. Future parameter positions
	 * are marked using a questions mark.
	 * 
	 * @param tableName
	 * 		The name of the table the user wishes to update from.
	 * @return updateSQL
	 * 		The plain SQL update query string with parameters marked.
	 * 
	 * @requires tableName is one of the four valid tables.
	 */
	public static String buildSQLStringUpdate(String tableName) {
		//Check method requirement that table name is valid.
		assert tableName != null;
		
		String updateSQL = null;
		switch (tableName) {
			case TNAME_CUS: 
				updateSQL = "UPDATE " + tableName + " "
					+ "SET "
					+ "Password = ?, "
					+ "First_name = ?, "
					+ "Last_name = ?, "
					+ "Email = ?, "
					+ "Creation_date = ?, "
					+ "Billing_info = ?, "
					+ "DOB = ? "
					+ "WHERE Username = ?;";
				break;
				
			case TNAME_ANI: 
				updateSQL = "UPDATE " + tableName + " "
					+ "SET "
					+ "Description = ?, "
					+ "Genre = ?, "
					+ "Price = ?, "
					+ "Release_year = ? "
					+ "WHERE Title = ?;";
				break;
				
			case TNAME_STU: 
				updateSQL = "UPDATE " + tableName + " "
					+ "SET "
					+ "Description = ?, "
					+ "Website = ?, "
					+ "Address = ? "
					+ "WHERE Name = ?;";
				break;
				
			case TNAME_CRE: 
				updateSQL = "UPDATE " + tableName + " "
					+ "SET "
					+ "Anime_title = ?, "
					+ "Studio_name = ? "
					+ "WHERE Anime_title = ? AND Studio_name = ?;";
				break;
				
			default: 
				updateSQL = null;
				break;
		}
		return updateSQL;
	}
	
	/**
	 * Method to build a String object used to later create a
	 * PreparedStatement to delete a row. Future parameter positions
	 * are marked using a questions mark.
	 * 
	 * @param tableName
	 * 		The name of the table the user wishes to delete from.
	 * @return deleteSQL
	 * 		The plain SQL update query string with parameters marked.
	 * 
	 * @requires tableName is one of the four valid tables.
	 */
	public static String buildSQLStringDelete(String tableName) {
		//Check method requirement that table name is valid.
		assert tableName != null;
		
		String deleteSQL = null;
		switch (tableName) {
			case TNAME_CUS: 
				deleteSQL = "DELETE FROM " + tableName + " WHERE Username = ?;";
				break;
			case TNAME_ANI: 
				deleteSQL = "DELETE FROM " + tableName + " WHERE Title = ?;";
				break;
			case TNAME_STU: 
				deleteSQL = "DELETE FROM " + tableName + " WHERE Name = ?;";
				break;
			case TNAME_CRE: 
				deleteSQL = "DELETE FROM " + tableName 
					+ " WHERE Anime_title = ? AND Studio_name = ?;";
				break;
			default: 
				deleteSQL = null;
				break;
		}
		return deleteSQL;
	}
	
	/**
	 * Method to set the parameters of the PreparedStatement
	 * object used for inserting a new row to a given table.
	 * The parameters are provided in a string array.
	 * 
	 * @param pStat
	 * 		The PreparedStatement object to set parameters of.
	 * @param tableName
	 * 		The name of the table to insert into.
	 * @param rowVal
	 * 		The column values for the new row to be inserted.
	 * 
	 * @requires pStat != null
	 * @requires tableName is one of the four valid tables.
	 * @requires rowVal contains the valid column values of a new row
	 * 		to be inserted into the given table.
	 */
	public static void setParamInsert (PreparedStatement pStat, 
			String tableName, String [] rowVal) {
		//Check method requirements that the PreparedStatement is not null,
		//and that the table name and the new row column values are valid.
		assert pStat != null && tableName != null && rowVal != null;
		
		try {
			switch (tableName) {
			
				case TNAME_CUS:
				case TNAME_STU:
				case TNAME_CRE:
					for(int i = 0; i < rowVal.length; i++) {
						pStat.setString((i+1), rowVal[i]);
					}
					break;
				
				case TNAME_ANI: 
					//The first three columns of ANIME is type text;
					//and the last two columns of ANIME is type integer.
					for(int i = 0; i < rowVal.length; i++) {
						
						int textIntCutoff = 3;
						if(i < textIntCutoff) {
							pStat.setString((i+1), rowVal[i]);
							
						} else {
							//Parse user string to integer.
							//Handle and print if exception is thrown during parsing.
							try {
								int tmpVal = Integer.parseInt(rowVal[i]);
								pStat.setInt((i+1), tmpVal);
								
							} catch (NumberFormatException numE) {
								System.out.println(numE.getMessage());
							}
						}
					}
					break;
				
				default: 
					System.out.println("Err: Invalid table name.");
					break;
			}
		} catch (SQLException sE) {
			SQLHelpers.printSQLExceptionData(sE, 
					"setting parameters for insert PreparedStatment.");
		}
	}
	
	/**
	 * Method to set the parameters of the PreparedStatement
	 * object used for searching up one specific row from a given table.
	 * The parameters are provided in a string array.
	 * 
	 * @param pStat
	 * 		The PreparedStatement object to set parameters of.
	 * @param tableName
	 * 		The name of the table to search from.
	 * @param pKVal
	 * 		The primary key values used to search one specific row.
	 * 
	 * @requires pStat != null
	 * @requires tableName is one of the four valid tables.
	 * @requires pKVal contains the valid primary key values of a row
	 * 		in the given table.
	 */
	public static void setParamSearchSpecific (PreparedStatement pStat,
			String tableName, String [] pKVal) {
		
		//Check method requirements that the PreparedStatement is not null,
		//and that the table name and the primary key values are valid.
		assert pStat != null && tableName != null && pKVal != null;
		
		try {
			//Use switch statement here in case of future changes.
			switch (tableName) {
				case TNAME_CUS:
				case TNAME_ANI:
				case TNAME_STU:
				case TNAME_CRE:
					for(int i = 0; i < pKVal.length; i++) {
						pStat.setString((i+1), pKVal[i]);
					}
					break;
					
				default:
					break;
			}
		} catch (SQLException sE) {
			SQLHelpers.printSQLExceptionData(sE, 
					"setting parameters for specific search PreparedStatment.");
		}
	}
	
	/**
	 * Method to set the parameters of the PreparedStatement
	 * object used for searching up multiple rows from a given table.
	 * The parameters are provided in a string array.
	 * 
	 * @param pStat
	 * 		The PreparedStatement object to set parameters of.
	 * @param tableName
	 * 		The name of the table to search from.
	 * @param pKVal
	 * 		The primary key values used to search all matching rows.
	 * 
	 * @requires pStat != null
	 * @requires tableName is one of the four valid tables.
	 * @requires pKVal contains substrings of valid primary key values of rows
	 * 		in the given table.
	 */
	public static void setParamSearchGeneral (PreparedStatement pStat,
			String tableName, String [] pKVal) {
		
		//Check method requirements that the PreparedStatement is not null,
		//and that the table name and the primary key values are valid.
		assert pStat != null && tableName != null && pKVal != null;
		
		try {
			//Use switch statement here in case of future changes.
			switch (tableName) {
				case TNAME_CUS:
				case TNAME_ANI:
				case TNAME_STU:
				case TNAME_CRE:
					//Add wild-card character for general search.
					for(int i = 0; i < pKVal.length; i++) {
						pStat.setString((i+1), ("%" + pKVal[i] + "%"));
					}
					break;
					
				default:
					break;
			}
		} catch (SQLException sE) {
			SQLHelpers.printSQLExceptionData(sE, 
					"setting parameters for general search PreparedStatment.");
		}
	}
	
	/**
	 * Method to set the parameters of the PreparedStatement
	 * object used for updating a row in a given table.
	 * The parameters are provided in two separate string arrays:
	 * one containing the update column values,
	 * the other containing the primary key values to identify the row.
	 * 
	 * @param pStat
	 * 		The PreparedStatement object to set parameters of.
	 * @param tableName
	 * 		The name of the table to update a row from.
	 * @param pKVal
	 * 		The primary key values used to identify a row to update.
	 * @param rowVal
	 * 		The updated row values.
	 * 
	 * @requires pStat != null
	 * @requires tableName is one of the four valid tables.
	 * @requires pKVal contains valid primary key values of a row
	 * 		in the given table.
	 * @requires rowVal contains valid updated row values.
	 */
	public static void setParamUpdate (PreparedStatement pStat,
			String tableName, String [] pKVal, String [] rowVal) {
		
		//Check method requirements that the PreparedStatement is not null,
		//and that the table name and the primary key values are valid.
		assert pStat != null && tableName != null 
				&& pKVal != null && rowVal != null;
		
		try {
			
			int globalIdx = 0;
			int localIdx = 0;
			
			//First set the update values.
			switch (tableName) {
			
				case TNAME_CUS:
				case TNAME_STU:
				case TNAME_CRE:
					for(globalIdx = 0; globalIdx < rowVal.length; globalIdx++) {
						pStat.setString((globalIdx+1), rowVal[globalIdx]);
					}
					break;
				
				case TNAME_ANI: 
					//The first two non-key columns of ANIME is type text;
					//and the last two columns of ANIME is type integer.
					for(globalIdx = 0; globalIdx < rowVal.length; globalIdx++) {
						
						int textIntCutoff = 2;
						if(globalIdx < textIntCutoff) {
							pStat.setString((globalIdx+1), rowVal[globalIdx]);
							
						} else {
							//Parse user string to integer.
							//Handle and print if exception is thrown during parsing.
							try {
								int tmpVal = Integer.parseInt(rowVal[globalIdx]);
								pStat.setInt((globalIdx+1), tmpVal);
								
							} catch (NumberFormatException numE) {
								System.out.println(numE.getMessage());
							}
						}
					}
					break;
				
				default: 
					System.out.println("Err: Invalid table name.");
					break;
			}
			
			//Next set the primary key values.
			//Use switch statement here in case of future changes.
			switch (tableName) {
				case TNAME_CUS:
				case TNAME_ANI:
				case TNAME_STU:
				case TNAME_CRE:
					for(localIdx = 0; localIdx < pKVal.length; ) {
						pStat.setString((globalIdx+1), pKVal[localIdx]);
						localIdx++;
						globalIdx++;
					}
					break;
					
				default:
					break;
			}
			
		} catch (SQLException sE) {
			SQLHelpers.printSQLExceptionData(sE, 
					"setting parameters for insert PreparedStatment.");
		}
	}
	
	/**
	 * Method to set the parameters of the PreparedStatement
	 * object used for deleting a row from a given table.
	 * The parameters are provided in a string array.
	 * 
	 * @param pStat
	 * 		The PreparedStatement object to set parameters of.
	 * @param tableName
	 * 		The name of the table to delete a row from.
	 * @param pKVal
	 * 		The primary key values used to identify a row to delete.
	 * 
	 * @requires pStat != null
	 * @requires tableName is one of the four valid tables.
	 * @requires pKVal contains valid primary key values of a row
	 * 		in the given table.
	 */
	public static void setParamDelete (PreparedStatement pStat,
			String tableName, String [] pKVal) {
		
		//Check method requirements that the PreparedStatement is not null,
		//and that the table name and the primary key values are valid.
		assert pStat != null && tableName != null && pKVal != null;
		
		try {
			//Use switch statement here in case of future changes.
			switch (tableName) {
				case TNAME_CUS:
				case TNAME_ANI:
				case TNAME_STU:
				case TNAME_CRE:
					for(int i = 0; i < pKVal.length; i++) {
						pStat.setString((i+1), pKVal[i]);
					}
					break;
					
				default:
					break;
			}
		} catch (SQLException sE) {
			SQLHelpers.printSQLExceptionData(sE, 
					"setting parameters for delete PreparedStatment.");
		}
	}

	/**
	 * Method (procedure, no return values) to notify
	 * the user that the new row has been successfully inserted. 
	 * Also print the corresponding table content as confirmation.
	 * 
	 * @param conn
	 * 		The Connection object to the SQLite database.
	 * @param tableName
	 * 		The name of the table to print confirmation of.
	 * 
	 * @requires conn != null
	 * @requires tableName is one of the four valid tables.
	 */
	public static void printSuccessInsert(Connection conn, String tableName) {
		//Check method requirements that Connection is not null,
		//and that tableName is valid.
		assert conn != null && tableName != null;
		
		System.out.println("New data successfully inserted into table: " 
				+ tableName + ".");
		System.out.println("...Printing updated table content as confirmation.");
		System.out.println();
		
		SQLHelpers.printTable(conn, tableName);
	}
	
	/**
	 * Method (procedure, no return values) to notify
	 * the user that the chosen, valid row has been
	 * successfully updated. Also print the corresponding
	 * table content as confirmation.
	 * 
	 * @param conn
	 * 		The Connection object to the SQLite database.
	 * @param tableName
	 * 		The name of the table to print confirmation of.
	 * 
	 * @requires conn != null
	 * @requires tableName is one of the four valid tables.
	 */
	public static void printSuccessUpdate(Connection conn, String tableName) {
		//Check method requirements that Connection is not null,
		//and that tableName is valid.
		assert conn != null && tableName != null;
		
		System.out.println("Record successfully updated in table: " 
				+ tableName + ".");
		System.out.println("...Printing updated table content "
				+ "as confirmation.");
		System.out.println();
		
		SQLHelpers.printTable(conn, tableName);
	}
	
	/**
	 * Method (procedure, no return values) to notify
	 * the user that the chosen, valid row has been
	 * successfully deleted. Also print the corresponding
	 * table content as confirmation.
	 * 
	 * @param conn
	 * 		The Connection object to the SQLite database.
	 * @param tableName
	 * 		The name of the table to print confirmation of.
	 * 
	 * @requires conn != null
	 * @requires tableName is one of the four valid tables.
	 */
	public static void printSuccessDelete(Connection conn, String tableName) {
		//Check method requirements that Connection is not null,
		//and that tableName is valid.
		assert conn != null && tableName != null;
		
		System.out.println("Record successfully deleted from table: " 
				+ tableName + ".");
		System.out.println("...Printing updated table content "
				+ "as confirmation.");
		System.out.println();
		
		SQLHelpers.printTable(conn, tableName);
	}
}
