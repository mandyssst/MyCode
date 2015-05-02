/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forest_simulator;

/**
 *
 * @author songx544
 */

import java.util.List;
public class Plant implements Comparable<Plant>{
    String name;
    //Adapted temperature and precipitation
    double temp, pre;
    int lifetime;
    double GeneticRotio;
    double bornYear;

    
    public Plant()
    {
    }
    
    public Plant(String name, double temp, double pre, int lifetime, double geneticRatio, double born)
    {
        this.GeneticRotio = geneticRatio;
        this.name = name;
        this.pre = pre;
        this.temp = temp;
        this.lifetime= lifetime;
        this.bornYear = born;
    }
    
    public void setBorn(int n)
    {
        bornYear = n;
    }

    @Override
    public int compareTo(Plant p) {
        if (bornYear< p.bornYear) {
            return -1;
        }
        else if (bornYear == p.bornYear)
        {
            return 0;
        }
        else{
            return 1;
        }
    }
    

    
}
