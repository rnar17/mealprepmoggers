package org.example.controller;
import org.example.model.UserModel.FitnessGoals;
import org.example.model.UserModel.Profile;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ProfileController {
    //TODO update profile.JSON through profileView
     String profilePath = "src/main/User/Profile.json";
     Gson gson = new Gson();


    FileReader reader;
    {
        try {
            reader = new FileReader(profilePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Profile user = gson.fromJson(reader, Profile.class);

    public boolean updateProfile(){
        Profile update = gson.fromJson(reader, Profile.class);
        user.name = update.name;
        user.age = update.age;
        user. weight = update.weight;
        user.height = update.height;

        //add condition for successful update
        return true;
    }

    public FitnessGoals getGoal(){
        if(user.goal == null){
            return FitnessGoals.MAINTENANCE;
        }
        else return user.goal;
    }


}
