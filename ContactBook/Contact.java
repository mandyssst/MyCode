/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ContactBook;

/**
 *
 * @author songx544
 */
import java.io.Serializable;

/**
 *
 * @author Mandy
 */
public class Contact implements Serializable {
    String address;
    String comments;
    long phone;
    String name; 
    public Contact(String name, String address, String comments, long phone)
    {
        this.address = address;
        this.comments = comments;
        this.name = name;
        this.phone = phone;
    }
    
    @Override
    public String toString()
    {
        String str = "";
        str = name+ "\n";
        str += address+"\n";
        str += comments +"\n";
        str+= phone +"\n";
        return str;
    }
    
    public String getName()
    {
        return name;
    }
    
    public String getComments()
    {
        return comments;
    }
    
    public String getAddress()
    {
        return address;
    }
    
    public long getPhone()
    {
        return phone;
    }
    
    public void setName(String name)
    {
         this.name = name;
    }
    
    public void setComments(String comments)
    {
        this.comments = comments;
    }
    
    public void setAddress(String address)
    {
        this.address = address;
    }
    
    public void setPhone(long phone)
    {
        this.phone = phone;
    }
    
    
    public boolean equals(Contact ins)
    {
        if (ins.name.equals(name)&&ins.comments.equals(comments)&&ins.phone==phone&&ins.address.equals(address)) {
            return true;
        }
        return false;
    }
}
