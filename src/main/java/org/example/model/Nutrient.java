package org.example.model;

public record Nutrient(
    String name,
    double amount,
    String unit,
    double percentOfDailyNeeds) { }
