package org.example.controller;

import org.example.model.*;

import javax.swing.SwingUtilities;
import java.util.ArrayList;
import java.util.List;

public class MealController {
    private static SpoonacularClient client;
    private static List<Recipe> savedRecipes = new ArrayList<>();
    /**
     * Controller to handle client/API calls and manage views. Also serves to cache recipies.... mayb wanna make this a diff class
     */
     static {
        try {
            client = new SpoonacularClient(System.getenv("KEY"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Connects to the API and fetches recipies and updates saved recipies
     * @return list of recipies
     */
    public static List<Recipe> findRecipies(List<String> selectedIngredients) throws Exception{
        try {
            return client.findRecipesByIngredients(selectedIngredients, 2, 6);
        } catch (Exception ex) {
            throw new Exception();//TODO add exception detailing for unfound recipies
        }
    }

    public static boolean addRecipies(List<Recipe> recipes){
        return savedRecipes.addAll(recipes);
    }

    public static boolean removeRecipie(Recipe recipe){
        return savedRecipes.remove(recipe);
    }

    public static void removeAllRecipies(){
        savedRecipes.clear();
    }

    public static List<Recipe> getSavedRecipes(){
        return savedRecipes;
    }
}
