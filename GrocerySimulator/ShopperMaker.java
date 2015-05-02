package sample;

// Priority Queue and Simulation

import java.util.Random;

/**
 *
 * @author songx544
 */

/*
 * ShopperMaker generates shoppers and cntinuesly put them on the agenda,  then through cntinously rescheduing them, t creat new objects.
 */

public class ShopperMaker implements Event {
	/*Arrival Generator class
	 * 1. generate arrivals for all the checkout lanes
	 * 		-> reschedule another ShopperMaker object in the agenda
	 * 2. generates all of the Shoppers
	 * 		-> determine which Checker the Shopper should join
	 * 		-> enterLine the Shopper into that Checker's Q
	 * 3. only need one instance of this class
	 */
	
	private int interArrival; //average inter-arrival rate of shoppers
	private double[] arrivalDistrib; //hold the values that determine the arrival time
	private int[] itemNums; //array that holds the item number options
	private Random rand = new Random();
	
	public ShopperMaker(int intval , double[] arrDist , int[] itemNums) {
		interArrival = intval;
		arrivalDistrib = arrDist;
		this.itemNums = itemNums;
	}
	
	//The function id driven from the given formula
	private double randomInterval() {
		return interArrival + arrivalDistrib[rand.nextInt(arrivalDistrib.length)]*interArrival;
	}
	
	//returns a random value from the numOfItem array
	private int randomItemNum(){
		int range = itemNums[rand.nextInt(itemNums.length)]; //grab a random number from the array
		return rand.nextInt(10) + (range-9);
	}
	
	//takes a boolean determining if the Shopper is express or not
	//returns the index of the Checker most appropriate for the Shopper
	public int NextLine(boolean exp) {
		int min = GrocerySim.checkers[0].getLineLength();
		int checkerID = 0;
		
		for (int i = 0; i < GrocerySim.checkers.length; i++) {
			if(!exp) { //if we are looking for non express line
				if(!GrocerySim.checkers[i].isExpress()) { //if the Checker is not express
					if(GrocerySim.checkers[i].getLineLength() < min) {
						min = GrocerySim.checkers[i].getLineLength();
						checkerID = i;
					}
				}
			}
			else { //if we don't care if the lanes are express or not, because the items <= 10
				if(GrocerySim.checkers[i].getLineLength() < min) {
					min = GrocerySim.checkers[i].getLineLength();
					checkerID = i;
				}
			}
		}
		
		return checkerID;
	}
	
	//the following method is largely inspired by the previously coded CarMaker class
	public void run() {
		GrocerySim.agenda.add(new ShopperMaker(interArrival , arrivalDistrib , itemNums), randomInterval());
		
		int shortID;
		int NumberofItems = randomItemNum();
		if(NumberofItems <= 10) { //to the express lane! or not!
			shortID = NextLine(true);
		}
		else { 
			shortID = NextLine(false);
		} 
		Shopper newShopper = new Shopper(GrocerySim.agenda.getCurrentTime() , NumberofItems , shortID);
		
		if (!GrocerySim.checkers[shortID].isBusy()) { //if the specific Checker is NOT busy
			GrocerySim.checkers[shortID].enterLine(newShopper); //add the new Shopper to the shortest line
			
			if(GrocerySim.employeeBagging) { //if there IS employee bagging only
				GrocerySim.agenda.add(new CheckerEvent(shortID) ,/* GrocerySim.agenda.getCurrentTime() + */5*newShopper.getItemNum()); //schedule new ChecerEvent
				Stat.updateEServiceTimeStats(5*newShopper.getItemNum()); //update the time it took for the items to be checked out
				Stat.updateBusyTimeStats(GrocerySim.agenda.getCurrentTime());
			}
			else {
				GrocerySim.agenda.add(new CheckerEvent(shortID) , GrocerySim.agenda.getCurrentTime() + 9*newShopper.getItemNum()); //schedule new ChecerEvent
				Stat.updateSServiceTimeStats(9*newShopper.getItemNum()); //update the time it took for the items to be checked out
				Stat.updateBusyTimeStats(GrocerySim.agenda.getCurrentTime());
			}
		}
		else { //if the specific Checker IS busy, then there is already a CheckerEvent running for that Checker
			GrocerySim.checkers[shortID].enterLine(newShopper); //add the new Shopper to the shortest line
		}
	}
}
