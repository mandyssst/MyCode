/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forest_simulator;

import java.util.Scanner;

/**
 *
 * @author songx544
 */
public class Helper {
     public static boolean bool(String str)
    {
        if (str.equalsIgnoreCase("yes")) {
            return true;
        }
        else if (str.equalsIgnoreCase("no")) {
            return false;
        }
        else
        {
            System.out.println("Invalid Input");
            System.out.println("Default: Select from liibrary");
            return true;
        }
        
    }
    
     public static void UserInput()
     {
         Scanner scan = new Scanner(System.in);
        System.out.println("Hi, welcome to forest simulator!!");
        System.out.println("Do you want to select a simulator from our library or create one?");
        System.out.println("We have Amazon Rainforest, Sequoia National Forest, Redwood National Park, Beech Forest, Black Forest");
        System.out.println("Please enter yes/no");
        boolean TF = Helper.bool(scan.next());
        if (TF) {
            System.out.println("Which one would you like to choose? In presented order, each one is labelled 1-5, enter the number");
            Simulator.setForest(Data.getForest(scan.nextInt()));
        }
        
        else
        {
            System.out.println("rainForest, desert, grassland, Decidous, alpine");
            System.out.println("Enter temperature, precipitation and name, seperated by space");
            int num = scan.nextInt();
            int num2 = scan.nextInt();
            String name = scan.next();
            Forest newF = new Forest( name, new climate(num2, num));
            Simulator.setForest(newF);
        }
        
        System.out.println("How long would you like the simulation last");
        System.out.println("Suggest to put in 500-600, since the default values fit");
        Simulator.setYear(scan.nextInt());
     }
     
}
