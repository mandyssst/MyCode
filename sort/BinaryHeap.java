/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sort;

/**
 *
 * @author Mandy
 * @param <T>
 */

//public class Student implements Comparable<Student>{
//    
//    public String name;
//    double GPA;
//    int grade;
//    
//    public Student(String n, double G, int g){
//        name = n;
//        GPA = G;
//        grade = g;
//    }
//    
//    public int getGrade()
//    {
//        return grade;
//    }
//    
//    public double getGPA()
//    {
//        return GPA;
//    }
//
//    
////    public int compareTo(Object other) {
////        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
////    }
//
//    @Override
//    public int compareTo(Student other) {
//        if (getGPA()<other.getGPA()){
//            return -1;
//        }
//        if (getGPA()>other.getGPA()){
//            return 1;
//        }
//        
//        return 0;
//    }
//}
 public class BinaryHeap
 {
    public int currentSize;
    int[] newArray;
    int capacity;
     public BinaryHeap(int []items)
     {
         currentSize = items.length;
         capacity = currentSize;
         newArray = new int[currentSize];
         int i = 0;
         for(int item: items)
         {
             newArray[i++] = item;
         }
     }
     
     public void insertArray(int index, int item)
     {
         enlargeArray();
         for (int i = capacity-1; i > index; i--) 
         {
             newArray[i] = newArray[i-1];
         }
         newArray[index] = item;
         //currentSize++;
     }
     public void insert(int data)
     {
         //imcomplete
         insertArray(0,data);
         heaplify(0);
         
         //heaplify backwards
     }
     public int finMax()
     {
         //for findMin use minHeap
         buildHeap();
         return newArray[0];
     }

     public int deleteMax()
     {
         int res;
         buildHeap();
         exchange();
         res = newArray[currentSize-1];
         //currentSize--;
         resize();
         heaplify(0);
         return res;
     }
     
     public void resize()
     {
         //incomplete
         currentSize--;
         capacity = currentSize;
         int[] temp = new int[capacity];
       
         for (int i = 0; i < capacity; i++) {
             temp[i] = newArray[i];
             //System.out.println(temp[i]);
           
         }

         newArray = new int[capacity];
         //System.out.println(newArray.length);
         for (int i = 0; i < capacity; i++) {
             newArray[i] = temp[i];
         }
         
     }
     public void exchange()
     {
         int temp;
         temp = newArray[0];
         newArray[0] = newArray[currentSize-1];
         newArray[currentSize-1] = temp;
     }
     public boolean isEmpty()
     {
         int a =0;
         for (int i = 0; i < currentSize; i++) {
             if (newArray[i]==0) {
                 a = 0;
             }
             else
             {
                 a = 1;
             }
         }
         return a == 0;
     }
     
     public void heaplify(int i)
     {
        int l = left(i);
        int r = right(i);
        int largest;
        if ((l <= currentSize-1) && (newArray[l]>newArray[i]))
        {
            largest = l;
        }
        else 
        {    
            largest = i;
        }
        if((r<=currentSize-1) && (newArray[r]>newArray[largest]))
        {
            largest = r;
        }
        if(i!=largest)
        {
            int temp;
            temp = newArray[i];
            newArray[i] = newArray[largest];
            newArray[largest] = temp;
            heaplify(largest);
        }
        
     }
     
     public int parent(double i)
     {
         return (int) (i-1)/2;
     }
     public int left(int i)
     {
         return 2*(i+1) -1;
     }
     public int right(int i)
     {
         return 2*i + 2;
     }
     
     
     public void buildHeap() 
     {
         for (int i = (currentSize/2); i >= 0; i--) 
         {
             heaplify(i);
         }
     }
     
     public void enlargeArray()
     {
         capacity = currentSize+1;
         int[] temp = new int[capacity];
       
         for (int i = 0; i < currentSize; i++) {
             temp[i] = newArray[i];
             //System.out.println(temp[i]);
           
         }

         newArray = new int[capacity];
         //System.out.println(newArray.length);
         for (int i = 0; i < currentSize; i++) 
         {
             newArray[i] = temp[i];
         }
         
         currentSize = capacity;
         //System.out.println(currentSize);
         //System.out.println(capacity);
     }
     


 }