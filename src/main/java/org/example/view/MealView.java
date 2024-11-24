package org.example.view;

import org.example.model.*;
import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.example.controller.MealController.*;
import static org.example.view.ViewUtility.*;


public class MealView extends JPanel {
    static int DEBUG = 1;
    public MealView(String selectedGoal){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(LIGHT_GREEN);

        String titleText = "Meals";
        if (selectedGoal != null) {
            titleText += " for " + selectedGoal;
        }
        JLabel titleLabel = new JLabel(titleText);

        styleTitleLabel(titleLabel);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(titleLabel);
        add(Box.createRigidArea(new Dimension(0, 20)));

        if (!getSavedRecipes().isEmpty()) {
            // Sort recipes based on fitness goal
            List<Recipe> sortedRecipes = new ArrayList<>(getSavedRecipes());
            sortedRecipes.sort((r1, r2) -> {
                double calories1 = getCalories(r1);
                double calories2 = getCalories(r2);
                if (selectedGoal != null) {
                    switch (selectedGoal) {
                        case "Weight Loss":
                            return Double.compare(calories1, calories2); // Lower calories first
                        case "Muscle Gain":
                            return Double.compare(calories2, calories1); // Higher calories first
                        default:
                            return 0; // No sorting for maintenance
                    }
                }
                return 0;
            });

            JPanel buttonPanel = new JPanel(new GridLayout(0, 2, 50, 50));
            buttonPanel.setBackground(LIGHT_GREEN);

            for (Recipe recipe : sortedRecipes) {
                if(DEBUG == 1){
                    Logger logger = Logger.getLogger(MealView.class.getName());
                    logger.info(String.valueOf(recipe));
                }
                // Create a panel for each recipe that will contain both button and label
                JPanel recipePanel = new JPanel();
                recipePanel.setLayout(new BoxLayout(recipePanel, BoxLayout.Y_AXIS));
                recipePanel.setBackground(LIGHT_GREEN);

                // Create and add the image button
                String recipeURL = recipe.image();
                JButton recipeButton = URLImageButton.createImageButton(recipeURL, 240, 135, URLImageButton.FitMode.STRETCH);
                recipeButton.addActionListener(e -> showRecipeDetails(recipe));
                recipeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                recipePanel.add(recipeButton);

                // Add some vertical spacing between button and label
                recipePanel.add(Box.createRigidArea(new Dimension(0, 5)));

                // Create and add the title label with calories
                String titleWithCalories = String.format("%s (%.0f cal)",
                        Recipe.cutTitle(recipe.title()),
                        getCalories(recipe));
                JLabel titleBox = new JLabel(titleWithCalories);
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

    private void showRecipeDetails(Recipe recipe) {
        JDialog dialog = new JDialog();
        dialog.setSize(500, 600);
        dialog.setLocationRelativeTo(this);

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        detailsPanel.setBackground(LIGHT_GREEN);

        // Create recipe details using JEditorPane
        JEditorPane detailsArea = new JEditorPane();
        detailsArea.setContentType("text/html");
        detailsArea.setEditable(false);
        detailsArea.setBackground(LIGHT_GREEN);
        detailsArea.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);

        // Build HTML content
        StringBuilder details = new StringBuilder();
        details.append("<html><body style='font-family: Arial; font-size: 10px; background-color: rgb(220, 237, 218);'>");
        details.append("<p>Recipe: ").append(recipe.title()).append("</p>");

        // Add calories if available
        if (recipe.nutrition() != null && recipe.nutrition().nutrients() != null) {
            double calories = recipe.nutrition().nutrients().stream()
                    .filter(n -> n.name().equals("Calories"))
                    .findFirst()
                    .map(n -> n.amount())
                    .orElse(0.0);
            details.append("<p>Calories: ").append(calories).append("</p>");
        }

        // Add clickable source URL
        details.append("<p><b>Source: </b><a href='").append(recipe.sourceUrl()).append("'>")
                .append("Link to Recipe").append("</a></p>");

        // List used ingredients
        details.append("<p>Used ingredients:</p><ul>");
        for (Ingredient ingredient : recipe.usedIngredients()) {
            details.append("<li>").append(ingredient.original())
                    .append(" (").append(ingredient.amount())
                    .append(" ").append(ingredient.unit()).append(")</li>");
        }
        details.append("</ul>");

        // List missing ingredients
        details.append("<p>Missing ingredients:</p><ul>");
        for (Ingredient ingredient : recipe.missedIngredients()) {
            details.append("<li>").append(ingredient.original())
                    .append(" (").append(ingredient.amount())
                    .append(" ").append(ingredient.unit()).append(")</li>");
        }
        details.append("</ul></body></html>");

        detailsArea.setText(details.toString());

        // Add hyperlink listener
        detailsArea.addHyperlinkListener(e -> {
            if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                try {
                    Desktop.getDesktop().browse(new URI(e.getURL().toString()));
                } catch (IOException | URISyntaxException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(dialog,
                            "Error opening link: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        JScrollPane scrollPane = new JScrollPane(detailsArea);
        scrollPane.setPreferredSize(new Dimension(500, 700));
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

    // Helper method to get calories from a recipe
    private double getCalories(Recipe recipe) {
        if (recipe.nutrition() != null && recipe.nutrition().nutrients() != null) {
            return recipe.nutrition().nutrients().stream()
                    .filter(n -> n.name().equals("Calories"))
                    .findFirst()
                    .map(n -> n.amount())
                    .orElse(0.0);
        }
        return 0.0;
    }

}
