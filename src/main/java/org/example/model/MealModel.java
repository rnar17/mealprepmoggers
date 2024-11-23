//package org.example.model;
//import java.util.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class MealModel {
//    private final List<SpoonacularClient.Recipe> recipes;
//    private final SpoonacularClient apiClient;
//
//    public MealModel(SpoonacularClient apiClient) {
//        this.apiClient = apiClient;
//        this.recipes = new ArrayList<>();
//    }
//
//    // Get recipe - first check our list, then API if not found
//    public SpoonacularClient.Recipe getRecipe(int id) throws Exception {
//        // First check our stored recipes
//        for (SpoonacularClient.Recipe recipe : recipes) {
//            if (recipe.id == id) {
//                return recipe;
//            }
//        }
//
//        // If not found, get from API and store it
//        SpoonacularClient.Recipe recipe = apiClient.getRecipeById(id);
//        if (recipe != null) {
//            recipes.add(recipe);
//        }
//        return recipe;
//    }
//
//    // Store recipes from ingredient search
//    public List<SpoonacularClient.Recipe> findAndStoreRecipes(List<String> ingredients, int ranking) throws Exception {
//        List<SpoonacularClient.Recipe> foundRecipes = apiClient.findRecipesByIngredients(ingredients, ranking);
//
//        for (SpoonacularClient.Recipe recipe : foundRecipes) {
//            // Only add if we don't already have it
//            if (!hasRecipe(recipe.id)) {
//                // Get full recipe details since search endpoint doesn't return complete info
//                SpoonacularClient.Recipe fullRecipe = apiClient.getRecipeById(recipe.id);
//                recipes.add(fullRecipe);
//            }
//        }
//
//        return foundRecipes;
//    }
//
//    // Helper method to check if we already have a recipe
//    private boolean hasRecipe(int id) {
//        return recipes.stream().anyMatch(recipe -> recipe.id == id);
//    }
//
//    // Get all stored recipes
//    public List<SpoonacularClient.Recipe> getAllRecipes() {
//        return new ArrayList<>(recipes);
//    }
//
//    // Get recipes by ingredient
//    public List<SpoonacularClient.Recipe> getRecipesByIngredient(String ingredient) {
//        return recipes.stream()
//            .filter(recipe -> recipe.usedIngredients.stream()
//                .anyMatch(ing -> ing.name.toLowerCase().contains(ingredient.toLowerCase())))
//            .collect(Collectors.toList());
//    }
//
//    // Delete recipe
//    public void deleteRecipe(int id) {
//        recipes.removeIf(recipe -> recipe.id == id);
//    }
//
//    // Clear all recipes
//    public void clearRecipes() {
//        recipes.clear();
//    }
//
//    // Get number of stored recipes
//    public int getRecipeCount() {
//        return recipes.size();
//    }
//}
//
