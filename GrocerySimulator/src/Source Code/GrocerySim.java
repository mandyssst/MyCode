package sample;

// Priority Queue and Simulation

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


// GrocerySim is the driver routine for the Car Wash Simulation
// Uses PQ.java, Checker.java, Stat.java

public class GrocerySim {
	/*
         * ReadMe: suggest to use default values, and the values can be changed in the test class.
         * otherwise you can select no and input the values one by one.
         */
	
	static PQ agenda = new PQ();
	static Checker[] checkers; //array holding all of the the Checkers
	 static int numOfCheckers = 12;
	 static Scanner scan = new Scanner(System.in);
	 static int numOfExp =2;//hold the number of express lanes
	 static boolean employeeBagging = false; //hold if all lanes are employee bagging or not
	 static int intevalArr = 30; //inter-arrival time
	 static int[] numOfItem =
		{10,10,10,
		20,20,20,20,
		30,30,30,30,30,
		40,40,40,40,
		50,50,50,50,
		60,60,60,
		70,70,70,
		80,80,
		90,
		100};
	 static int runTime = 50000; //total run time of the agenda
	
	public static void main(String[] args) {
		
            System.out.println("Do you want to use default values?");
           Test.test();
            if (scan.next().equalsIgnoreCase("no")) {
                UserInput.userInput();
            }
            
            HashMap map = new HashMap<>();
		checkers = new Checker[numOfCheckers]; //instantiate the array holding all the Checkers
		registerChecker();
		
		double[] arrDistrib = distributionArr();
		
		agenda.add(new ShopperMaker(intevalArr , arrDistrib , numOfItem) , 10);
                
                int k =1;
		
		while (agenda.getCurrentTime() <= runTime) {
                    //System.out.println("debugging");
                    //System.out.println("we get here");
                    //System.out.println("Time: " + agenda.getCurrentTime());
                     if (agenda.getCurrentTime()<3600*k && agenda.getCurrentTime()>3400*k) {
                         System.out.println("At "+k+ " iteration");
                     //    System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
                        Stat.displayStats();
                        k++;
                    }
                     
			agenda.remove().run();
		}
                
                try {
                 FileOutputStream fop = null;
		File file;
                    PrintWriter out = new PrintWriter("myfile.txt");
                    //out.println("hhhhhhh"); 
                    } 
                catch (IOException e) {
          
                        System.out.println("Error");
                }
                
                        
                //System.out.println(map.toString());
                
		Stat.displayStats();
	}
	/*
         * This would make checkers regarding the number of express line
         */
	private static void registerChecker() { 
		int exp = 0;
		
		for (int i = 0; i < checkers.length; i++) {
			if (exp < numOfExp) {
				checkers[i] = new Checker(i , true);
				exp ++;
			}
			else {
				checkers[i] = new Checker(i, false);
			}
		} 
	}
	
	private static double[] distributionArr(){
		double[] distrib = new double[100];
		
		for (int i = 0; i < distrib.length; i++) {
			if(i < 10) {
				distrib[i] = 0.75;
			}
			else if(i < 25) {
				distrib[i] = 0.50;
			}
			else if(i < 45) {
				distrib[i] = 0.20;
			}
			else if(i < 55) {
				distrib[i] = 0;
			}
			else if(i < 75) {
				distrib[i] = -0.20;
			}
			else if(i < 90) {
				distrib[i] = -0.50;
			}
			else {
				distrib[i] = -0.75;
			}
		}
		
		return distrib;
	}
}
