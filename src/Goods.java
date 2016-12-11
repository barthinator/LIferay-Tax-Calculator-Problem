
//////////////////////////////////////////////////////////////////////////////////////
// By: David Bartholomew															//
// LifeRay SalesTax Coding Problem													//
// Class: Goods																		//
// Description: This class is the object class for each item that is read in		//	
// from the files. Each object has a cost, taxRate and name. There are provided		//
// getters and setters as well as some taxRate checking methods.					//
//////////////////////////////////////////////////////////////////////////////////////


public class Goods {

	private double cost;
	private double taxRate;
	private String name;

	/**
	 * Constructor that has a price and itemName, the taxRate is calculated after
	 * @param price
	 * @param itemName
	 */
	public Goods(double price, String itemName) {
		cost = price;
		name = itemName;
	}
	
	/**
	 * Used to check if the .10 tax factor applies to the given items.
	 * @return
	 */
	public boolean isTaxApplicable(){
		//The idea is there could be a database of keywords that are checked here
		String[] keywords = {"pills", "book", "chocolate", "food"};
		for(int i = 0; i < keywords.length; i++){
			if(name.contains(keywords[i]))
				return false;
		}
		return true;
	}
	
	/**
	 * Used to check if the .05 tax factor applies to the given items.
	 * @return
	 */
	public boolean isImportApplicable(){
		//Checks to see if the name contains the string "imported"
		if(name.contains("imported"))
			return true;
		return false;
	}
	
	/**
	 * Getters and setters for the cost, name, and taxRate
	 */

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(double taxRate) {
		this.taxRate = taxRate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * toString method just created for debugging.
	 */
	
	public String toString(){
		return name + " " + cost;
	}
}
