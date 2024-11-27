package org.example.model;

/**
 * Represents an ingredient used in a recipe, including details such as its ID, name,
 * original description, amount, and unit.
 *
 * @param id       the unique identifier for the ingredient
 * @param name     the name of the ingredient
 * @param original the original description or representation of the ingredient in the recipe
 * @param amount   the quantity of the ingredient used
 * @param unit     the unit of measurement for the ingredient
 */
public record Ingredient(
     int id,
     String name,
     String original,
     double amount,
     String unit) { }
