import java.util.Scanner;

public class MainApplication {

	public static void main(String[] args) {

		//Declare and seed (add data to) relations.
		Relation [] allRelations = initAllRelations();
		
		//Remember to close input stream at the very end.
		Scanner consoleIn = new Scanner(System.in);
		
		while(true) {
			//Start at the main menu.
			printMainMenu();
			
			//nextInt() does not consume '\n',
			//must then call nextLine().
			int userSelection = consoleIn.nextInt();
			consoleIn.nextLine();
			
			int mainMenuInsertOption = 0;
			int mainMenuSearchOption = 1;
			
			if(userSelection == mainMenuInsertOption) {
				
				/**
                 * Option "0": insert new tuple.
                 * 
                 * First select the relation for insertion;
                 * Second obtain all attribute values for the tuple;
                 * Third add tuple to the relation;
                 * Fourth print all tuples of the relation;
                 * Finally return to main menu.
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
				
				String [] newTuple = createNewTuple(tmpR, consoleIn);
				tmpR.insertNewTuple(newTuple);
				
				System.out.println("New tuple successfully added to "
						+ tmpR.getName()
						+ ".\n\nPrinting all existing tuples:\n");
				tmpR.printAllTuples();
				
				System.out.println("Returning to Main Menu.\n");
				
			} else if(userSelection == mainMenuSearchOption) {
				
				//TODO
				
				System.out.println("Returning to Main Menu.\n");
				
			} else {
				break;
			}
		}
		
		
		//Main loop exited, print exit message and close scanner.
		String exitMessage = "Thank you for using Team SHRX's Java frontend."
				+ " Goodbye.";
		System.out.println(exitMessage);
		consoleIn.close();
		
//		
//		printAllRelations(allRelations);
//		printMainMenu();
//		
//		Relation R2 = allRelations[2];
//		
//		R2.insertNewTuple(new String [] {
//			"TestName",
//			"TestDesc",
//			"TestWebsite",
//			"TestAddress"});
//		
//		R2.updateExistingTuple(
//				new String [] {
//					"NewTestName",
//					"NewTestDesc",
//					"NewTestWebsite",
//					"NewTestAddress"},
//				3);
//		
//		R2.deleteExistingTuple(0);
//		
//		Scanner consoleIn = new Scanner (System.in);
//		String [] tmpNewTuple = createNewTuple(R2, consoleIn);
//		R2.insertNewTuple(tmpNewTuple);
//		
//		R2.printAllTuples();
				
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
	 * Print frontend main menu options,
	 * and prompt for user selection.
	 */
	private static void printMainMenu() {
		System.out.println("Main Menu\n");
		System.out.println("Select an option"
				+" by entering the corresponding index below:\n");
		System.out.println("0. Add new record.");
		System.out.println("1. Search existing record.");
		System.out.println("2. Exit.\n");
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
				
			} else {
				break;
			}
		}
		
		System.out.print("\nEnter your selection: ");
	}
	
	private static String [] createNewTuple (Relation R, Scanner consoleIn) {
		
		String [] tmpAttributeList = R.getAttributeList();
		String [] newTuple = new String [tmpAttributeList.length];
		
		int isKeyIdxLimit = R.getPAList().length;
		int attributeIdxLimit = tmpAttributeList.length;
		
		for(int tmpIdx = 0; tmpIdx < attributeIdxLimit; tmpIdx++) {
			System.out.print(
					"Enter value for attribute ("
					+ tmpAttributeList[tmpIdx]);
			
			if(tmpIdx < isKeyIdxLimit) {
				System.out.print(" (key)");
			}
			
			System.out.print("): ");
			newTuple[tmpIdx] = consoleIn.nextLine();
		}
		
		System.out.println();
		
		return newTuple;
	}
}
