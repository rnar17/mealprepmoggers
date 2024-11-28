package org.example.model.UserModel;

public enum FitnessGoals {
    WEIGHT_LOSS("Weight Loss","• Focus on lower-calorie meals\n• Higher protein content\n• More vegetables and fiber"),
    MUSCLE_GAIN("Muscle Gain","• Higher protein meals\n• Complex carbohydrates\n• Nutrient-dense ingredients"),
    MAINTENANCE("Maintenance", "• Balanced macronutrients\n• Sustainable portion sizes\n• Variety of nutrients"),
    NONE("No Goal Selected","");

    public final String title;
    public final String description;

    FitnessGoals(String title,String description) {
        this.title = title;
        this.description = description;
    }
}


