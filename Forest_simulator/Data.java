/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forest_simulator;

/**
 *
 * @author songx544
 */
public class Data {
   
    //Sequoia National Forest, Redwood National Park, Beech Forest, Black Forest");

    
    private static climate rainForest = new climate("rainForest", 18, 262);
    private static climate desert = new climate("desert", 16, 0.25);
    private static climate grassland = new climate("grassland", 31, 81);
    private static climate Decidous = new climate("Decidous Forest", 31, 81);
    private static climate alpine = new climate("Alpine", 0, 23);
    public static climate[] dataClimate = {rainForest, desert, grassland, Decidous, alpine};
    
    private static Forest amazon = new Forest("Amazon", rainForest);
    private static Forest SNForest = new Forest("SNForest", grassland);
    private static Forest RNPark = new Forest("RNPark", alpine);
    private static Forest BeechForest = new Forest("BeechForest", Decidous);
    private static Forest BlackForest = new Forest("BlackForest", Decidous);
    public static Forest[] dataForest = {amazon, SNForest, RNPark, BeechForest, BlackForest};
    
    static int year = Simulator.year;
    //String name, double temp, double pre, int lifetime, double geneticRatio, int born
    
    private static Plant AlpinePhacelia = new Plant("AlpinePhacelia", 10, 23, 10, 0.8, Math.random()*year/1);
    private static Plant grass = new Plant("grass", 10, 31, 20, 0.9, Math.random()*year/1);
    private static Plant Aspen = new Plant("Aspen", 5, 81, 60, 0.9, Math.random()*year/1);
    private static Plant Bamboo = new Plant("Bamboo", 23, 250, 60, 0.8, Math.random()*year/1);
    public static Plant[] dataplant = {AlpinePhacelia, grass, Aspen, Bamboo};
    
    
    public static Forest getForest(int n)
    {
        return dataForest[n];
    }
    
    public static climate getClimate(int n)
    {
        return dataClimate[n];
    }
    
    public static Plant getPlants(int n)
    {
        return dataplant[n];
    }
    
    
}
