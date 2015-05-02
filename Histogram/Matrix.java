/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Histogram;

import java.util.Arrays;

/**
 *
 * @author Mandy
 */
public class Matrix {
   
    
    public void matrixTranspose(int[][] m)
    {
        int[][] arr = new int[m[0].length][m.length];
        
            for (int j = 0; j < m[0].length; j++) {
                for (int i = 0; i < m.length; i++) {
                arr[j][i] = m[i][j];
                }
            }
        
           
        System.out.println(Arrays.deepToString(arr));
    }
}
