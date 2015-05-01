package sample;


// Checker is the class that models the car washer and it's waiting line
// The method run() simulates the behavior of the washer.
// This implements the Event interface (Event.java)

public class Checker {
	/* 
	 * Needs a queue associated with each instance
	 * 	-> represents the line at each checkout
	 */
	
	public Q1 waitline = new Q1(); //will hold Shopper objects
	int checkerID; //holds the index associated with this object in the checkers array of the GrocerySim class 
	private static boolean express;
	
	public Checker(int ID , boolean isExpress) {
		checkerID = ID;
		express = isExpress;
	}
	
	//the following method is inspired by the previously coded Washer class
	public void enterLine(Shopper s) {
		waitline.add(s);
		
		Stat.updateQueueStats(GrocerySim.agenda.getCurrentTime(), getLineLength());
	}
	
	//the following method is copied from the previously coded Washer class
	public boolean isBusy() {
		if (getLineLength() == 0) { //if there is no one in line
			return false;
		}
		else {
			return true;
		}
	}
	
	public boolean isExpress() {
		return express;
	}
	
	public int getLineLength() {
		return waitline.length();
	}
}
