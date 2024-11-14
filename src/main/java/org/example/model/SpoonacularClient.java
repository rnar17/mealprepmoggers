package org.example.model;

import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

public class SpoonacularClient {
    private final String API_KEY;
    private final HttpClient client;
    private final Gson gson;

    public SpoonacularClient(String apiKey) {
        this.API_KEY = apiKey;
        this.client = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    public Recipe getRecipeById(int id) throws Exception {
        String endpoint = String.format("/recipes/%d/information?includeNutrition=true", id);
        String response = makeGetRequest(endpoint);
        return gson.fromJson(response, Recipe.class);
    }

    public List<Recipe> findRecipesByIngredients(List<String> ingredients, int ranking)
        throws Exception {
        // Join ingredients with comma
        String ingredientsList = String.join(",", ingredients);

        // ranking=2 maximizes used ingredients, ranking=1 minimizes missing ingredients
        String endpoint = String.format("/recipes/findByIngredients?ingredients=%s&ranking=%d&ignorePantry=true&number=10",
            ingredientsList, ranking);

        String response = makeGetRequest(endpoint);
        Recipe[] recipeArray = gson.fromJson(response, Recipe[].class);
        return Arrays.asList(recipeArray);
    }

    private String makeGetRequest(String endpoint) throws Exception {
        String BASE_URL = "https://api.spoonacular.com";
        String fullUrl = BASE_URL + endpoint;
        if (!endpoint.contains("?")) {
            fullUrl += "?";
        }
        if (!endpoint.endsWith("?")) {
            fullUrl += "&";
        }
        fullUrl += "apiKey=" + API_KEY;

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(fullUrl))
            .header("Accept", "application/json")
            .GET()
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public static class Recipe {
        public int id;
        public String title;
        public int servings;
        public String sourceUrl;
        public String image;
        public Nutrition nutrition;
        public List<Ingredient> usedIngredients;
        public List<Ingredient> missedIngredients;
    }

    public static class Nutrition {
        public List<Nutrient> nutrients;
    }

    public static class Nutrient {
        public String name;
        public double amount;
        public String unit;
        public double percentOfDailyNeeds;
    }

    public static class Ingredient {
        public int id;
        public String name;
        public String original;
        public double amount;
        public String unit;
    }
}