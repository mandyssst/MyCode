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
public class NewQuickSort {
    int[] array;
    //purpose to create this field is to make array accessible to every functions.
    
    public void bigToString(int[] anArray)
    {
        if (anArray == null) {
            System.out.println("Null");
        }
        else
        {
            quickSort(anArray, 1, anArray.length-1);
            System.out.println(Arrays.toString(array));
        } 
    }
    
    public void quickSort(int[] anArray, int p, int r)
    {
        array = anArray;
        if (p<r) {
            int q = partition(p,r);
            quickSort(array, p, q-1);
            quickSort(array,q+1,r);
        }
        
    }
    
    private int partition(int p, int r)
    {
        int i = p;
        for (int j = p; j < r; j++) {
            if (array[j]<array[r]) 
            {
                exchange(i,j);
                i++;
            }
        }
        exchange(i,r);
        return i;
    }


    private void exchange(int i, int j) {
        int c;
        c = array[i];
        array[i] = array[j];
        array[j] = c;
    }
    
}
