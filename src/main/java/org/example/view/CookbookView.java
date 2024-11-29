package org.example.view;

import org.example.controller.MealController;
import org.example.model.Ingredient;
import org.example.model.Recipe;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.example.view.ViewUtility.*;

public class CookbookView extends JPanel {
    MealController mealController;
    JPanel recipePanel = new JPanel();

    public CookbookView(MealController mealController){
        this.mealController = mealController;
        initializePanel(this);

        JLabel titleLabel = new JLabel("Cookbook");
        styleTitleLabel(titleLabel);

        recipePanel.setLayout(new BoxLayout(recipePanel, BoxLayout.Y_AXIS));
        recipePanel.setBackground(Color.WHITE);
        updateFavourites();

        JScrollPane scrollPane = new JScrollPane(recipePanel);
        scrollPane.setPreferredSize(new Dimension(300, 200));
        scrollPane.setMaximumSize(new Dimension(400, 300));
        scrollPane.setBorder(BorderFactory.createLineBorder(DARKER_GREEN));

        //Add elements to panel
        add(titleLabel);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(scrollPane);

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

        JButton removeRecipeButton = new JButton("Remove Recipe");
        styleButton(removeRecipeButton);
        removeRecipeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        removeRecipeButton.addActionListener(e -> {
            mealController.removeStarRecipe(recipe);
            updateFavourites();
        });

        detailsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        detailsPanel.add(closeButton);
        detailsPanel.add(removeRecipeButton);

        dialog.add(detailsPanel);
        dialog.setVisible(true);
    }

    private void updateFavourites(){
        recipePanel.removeAll();

        for(Recipe recipe: mealController.getFavouriteRecipes()){
            JButton recipeButton = new JButton(Recipe.cutTitle(recipe.title()));
            styleButton(recipeButton);
            recipeButton.setBorder(BorderFactory.createEmptyBorder(8, 80, 8, 80));
            recipeButton.setPreferredSize(new Dimension(250,15));
            recipeButton.addActionListener(e -> {
                showRecipeDetails(recipe);
            });
            recipePanel.add(recipeButton);
            recipeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        }

        this.revalidate();
        this.repaint();
    }
}
