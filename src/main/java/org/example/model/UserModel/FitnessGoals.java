package org.example.model.UserModel;

public enum FitnessGoals {
    WEIGHT_LOSS("Weight Loss"),
    MUSCLE_GAIN("Muscle Gain"),
    MAINTENANCE("Maintenance");

    public final String title;

    FitnessGoals(String title) {
        this.title = title;
    }
}


