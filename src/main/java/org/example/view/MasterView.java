package org.example.view;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.*;

import org.example.controller.MealController;
import org.example.controller.ProfileController;
import static org.example.view.ViewUtility.*;

/**
 * This class manages the main application window and navigation between different view classes
 * for our meal prepping application.
 * <p>
 *   Representation Invariant:
 *  1. frame is not null
 *  2. panel is not null
 *  3. profileView is not null
 *  4. mealView is not null
 *  5. fitnessView is not null
 *  6. groceryView is not null
 *  7. frame size must be (720, 1080)
 * <p>
 *
 * Abstraction Function:
 * AF(r) = A meal planning application window where:
 *  1. r.frame = the main application window
 *  2. r.panel = the current active view container
 *  3. r.selectedGoal = the user's selected fitness goal
 *  4. r.profileView = the user profile view that shows the user's statistics
 *  5. r.mealView = the meal view that shows all generated meals
 *  6. r.fitnessView = the fitness goal view that shows the user's fitness goals
 *  7. r.groceryView = the pantry view that shows the user's own ingredients
 *
 */
public class MasterView {
    private final JFrame frame;
    private final JPanel panel;
    String selectedGoal = null;

    //Controllers
    ProfileController profileController = new ProfileController("src/main/User/Profile.json");
    MealController mealController = new MealController();

    //Views
    ProfileView profileView = new ProfileView(profileController);
    MealView mealView = new MealView(profileController, mealController);
    FitnessGoalView fitnessView = new FitnessGoalView(profileController);
    GroceryListView groceryView = new GroceryListView(mealController);
    CookbookView cookbookView = new CookbookView(mealController);

    /**
     * A helper method that checks the state of the presentation invariant.
     */
    private void checkRep() {
        if (frame == null) {
            throw new IllegalStateException("Representation invariant violated: frame is null");
        }

        if (panel == null) {
            throw new IllegalStateException("Representation invariant violated: panel is null");
        }

        if (profileView == null) {
            throw new IllegalStateException("Representation invariant violated: profileView is null");
        }

        if (mealView == null) {
            throw new IllegalStateException("Representation invariant violated: mealView is null");
        }

        if (fitnessView == null) {
            throw new IllegalStateException("Representation invariant violated: fitnessView is null");
        }

        if (groceryView == null) {
            throw new IllegalStateException("Representation invariant violated: groceryView is null");
        }

        Dimension frameSize = frame.getSize();
        if (frameSize.width != 720 || frameSize.height != 1080) {
            throw new IllegalStateException(String.format(
                "Representation invariant violated: frame size must be (720, 1080), but was (%d, %d)",
                frameSize.width, frameSize.height
            ));
        }
    }

    /**
     * Creates the main application window for the Meal Prep Assistant.
     */
    public MasterView(){
        frame = new JFrame("Meal Prep Assistant");
        frame.setSize(720, 1080);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(LIGHT_GREEN);
        frame.setLayout(new BorderLayout());

        panel = new JPanel();
        panel.setBackground(LIGHT_GREEN);
        frame.add(panel, BorderLayout.CENTER);

        // Style the control panel
        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(PASTEL_GREEN);
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create and style navigation buttons
        JButton[] buttons = {
            createButtonWithIcon("Profile", "/profile_icon.png"),
            createButtonWithIcon("Fitness Goal", "/fitness_goals_icon.png"),
            createButtonWithIcon("Pantry", "/grocery_icon.png"),
            createButtonWithIcon("Meals", "/meal_options_icon.png"),
            createButtonWithIcon("Cookbook","/cookbook_icon.png")
        };
      
        // Add action listeners to change views whenever the buttons at the top are pressed
        buttons[3].addActionListener(e -> switchView(new MealView(profileController, mealController)));
        buttons[0].addActionListener(e -> switchView(profileView));
        buttons[1].addActionListener(e -> switchView(fitnessView));
        buttons[2].addActionListener(e -> switchView(groceryView));
        buttons[4].addActionListener(e -> switchView(new CookbookView(mealController)));

        for (JButton button : buttons) {
            controlPanel.add(button);
        }

        frame.add(controlPanel, BorderLayout.NORTH);
        switchView(profileView);
        frame.setVisible(true);
    }

    /**
     * Helper method to switch the current view in the main panel to a new view.
     *
     * @param newView the new view to be switched to
     */
    private void switchView(JPanel newView) {
        panel.removeAll();
        panel.add(newView);
        panel.revalidate();
        panel.repaint();
    }

    /**
     * Helper method to create a navigation button with a specified icon and text.
     *
     * @param text the text to be placed on the button
     * @param iconPath the path to the icon to be placed on the button
     * @return a styled JButton, with the specified button and icon
     */
    private JButton createButtonWithIcon(String text, String iconPath) {
        JButton button = new JButton(text);
        try {
            // Load image using class loader and resource path
            Image img = ImageIO.read(Objects.requireNonNull(getClass().getResource(iconPath))); // This is the correct way

            if (img == null) {
                System.out.println("Error: Image not found at path: " + iconPath);
                return button; // return the button without an icon if image not found
            }

            // Scale the image to fit the button (optional)
            ImageIcon icon = new ImageIcon(img.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
            button.setIcon(icon);

        } catch (IOException ex) {
            System.out.println("Error loading image: " + ex);
        }
        styleButton(button);
        return button;
    }
}