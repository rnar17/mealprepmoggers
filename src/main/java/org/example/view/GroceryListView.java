package org.example.view;

import org.example.controller.MealController;
import org.example.model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static org.example.controller.MealController.*;
import static org.example.view.ViewUtility.*;

public class GroceryListView extends JPanel {
    //private List<SpoonacularClient.Recipe> savedRecipes;
    //List<Recipe> savedRecipies= new ArrayList<>();

    public GroceryListView(MealController mealController){
        //this.savedRecipies = mealController.getSavedRecipes();

        //UI stuff
        /*
        Panel Background and formatting
         */
       initializePanel(new JPanel());

        JLabel titleLabel = new JLabel("Pantry");
        styleTitleLabel(titleLabel);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        inputPanel.setMaximumSize(new Dimension(400, 50));
        inputPanel.setBackground(LIGHT_GREEN);

        JPanel checkboxPanel = new JPanel();
        checkboxPanel.setLayout(new BoxLayout(checkboxPanel, BoxLayout.Y_AXIS));
        checkboxPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(checkboxPanel);
        scrollPane.setPreferredSize(new Dimension(300, 200));
        scrollPane.setMaximumSize(new Dimension(400, 300));
        scrollPane.setBorder(BorderFactory.createLineBorder(DARKER_GREEN));

        JTextArea recipeResults = new JTextArea();
        recipeResults.setEditable(false);
        recipeResults.setLineWrap(true);
        recipeResults.setWrapStyleWord(true);
        recipeResults.setBackground(Color.WHITE);
        recipeResults.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane recipeScrollPane = new JScrollPane(recipeResults);
        recipeScrollPane.setPreferredSize(new Dimension(300, 200));
        recipeScrollPane.setMaximumSize(new Dimension(400, 300));
        recipeScrollPane.setBorder(BorderFactory.createLineBorder(DARKER_GREEN));


        /*
        Pantry front facing UI to add items
         */
        java.util.List<JCheckBox> checkBoxList = new ArrayList<>();

        JTextField itemInput = new JTextField(20);
        styleTextField(itemInput);

        JButton addButton = new JButton("Add Item");
        styleButton(addButton);

        ActionListener addItem = e -> {
            String newItem = itemInput.getText().trim();
            if (!newItem.isEmpty()) {
                JCheckBox checkBox = new JCheckBox((checkBoxList.size() + 1) + ". " + newItem);
                styleCheckBox(checkBox);
                checkBoxList.add(checkBox);
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
            checkBoxList.removeIf(AbstractButton::isSelected);
            checkboxPanel.removeAll();
            for (int i = 0; i < checkBoxList.size(); i++) {
                JCheckBox checkbox = checkBoxList.get(i);
                String itemName = checkbox.getText().substring(checkbox.getText().indexOf(".") + 2);
                checkbox.setText((i + 1) + ". " + itemName);
                checkboxPanel.add(checkbox);
            }
            checkboxPanel.revalidate();
            checkboxPanel.repaint();
        });

        generateMealButton.addActionListener(e -> {
            List<String> selectedIngredients = new ArrayList<>();
            for (JCheckBox checkbox : checkBoxList) {
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
            JCheckBox checkBox = new JCheckBox((checkBoxList.size() + 1) + ". " + item);
            styleCheckBox(checkBox);
            checkBoxList.add(checkBox);
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
