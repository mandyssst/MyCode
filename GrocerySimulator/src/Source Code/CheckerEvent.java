/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

/**
 *
 * @author songx544
 */

/*
 * CheckerEvent generates events which lines up in each checkers. 
 */
public class CheckerEvent implements Event{
	/* Occurs each time a Shopper finishes checkout and leaves
	 * An instance is created for each completion of a customer's checkout
	 * When a customer is finished, the CheckerEvent causes the Checke from which the customer just left, to check to see if there is another Shopper in line
	 * 	It will reschedule itself
	 */
	
	private int checkerID; //stores the ID of the Checker that called this instance of CheckerEvent
	
	public CheckerEvent(int checkerID) {
		this.checkerID = checkerID;
	}
	
	public void run() {		
                //remove from the wait line
		Shopper next = (Shopper) GrocerySim.checkers[checkerID].waitline.remove(); 
		
		Stat.updateQueueStats(GrocerySim.agenda.getCurrentTime(), GrocerySim.checkers[checkerID].getLineLength());
		Stat.updateWaitTimeStats(GrocerySim.agenda.getCurrentTime(), next.getArrivalTime());
		
		if(GrocerySim.checkers[checkerID].isBusy()) { 
			if(GrocerySim.employeeBagging) { 
				GrocerySim.agenda.add(this , 5*next.getItemNum()); 
				Stat.updateEServiceTimeStats(5*next.getItemNum()); 
				Stat.updateBusyTimeStats(GrocerySim.agenda.getCurrentTime());
			}
			else {
				GrocerySim.agenda.add(this , 9*next.getItemNum()); 
				Stat.updateSServiceTimeStats(9*next.getItemNum()); 
				Stat.updateBusyTimeStats(GrocerySim.agenda.getCurrentTime());
			}
		}
		
		else{ //Checker is idle, therefore update idle time stats
			Stat.updateIdleTimeStats(GrocerySim.agenda.getCurrentTime());
		}
	}

}
