package sort;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mandy
 */
public class MergeSort {
    
    public void bigMergeSort(int[] array) {
        
        mergeSort(array, 0, array.length-1);
    }
    public void mergeSort(int[] array, int p,int r)
    {
        int q;
        if (r-p+1<=1) {
            return;
        }
        else
        {
            q = (p+r)/2;
        }
        mergeSort(array,p,q);
        mergeSort(array,q+1,r);
        merge(array,p,q,r);
    }

    public void merge(int[] array, int p, int q, int r) 
    {
        int[] newArray = new int[array.length];
        for (int i = 0; i< array.length; i++) 
        {
                if (array[p]<array[q+1]) 
                {
                    //System.out.println("i is:::::::::" + i);
                    newArray[i] = array[p];
                    deleteMin(array, p, q);
                }
                else
                {
                    newArray[i] = array[q+1];
                    deleteMin(array, q+1, r);
                }
        }
        
        for (int i = 0; i < array.length; i++) 
        {
            array[i] = newArray[i];
        }
    }

    private void deleteMin(int[] array, int start, int end) 
    {
        //int temp = array[start];
        for (int i = start; i < end; i++) 
        {
            array[i] = array[i+1];
        }
        for (int i = start; i < end; i++) {
            if (array[i]==0) {
                array[i] = 100;
            }
        }
        array[end] = 100;
    }
}
