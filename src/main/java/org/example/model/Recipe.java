package org.example.model;

import java.util.List;
import java.util.Objects;

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

    /**
     * Returns {@code true} iff the recipe ids are identical
     * @param obj   the reference object with which to compare.
     * @return {@code true} iff the recipe ids are identical
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Recipe)) {
            return false;
        }
        Recipe recipe = (Recipe) obj;

        return recipe.id == this.id;
    }

    /**
     * hashcode based on id of recipe
     * @return the id of the recipe
     */
    @Override
    public int hashCode() {
        return this.id;
    }
}

