package org.example.UnitTests;

import org.example.controller.MealController;
import org.example.model.Recipe;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MealTests {

    @Test
    public void findRecipes() throws Exception {
        MealController mealController = new MealController();
        List<String> ingredients = new ArrayList<>();
        ingredients.add("egg");
        ingredients.add("rice");
        assertNotNull(mealController.findRecipies(ingredients));
    }

    @Test
    public void saveRecipe() throws Exception {
        MealController mealController = new MealController();
        List<String> ingredients = new ArrayList<>();
        ingredients.add("egg");
        ingredients.add("rice");
        List<Recipe> recipes = mealController.findRecipies(ingredients);
        mealController.addRecipies(recipes);
        assertEquals(recipes,mealController.getSavedRecipes());
    }

    @Test
    public void removeRecipes() throws Exception {
        MealController mealController = new MealController();
        List<String> ingredients = new ArrayList<>();
        ingredients.add("egg");
        ingredients.add("rice");
        List<Recipe> recipes = mealController.findRecipies(ingredients);
        mealController.addRecipies(recipes);
        mealController.removeAllRecipies();
        assertEquals(0,mealController.getSavedRecipes().size());

    }

    @Test
    public void starRecipe() throws Exception {
        MealController mealController = new MealController();
        List<String> ingredients = new ArrayList<>();
        ingredients.add("egg");
        List<Recipe> recipes = mealController.findRecipies(ingredients);
        mealController.addRecipies(recipes);
        recipes.forEach(mealController::starRecipe);
        assertTrue(recipes.containsAll(mealController.getFavouriteRecipes()));
    }

//    @Test
//    public void removeStar() throws Exception {
//        MealController mealController = new MealController();
//        List<String> ingredients = new ArrayList<>();
//        ingredients.add("egg");
//        List<Recipe> recipes = mealController.findRecipies(ingredients);
//        mealController.addRecipies(recipes);
//        recipes.forEach(mealController::starRecipe);
//        assertTrue(recipes.containsAll(mealController.getFavouriteRecipes()));
//    }
}
