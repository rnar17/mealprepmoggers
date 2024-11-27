package org.example.controller;
import com.google.gson.JsonIOException;
import org.example.model.UserModel.FitnessGoals;
import org.example.model.UserModel.Profile;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ProfileController {
    Profile user;
    String profilePath = "src/main/User/Profile.json";
    Gson gson1 = new Gson();
    Gson gson2 = new Gson();
    FileReader reader;
    {
        try {
            reader = new FileReader(profilePath);
            user = gson1.fromJson(reader, Profile.class);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
//    FileWriter writer;
//    {
//        try {
//            writer = new FileWriter(profilePath);
//        } catch (IOException d) {
//            d.printStackTrace();
//        }
//    }

    public Profile fetchProfile(){
//        Profile profile = gson1.fromJson(reader, Profile.class);
//        user = profile;
        return user;
    }

    public boolean updateProfile(String userName, int userAge, int userWeight, int userHeight, FitnessGoals userGoal){
        try (FileWriter writer = new FileWriter(profilePath)) {
            gson2.toJson(new Profile(userName,userAge,userWeight, userHeight, userGoal), writer);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public FitnessGoals getGoal(){
        if(user.goal == null){
            return FitnessGoals.NONE;
        }
        else return user.goal;
    }



}




