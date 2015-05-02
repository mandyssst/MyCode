package sample;

// Priority Queue and Simulation

// Shopper class the represents cars in the carwash simulation
// Shopper objects contain the time they were created and their service time.
// Shopper objects are immediately queued in the washer queue.
// Shopper objects are passive except in constructor (creation)

/**
 *
 * @author songx544
 */
public class Shopper {
	/*need to contain:
	 * 1. time they entered a checkout line
	 * 2. # of items in their cart (determined statistically)
	 */
	
	private double arrivalTime;
	private int itemNum;
	private int checkerID;

    public Shopper(double t, int num , int ID) {
        arrivalTime = t;
        itemNum = num;
        checkerID = ID;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }
  
    public int getItemNum() {
        return itemNum;
    }
}
