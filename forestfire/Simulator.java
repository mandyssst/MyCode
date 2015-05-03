/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forestfiremap;

import java.util.Arrays;
import java.util.LinkedList;

/**
 *
 * @author songx544
 */
public class Simulator {
    
    
    static public void run()
    {
        Forest fo = new Forest(10, 10);
        fo.fireSpread(0, 0, 15);
        System.out.println(fo.toString());
    }

    
}
