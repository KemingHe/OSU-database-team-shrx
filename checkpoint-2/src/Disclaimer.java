import java.util.Scanner;

public class Disclaimer {

	public static void printDisclaimer (Scanner consoleIn) {
		
		System.out.println("IMPORTANT DISCLAIMER\n");
		
		System.out.println("The following Java text-based front-end:");
		System.out.println("1. Is for demonstration core features only;");
		System.out.println("2. Does not represent Team SHRX's final application;");
		System.out.println("3. Does not fully implement all constraints;");
		System.out.println("4. Does not fully implement all relations.");
		System.out.println("5. Does not fully populate all relations.");

		System.out.println();
		
		System.out.println("As a user for this prototype, I consent to:");
		System.out.println("0. Reading the README.txt file beforehand;");
		System.out.println("1. Following the prompts given by the program;");
		System.out.println("2. Entering only values allowed by the program;");
		System.out.println("3. Preserving key constraints during insert/update/delete.");
		
		System.out.println();
		
		System.out.println("Team SHRX does not claim responsibility of any runtime errors");
		System.out.println("caused by user trying to break the prototype,");
		System.out.println("as, again, the prototype is for demonstration purposes only.");
		
		System.out.println();
		
		System.out.println("Press enter to confirm that "
				+ "you fully acknowledge the disclaimer.");
		//Use input stream to pause program.
		consoleIn.nextLine();
	}

}
