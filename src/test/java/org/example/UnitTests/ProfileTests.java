package org.example.UnitTests;

import org.example.model.UserModel.DietRestriction;
import org.example.model.UserModel.Profile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//Create new profile
public class ProfileTests {
    String name = "eric";
    int age = 18; //date of birth?
    int weight = 220;
    int height = 180;
    private List<String> allergies = new ArrayList<>(List.of("milk"));
    public DietRestriction restriction;

//    @Test
//    public void hasAllergy(){
//        Profile profile = new Profile(name,age,weight,height,allergies,restriction);
//        assertTrue(profile.hasAllergy("milk"));
//
//    }

}
