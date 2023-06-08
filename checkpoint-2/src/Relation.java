import java.util.ArrayList;
import java.util.Arrays;

/**
 * 
 */

/**
 * 
 * 
 * @author Keming He
 * @version 20230607
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
	private ArrayList <Object[]> tuples = new ArrayList <> ();
	
	
	
	/**
	 * 
	 */
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
		return Arrays.copyOfRange(this.attributeList, 0, numOfPA-1);
	}
	
	public Object[] getOneTuple (int tupleIdx) {
		return this.tuples.get(tupleIdx);
	}
	
	public ArrayList <Object[]> getAllTuples () {
		return this.tuples;
	}
	
	
	
	public void insertNewTuple (Object[] newTuple) {
		this.tuples.add(newTuple);
	}
	
	public void updateExistingTuple (Object[] updateTuple, int tupleIdx) {
		this.tuples.set(tupleIdx, updateTuple);
	}
	
	public void deleteExistingTuple (int tupleIdx) {
		this.tuples.remove(tupleIdx);
	}
}
