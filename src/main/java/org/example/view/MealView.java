package org.example.view;

import org.example.model.SpoonacularClient;
import org.example.model.URLImageButton;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import static org.example.view.ViewUtility.*;

public class MealView extends JPanel {

    public MealView(List<SpoonacularClient.Recipe> savedRecipes){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(LIGHT_GREEN);

        JLabel titleLabel = new JLabel("Meals");
        styleTitleLabel(titleLabel);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(titleLabel);
        add(Box.createRigidArea(new Dimension(0, 20)));

        if (!savedRecipes.isEmpty()) {
            JPanel buttonPanel = new JPanel(new GridLayout(0, 2, 50, 50));
            buttonPanel.setBackground(LIGHT_GREEN);

            for (SpoonacularClient.Recipe recipe : savedRecipes) {
                // Create a panel for each recipe that will contain both button and label
                JPanel recipePanel = new JPanel();
                recipePanel.setLayout(new BoxLayout(recipePanel, BoxLayout.Y_AXIS));
                recipePanel.setBackground(LIGHT_GREEN);

                // Create and add the image button
                String recipeURL = recipe.image;
                JButton recipeButton = URLImageButton.createImageButton(recipeURL, 240, 135, URLImageButton.FitMode.STRETCH);
                recipeButton.addActionListener(e -> showRecipeDetails(recipe));
                recipeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                recipePanel.add(recipeButton);

                // Add some vertical spacing between button and label
                recipePanel.add(Box.createRigidArea(new Dimension(0, 5)));

                // Create and add the title label
                JLabel titleBox = new JLabel(SpoonacularClient.Recipe.cutTitle(recipe.title));
                titleBox.setAlignmentX(Component.CENTER_ALIGNMENT);
                titleBox.setForeground(TEXT_COLOR);
                recipePanel.add(titleBox);

                // Add the recipe panel to the button panel
                buttonPanel.add(recipePanel);
            }

            JPanel centeringPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            centeringPanel.setBackground(LIGHT_GREEN);
            centeringPanel.add(buttonPanel);
            add(centeringPanel);
        } else {
            JLabel noMealsLabel = new JLabel("No meals generated yet. Go to Grocery List to generate meals!");
            noMealsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            noMealsLabel.setForeground(TEXT_COLOR);
            add(noMealsLabel);
        }
    }

    private void showRecipeDetails(SpoonacularClient.Recipe recipe) {
        JDialog dialog = new JDialog();
        dialog.setSize(400, 500);
        dialog.setLocationRelativeTo(this);

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        detailsPanel.setBackground(LIGHT_GREEN);

        // Create recipe details
        JTextArea detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        detailsArea.setWrapStyleWord(true);
        detailsArea.setLineWrap(true);
        detailsArea.setBackground(LIGHT_GREEN);
        detailsArea.setFont(new Font("Arial", Font.PLAIN, 14));

        StringBuilder details = new StringBuilder();
        details.append("Recipe: ").append(recipe.title).append("\n\n");

        // Add calories if available
        if (recipe.nutrition != null && recipe.nutrition.nutrients != null) {
            double calories = recipe.nutrition.nutrients.stream()
                    .filter(n -> n.name.equals("Calories"))
                    .findFirst()
                    .map(n -> n.amount)
                    .orElse(0.0);
            details.append("Calories: ").append(calories).append("\n\n");
        }

        // Add source URL
        details.append("Source: ").append(recipe.sourceUrl).append("\n\n");

        // List used ingredients
        details.append("Used ingredients:\n");
        for (SpoonacularClient.Ingredient ingredient : recipe.usedIngredients) {
            details.append("- ").append(ingredient.original)
                    .append(" (").append(ingredient.amount)
                    .append(" ").append(ingredient.unit).append(")\n");
        }

        // List missing ingredients
        details.append("\nMissing ingredients:\n");
        for (SpoonacularClient.Ingredient ingredient : recipe.missedIngredients) {
            details.append("- ").append(ingredient.original)
                    .append(" (").append(ingredient.amount)
                    .append(" ").append(ingredient.unit).append(")\n");
        }

        detailsArea.setText(details.toString());

        JScrollPane scrollPane = new JScrollPane(detailsArea);
        scrollPane.setPreferredSize(new Dimension(350, 400));
        detailsPanel.add(scrollPane);

        JButton closeButton = new JButton("Close");
        styleButton(closeButton);
        closeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        closeButton.addActionListener(e -> dialog.dispose());

        detailsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        detailsPanel.add(closeButton);

        dialog.add(detailsPanel);
        dialog.setVisible(true);
    }
}
