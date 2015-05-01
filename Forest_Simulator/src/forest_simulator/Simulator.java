/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forest_simulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author songx544
 */
public class Simulator {
    static Forest aForest = new Forest();
    static HashMap<String, Integer> trees = new HashMap<>();
    static Plant d;
    static int year = 500; 
    static ArrayList<Plant> Waiting = new ArrayList<>();
    static ArrayList<Plant> Existing = new ArrayList<>();
    static Queue<Plant> WaitingQ = new LinkedList<Plant>();
    
    
    public static void addPlant(Plant tree, int number)
    {
        for (int i = 0; i < number; i++) {
              Waiting.add(tree);
        }
        Collections.sort(Waiting); 
        for(Plant p: Waiting)
        {
            WaitingQ.add(p);
        }
    }
    
    public static void addAllPlants(int number)
    {
        //input is the number of every types of the tree
        Plant[] arr = Data.dataplant;
        for (Plant aT: arr) {
            addPlant(aT, number);
        }
    }
    
    public static void setForest(Forest f)
    {
        aForest = f;
    }
    
    public static void setYear(int n)
    {
        year = n;
    }
    
    public static int TreeNumDeduction(Plant p)
    { 
        int res = p.lifetime;
        if (Math.abs(aForest.climate.precipitation - p.pre)>20) {
            res -=  2;    
        }
        
        if (Math.abs(aForest.climate.temperature - p.temp)>20) {
            res -= 2;
        }
        
        if (res<0) {
            res =0;
        }
        return res;
    }
    
    
    
    
    public static void run()
    {
        int count = 0;
        int changeTimes =0;
        while(count< Simulator.year)
        {
            
            //Climate change every sevral years  (year/10)
            if (count == (Simulator.year/10)*changeTimes) {
                 climate.climateChange();
                 changeTimes++;
            }
            
            //Trees Born
            Plant temp = WaitingQ.peek();
            //System.out.println(WaitingQ.toArray().toString());
            if (WaitingQ!=null && temp.bornYear < count) {
                if (!trees.containsKey(temp.name)) {
                    trees.put(WaitingQ.poll().name, 1);
                }
                else
                {
                    //System.out.println(WaitingQ.poll().name);
                    trees.put(WaitingQ.poll().name, trees.get(temp.name)+1);
                }
                //System.out.println(Existing.size());
                Existing.add(temp);
                //System.out.println(Existing.size());
            }
            
            boolean b = false;
           Plant record = null;
            if (!Existing.isEmpty()) {
                for (Plant p : Existing) {
                     //Trees die
                    if (p.bornYear + p.lifetime == count) {
                         b = true;
                         record = p;
                         if (trees.containsKey(p.name)&&trees.get(p.name)>0) {
                            trees.put(p.name, trees.get(p.name)-1);
                        }
                         
                    }
                    
                       //Trees reproduce
                    if ((p.bornYear + p.lifetime)/3 == count) {
                        p.setBorn(count);
                         addPlant(p, 2);
                    }
                    
                    //Tree number deduction due to enviroment
                    int lifeT = TreeNumDeduction(p);
                    p.lifetime = lifeT;
                }
                Existing.remove(record);
            }
            
         
            
            count++;
        }
    }
}
