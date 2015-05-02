/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ContactBook;

/**
 *
 * @author songx544
 */

import java.io.ObjectOutputStream; // output stream for Object
import java.io.FileOutputStream; // generic output stream
import java.io.ObjectInputStream; // input stream for Objects
import java.io.FileInputStream; // generic input stream
import java.io.*; // provide easy access to all IO Exceptions
public class ContactLinkedList {
	MyNodeList list;
	private  int ptr;	
    public static void main(String[] args){
		ContactLinkedList cL1 = new ContactLinkedList();//create an object( instance)
		ContactLinkedList cl2	= new ContactLinkedList();
		NewContact c1 = new NewContact( "Sally", "home", "friend", 66614143423L, "s.gmail.com","classmate","sal");
		NewContact c2 = new NewContact("Mom", "Tel", "Mom",13806310312L, "520@qq.com","family","mom");
		NewContact c3 = new NewContact("Hairong",  "tel", "Friend", 6123342342L, "hai.@gmail.com","family","jinhua");
		NewContact c4 = new NewContact("Hairong", "tel", "Friend",6123342342L, "hai.@gmail.com","family","jinhua");
		NewContact c5 = new NewContact("Fangfei", "tel","bbf",6124019193L,"fanfeeer@gmail.com","family","Fei");
		System.out.println("check the addinorder method");
		cl2.addInOrder(c4);
		cl2.addInOrder(c1);
		cl2.addInOrder(c2);
		cl2.addInOrder(c5);
		cl2.addInOrder(c3);
		
//		for(Contact c: contactlist){
//			System.out.println("contact"+c);
//		}
		System.out.println("check the add method");		
		cL1.add(c1);
		cL1.add(c2);
		if(cL1.add(c3)){ 
			System.out.println("Successfully added");
		}
		else{
			System.out.println("NOT Added");
		}
		System.out.println("check the toString method");
		System.out.println(c3);
		System.out.println("check the equals method");
		System.out.println(c1.equals(c3));
		System.out.println(c3.equals(c4));
		System.out.println("check the getCurrent()");
	//	System.out.println("currently is " + getCurrent());
		cL1.add(c2);
		cL1.add(c3);
		//System.out.println("after add c3, the current line is: "+ getCurrent());
		System.out.println("check the previous() method");
		System.out.println("previous contact is: "+ cL1.previous());
		System.out.println("check the next() method");
		System.out.println("next contact is: " +cL1.next());
		System.out.println("check the sort method: ");
		cL1.sort();
//		for(Contact c: contactlist){
//			System.out.println(c);
//		}
		//test find method
		System.out.println("\nTry to find 'Bob'");
		if(cL1.findContact("Bob") != null) {
			System.out.println(cL1.findContact("Bob"));
		}
		else {
			System.out.println("Cannot find Bob");
		}
		
		System.out.println("\nTry to find 'om'");
		if(cL1.findContact("om") != null) {
			System.out.println(cL1.findContact("om"));
		}
		else {
			System.out.println("Cannot find 'om'");
		}
		
       /*System.out.println("write the filename: ");
		write(input.next());
	   System.out.println("input the filename to read: ");
		read(input.next());*/

	}
	
    public ContactLinkedList(){
		 list = new MyNodeList();
                 ptr =0;
	}
	
	
    public boolean write(String file){
	    	ObjectOutputStream o;
		int numCount = 0;
	    	try {
	    	o = new ObjectOutputStream(new FileOutputStream("datafile"));
	    	o.close();
	    	return true;
	    	}
	    	catch (IOException e) {
	    	System.out.println("File write problem to fix");
	    	return false;
	    	} 
	    }
	public static boolean read(String name){
		ObjectInputStream i;
		int numCount = 0;//count how many contacts are in the file
		boolean success = true;
		try {
			i = new ObjectInputStream(new FileInputStream(""));
			while(i.readObject()!=null){
				numCount++;
			}
		}
		catch (EOFException e) {
			System.out.println("End of File read");
		}
		catch (IOException e) { 
			System.out.println("IO problem to fix");
		}

		catch (ClassNotFoundException e) { 
			System.out.println("Class does not exist");
		}
		Contact[] temp = new Contact[numCount];
		try{
			i = new ObjectInputStream(new FileInputStream(""));
				for(int j = 0; j<temp.length; j++){
					temp[j] = (Contact) i.readObject();//add read object to the temp array
				}
		//	contactlist = temp;
			i.close();
		}
		
		catch (EOFException e) {
			System.out.println("End of File read");
			success = false;
			}
			catch (IOException e) { 
			System.out.println("IO problem to fix");
			success = false;
			}
			
			catch (ClassNotFoundException e) { 
			System.out.println("Class does not exist");
			success = false;
			}
		return success;
	}// close read

	// use s as with System.in, except reading will be done from data.txt		
	public boolean addInOrder(Contact mem){
		boolean success = false;
		if(add(mem)){
			if(ptr!=0){
				sort();
			}
			success = true;
		}
		return success;	
	}
	//insert a new Contact into the ContactLinkedList and return true if successful, 
	//and false if the ContactLinkedList array is full.
	
	public boolean add(Contact mem){
	
		//Each time a Contact is added, looked up, etc., ptr is set to the Contact referenced.
		//Include the following three methods:
		ptr = list.size();
		return list.add(mem);
	}
	
	//returns the first Contact found,
	//relative to the current position in the ContactLinkedList that has a name field that 
	//contains a match for String.   
	//If the contact is not found, return null and do not update ptr.
	
	public Contact findContact(String input){		
		// relative to the current position
		
                Node c = list.head;
                while(c.getNext()!=null)
                {
                    if (((Contact)c.getData()).getName().equals(input)) {
                        ptr = list.indexOf(c);
                        return (Contact) c.getData();
                    }
                    c = c.getNext();
                }
                if (((Contact)c.getData()).getName().equals(input)) {
                ptr = list.indexOf(c);
                return (Contact) c.getData();
            }
		
		return null;

		}//method
        
	public Contact remove(){
		return (Contact) list.remove(ptr);
	}
	
	//returns the contact currently pointed to
	public Contact getCurrent(){
		return (Contact)list.get(ptr);
	}
	
	public Contact get(int index) {
		/*
		 *  returns the Contact at location represented by int. 
		 *  If the ContactLinkedList is empty, null is returned.
		 */
               
		return (Contact) list.get(index);
	} 
	
	public Contact next(){
		/*
		 * moves ptr ahead to the next Contact and returns Contact.
		 * If ptr is at end of the list, ptr is set to first Contact and returns first Contact, 
		 * and if the list is empty, 
		 * this method does nothing and returns null.
		 */
	    if (ptr>=list.size()) {
                return null;
            }
            
            if (ptr == list.size()-1) {
                ptr = 0;
                return (Contact)list.get(0);
            }
            
            ptr++;
            return (Contact) list.get(ptr);
	}

	public Contact previous() {
		/*
		 * moves ptr back to the previous Contact. 
		 */
            if (ptr >= list.size()) {
                return null;
            }
            
            if (ptr == 0) {
                ptr = list.size()-1;
                return (Contact) list.get(ptr);
            }
            
            ptr--;
            return (Contact) list.get(ptr);
            
	}
	// sorts the ContactLinkedList according to the name attribute. in "roughly" alphabetical order
	public void sort(){
		int i, j;
		Contact adjust;
                int temp=-1;
		for (i=1; i<list.size(); i++){
			adjust = (Contact)list.get(i);
			if((Contact)list.get(i)==null){
				break;
			}
			else{
				for(j = i-1; j >= 0 && adjust.getName().compareTo(((Contact)list.get(i)).getName()) < 0; j--){
                                    temp = j;
					
				}
                                list.remove(i);
                                list.insert(temp, adjust);
			}	
			}
		}
	}	
	