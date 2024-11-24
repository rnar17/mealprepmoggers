package org.example.model;

import java.util.List;

public record Recipe() {
    public static int id;
    public static String title;
    public static String cutTitle(String title) {
        String newTitle = title;
        if (title.length() > 30) {
            newTitle = title.substring(0, 29);
            newTitle = newTitle + "...";
        }
        return newTitle;
    }
    public static int servings;
    public static String sourceUrl;
    public static String image;
    public static Nutrition nutrition;
    public static List<Ingredient> usedIngredients;
    public static List<Ingredient> missedIngredients;
}
