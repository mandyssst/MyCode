/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import java.util.Scanner;

/**
 *
 * @author songx544
 */
public class UserInput {
    public static void userInput()
    {
        Scanner scan = new Scanner(System.in);
        
		System.out.println("Enter the inter-arrival time:");
		GrocerySim.intevalArr = scan.nextInt();
		
		System.out.println("All lanes to have employee bagging? Please enter yes or no");
		String emp = scan.next();
		if(emp.equalsIgnoreCase("yes")) {
			GrocerySim.employeeBagging = true;
		}
		else{
			System.out.println("All lanes will be self bagging lanes.");
			GrocerySim.employeeBagging = false;
		}
		
		System.out.println("Enter how many of the lanes you want to be express lanes, 0 to 2:");
		GrocerySim.numOfExp = scan.nextInt();
		
		System.out.println("How long will this simulation run?");
		GrocerySim.runTime = scan.nextInt();
    }
}
