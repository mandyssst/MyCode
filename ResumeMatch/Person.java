/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package resume;




class Person {
    public String address, name, phone;
    public int age,id;
    
    
    
    public Person ()
    {
    this.address = "";
    this.name = "";
    this.phone = "";    
    this.age = 0;
    this.id = 0;
    
    }
    
    
    public Person (int myAge, int myId, String myAddress, String myName, String myPhone)
    {
            this.age = myAge;
            this.address = myAddress;
            this.id = myId;
            this.name = myName;
            this.phone = myPhone;
    
                    }
}
