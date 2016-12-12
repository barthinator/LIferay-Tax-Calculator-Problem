import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

//////////////////////////////////////////////////////////////////////////////////////
// By: David Bartholomew															//
// LifeRay SalesTax Coding Problem													//
// Class: TaxCalculator																//
// Description: This class is calculates all of the tax properties for each object  //
// in the file. It creates the objects and adds them to an arraylist, it also	    //
// handles file reading, tax calculation and file outputting.						//
//////////////////////////////////////////////////////////////////////////////////////

public class TaxCalculator {
	
	//Constant that is used to format the money
	public final DecimalFormat money = new DecimalFormat("$0.00");
	
	/**
	 * Reads in the file using Scanner and File java libraries. It then adds
	 * the results into an arraylist that contains objects for each good and
	 * then returns that arraylist.
	 * @param fileName
	 * @return
	 */
	public ArrayList<Goods> readIn(String fileName){
		
		//Used input stream so that file is treated as a classpath stream
		InputStream in = this.getClass().getClassLoader().getResourceAsStream(fileName + ".txt");
		
		//File needs to be error handled, so if for some reason an error occurs
		Scanner scan = null;
		try{
			scan = new Scanner(new File(fileName + ".txt"));
		}catch (FileNotFoundException e){
			System.err.println("The was a problem reading the file");
		}
		
		//Creates the arraylist that contains each Good object that is read in from the file
		ArrayList<Goods> itemsList = new ArrayList<Goods>();
		while(scan.hasNextLine()){
			String line = scan.nextLine();
			//Splits up the string where there is a space before the "at" (using regex)
			String[] items = line.split("\\sat");
			//Substrings the name out of the first array index
			String name = items[0].substring(2, items[0].length());
			//Parses out the double cost
			double cost = Double.parseDouble(items[1]);
			
			//Creates the Good object and then adds it to the arraylist
			Goods newItem = new Goods(cost, name);
			itemsList.add(newItem);
		}
		
		//Returns the arraylist
		return itemsList;
	}
	
	/**
	 * This function calculates the tax for each item in the goods arraylist. The
	 * arraylist passed in, is created by the readIn method above. It works by checking
	 * to see if the item should be taxed with a .10 factor and also checks if it is
	 * imported, then adds those total results to the objects salesTax variable.
	 * @param goods
	 */
	public void calculateTax(ArrayList<Goods> goods){
		//Loops through each good
		for(Goods good : goods){
			//Sets the initial taxRate at 0
			double taxRate = 0.0;
			//Checks to see if it needs the .10 factor
			if(good.isTaxApplicable())
				taxRate += 0.10;
			//Checks to see if it needs the 0.05 factor
			if(good.isImportApplicable())
				taxRate += 0.05;
			//Sets the final result with a setter
			good.setTaxRate(taxRate);
		}
	}
	
	/**
	 * This function is used to output the totals to the console. It can be easily changed to
	 * output to a file if needed. The way it works is it loops through all the of the items in
	 * the test case and then calculates the given values based on the forumulas.
	 * @param goods
	 */
	public void outputTotals(ArrayList<Goods> goods){
		//First calculates the tax for each object so it is not a null value
		calculateTax(goods);
		
		double totalSalesTax = 0.0;
		double totalCost = 0.0;
		
		//Loops through each good in the goods arraylist.
		for(Goods good: goods){
			//Calculates the base salesTax
			double salesTax = good.getCost() * good.getTaxRate();
			//Rounds it up to the nearest 0.05 by rounding and using Math.ceil's upper bound
			salesTax = 0.05*(Math.ceil(salesTax/0.05));
			//Gets the final price for the given item
			double finalPrice = good.getCost() + salesTax;
			//Prints the items price after tax
			System.out.println("1 " + good.getName() + ": " + money.format(finalPrice));
			//Used for later total values that are printed
			totalSalesTax += salesTax;
			totalCost += finalPrice;
		}
		
		//Prints the total values
		System.out.println("Sales Tax: " + money.format(totalSalesTax));
		System.out.println("Total: " + money.format(totalCost));
	}
	
	/**
	 * Runs the program. The user has the ability to choose between 4 test cases.
	 * The 3 given test cases and then a custom test case I created.
	 * @param args
	 */
	public static void main(String args[]){
		TaxCalculator test = new TaxCalculator();
		
		//Uses Scanner to read in
		Scanner scan = new Scanner(System.in);
		System.out.println("Which test case would you like to choose (1-4):");
		
		//Reads in the choice
		int choice = scan.nextInt();
		
		//Runs the test case that is chosen
		if(choice == 1)
			test.outputTotals(test.readIn("testCase1"));
		if(choice == 2)
			test.outputTotals(test.readIn("testCase2"));
		if(choice == 3)
			test.outputTotals(test.readIn("testCase3"));
		if(choice == 4){
			System.out.println("Running custom test case:");
			//It is just a bigger test case that can be changed to test more things
			test.outputTotals(test.readIn("customTestCase"));
		}
	}
}
