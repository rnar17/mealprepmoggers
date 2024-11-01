**Models**

**UserModel**



* This model stores user profile data, fitness goals, and dietary preferences relevant to choosing meals.
* Model resides only on the client
* The ProfileController and FitnessController access the user model to communicate user preferences.
    * The **ProfileController** can ask for the profile data from the UserModel such as preferences and identity information
    * The **FitessController**can ask the UserModel for user-defined fitness goals and health information (weight, bmi)

**MealModel**



* The MealModel stores recipe suggestions and required ingredients, nutritional fact breakdown, and dietary information.
* This model is managed through the client, but accesses online APIs for retrieving nutritional information. Users can also add their own meals / recipes into this model.
* Both MealController and NutrionController manage the MealModel. 
    * The **MealController** can ask for meal data based on available ingredients and user preferences.
    * **FitnessController** asks for detailed nutritional information for selected meals.

**IngredientModel**



* **IngredientModel**tracks items users have, and need to obtain for meals.
* The **IngredientModel** resides only on the client
* Only the IngredientsController accesses this model.
    * The **IngredientsController** asks the **IngredientModel** what ingredients are available.


#### 


#### **Controllers**

**ProfileController**



* Manages user profile setup, including updating preferences and goals.
    * **ProfileController**asks **UserModel** for current preferences and profile data.
    * This controller is managed locally
* Can access **UserModel** to change preferences and profile data.
* Updates **UserView** interface when profile data changes.

**MealController**



* Handles meal suggestions, including retrieving meal recipe and nutritional data from API, filtering by macros and dietary goals, and ranking suggestions.
* Has both server and local components. API access is managed through external servers, while meal suggestions and recipes through **MealView**are local and modeled by **MealModel**
* Asks **MealModel** for meal options based on user-selected ingredients and fitness goals.
* Asks **IngredientModel**for available ingredients. Provides this information to **MealModel.**
* Provides **MealView** with the recommended meals to the user.

    **FitnessGoalController**

* This controller serves to calculate caloric intake and suggested intake based on provided user goals and dietary preferences from **UserModel**.
* The **FitnessGoalController**is managed and interfaced locally on the client
* **FitnessGoalController** asks **UserModel** for profile and fitness information
    * Asks **UserModel** for fitness goals and determines what nutritional information meals should contain
    * Asks **MealModel** for the nutritional data of meals. Sends suggested nutritional data to **MealModel**and **MealController**.
    * **FitnessGoalView**: Provides feedback to the user on their current goal settings.

**IngredientsController**



* Manages the user's grocery list, allowing additions, removals.
* The **IngredientsController** resides locally on the Client.
* Communicates with both **IngredientModel**to manage what ingredients are available.
    * Asks **IngredientModel**on what ingredients are available and updates **IngredientsView**.
    * Updates **GroceryListView** with **IngredientModel** data for ingredients to source.


#### **Views**

**MealView**



* Displays to the user the recommended meals, ingredient lists, and prep/cooking instructions.
* The **MealView** is managed locally.
* Updated through **MealController** that displays the meal recommendations 

**FitnessGoalView**



* Displays fitness goals and adjusts meal recommendations to fit goals. 
* The **FitnessGoalView** is managed locally.
* **FitnessGoalView** is updated from **FitnessGoalController**with user goal data.

**IngredientsView**



* Displays the current ingredients the user has, and also displays a prompt for the user to enter more ingredients.
* The **IngredientsView** is managed locally.
* **IngredientsView**displays the users available ingredients from **IngredientController**.

**GroceryListView**



* Provides an interface for users to view and modify their grocery list.
* The **GroceryView** is managed locally.
* Displays feedback from **IngredientsController**. Reflects any additions, removals, or updates to the grocery list.


---

// FitnessGoal.java

class FitnessGoal {

    private String type;  // "weight_loss", "muscle_gain", or "maintenance"

    private int calories;

    private int protein;

    private int carbs;

    private int fat;

    public FitnessGoal(String type, int calories, int protein, int carbs, int fat) {

        this.type = type;

        this.calories = calories;

        this.protein = protein;

        this.carbs = carbs;

        this.fat = fat;

    }

    // Getters and setters

    public String getType() { return type; }

    public int getCalories() { return calories; }

    public int getProtein() { return protein; }

    public int getCarbs() { return carbs; }

    public int getFat() { return fat; }

}

// UserProfile.java

class UserProfile {

    private String name;

    private ArrayList&lt;String> restrictions;

    private ArrayList&lt;String> preferences;

    private FitnessGoal goal;

    private double weight;

    private double height;

    public UserProfile(String name) {

        this.name = name;

        this.restrictions = new ArrayList&lt;>();

        this.preferences = new ArrayList&lt;>();

    }

    // Getters and setters

    public String getName() { return name; }

    public ArrayList&lt;String> getRestrictions() { return restrictions; }

    public ArrayList&lt;String> getPreferences() { return preferences; }

    public FitnessGoal getGoal() { return goal; }

    public void setGoal(FitnessGoal goal) { this.goal = goal; }

}

// Meal.java

class Meal {

    private String name;

    private ArrayList&lt;String> ingredients;

    private int calories;

    private int protein;

    private int carbs;

    private int fat;

    private ArrayList&lt;String> instructions;

    public Meal(String name) {

        this.name = name;

        this.ingredients = new ArrayList&lt;>();

        this.instructions = new ArrayList&lt;>();

    }

    // Getters and setters

    public String getName() { return name; }

    public ArrayList&lt;String> getIngredients() { return ingredients; }

    public int getCalories() { return calories; }

}

// Models

class UserModel {

    public UserProfile getProfile() {

        // TODO: Get user profile from storage

        return new UserProfile("default");

    }

    public void updateProfile(UserProfile profile) {

        // TODO: Save user profile

    }

}

class MealModel {

    public ArrayList&lt;Meal> getMealSuggestions(ArrayList&lt;String> ingredients) {

        // TODO: Return meals that can be made with given ingredients

        return new ArrayList&lt;>();

    }

    public Meal getNutrition(String mealName) {

        // TODO: Get nutritional info for a meal

        return new Meal(mealName);

    }

    public void addRecipe(Meal meal) {

        // TODO: Save new recipe

    }

}

class IngredientModel {

    private ArrayList&lt;String> ingredients;

    public IngredientModel() {

        this.ingredients = new ArrayList&lt;>();

    }

    public ArrayList&lt;String> getIngredients() {

        return ingredients;

    }

    public void addIngredient(String ingredient) {

        ingredients.add(ingredient);

    }

    public void removeIngredient(String ingredient) {

        ingredients.remove(ingredient);

    }

}

// Controllers

class ProfileController {

    private UserModel model;

    private UserView view;

    public ProfileController(UserModel model, UserView view) {

        this.model = model;

        this.view = view;

    }

    public void updateProfile(UserProfile profile) {

        model.updateProfile(profile);

        view.showProfile(profile);

    }

    public UserProfile getProfile() {

        return model.getProfile();

    }

}

class MealController {

    private MealModel model;

    private MealView view;

    public MealController(MealModel model, MealView view) {

        this.model = model;

        this.view = view;

    }

    public ArrayList&lt;Meal> getSuggestions(ArrayList&lt;String> ingredients) {

        ArrayList&lt;Meal> meals = model.getMealSuggestions(ingredients);

        view.showMeals(meals);

        return meals;

    }

}

class GroceryController {

    private IngredientModel model;

    private GroceryView view;

    public GroceryController(IngredientModel model, GroceryView view) {

        this.model = model;

        this.view = view;

    }

    public void addItem(String item) {

        model.addIngredient(item);

        view.showList(model.getIngredients());

    }

    public void removeItem(String item) {

        model.removeIngredient(item);

        view.showList(model.getIngredients());

    }

}

// Views

class MealView {

    public void showMeals(ArrayList&lt;Meal> meals) {

        // TODO: Display meal suggestions

        System.out.println("Showing " + meals.size() + " meals");

    }

    public void showRecipe(Meal meal) {

        // TODO: Display recipe

        System.out.println("Showing recipe for: " + meal.getName());

    }

}

class GroceryView {

    public void showList(ArrayList&lt;String> items) {

        // TODO: Display grocery list

        System.out.println("Grocery list has " + items.size() + " items");

    }

    public void updateItem(String item, boolean checked) {

        // TODO: Update item status

        System.out.println("Updated item: " + item);

    }

}

class UserView {

    public void showProfile(UserProfile profile) {

        // TODO: Display user profile

        System.out.println("Showing profile for: " + profile.getName());

    }

}
