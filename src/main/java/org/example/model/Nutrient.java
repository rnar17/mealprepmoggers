package org.example.model;

/**
 * Represents a nutrient with its name, amount, unit, and contribution to daily needs.
 *
 * @param name                the name of the nutrient
 * @param amount              the amount of the nutrient in the recipe
 * @param unit                the unit of measurement for the nutrient amount
 * @param percentOfDailyNeeds the percentage of daily nutritional needs fulfilled by this nutrient
 */
public record Nutrient(
    String name,
    double amount,
    String unit,
    double percentOfDailyNeeds) { }
