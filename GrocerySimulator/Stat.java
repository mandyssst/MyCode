package sample;

// Priority Queue and Simulation

import java.util.ArrayList;


// Statistics class for Car Simulation

public class Stat {

    // public methods
    
    private static double lastUpdateTime;
    private static int oldQLength;
    private static double lastQUpdateTime;
    private static int bagged;
    private static double maxWait;
    private static double averageWait;
    private static int maxQLength;
    public static double averageQLength; 
    private static double averageEServiceTime;
    private static double averageSServiceTime;
    
    private static int count;
    public static double totalTime;
    private static double busyTime;
    private static double idleTime;
    private static int[] arr;
    static ArrayList arrList = new ArrayList<>();


    public static void updateQueueStats(double time, int qlen) {
                
        if (qlen > maxQLength)
          maxQLength = qlen;
        averageQLength += oldQLength * (time - lastQUpdateTime);
        totalTime += time - lastQUpdateTime;
        lastQUpdateTime = time;
        oldQLength = qlen;
    }

    public static void updateBusyTimeStats(double time) {

        busyTime += time - lastUpdateTime;
        lastUpdateTime = time;
    }        
    
    public static void updateTest(int n)
    {
        int temp =0; 
        temp = temp+ n;
    }

    public static void updateIdleTimeStats(double time) {
        
        idleTime += time - lastUpdateTime;
        lastUpdateTime = time;
    }

    public static void updateSServiceTimeStats(double st) {
   
        averageSServiceTime += st;
    }
    
    public static void updateEServiceTimeStats(double st)
    {
        averageEServiceTime +=st;
    }

    public static void updateWaitTimeStats(double time, double enterTime) {
    
        double wait = time - enterTime;
        if (wait > maxWait)
          maxWait = wait;
        averageWait += wait;
        count++;  // another leaves the waiting queue
        averageWait = averageWait/count;
    } 
    

    public static void displayStats() {
        System.out.println("\n** Simulation Results **\n");
        System.out.println(" Calculated Simulation Time: " + totalTime);
        System.out.println("Average Wait time: " + averageWait);
//        System.out.println(" Idle Time: " + idleTime);
        System.out.println(" Busy Time: " + busyTime);
//        System.out.println(" (Busy Time based on service time, Self bagging: " + 
//                                               averageSServiceTime + ")\n");
//        System.out.println(" (Busy Time based on service time, emp bagging: " + 
//                                               averageEServiceTime + ")\n");
//        System.out.println(" Maximum Queue Length: " + maxQLength);
        System.out.println(" Avg. Queue Length: " + averageQLength/totalTime);

        System.out.println("\n");

    }  // displayStats

    // private variables


}  // Stat class
