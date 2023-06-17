import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 
 * 
 * @author Keming He
 * @version 20230616
 *
 */
public class Relation {

	/**
	 * Private attributes for each instance of the Relation object:
	 *     name:       name of the relation;
	 *     
	 *	   attributes: array of all attribute names,
	 *                 begins with prime attributes;
	 *                 
	 *     numOfPA:    keep track of the number of prime attributes;
	 *     
	 *     tuples:     growing list of data tuples,
	 *                 each tuple is an array of Objects,
	 *                 tuple array length matches attr array len.
	 * 
	 * Private so requires getter/setters to access.
	 */
	private String name;
	private String[] attributeList;
	private int numOfPA;
	private ArrayList <String[]> tuples = new ArrayList <> ();
	
	public Relation(String newName, String[] newAttributeList, int newNumOfPA) {
		
		this.name = newName;	
		this.attributeList = newAttributeList;
		this.numOfPA = newNumOfPA;
	}
	
	public String getName () {
		return this.name;
	}
	
	public String[] getAttributeList () {
		return this.attributeList;
	}
	
	public String[] getPAList () {
		return Arrays.copyOfRange(this.attributeList, 0, numOfPA);
	}
	
	public String[] getOneTuple (int tupleIdx) {
		return this.tuples.get(tupleIdx);
	}
	
	public ArrayList <String[]> getAllTuples () {
		return this.tuples;
	}
	
	public void printName () {
		System.out.println(this.name);
	}
	
	public void printAllAttributes () {
		
		System.out.println(
				"All attributes for " 
				+ this.getName()
				+ ": ");
		
		String [] tmpAttributeList = this.getAttributeList();
		int isKeyIdxLimit = this.getPAList().length;
		
		for(int tmpIdx = 0; tmpIdx < tmpAttributeList.length; tmpIdx++) {
			System.out.print(
					"Attr "
					+ String.valueOf(tmpIdx) 
					+ ". "
					+ tmpAttributeList[tmpIdx]);
			
			if (tmpIdx < isKeyIdxLimit) {
				System.out.print(" (key)");
			}
			System.out.println();
		}
		System.out.println();
	}
		
	public void printOneTuple (int tupleIdx) {
		
		String [] tmpTuple = this.getOneTuple(tupleIdx);
		String [] tmpAttributeList = this.getAttributeList();
		int isKeyIdxLimit = this.getPAList().length;
		
		for(int tmpIdx = 0; tmpIdx < tmpAttributeList.length; tmpIdx++) {
			System.out.print(
					"Attr "
					+ String.valueOf(tmpIdx) 
					+ ". "
					+ tmpAttributeList[tmpIdx]);
			
			if (tmpIdx < isKeyIdxLimit) {
				System.out.print(" (key)");
			}
			
			System.out.println(
					": \t" 
					+ tmpTuple[tmpIdx]);
		}
		System.out.println();
	}
	
	public void printAllTuples () {
		int idxLimit = this.getAllTuples().size();
		for(int tmpIdx = 0; tmpIdx < idxLimit; tmpIdx++) {
			System.out.println("Tuple " + String.valueOf(tmpIdx));
			this.printOneTuple(tmpIdx);
		}
	}
	
	public void insertNewTuple (String[] newTuple) {
		this.tuples.add(newTuple);
	}
	
	public void updateExistingTuple (String[] updateTuple, int tupleIdx) {
		this.tuples.set(tupleIdx, updateTuple);
	}
	
	public void deleteExistingTuple (int tupleIdx) {
		this.tuples.remove(tupleIdx);
	}
	
	public static String [] createNewTuple (Relation R, Scanner consoleIn) {
		
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
