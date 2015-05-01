/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forest_simulator;

/**
 *
 * @author songx544
 */
public class climate {
    static String name;
    static double precipitation, temperature;
    
    public climate()
    {}
    
    public climate(double precipitation, double temp)
    {
        this.name = name;
        this.precipitation = precipitation;
        this.temperature = temp;
    }
    
    
    public climate(String name, double precipitation, double temp)
    {
        this.name = name;
        this.precipitation = precipitation;
        this.temperature = temp;
    }
    
    public static void climateChange()
    {
          precipitation = Math.random()*precipitation*0.1 - precipitation;
          temperature = Math.random()*precipitation*0.1 + precipitation;
    }
}
