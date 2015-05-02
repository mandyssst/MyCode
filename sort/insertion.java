/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sort;

import java.util.Arrays;

/**
 *
 * @author Mandy
 */
public class insertion {
    
    public void insertion(int[] array)
    {
        for (int i = 1; i < array.length; i++) {
            //compare A[i] with previous sorted subarray
            int c =0;
            int d =array[i];
            boolean haha = false;
            
            //System.out.println("i is: "+i);
            
            for (int j = i-1; j >= 0; j--) 
            {
              //  System.out.println("j is: "+j);
                
                if (array[i]<= array[j]) 
                {   
                    if ((j == 0)||(array[i]>array[j-1])) {
                        haha = true;
                        c =j;
                        break;
                    }
                    //System.out.println("first if is true");
                    //d = array[i];
               

                }
                
                
                else
                {
                 haha = false;
                }
                
                //System.out.println(c);
                //System.out.println(haha);
                
            }
            if (haha== true) {
               // System.out.println("########  haha is true");
                for (int k = i; k >c; k--) {
                array[k] = array[k-1];
                
                  //  System.out.println(Arrays.toString(array));
            }
                array[c] = d;
                //System.out.println(Arrays.toString(array));
            }
            
//            else
//            {
//                array[i] = array[i];
//            }
            
            
        }
    }
    
}
