
public class MainApplication {

	public static void main(String[] args) {

		Relation [] allRelations = initAllRelations();
		//printAllRelations(allRelations);
		//printMainMenu();
		

		
	}

	/**
	 * 
	 * @return
	 */
	private static Relation[] initAllRelations() {
		
		int numOfRelations = 14;
		Relation[] allRelations = new Relation[numOfRelations];
		
		int idxCount = 0;
		
		//Init and add CUSTOMER relation to list of all relations. (1/14)
		String [] customerAttributeList = 
			{"Username", "Password", "First_name", "Last_name",
			"Email", "Creation_date", "Billing_info", "DOB"};
		int customerNumOfPA = 1;
		Relation customerRelation = new Relation ("CUSTOMER", 
				customerAttributeList, 
				customerNumOfPA);
		
		allRelations[idxCount] = customerRelation;
		idxCount++;
		
		//Init and add ANIME relation to list of all relations. (2/14)
		String [] animeAttributeList = 
			{"Title", "Description", "Genre", "Price", "Release_year"};
		int animeNumOfPA = 1;
		Relation animeRelation = new Relation ("ANIME", 
				animeAttributeList, 
				animeNumOfPA);
		
		allRelations[idxCount] = animeRelation;
		idxCount++;
		
		//Init and add STUDIO relation to list of all relations. (3/14)
		String [] studioAttributeList = 
			{"Name", "Description", "Website", "Address"};
		int studioNumOfPA = 1;
		Relation studioRelation = new Relation ("STUDIO", 
				studioAttributeList, 
				studioNumOfPA);
				
		allRelations[idxCount] = studioRelation;
		idxCount++;
		
		//TODO: add other relations later, pad null for now.
		while(idxCount < numOfRelations) {
			allRelations[idxCount] = null;
			idxCount++;
		}
		
		return allRelations;
	}
	
	/**
	 * 
	 * @param allRelations
	 */
	private static void printAllRelations(Relation[] allRelations) {
		for(int idx = 0; idx < allRelations.length; idx++) {
			if(allRelations[idx] != null) {
				System.out.println(String.valueOf(idx+1) 
						+ ". " + allRelations[idx].getName());
			} else { 
				break; 
			}
		}
	}
	
	private static void printMainMenu() {
		System.out.println("Main Menu");
		System.out.println("Select an option by entering the corresponding number below:");
		System.out.println("1. Add new record.");
		System.out.println("2. Search existing record.");
		System.out.println("3. Exit.");
		System.out.print("Enter a your selection: ");
	}
}
