/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forestfiremap;

import java.util.LinkedList;

/**
 *
 * @author songx544
 */
public class Node {
    int x, y;
    int level = 0;
    int enterT = Integer.MAX_VALUE;
    public Node(){}
    
    public Node(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    public void MarkNodeDiscovered()
    {
        level = 1;
    }
    
    public boolean discovered()
    {
        if (level == 0) {
            return false;
        }
        return true;
    }
    
    
    //Test
    public void printXY()
    {
        System.out.println("x: " + x + ", y: " +y);
    }
    

}
