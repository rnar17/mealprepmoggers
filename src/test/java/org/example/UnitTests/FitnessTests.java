package org.example.UnitTests;

import org.example.controller.ProfileController;
import org.example.model.UserModel.FitnessGoals;
import org.example.model.UserModel.Profile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.example.controller.FitnessController.calculateMaintainanceCalories;
import static org.junit.jupiter.api.Assertions.*;

public class FitnessTests {
    @Test
    public void calculateCalories(){
        assertEquals(2911.25, calculateMaintainanceCalories(110,180,20));
    }
}
