package org.example.model;

import java.util.List;

/**
 * Represents the nutritional information of a recipe, including a list of nutrients.
 * <p>
 * This class was designed as a separate record to encapsulate the nutritional data
 * in a clean and reusable way.
 *
 * @param nutrients A list of {@link Nutrient} objects that describe the nutritional content
 *                  of the recipe.
 */
public record Nutrition(List<Nutrient> nutrients) {
}
