package org.example.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class Profile {
    public String name;
    public int age; //date of birth?
    public int weight;
    public int height;
    private List<String> allergies;
    public DietRestriction restriction;
    public FitnessGoals goal;

    public Profile(String name, int age, int weight, int height, List<String> allergies, DietRestriction restriction,FitnessGoals goal){
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.allergies = allergies;
        this.restriction = restriction;
        this.goal = goal;
    }

    public Profile(String name, int age, int weight, int height){
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.height = height;
    }

    public List<String> getAllergies(){
        return new ArrayList<>(allergies);
    }

    public boolean addAllergy(String food){
        return (allergies.add(food));
    }

    public boolean removeAllergy(String food){
        return (allergies.remove(food));
    }

    public boolean hasAllergy(String food){
        return (allergies.contains(food));
    }

    public boolean hasAllergy(){
       return allergies.isEmpty();
    }

   public void setGoal(FitnessGoals goal){
        this.goal = goal;
   }
}
