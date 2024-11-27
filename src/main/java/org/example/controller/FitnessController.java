package org.example.controller;

public class FitnessController {

    // Calculate maintenance calories using the formula:
    // ((10 × weight in kg) + (6.25 × height in cm) - (5 × age in years)) * 1.3
    public static double calculateMaintainanceCalories(int userWeight, int userHeight, int userAge){
        return ((10 * userWeight) + (6.25 * userHeight) - (5 * userAge)) * 1.37;
    }
}
