package org.example.UnitTests;

import org.example.controller.ProfileController;
import org.example.model.UserModel.FitnessGoals;
import org.example.model.UserModel.Profile;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

//Create new profile
public class ProfileTests {
    @Test
    public void profileCreation(){
        String name = "Eric";
        int age = 19;
        int weight = 200;
        int height = 180;
        FitnessGoals goal = FitnessGoals.WEIGHT_LOSS;
        Profile Eric = new Profile(name, age, weight, height, goal);
        assertEquals(name, Eric.name);
        assertEquals(age, Eric.age);
        assertEquals(weight,Eric.weight);
        assertEquals(height,Eric.height);
    }

//    @Test
//    public void invalidProfile(){
//        String name = "Ronit";
//        int age = 0;
//        int weight = 0;
//        int height = 0;
//        FitnessGoals goal = FitnessGoals.WEIGHT_LOSS;
//        assertThrows(IllegalArgumentException.class, () -> {new Profile(name,age,weight,height,goal);});
//    }

    @Test
    public void goalSet(){
        String name = "Eric";
        int age = 19;
        int weight = 200;
        int height = 180;
        FitnessGoals goal = FitnessGoals.NONE;
        Profile Eric = new Profile(name, age, weight, height, goal);
        assertEquals(FitnessGoals.NONE, Eric.goal);
        Eric.setGoal(FitnessGoals.WEIGHT_LOSS);
        assertEquals(FitnessGoals.WEIGHT_LOSS, Eric.goal);
    }


    @Test
    public void fetchProfile(){
        ProfileController profileController = new ProfileController("src/test/java/org/example/UnitTests/ProfileRead.json");
        Profile profile = profileController.fetchProfile();
        Profile expected = new Profile("Ronit",18, 100, 180, FitnessGoals.NONE);
        assertEquals(expected,profile);
        assertEquals(FitnessGoals.NONE, profileController.getGoal());
    }

    @Test
    public void updateProfile(){
        ProfileController profileController = new ProfileController("src/test/java/org/example/UnitTests/ProfileWrite.json");
        Profile expected = new Profile("Eric", 18, 200,100,FitnessGoals.WEIGHT_LOSS);
        profileController.updateProfile("Eric", 18, 200,100,FitnessGoals.WEIGHT_LOSS);
        assertEquals(expected,profileController.fetchProfile());
    }

    @Test
    public void defualtGoal(){
        ProfileController profileController = new ProfileController("src/test/java/org/example/UnitTests/ProfileRead.json");
        Profile profile = profileController.fetchProfile();
        profile.setGoal(null);
        assertEquals(FitnessGoals.NONE, profileController.getGoal());
    }
}
