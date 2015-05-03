/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forestfiremap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;




/**
 *
 * @author songx544
 */
public class Forest {
    static Node[][] forest = new Node[10][10];
    static ArrayList<Node> fireArea = new ArrayList<>();
    int dX, dY;
    int time = 0;
    
    public Forest(){}
    
    public Forest(int dX, int dY)
    {
        forest = new Node[dX][dY];
        for (int i = 0; i < dX; i++) {
            for (int j = 0; j < dY; j++) {
                forest[i][j] = new Node(i ,j);
            }
        }
        
        this.dX = dX;
        this.dY = dY;
    }
    
    public void fireSpread(int x, int y, int timeS)
    {
        int fx, fy;
        if (x >=  forest.length || y >= forest[0].length) {
            System.out.println("Your input index is out of bound");
            fx = 4;
            fy = 4;
        }
        
        else{
            fx = x;
            fy = y;
        }
        
        
        
        
         Queue<Node> q = new LinkedList<>();
         Node aNode = forest[x][y];
         aNode.enterT = 1;
         
        q.add(aNode);
        
        fireArea.add(forest[x][y]);
        forest[x][y].MarkNodeDiscovered();
        
        while(time<timeS && !q.isEmpty())
        {
            Node newN = q.poll();
            
          
            LinkedList<Node> nl = findAdjandecy(newN);
            System.out.println("Adja");
            for(Node n: nl)
            {
                n.printXY();
            }
           
            
            if (newN.enterT>time) {
                if (time +1 >=timeS) {
                    break;
                }
                update();
            }
            

            
            time = newN.enterT;
            
            
            for(Node n: nl)
            {
     
               
                if (!n.discovered()) {
                     
                     
                     n.MarkNodeDiscovered();
                     n.enterT = newN.enterT +1;
                     q.add(n); 
                     fireArea.add(n);
                }
            }
         

        }
        
        if (time<timeS) {
                for (int i = 1; i < timeS -time; i++) {
                update();
            }
            }
    }
    
    
    public void update()
    {
       
        for (Node n: fireArea) {

            n.level +=1;

        }
    }
    
    
    
    public boolean allDiscovered()
    {
        if (forest[0][0].discovered()&&forest[0][dY].discovered()) {
            if (forest[dX][0].discovered() && forest[dX][dY].discovered()) {
                return true;
            }
        }
        return false;
    }
    
        public LinkedList<Node> findAdjandecy(Node n)
    {
        LinkedList<Node> list = new LinkedList<>();
        for (int i = n.x-1; i <= n.x+1; i++) {
            for (int j = n.y-1; j <= n.y+1; j++) {
                if (i>=0 && j>=0 && i<dX && j<dY && forest[i][j]!= n) {
                    list.add(forest[i][j]);
                   
                } 
            }
        }
        
        return list;
    }
        
    public String toString()
    {
        String str = "";
        int[][] arr = new int[dX][dY];
        for (int i = 0; i < dX; i++) {
            for (int j = 0; j < dY; j++) {
                arr[i][j] = forest[i][j].level;
            }
            str += Arrays.toString(arr[i]) + "\n";
        }  
        
        
        return str;
    }
}
