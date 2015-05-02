/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sort;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 *
 * @author Mandy
 */
public class Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        HashMap<Integer, Integer> map = new HashMap<>();
        
        map.keySet();
        for(Entry<Integer, Integer> entry: map.entrySet())
        {
            entry.getValue();

        }
        
        
//        
//        int i = s1.compareTo(s2);
//        if(i == 1)
//        {
//            System.out.println("s1 is better");
//        }
//        
//        else if (i == -1){
//            System.out.println("s2 is better");
//        }
//        else
//        {
//            System.out.println("they are all good");
//        }
        
        //char e = 'e';
        int[] array1 = new int[]{};
        char[] array3 = new char[]{'a','e','o','r'};
        int[] array2 = new int[]{1,3,5,7,9,2,4,6,8,10};
        System.out.println(array3);
        //int[] arrayH = new int[]{16,14,10,8,7,9,3,2,4,1};
        //int[] arrayE = new int[5];
        //BinaryHeap aHeap = new BinaryHeap(arrayH);
        
        //System.out.println(Arrays.toString(aHeap.newArray));
        
        //aHeap.heaplify(1);
        //aHeap.buildHeap();
        //aHeap.enlargeArray();
        //System.out.println(aHeap.deleteMax());
        //System.out.println(aHeap.finMax());
        //System.out.println(aHeap.isEmpty());
        //aHeap.insertArray(3,10);
        //aHeap.insert(6);
        //aHeap.enlargeArray();
        //aHeap.insert(13);
        
        //test right left and parent
        //System.out.println(aHeap.left(5));
        //System.out.println(aHeap.right(5));
        //System.out.println(aHeap.parent(11));
        //System.out.println(aHeap.last());
        
        //System.out.println(Arrays.toString(aHeap.newArray));
        //System.out.println(aHeap.currentSize);
        //System.out.println(aHeap.capacity);
        //System.out.println(aHeap.newArray.length);
//        
//        MergeSort ms = new MergeSort();
//        //mergeSort
//        ms.bigMergeSort(array2);
//        System.out.println(Arrays.toString(array2));
        
        
//        //quickSort
//        NewQuickSort instance = new NewQuickSort();
//        int[] anArray1 = new int[]{0,2,8,7,1,3,5,6,4};
//        instance.bigToString(anArray1);
//        
//        //instance.toStringHHH();
//        
//        //instance.quickSort(anArray1, 1, 8);
//        //instance.partition(0,8);
//        //instance.exchange(0, 1);
//        int[] anArray2 = new int[]{0,2,8,7,1,3,5,6,4,3};
//        instance.bigToString(anArray2);
//        
//        int[] anArray3 = new int[]{};
//        instance.bigToString(anArray3);
//        
//        
//        int[] anArray4 =null;
//        instance.bigToString(anArray4);
//        
        //insertion
        int[] array = new int[] {5,2,4,6,1,3}; 
        insertion instance2 = new insertion();
        instance2.insertion(array2);
        System.out.println(Arrays.toString(array2));
       
      
    }
    
}
