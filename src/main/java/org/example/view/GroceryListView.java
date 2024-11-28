package org.example.view;
import org.example.controller.MealController;
import org.example.model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import static org.example.view.ViewUtility.*;

/**
 * Represents the Grocery List (Pantry) view in the Meal Prep Assistant application.
 * This view allows users to add, remove, and generate recipes based on selected ingredients.
 *
 * Representation Invariant:
 * 1. checkBoxList is not null
 * 2. checkBoxList contains only non-null JCheckBox elements
 * 3. Each JCheckBox in checkBoxList has a unique, sequentially numbered prefix
 * 4. inputPanel, checkboxPanel, and scrollPane are not null
 * 5. mealController is not null
 * 6. The background color of the panel is consistent (LIGHT_GREEN)
 *
 * Abstraction Function:
 * A grocery list view where:
 * 1. r.checkBoxList = list of ingredient checkboxes representing available pantry items
 * 2. r.itemInput = text field for adding new ingredients
 * 3. r.checkboxPanel = panel displaying the list of ingredients
 * 4. r.mealController = controller managing recipe generation and storage
 * 5. r.recipeResults = text area showing generated recipes
 */
public class GroceryListView extends JPanel {

    private final List<JCheckBox> ingredients;
    private final JTextField itemInput;
    private final JPanel checkboxPanel;
    private final JTextArea recipeResults;
    private final MealController mealController;
    private final JScrollPane scrollPane;

    /**
     * A helper method that checks the state of the presentation invariant.
     */
    private void checkRep() {
        if (ingredients == null) {
            throw new IllegalStateException("Representation invariant violated: ingredients list is null");
        }

        if (mealController == null) {
            throw new IllegalStateException("Representation invariant violated: mealController is null");
        }

        for (JCheckBox checkbox : ingredients) {
            if (checkbox == null) {
                throw new IllegalStateException("Representation invariant violated: ingredients contains null elements");
            }
        }
    }

    /**
     * Constructs a new GroceryListView with the given MealController.
     *
     * @param mealController the controller responsible for managing meal-related operations
     *
     * Requires:
     * - mealController is not null
     *
     * Effects:
     * - Initializes a new Grocery List view with default ingredients
     * - Sets up UI components for adding, removing, and generating recipes
     * - Configures event listeners for user interactions
     */
    public GroceryListView(MealController mealController){
        // Validate input
        if (mealController == null) {
            throw new IllegalArgumentException("MealController cannot be null");
        }

        // Initialize class fields
        this.mealController = mealController;
        this.ingredients = new ArrayList<>();

        // UI setup
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(LIGHT_GREEN);

        JLabel titleLabel = new JLabel("Pantry");
        styleTitleLabel(titleLabel);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        inputPanel.setMaximumSize(new Dimension(400, 50));
        inputPanel.setBackground(LIGHT_GREEN);

        this.checkboxPanel = new JPanel();
        checkboxPanel.setLayout(new BoxLayout(checkboxPanel, BoxLayout.Y_AXIS));
        checkboxPanel.setBackground(Color.WHITE);

        this.scrollPane = new JScrollPane(checkboxPanel);
        scrollPane.setPreferredSize(new Dimension(300, 200));
        scrollPane.setMaximumSize(new Dimension(400, 300));
        scrollPane.setBorder(BorderFactory.createLineBorder(DARKER_GREEN));

        this.recipeResults = new JTextArea();
        recipeResults.setEditable(false);
        recipeResults.setLineWrap(true);
        recipeResults.setWrapStyleWord(true);
        recipeResults.setBackground(Color.WHITE);
        recipeResults.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane recipeScrollPane = new JScrollPane(recipeResults);
        recipeScrollPane.setPreferredSize(new Dimension(300, 200));
        recipeScrollPane.setMaximumSize(new Dimension(400, 300));
        recipeScrollPane.setBorder(BorderFactory.createLineBorder(DARKER_GREEN));

        // Input setup
        this.itemInput = new JTextField(20);
        styleTextField(itemInput);

        JButton addButton = new JButton("Add Item");
        styleButton(addButton);

        ActionListener addItem = e -> {
            String newItem = itemInput.getText().trim();
            if (!newItem.isEmpty()) {
                JCheckBox checkBox = new JCheckBox((ingredients.size() + 1) + ". " + newItem);
                styleCheckBox(checkBox);
                ingredients.add(checkBox);
                checkboxPanel.add(checkBox);
                checkboxPanel.revalidate();
                checkboxPanel.repaint();
                itemInput.setText("");
            }
        };

        addButton.addActionListener(addItem);
        itemInput.addActionListener(addItem);

        JButton removeButton = new JButton("Remove Selected");
        JButton generateMealButton = new JButton("Generate Recipes");
        styleButton(removeButton);
        styleButton(generateMealButton);

        removeButton.addActionListener(e -> {
            ingredients.removeIf(AbstractButton::isSelected);
            checkboxPanel.removeAll();
            for (int i = 0; i < ingredients.size(); i++) {
                JCheckBox checkbox = ingredients.get(i);
                String itemName = checkbox.getText().substring(checkbox.getText().indexOf(".") + 2);
                checkbox.setText((i + 1) + ". " + itemName);
                checkboxPanel.add(checkbox);
            }
            checkboxPanel.revalidate();
            checkboxPanel.repaint();
        });

        generateMealButton.addActionListener(e -> {
            List<String> selectedIngredients = new ArrayList<>();
            for (JCheckBox checkbox : ingredients) {
                if (checkbox.isSelected()) {
                    String ingredient = checkbox.getText().substring(checkbox.getText().indexOf(".") + 2);
                    // Account for spacing in ingredient names
                    if (ingredient.contains(" ")){
                        ingredient = ingredient.replaceAll(" ", "%20");
                    }
                    selectedIngredients.add(ingredient);
                }
            }

            if (selectedIngredients.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Please select some ingredients first!",
                        "No Ingredients Selected",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            recipeResults.setText("Searching for recipes...");

            // Use a Timer to delay the API calls to ensure that the search text is displayed
            Timer timer = new Timer(200, event -> {
                try {
                    mealController.removeAllRecipies();
                    mealController.addRecipies(mealController.findRecipies(selectedIngredients));
                    // Display recipes in box below
                    StringBuilder resultText = new StringBuilder();
                    resultText.append("Generated ").append(mealController.getSavedRecipes().size())
                            .append(" recipes! Check the Meals page to view them.\n\n");
                    JOptionPane.showMessageDialog(null,
                              resultText.toString(),
                              "Information",
                              JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception ex) {
                    recipeResults.setText("Error fetching recipes: " + ex.getMessage());
                    ex.printStackTrace();
                    return;
                }

                // Display recipes in box below
                StringBuilder resultText = new StringBuilder();
                resultText.append("Recipes Generated:\n\n");

                for (Recipe recipe : mealController.getSavedRecipes()) {
                    resultText.append("- ").append(recipe.title()).append("\n");
                }

                recipeResults.setText(resultText.toString());
                recipeResults.setCaretPosition(0);
            });

            timer.setRepeats(false); // only runs timer once
            timer.start();
        });

        String[] defaultItems = {"Chicken", "Rice", "Carrots", "Onion"};
        for (String item : defaultItems) {
            JCheckBox checkBox = new JCheckBox((ingredients.size() + 1) + ". " + item);
            styleCheckBox(checkBox);
            ingredients.add(checkBox);
            checkboxPanel.add(checkBox);
        }

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBackground(LIGHT_GREEN);
        buttonPanel.add(removeButton);
        buttonPanel.add(generateMealButton);

        inputPanel.add(itemInput);
        inputPanel.add(addButton);

        add(titleLabel);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(inputPanel);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(scrollPane);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(buttonPanel);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(recipeScrollPane);
    }
}

