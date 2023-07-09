import java.util.*;
import java.sql.*;

public class MainApplication {
	
	private final static String DBPATH = "AllRecords.db";
	
	private static void printSQLExceptionData(SQLException sE, String exLabel) {
		System.out.println();
		System.out.println("Err: Exception thrown when " + exLabel);

		System.out.println();
		System.out.println("Printing message for exception below:");
		System.out.println(sE.getMessage());
		
		System.out.println();
		System.out.println("Printing stack trace for exception below:");
		sE.printStackTrace();
	}
	
	private static Connection initConnection(String dBPath) {
		
		String dBUrl = "jdbc:sqlite:" + dBPath;
		Connection conn = null;
		
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
	
	private static void closeConnection(Connection conn) {
		try {
			if (conn != null) conn.close();
		} catch (SQLException sE) {
			String exLabel = "closing connection.";
			printSQLExceptionData(sE, exLabel);
		}
	}

	public static void main(String[] args) {

		//Initialize connection to the database.
		//Remember to close connection at the very end.
		Connection conn = initConnection(DBPATH);
		
		//Initialize console input stream.
		//Remember to close input stream at the very end.
		Scanner consoleIn = new Scanner(System.in);
		
		//Print disclaimer (application README).
		Disclaimer.printDisclaimer(consoleIn);
		
		while(true) {
			//Start at the main menu.
			printMainMenu();
			
			//nextInt() does not consume '\n',
			//must then call nextLine().
			int userSelection = consoleIn.nextInt();
			consoleIn.nextLine();
			
			int mainMenuInsertOption = 0;
			int mainMenuSearchOption = 1;
			int mainMenuPrintAllOption = 2;
			
			if(userSelection == mainMenuInsertOption) {
				
				/**
                 * Option 0: insert new tuple.
                 * 
                 * First,   select the relation for insertion;
                 * Second,  obtain all attribute values for the tuple;
                 * Third,   add tuple to the relation;
                 * Fourth,  print all tuples of the relation;
                 * Finally, return to main menu.
                 */
				System.out.println();
				System.out.println("Select Relation to Insert");
				
				
				
				System.out.println("Returning to Main Menu.");
				System.out.println();
				
			} else if(userSelection == mainMenuSearchOption) {
				
				/**
                 * Option 1: search for existing tuple.
                 * 
                 * First,   select the relation to search from;
                 * Second,  obtain all key attribute values for the search;
                 * Third,   search for and return the results (tuple or not found);
                 * Fourth,  let user decide whether to delete or update tuple;
                 * Finally, return to main menu.
                 */
				
				System.out.println();
				System.out.println("Select Relation to Search From");
				
				
				
				System.out.println("Returning to Main Menu.");
				System.out.println();
				
			} else if (userSelection == mainMenuPrintAllOption) {
				
				/**
                 * Option 2: print every relation name and tuples.
                 */
				
				System.out.println();
				
				
				
				System.out.println("Returning to Main Menu.");
				System.out.println();
				
			} else break;
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
	 * Print front-end main menu options,
	 * and prompt for user selection.
	 */
	private static void printMainMenu() {
		System.out.println("[Main Menu]");
		System.out.println();
		
		System.out.println("Select an option"
				+" by entering the corresponding index below:");
		System.out.println();

		System.out.println("0. Add new record.");
		System.out.println("1. Search existing record.");
		System.out.println("2. Print all relations and tuples.");
		System.out.println("3. Exit.");
		System.out.println();

		System.out.print("Enter your selection: ");
	}
	
}
