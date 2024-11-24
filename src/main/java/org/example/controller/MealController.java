package org.example.controller;

import org.example.model.SpoonacularClient;

import javax.swing.SwingUtilities;
import java.util.ArrayList;
import java.util.List;

public class MealController {
    static SpoonacularClient client;

    /**
     * Controller to handle client/API calls and manage views. Also serves to cache recipies.... mayb wanna make this a diff class
     */
    private MealController(){
        try {
            client = new SpoonacularClient(System.getenv("KEY"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Connects to the API and fetches recipies
     * @return
     */
    public static boolean fetchRecipies(){
        try {
            java.util.List<SpoonacularClient.Recipe> recipes = client.findRecipesByIngredients(selectedIngredients, 2, 6);

            // Get full recipe information for each recipe
            List<SpoonacularClient.Recipe> fullRecipes = new ArrayList<>();
            for (SpoonacularClient.Recipe recipe : recipes) {
                SpoonacularClient.Recipe fullRecipe = client.getRecipeById(recipe.id);
                fullRecipe.usedIngredients = recipe.usedIngredients;
                fullRecipe.missedIngredients = recipe.missedIngredients;
                fullRecipes.add(fullRecipe);
            }

            // Update saved recipes and UI
            SwingUtilities.invokeLater(() -> {
                savedRecipes.addAll(fullRecipes);
                StringBuilder resultText = new StringBuilder();
                resultText.append("Generated ").append(recipes.size())
                    .append(" recipes! Check the Home page to view them.\n\n");

                for (SpoonacularClient.Recipe recipe : fullRecipes) {
                    resultText.append("- ").append(recipe.title).append("\n");
                }

                recipeResults.setText(resultText.toString());
                recipeResults.setCaretPosition(0);

                // Show confirmation dialog
//                            JOptionPane.showMessageDialog(frame,
//                                    "Recipes generated successfully! Go to Meals page to view them.",
//                                    "Success",
//                                    JOptionPane.INFORMATION_MESSAGE);
            });

            return true;
        } catch (Exception ex) {
            SwingUtilities.invokeLater(() -> {
                recipeResults.setText("Error fetching recipes: " + ex.getMessage());
                ex.printStackTrace();
            });
            return false;
        }
    }
}
