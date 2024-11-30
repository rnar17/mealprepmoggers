package org.example.model;

import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

/**
 * This class is to retrieve information about recipes, ingredients, and nutritional facts from
 * our API, Spoonacular
 * Representation Invariant:
 * - API_KEY is not null
 * - client is not null
 * - gson is not null
 * Abstract function:
 * Client to pull recipe, ingredient, and nutrition info from API
 * r.API_KEY = key to access API
 * r.client = client to make http requests to API
 * r.gson = json parser to read return json file from API
 */

public class SpoonacularClient {
    private final String API_KEY;
    private final HttpClient client;
    private final Gson gson;

    /**
     * Constructor for SpoonacularClient, initializes KEY to be used, client to send HTTP
     * requests, and gson parser
     * @param apiKey API_KEY to make requests to API
     */
    public SpoonacularClient(String apiKey) {
        this.API_KEY = apiKey;
        this.client = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    /**
     * Fetches recipe information with nutrition from recipe ID.
     * @param id recipe id to fetch information from
     * @return Recipe type with fields set from json
     * @throws Exception JsonSyntaxException if syntax is not as expected
     */
    public Recipe getRecipeNutrition(int id) throws Exception {
        String endpoint = String.format("/recipes/%d/information?includeNutrition=true", id);
        String response = makeGetRequest(endpoint);
        return gson.fromJson(response, Recipe.class);
    }

    /**
     * Finds recipe by ingredients, and uses a ranking system to sort recipes
     * @param ingredients ingredient inputs to search for
     * @param ranking ranking to filter recipes with, 1 minimizes missing ingredients and 2
     *                maximizes used ingredients
     * @param number number of recipes to generate
     * @return list of recipes depending on user input
     * @throws Exception on illegal json formatting
     */
    public List<Recipe> findRecipesByIngredients(List<String> ingredients, int ranking, int number)
        throws Exception {
        // Join ingredients with comma
        String ingredientsList = String.join(",", ingredients);

        // ranking=2 maximizes used ingredients, ranking=1 minimizes missing ingredients
        String endpoint = String.format("/recipes/findByIngredients?ingredients=%s&ranking=%d&ignorePantry=true&number=%d",
            ingredientsList, ranking, number);

        String response = makeGetRequest(endpoint);
        Recipe[] recipeArray = gson.fromJson(response, Recipe[].class);
        return Arrays.asList(recipeArray);
    }

    /**
     * makes request to API depending on what the user wants
     * @param endpoint request url ending to append onto base url
     * @return String containing the request output
     * @throws Exception on client send request if interrupted or IO exception
     */
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
}