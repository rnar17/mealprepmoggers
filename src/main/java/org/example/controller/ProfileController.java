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
    Gson gson = new Gson();
    FileReader reader;
    {
        try {
            reader = new FileReader(profilePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    FileWriter writer;
    {
        try {
            writer = new FileWriter(profilePath);
        } catch (IOException d) {
            d.printStackTrace();
        }
    }


    public Profile fetchProfile(){
        this.user = gson.fromJson(reader, Profile.class);
        return user;
    }

    public boolean updateProfile(String userName, int userAge, int userWeight, int userHeight, FitnessGoals userGoal){
        try {
            gson.toJson(new Profile(userName,userAge,userWeight, userHeight, userGoal), writer);
        } catch (JsonIOException e) {
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
