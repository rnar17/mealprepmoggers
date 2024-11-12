package org.example.controller;

import org.example.model.UserModel.Profile;

public class ProfileController {
    //TODO update profile.JSON through profileView

    String userName;
    int age;
    int weight;
    int height;

    Profile user = new Profile(userName,age,weight,height);

    public boolean updateProfile(){
        //TODO read json and update fields
        user.name = userName;
        user.age = age;
        user. weight = weight;
        user.height = height;

        //add condition for successful update
        return true;
    }

}
