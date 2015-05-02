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
public class ForestSimulator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("Do you want to use default value? yes/no");
        Scanner scan = new Scanner(System.in);
        String val = scan.next();
        if (val.equalsIgnoreCase("yes")) {
            Simulator.setForest(Data.getForest(0));
            Simulator.addAllPlants(80);
        }
        else
        {
            Helper.UserInput();
        }
        
        
        Simulator.run();
        System.out.println("The number of different trees: " + Simulator.trees.toString());
    
   
        
    }
    
    
   
}
