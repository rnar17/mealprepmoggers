1. **How will you coordinate your work?**
    1. **Who will coordinate the work?**
    2. **What will their project management practices be?**
    3. **Will you have meetings? How frequently? Who plans their agendas?**

    We will coordinate our work through a mix of weekly meetings and regular updates through Discord. Each team member will provide updates on what they’ve completed, what they are currently working on, and any issues they are facing. The Project Manager will coordinate our work by assigning tasks and tracking progress. They will accomplish this by breaking down the project into multiple stages with defined goals for each stage. This will allow us to continuously review progress and adapt to changes as needed. 

2. **What tools will you use to communicate?**
    4. **For each, articulate the alternatives and why that is the best choice.**

	**Primary Tool: Discord**



* Rationale: Discord’s code formatting ability along with its voice call and screen-sharing integration make it ideal for design and development. Moreover, it allows channel-based communication that keeps conversations organized by topic. 
* Alternatives: 
    * Teams: While Teams is powerful for design and development, it can feel overly complex for smaller projects where quick and easy communication is important. 

    **Secondary Tool: Google Drive**

* Rationale: Google Drive’s flexibility in sharing multiple documents and implementing cloud updates allows everyone to work together on the same document at the same time. 
* Alternatives:
    * Microsoft Office: Although more powerful than Google in terms of pure functionality, the user interface as well as seamless integration from Google makes it the more promising choice.

	**Tertiary Tool: GitHub**



* Rationale: The industry standard for working in teams for programming projects. Branching is crucial for effective team coding, and is very well integrated in GitHub.
* Alternatives: 
    * Discord: Only allows for sending code through messages, not ideal for organization and updating code
3. **Who will own component in your architecture?**
    5. **Owning them means being responsible for writing them and making sure they are functional and correct.**
    6. **For each component, list the one person who is in charge of getting it done.**

    Each team member will be assigned ownership of a specific component, making them responsible for the testing and completion of the component. Since we have five team members, we will split the components into Frontend Interface, Backend API, Database Architecture, Authentication and Profile Module, and Deployment. Assigning each component to a specific person encourages accountability and gives the members of our team a sense of involvement and ownership. This approach will also reduce overlap in responsibilities, ensuring that tasks are completed quicker. 

4. **What is your timeline?**
    7. **Include a list of milestones you'll reach and deadlines for each.**

**4 WEEK TIMELINE:**


**Week 1: Initial Planning and Basic Setup**



* Complete detailed requirements and task breakdown.
* Set up project repositories and development environment, including GitHub for version control.
* Begin initial setup of Java SWING for the GUI framework.
* Familiarize the team with the Edamam API, especially for retrieving recipe data and tracking calorie information.

**Week 2: API Integration and Core Functionality**

* Develop the backend structure to handle API requests to Edamam.
* Build core functionality to retrieve recipes based on input (e.g., ingredients or dietary requirements) and display them on the GUI.
* Implement a preliminary calorie tracker that pulls data from the API and calculates total calorie intake.

**Week 3: GUI Enhancement and Additional Features**



* Enhance the GUI with Java SWING components for a more user-friendly interface.
* Add interactive elements like search bars, input fields, and buttons to navigate recipe results.
* Complete error handling for API requests (e.g., handling failed requests or unavailable recipes).
* Integrate calorie-tracking features into the GUI to allow users to view and interact with their calorie intake information.

**Week 4: Testing, Debugging, and Final Deployment**



* Conduct comprehensive testing on both functionality (API integration and calorie calculations) and the GUI (user interface and user experience).
* Fix bugs and optimize the application for performance, especially regarding API calls and UI responsiveness.
* Complete final adjustments based on testing feedback and polish the GUI for user-ready presentation.
5. **How will you verify that you've met your requirements?**
    * For each requirement in your requirements document, detail how you will verify it, and if you won't verify it, justify why you won't. This is called an acceptance testing plan.
        * If you propose to write tests, what exact tests will you conduct and what will count as each test passing?
        * If you propose to conduct reviews or inspections, how will you analyze the code?
        * If you write a proof, what property will you prove?
        * If you conduct a review or inspection, what aspects of the code will you inspect to verify the requirement is met?  

**1. User Profile and Goals**



* Acceptance Tests
    * Prompt Goal Selection for First-Time Users: Will test by verifying that the app prompts new users to select a fitness goal. This ensures a complete onboarding experience.
    * Store Fitness Goal: Will test by restarting the app to confirm the goal persists in local storage. This verifies data persistence, a key feature.
    * Modify Fitness Goal in Settings: Will test by updating the goal in settings and checking if it’s saved correctly.
    * Goal Limitation to One Choice: Will not test; we assume the single-choice setup will function correctly due to standard selection limitations provided by the UI framework.
    * Adjust Daily Caloric Recommendations: Will test by ensuring caloric adjustments occur based on goal selection. This impacts all daily goals and directly affects user experience.


**2. Meal Planning Interface**

* Acceptance Tests:
    * Display Current Date’s Meals: Will test to confirm that each day’s meals are accurately displayed. Core to the interface, as users rely on it for daily planning.
    * Caloric Content Display for Each Meal: Will test to ensure visibility of each meal’s caloric content.
    * Macronutrient Content Display (Protein, Carbs, Fat): Will not test each nutrient individually but will test combined macros by spot-checking select meals. This avoids redundancy while ensuring accurate nutritional display.
    * Daily Digest Accessibility: Will test by verifying one-tap access to the Daily Digest tab.
    * Daily Caloric Sum Display: Will test to confirm daily total caloric sum is accurate, as it is crucial for tracking.

**3. Ingredient Management**

* Acceptance Tests:
    * Grocery List Tab Accessibility: Will not test specifically, as simple navigation functionality is expected to be stable. General navigation testing covers this.
    * Add New Grocery Item: Will test to confirm new items can be added to the list, as this functionality directly impacts user experience.
    * Delete Grocery Item: Will test by verifying items can be removed from both the list and storage, as data accuracy is essential for a manageable list.


**4. Meal Creation Process**

* Acceptance Tests:
    * Step-by-Step Prompts: Will test the sequence of carbohydrate → fiber → protein → beverage selections to confirm they follow the correct order.
    * Generate Meal Suggestions: Will test by inputting values and ensuring meal suggestions appear. Central to core functionality.
    * Try Another Button: Will test to confirm alternative meal suggestions generate on click. Important for user experience.
    * Caloric Target Verification: Will not test rigorously but will spot-check caloric ranges for generated meals. Slight discrepancies in daily caloric range do not majorly impact functionality and can be visually verified.


**5. Recipe Management**

* Acceptance Tests:
    * Save Recipe Functionality: Will test to ensure generated meals can be saved as recipes.
    * Recipe Naming Input: Will test to confirm saved recipes retain their custom names.
    * Cookbook Display: Will not test explicitly, as saved recipes will be checked during recipe-saving tests.
    * Ingredient Quantity Editing: Will test to verify quantities can be modified and updates are saved.

 
**6. Nutritional Calculator**

* Acceptance Tests:
    * Caloric Content Calculation: Will test by summing ingredient calories and ensuring they match the displayed meal total. Core to meal accuracy.
    * Protein, Carbohydrate, and Fat Calculations: Will test to ensure accuracy by summing nutrients for a few sample meals.
    * 100ms Update Requirement: Will not test rigorously due to variable hardware performance. We’ll observe that updates appear fast but won’t test specifically for exact timing.


**7. Other General Requirements**

* Acceptance Tests:
    * Responsive Text Display: Will not test for all screen widths but will test for standard device sizes. This covers a realistic range of usage.
    * Sound Effects: Will not test for every instance but will spot-check sounds to verify they play during key actions.
    * Error Message on No Meals: Will not test as a separate case; we’ll instead ensure meal generation generally works as expected.
    * Alternative Ingredient Suggestion: Will test to confirm at least one alternative ingredient appears when needed.
    * Local Storage for Preferences: Will test to verify immediate saving of preferences.
    * 7-Day Meal History: Will not test explicitly but will spot-check that recent meals appear correctly in history.
    * Ingredient Nutrition Database Storage: Will test by ensuring ingredients’ nutritional data saves accurately in the database.
    * Recalculate Meal Nutrition on Quantity Change: Will test to confirm accurate recalculation following quantity modifications.

* For all of the requirements, how will your verifications be integrated into your process? Will you run automated tests after every build? Before every commit? When will you conduct inspections and who will be involved?

    * Automated Testing: We will automate critical acceptance tests, particularly for nutritional calculations, ingredient management, and caloric adjustments, to ensure stability.


    * Manual Testing: Visual and audio elements (like UI responsiveness and sound effects) will be spot-checked to ensure that they meet user experience standards without redundant testing.


    * Testing Schedule: Automated tests will run after every major feature addition. Manual checks will occur weekly, covering recent updates and any specific edge cases. All team members will be involved in the testing process. 