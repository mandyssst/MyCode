/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ContactBook;

import java.util.Arrays;

/**
 *
 * @author songx544
 */
public class Test {

    /**
     * @param args the command line arguments
     */
 
        MyArrayList list = new MyArrayList();
        MyNodeList nodes = new MyNodeList();
    
        
    public static void main(String[] args)
    {
        Test ins = new Test();
        ins.runArray();
        ins.runNodes();
    }
    
    public void runArray()
    {
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");
        list.insert(2, "ddd");
        System.out.println("Expext aaa, bbb, ddd, ccc: "+Arrays.toString( list.array)); 
        System.out.println("Expext 4: "+list.size);
        System.out.println("Expext ddd: "+list.get(2));
        System.out.println("Expext null: "+list.get(4));
        System.out.println("Expext false: "+list.contains("abc")); 
        System.out.println("Expext true: "+list.contains("bbb"));
        System.out.println("Expect 2: "+ list.indexOf("ddd"));
        System.out.println("Expect -1: "+ list.indexOf("abc"));
        list.remove("bbb");
        list.remove("abc");
        System.out.println("Expect aaa ddd ccc: " + Arrays.toString(list.array)); 
        System.out.println("Expect False: "+ list.isEmpty());
        list.remove(2);
        System.out.println("Expect aaa ddd: " +Arrays.toString(list.array));
        list.set(0, "haha");
        System.out.println("Expect haha ddd: " +Arrays.toString(list.array));
        list.set(5, "null");
        System.out.println("Expect aaa ddd: " +Arrays.toString(list.array));
        System.out.println("Expect 2: "+ list.size);
        list.clear();
        System.out.println("Expect null *4: " +Arrays.toString(list.array));
        System.out.println("Expect true: " +list.isEmpty());
    }

   public void runNodes()
   {
        nodes.add("aaa");
        nodes.add("bbb");
        nodes.add("ccc");
        System.out.println("Expect True: "+nodes.insert(1, "first"));
        System.out.println("Size: expect 4: " + nodes.size());
        System.out.println("insert(), expect false: "+ nodes.insert(-1, " "));
        System.out.println("insert(), expect false: "+ nodes.insert(4, "haha"));
        //nodes.insert(2, "ddd");
        System.out.println("Expect aaa, first, bbb, ccc: "+nodes.toString()); 
        System.out.println("Expect 2: " + nodes.indexOf("bbb")); 
        System.out.println("Expect -1: "+ nodes.indexOf("abc")); 
        System.out.println("Expect -1: " + nodes.indexOf(null));  
        System.out.println("E true: " + nodes.contains("aaa"));
        System.out.println("Expect false: " + nodes.contains(null));
        System.out.println("Expect false: " + nodes.contains("abc"));
        System.out.println("Expect ccc: "+ ((Node)nodes.get(3)).getData().toString());
        System.out.println("Expect ccc: "+ ((Node)nodes.get(2)).getData().toString());
        System.out.println("False: " + nodes.isEmpty());
        
        nodes.set(2, "two");
        nodes.set(4, "haha");
        System.out.println(nodes.toString());
        nodes.remove("two");
        System.out.println(nodes.toString());
        nodes.remove("aaa");
        System.out.println(nodes.toString());
        nodes.add("hhh");
        nodes.add("kkk");
        nodes.remove(1);
        System.out.println(nodes.toString());
        nodes.remove(0);
        System.out.println(nodes.toString());
        
        nodes.clear();
        System.out.println("true: "+ nodes.isEmpty());
   } 

   
    
}
