package org.example.model;

import java.util.List;

public record Recipe(
    int id,
    String title,
    String image,
    Nutrition nutrition,
    List<Ingredient> usedIngredients,
    List<Ingredient> missedIngredients,
    int servings,
    String sourceUrl
) {
    //TODO ask eric y we need this?
    public static String cutTitle(String title) {
        String newTitle = title;
        if (title.length() > 30) {
            newTitle = title.substring(0, 29);
            newTitle = newTitle + "...";
        }
        return newTitle;
    }
}

