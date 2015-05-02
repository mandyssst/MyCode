/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Histogram;

import java.util.HashMap;

/**
 *
 * @author Mandy
 */
public class Histogram {
    int[] arr;
    int length=0;
   public Histogram(int n)
   {
       arr = new int[n];
   }
   public void add(int i)
   {
       if (0<=i&&i<=100) {
       arr[length]=i;
       length++;
       }
       
   }
   
   public void addAuto(int i)
   {
       int[] nArr = new int[length+1];
        System.arraycopy(arr, 0, nArr, 0, arr.length);
       arr = new int[length+1];
       System.arraycopy(nArr, 0, arr, 0, length);
       
       if (0<=i&&i<=100) 
       {
       arr[length]=i;
       length++;
       }
   }
   
   
   public void display(String option)
   {
       HashMap<Integer,String> map = new HashMap<>();
       int max =-1;
       for (int i = 0; i < arr.length; i++) {
           if (arr[i]>max) {
               max = arr[i];
           }
           if (map.containsKey(arr[i])) {
               map.put(arr[i], map.get(arr[i])+"*");
           }
           else
           {
               map.put(arr[i],"*");
           }
       }
       
       String out = "";
       if (option.equals("l")) {
           for (int i = 0; i <= max; i++) {
               if (map.containsKey(i)) {
                System.out.println(i+": " + map.get(i));
               }
               else
               {
                   System.out.println(i+": ");
               }
       }
       }
       if (option.equals("s")) {
           for (int i = 0; i <= 100; i++) {
               if (map.containsKey(i)) {
                   System.out.println(i+": "+map.get(i));
               }
               
           }
       }
       
   }
   
}
