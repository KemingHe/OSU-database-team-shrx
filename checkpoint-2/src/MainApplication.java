import java.util.ArrayList;
import java.util.Scanner;

public class MainApplication {

	public static void main(String[] args) {

		//Declare and seed (add data to) relations.
		Relation [] allRelations = initAllRelations();
		
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
				
				printAllRelationNames(allRelations);
				int userRIdx = consoleIn.nextInt();
				consoleIn.nextLine();
				
				Relation tmpR = allRelations[userRIdx];
				
				System.out.println();
				System.out.println("Insert new tuple for relation "
						+ tmpR.getName()
						+ "\n");
				
				String [] newTuple = 
						Relation.createNewTuple(tmpR, consoleIn);
				tmpR.insertNewTuple(newTuple);
				
				System.out.println("New tuple successfully added to "
						+ tmpR.getName()
						+ ".\n\nPrinting all existing tuples:\n");
				tmpR.printAllTuples();
				
				System.out.println("Returning to Main Menu.\n");
				
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
				
				printAllRelationNames(allRelations);
				int userRIdx = consoleIn.nextInt();
				consoleIn.nextLine();
				
				Relation tmpR = allRelations[userRIdx];
				
				System.out.println();
				System.out.println("Search for existing tuple from relation "
						+ tmpR.getName()
						+ "\n");
				
				//Obtain key value(s) used for tuple search
				System.out.println("Please enter key attributes values to search:\n");
				
				int tmpRNumOfKeys = tmpR.getPAList().length;
				String [] userKeyInput = new String [tmpRNumOfKeys];
				String [] tmpRPAList = tmpR.getPAList();
				
				for(int tmpIdx = 0; tmpIdx < tmpRNumOfKeys; tmpIdx++) {
					System.out.print("Enter value for key attribute ("
							+ tmpRPAList[tmpIdx]
							+ "): ");
					userKeyInput[tmpIdx] = consoleIn.nextLine();
				}
				
				//Search and return first matching tuple using user keys.
				String [] searchResult = null;
				ArrayList <String[]> tmpRAllTuples = tmpR.getAllTuples();
				
				//markingIdx indexes which tuple of the relation.
				int markingIdx = 0;
				while(markingIdx < tmpRAllTuples.size()) {
					
					boolean matchFound = true;
					String [] tmpTuple = tmpRAllTuples.get(markingIdx);
					
					//tmpIdx indexes which attribute of the tuple.
					for(int tmpIdx = 0; tmpIdx < tmpRNumOfKeys; tmpIdx++) {
						//Short-circuit to save time.
						if(!userKeyInput[tmpIdx].equals(tmpTuple[tmpIdx])) {
							matchFound = false;
							break;
						}
					}
					
					//Only return the first match.
					if(matchFound) {
						searchResult = tmpTuple;
						break;
					}
					
					markingIdx++;
				}
				
				//If a match has been found, continue to update or delete.
				if (searchResult != null) {
					
					//Confirm tuple found and print.
					System.out.println();
					System.out.println("The tuple from relation "
							+ tmpR.getName()
							+ " that matches your search is:\n");
					tmpR.printOneTuple(markingIdx);
					
					//Continue to select update, delete, or return to main menu.
					System.out.println();
					System.out.println("If you'd like to update or delete this tuple, "
							+ "select the corresponding index below:\n");
					System.out.println("0. Update tuple.");
					System.out.println("1. Delete tuple.");
					System.out.println("2. Return to main menu.");
					System.out.println();
					System.out.print("Enter your selection: ");
					int userTupleDecision = consoleIn.nextInt();
					consoleIn.nextLine();
					
					//Update is implemented as overwrite.
					if(userTupleDecision == 0) {
						
						System.out.println();
						System.out.println("To update tuple, "
								+ "please enter its updated attribute values.\n");
						
						String [] overwriteTuple = 
								Relation.createNewTuple(tmpR, consoleIn);
						tmpR.updateExistingTuple(overwriteTuple, markingIdx);
						
						System.out.println("Your selected tuple "
								+ "has been successfully updated.");
						
					} else if (userTupleDecision == 1) {
						tmpR.deleteExistingTuple(markingIdx);
						System.out.println();
						System.out.println("Your selected tuple "
								+ "has been successfully deleted.");
					}
					
					
				} else {
					System.out.println();
					System.out.println("Unfortunately,"
							+ " no matching tuple was found.");
				};
				
				System.out.println();
				System.out.println("Returning to Main Menu.\n");
				
			} else if (userSelection == mainMenuPrintAllOption) {
				
				/**
                 * Option 2: print every relation name and tuples.
                 */
				
				System.out.println();
				
				for(int tmpIdx = 0; tmpIdx < allRelations.length; tmpIdx++) {
					
					Relation tmpR = allRelations[tmpIdx];
					
					//Print until reached null Relation reference.
					if(tmpR != null) {
						
						System.out.println("Printing all tuples for "
								+ tmpR.getName()
								+ "\n");
						tmpR.printAllTuples();
						
					} else break;
				}
				
				System.out.println("Returning to Main Menu.\n");
				
			} else break;
		}
		
		
		//Main loop exited, print exit message and close scanner.
		String exitMessage = "Thank you for using Team SHRX's Java frontend."
				+ " Goodbye.";
		
		System.out.println();
		System.out.println(exitMessage);
		consoleIn.close();
				
	}

	/**
	 * Create an array of 14 Relation instances; and populate the first 3.
	 * Also populate 3 tuples per each of the 3 relations
	 * 
	 * @return allRelations
	 *   An size 14 array of Relation objects, 
	 *   first 3 initialized, rest null.
	 */
	private static Relation[] initAllRelations() {
		
		int numOfRelations = 14;
		Relation[] allRelations = new Relation[numOfRelations];
		
		int idxCount = 0;
		
		//Init and add CUSTOMER relation to list of all relations. (1/14)
		String [] customerAttributeList = {
				"Username", 
				"Password", 
				"First_name", 
				"Last_name",
				"Email", 
				"Creation_date", 
				"Billing_info", 
				"DOB"};
		int customerNumOfPA = 1;
		Relation customerRelation = new Relation (
				"CUSTOMER", 
				customerAttributeList, 
				customerNumOfPA);
		
		String [] customer1AttributeList = {
				"GreaterUsername",
				"MyBirthday",
				"Average",
				"Joe",
				"DarkFlameMaster1997@gmail.com",
				"20230612",
				"WhatBillingInfo",
				"19970824"};
		String [] customer2AttributeList = {
				"LesserUsername",
				"WhyIsThisNotEncrypted",
				"Rikka",
				"Takanashi",
				"IMissMyDad@gmail.com",
				"20230613",
				"ImOutOfIdeas",
				"20000113"};
		String [] customer3AttributeList = {
				"NormalUsername",
				"HowToStorePasswordInHash",
				"Kevin",
				"Heartman",
				"DoesMyOldEmailStillWork@hotmail.com",
				"20230614",
				"SomeBankIGuess",
				"20010214"};
		
		customerRelation.insertNewTuple(customer1AttributeList);
		customerRelation.insertNewTuple(customer2AttributeList);
		customerRelation.insertNewTuple(customer3AttributeList);
		
		allRelations[idxCount] = customerRelation;
		idxCount++;
		
		//Init and add ANIME relation to list of all relations. (2/14)
		String [] animeAttributeList = {
				"Title", 
				"Description", 
				"Genre", 
				"Price", 
				"Release_year"};
		int animeNumOfPA = 1;
		Relation animeRelation = new Relation (
				"ANIME", 
				animeAttributeList, 
				animeNumOfPA);
		
		String [] anime1AttributeList = {
				"Madoka Magica",
				"Maho shojo story gone wrong.",
				"Psychological thriller",
				"$14.33",
				"2011"};
		String [] anime2AttributeList = {
				"Ghost in the Shell",
				"Don't watch the live action remake.",
				"Sci-fi",
				"$12.74",
				"1995"};
		String [] anime3AttributeList = {
				"Shirobako",
				"Animators' day-to-day lives.",
				"Slice-of-life",
				"$12.85",
				"2014"};
		
		animeRelation.insertNewTuple(anime1AttributeList);
		animeRelation.insertNewTuple(anime2AttributeList);
		animeRelation.insertNewTuple(anime3AttributeList);
		
		allRelations[idxCount] = animeRelation;
		idxCount++;
		
		//Init and add STUDIO relation to list of all relations. (3/14)
		String [] studioAttributeList = {
				"Name", 
				"Description", 
				"Website", 
				"Address"};
		int studioNumOfPA = 1;
		Relation studioRelation = new Relation (
				"STUDIO", 
				studioAttributeList, 
				studioNumOfPA);
				
		String [] studio1AttributeList = {
				"Shaft",
				"The studio that make Madoka Magica.",
				"http://www.shaft-web.co.jp/",
				"Suginami, Tokyo, Japan"};
		String [] studio2AttributeList = {
				"Production I.G",
				"The studio that make Ghost in the Shell.",
				"http://www.production-ig.co.jp/",
				"Musashino, Tokyo, Japan"};
		String [] studio3AttributeList = {
				"P.A. Works", 
				"The studio that made Shirobako.", 
				"http://www.pa-works.jp/en/", 
				"Nanto, Toyama, Japan"};
		
		studioRelation.insertNewTuple(studio1AttributeList);
		studioRelation.insertNewTuple(studio2AttributeList);
		studioRelation.insertNewTuple(studio3AttributeList);
		
		allRelations[idxCount] = studioRelation;
		idxCount++;
		
		//Beyond prototyping: add other relations later, pad null for now.
		while(idxCount < numOfRelations) {
			allRelations[idxCount] = null;
			idxCount++;
		}
		
		return allRelations;
	}

	/**
	 * Print front-end main menu options,
	 * and prompt for user selection.
	 */
	private static void printMainMenu() {
		System.out.println("[Main Menu]\n");
		System.out.println("Select an option"
				+" by entering the corresponding index below:\n");
		System.out.println("0. Add new record.");
		System.out.println("1. Search existing record.");
		System.out.println("2. Print all relations and tuples.");
		System.out.println("3. Exit.\n");
		System.out.print("Enter your selection: ");
	}
	
	/**
	 * Print a list of all defined relations
	 * with their corresponding index
	 * from the allRelations Relation array;
	 * and prompt for user selection.
	 * 
	 * @return allRelations
	 *   An size 14 array of Relation objects, 
	 *   first 3 initialized, rest null.
	 */
	private static void printAllRelationNames(Relation [] allRelations) {
		System.out.println();
		System.out.println("Select a relation"
				+ " by entering the corresponding index below:\n");
		
		//Iterate and print the name of each Relation until null.
		for(int tmpIdx = 0; tmpIdx < allRelations.length; tmpIdx++) {
			
			Relation tmpRelation = allRelations[tmpIdx];
			if(tmpRelation != null) {
				
				System.out.print(String.valueOf(tmpIdx) + ". ");
				tmpRelation.printName();
				
			} else break;
		}
		
		System.out.println();
		System.out.print("Enter your selection: ");
	}
}
