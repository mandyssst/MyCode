/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Histogram;

import java.util.Scanner;

/**
 *
 * @author Mandy
 */
public class Lab_4 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("Enter the number of integers you are going to enter:");
        Scanner in = new Scanner(System.in);
        int num = in.nextInt();
        int[] arr = new int[num];
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            arr[i]= in.nextInt();
            sum+=arr[i];
        }
        System.out.println("The sum is: "+ sum);
        
        
//        System.out.println("Test of Section 3");
//        Matrix sol = new Matrix();
//        int m[ ][ ] = {{1,2,3},{4,5,6}};
//        sol.matrixTranspose(m);
        
    }
    
}
