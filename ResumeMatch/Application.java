package resume;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author huri0004
 */
public class Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Application haha = new Application();
        haha.getFullData();
        
        
        }
    
    
    public ResumeDatabase getFullData(){
        
        Person a = new Person();
        Person b = new Person();
        Person c = new Person();
        Person d = new Person();
      
        a.name="amy";
        a.id=1;
        b.name="black";
        b.id=2;
        c.name="claire";
        c.id=3;
        d.name="dick";
        d.id=4;
        
        ResumeDatabase instance = new ResumeDatabase();
       
        
        instance.application.put("amy",a);
        instance.application.put("black",b);
        instance.application.put("claire",c);
        instance.application.put("dick",d);
        
        
        instance.education.put(a.id,"");
        instance.education.put(b.id,"TsingHua");
        instance.education.put(c.id,"Harvard");
        instance.education.put(d.id,"MIT");
        
        
        
        instance.employerReputation.put("baylor",4);
        instance.employerReputation.put("carl",10);
        instance.employerReputation.put("steve",3);
        instance.employerReputation.put("chris",9);
        
        
        instance.previousEmployer.put(a.id, "chris");
        instance.previousEmployer.put(b.id, "baylor");
        instance.previousEmployer.put(c.id, "steve");
        instance.previousEmployer.put(d.id, "carl");
        
        
        
        instance.schoolReputation.put("MIT",10);
        instance.schoolReputation.put("Harvard",9);
        instance.schoolReputation.put("TsingHua",5);
        instance.schoolReputation.put("Beida",8);
        
        List<String> askill= new ArrayList<>();
        askill.add("computer");
        instance.skills.put(a.name,askill);
        
        List<String> bskill= new ArrayList<>();
        bskill.add("cooking");
        instance.skills.put(b.name,bskill);
        
        List<String> cskill= new ArrayList<>();
        cskill.add("computer");
        cskill.add("video games");
        instance.skills.put(c.name,cskill);
        
        List<String> dskill= new ArrayList<>();
        dskill.add("eating");
        instance.skills.put(d.name,dskill);
        
        System.out.println(instance.getBest("computer").name);
        return instance;
    }
}