package org.example.model;

public record Ingredient(
     int id,
     String name,
     String original,
     double amount,
     String unit) { }
