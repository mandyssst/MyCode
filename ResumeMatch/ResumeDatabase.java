/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package resume;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResumeDatabase {
    public Map<String,Person> application = new HashMap<>();
  
    
    public Map<Integer, String> education = new HashMap<>();
    
    public Map<String, Integer> employerReputation = new HashMap<>();
    
    public Map<Integer,String> previousEmployer = new HashMap<>();
    
    public Map<String, Integer> schoolReputation = new HashMap<>();
    
    public Map<String, List<String>> skills = new HashMap<>();  
    
   
    
    private int getEducationScore (int id)
    { 
       String schoolName = education.get(id);
       if (schoolName == null)
       {
           return 0;
       }
       else
       {
           return schoolReputation.get(schoolName);
       }
    }
    
    private int getExperienceScore (int id)
    {
        String employerName = previousEmployer.get(id);
        if (employerName == null)
        {
            return 0;
        }
        return employerReputation.get(employerName);
    
    }
    
    
    
    private int getScore (int id)
    {
     int score = getEducationScore(id)+ getExperienceScore(id);
        return score;
     
    }
    
    public Person getBest (String skill)
    {
        List<String> temp = new ArrayList();
        Map<Integer,Person>score =new HashMap<>();
        
        for(String name: skills.keySet())
        {
            if (skills.get(name).contains(skill))
            {
                temp.add(name);
            }
        }
        for (String name : temp){
            
            Person newPerson = application.get(name);
            int newId = newPerson.id;
            int newScore = getScore(newId);
            score.put(newScore, newPerson);
        }
        
    
    
    int highScore = Collections.max(score.keySet());
    return score.get(highScore);
    }
    
}