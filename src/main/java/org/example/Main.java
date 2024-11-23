package org.example;
import org.example.view.MasterView;

import org.example.model.SpoonacularClient;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
//        SpoonacularClient client = new SpoonacularClient(System.getenv("KEY"));
//
//        List<String> myIngredients = Arrays.asList("peanut");
//
//        // Find recipes that use these ingredients (ranking=2 to maximize used ingredients)
//        List<SpoonacularClient.Recipe> recipes = client.findRecipesByIngredients(myIngredients, 2);
//
//        // Print results
//        for (SpoonacularClient.Recipe recipe : recipes) {
//            System.out.println("Recipe: " + recipe.title);
//            SpoonacularClient.Recipe recipeWithInfo = client.getRecipeById(recipe.id);
//            double calories = recipeWithInfo.nutrition.nutrients.stream()
//                .filter(n -> n.name.equals("Calories"))
//                .findFirst()
//                .map(n -> n.amount)
//                .orElse(0.0);
//            System.out.println("Calories: " + calories);
//            System.out.println(recipeWithInfo.sourceUrl);
//
//            System.out.println("\nUsed ingredients:");
//            for (SpoonacularClient.Ingredient ingredient : recipe.usedIngredients) {
//                System.out.println("- " + ingredient.original + " (" + ingredient.amount + " " +
//                    ingredient.unit + ")");
//            }
//
//            System.out.println("\nMissing ingredients:");
//            for (SpoonacularClient.Ingredient ingredient : recipe.missedIngredients) {
//                System.out.println("- " + ingredient.original + " (" + ingredient.amount + " " +
//                    ingredient.unit + ")");
//            }
//
//            System.out.println("\n-------------------\n");
//        }

        MasterView test = new MasterView();

    }
}