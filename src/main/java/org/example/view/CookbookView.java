package org.example.view;

import org.example.controller.MealController;
import org.example.model.Recipe;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.example.view.ViewUtility.*;

public class CookbookView extends JPanel {
    MealController mealController;

    public CookbookView(MealController mealController){
        this.mealController = mealController;
        initializePanel(this);

        JLabel titleLabel = new JLabel("Cookbook");
        styleTitleLabel(titleLabel);

        JPanel recipePanel = new JPanel();
        recipePanel.setLayout(new BoxLayout(recipePanel, BoxLayout.Y_AXIS));
        recipePanel.setBackground(Color.WHITE);
        for(Recipe recipe: mealController.getFavouriteRecipes()){
            JButton recipeButton = new JButton(recipe.title());
            recipeButton.addActionListener(e -> {

            });
            recipePanel.add(recipeButton);
        }

        JScrollPane scrollPane = new JScrollPane(recipePanel);
        scrollPane.setPreferredSize(new Dimension(300, 200));
        scrollPane.setMaximumSize(new Dimension(400, 300));
        scrollPane.setBorder(BorderFactory.createLineBorder(DARKER_GREEN));

        //Add elements to panel
        add(titleLabel);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(scrollPane);

    }
}
