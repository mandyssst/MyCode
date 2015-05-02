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
public class QuickSort {
//    int p,r;
//    int[] array;
    
    int[] array;
    

    public void haha(int[] Farray)
    {
        if (Farray != null) 
        {
            quickSort(Farray, 1, Farray.length-1);
            System.out.println(Arrays.toString(Farray));
        }
        else
        {
            System.out.println("Null");
        }
        
    }
    
    public void quickSort(int[] Farray, int p, int r)
    {
        //Farray = Farray;
        array = Farray;
        if (p<r) {
            int q = partition(p, r);
//            System.out.println("pivot was: "+q + ":" +array[q]);
//            System.out.println("pivot now is:"+ (q-1));
//            System.out.println("  ");
            quickSort(array,p,q-1);
            
            quickSort(array,q+1,r);
//            System.out.println(q);
//            System.out.println(Arrays.toString(array));
        }
        
    }
    
    
    public int partition (int p, int r)
    {
//        System.out.println("print r:"+r + " value:"+array[r]);
        int x = array[r];
        int i = p;
        //int de = 1;
        for (int j = p; j < r; j++) 
        {
            if (array[j]<x) 
            {
//                System.out.println("smaller than pivot");
                exchange(i,j);
//                System.out.println("exchanged:"+array[i]+" with "+array[j]);
//                System.out.println(Arrays.toString(array));
//                System.out.println(" ");
                i++;
            }
            else
            {
//                System.out.println("bigger than pivot");
                //exchange(i,r-1);
//                System.out.println(+array[j]+" stay ");
//                System.out.println(Arrays.toString(array));
//                System.out.println("   ");
                //i++;
            }
            
        }
//        System.out.println("print r:"+r+ " value:"+array[r]);
        exchange(r, i);
//        System.out.println("End of this loop, pivot exhanged:");
//        System.out.println("       ");
//        System.out.println(Arrays.toString(array));
        return i;
    }
//    public int partition2(int p, int r)
//    {
//        int pivot = array[r];
//        int i = p;
//        
//        return 0;
//    }
    
    public void exchange(int a, int b)
    {
        int c;
        c = array[a];
        array[a] =array[b];
        array[b] = c;
//        System.out.println(array[a]);
//        System.out.println(array[b]);
//        System.out.println("a:"+a);
//        System.out.println("b:" +b);
    }
    
    
    public void toStringHHH()
    {
        System.out.println(Arrays.toString(array));
    }

    
}
