package org.example.model;

import java.util.List;

/**
 * Represents a recipe with details including its ID, title, image URL, nutrition information,
 * ingredients, servings, and source URL.
 *
 * @param id                the unique identifier for the recipe.
 * @param title             the title or name of the recipe.
 * @param image             the URL of the image associated with the recipe.
 * @param nutrition         the nutritional information of the recipe.
 * @param usedIngredients   a list of ingredients used in the recipe.
 * @param missedIngredients a list of ingredients missing or required for the recipe.
 * @param servings          the number of servings the recipe yields.
 * @param sourceUrl         the URL to the source of the recipe.
 */
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

    public static String cutTitle(String title) {
        String newTitle = title;
        if (title.length() > 30) {
            newTitle = title.substring(0, 29);
            newTitle = newTitle + "...";
        }
        return newTitle;
    }
}

