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
public class NewContact extends Contact implements Serializable{

    String email, group, quickRef;
    
    public NewContact(String name, String address, String comments, long phone, String email, String group, String quickRef) {
        super(name, address, comments, phone);
        this.email = email;
        this.group = group;
        this.quickRef = quickRef;
    }
    
    String getEmail()
    {
        return email;
    }
    
    String getGroup()
    {
        return group;
    }
    
    String getQuickRef()
    {
        return quickRef;
    }
    
    void setEmail(String e)
    {
        email = e;
    }
    void setGroup(String g)
    {
        group = g;
    }
    
    void setQuickRef(String q)
    {
        quickRef = q;
    }
    
    @Override
    public String toString()
    {
        String str = "";
        str = name+ "\n";
        str += address+"\n";
        str += comments +"\n";
        str+= phone +"\n";
        str+= email + "\n";
        str+= group + "\n";
        str += quickRef + "\n";
        return str;
    }
    public boolean equals(NewContact ins)
    {
        return ins.name.equals(name)&&ins.comments.equals(comments)&&ins.phone==phone&&ins.address.equals(address)&&email.equals(ins.email)&&ins.group.equals(group)&&quickRef.equals(ins.quickRef);
    }
    
}
