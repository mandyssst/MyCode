/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ContactBook;

/**
 *
 * @author songx544
 */
public class MyNodeList implements MyList{

    public Node head = null;
    
    public MyNodeList(){}
           
    //complete
     private Node getTail(){
        
		Node current = head;
		if (null == current)
		{
			return null;
		}

		while (null != current.getNext())
		{
			current = current.getNext();
		}
		return current;
	}
    @Override
    //complete
    public boolean add(Object o) {
         Node aNode = new Node(o,null);
        
          if (head == null)
          { 
          head = aNode;
          return true;
          }
          else {
                Node lastNode = getTail();
                lastNode.setNext(aNode);
                return true;
               }
    }



    @Override
    //complete
    public boolean insert(int index, Object o) {
        //problem
        Node ptr = head;
        Node newNode = new Node(o, null);
    //    Node temp;
   //     int count = 0;
        if (o == null) {
            return false;
        }
        if (index+1 >size() || index<0) {
            return false;
        }
        if (index == 0) {
            newNode.setNext(head);
            head.setNext(newNode);
        }
        else{
            newNode.setNext((Node)get(index)); 
            ((Node)get(index - 1)).setNext(newNode);
             return true;
        }
        
        return false;
    }

    @Override
    //complete
    public void clear() {
        head= null;
    }

    @Override
    //complete
    public boolean contains(Object o) {
        if (o==null) {
            return false;
        }
       for (int i=0; i<size(); i++)
       {     if (o.equals(((Node)get(i)).getData()))
            {
                return true;
            }
       }
       return false;
    }
    
   

    @Override
    //complete
    public Object get(int index) {
         if (index <0||index >= size())
         {
            return null;
         }
	int       count   = 0;
	Node  current = head;
	while ((count < index) && (null != current))
	{
		current = current.getNext();
		count   = count + 1;
	}

	if (null == current)
		
		return null;
	else
                return current ;
    }

    @Override
    //complete
    public int indexOf(Object o) {
        int count = 0;
        Node ptr = head;
        if (o == null) {
            //question
            return -1;
        }
        while(ptr.getNext()!=null)
        {
            //question ==
            if (ptr.getData().equals(o)) {
                return count;
            }
            ptr = ptr.getNext();
            count ++;
        }
        if (ptr.equals(o)) {
            return count;
        }
        return -1;
    }

    @Override
    //copmlete
    public boolean isEmpty() {
        return head== null;
    }

    @Override
    public Object remove(int index) {
        Node temp;
        if (index==0) {
            temp = head;
            head = head.getNext();
            return temp;
        }
        if (index<size()-1) {
            temp = (Node)get(index);
            ((Node)get(index-1)).setNext((Node)get(index+1));
            return temp;
        } 
        if(index == size()-1)
        {
            temp = (Node) get(index);
            ((Node)get(index-1)).setNext(null);
            return temp;
        }
        return null;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null|| indexOf(o)==-1) {
            return false;
        }
        if (o.equals(head.getData())) {
            head =head.getNext();
            return true;
        }
        else{
        ((Node)get(indexOf(o)-1)).setNext((Node)get(indexOf(o)+1));
        return true;
    }
    }
    @Override
    //complete
    public void set(int index, Object o) {
        if (o==null||index>=size()||index<0) {
            return;
        }
        ((Node)get(index)).setData(o); 
    }

    @Override
    //complete
    public int size() {
//        if (head.getNext()==null) {
//            return 0;
//        }
        if (null == head){
		return 0;
        }
		int      count   = 0;
		Node current = head;
		while (current != null)
		{
			count = count + 1;
			current = current.getNext();
		}
		return count;
    
    
    }
    

   
    //complete
   
        public String toString()
    {
        String str= "";
       Node current = head;
		if (null == current)
		{
			return null;
		}

		while (null != current.getNext())
		{
                        str += current.getData().toString() + "->";
			current = current.getNext();
		}
                str +=current.getData().toString();
		return str;
    }

 

    
}
