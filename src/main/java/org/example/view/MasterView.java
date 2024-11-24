package org.example.view;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.*;
import org.example.model.Recipe;
import static org.example.view.ViewUtility.*;
import static org.example.controller.MealController.*;
import java.util.List;

public class MasterView {
    private final JFrame frame;
    private final JPanel panel;
    String selectedGoal = null; //y is this null :c

    //Views
    ProfileView profileView = new ProfileView();
    MealView mealView = new MealView(selectedGoal);
    FitnessGoalView fitnessView = new FitnessGoalView(selectedGoal);
    GroceryListView groceryView = new GroceryListView();
  
    public MasterView(){
        // Set up the JFrame with custom styling
        frame = new JFrame("Meal Prep Assistant");
        frame.setSize(600, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(LIGHT_GREEN);
        frame.setLayout(new BorderLayout());

        // Initialize the main panel
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
            createButtonWithIcon("Meals", "/meal_options_icon.png")
        };

        // Add action listeners
        buttons[3].addActionListener(e -> switchView(new MealView(selectedGoal)));
        buttons[0].addActionListener(e -> switchView(profileView));
        buttons[1].addActionListener(e -> switchView(fitnessView));
        buttons[2].addActionListener(e -> switchView(groceryView));

        // Add buttons to control panel
        for (JButton button : buttons) {
            controlPanel.add(button);
        }

        frame.add(controlPanel, BorderLayout.NORTH);
        switchView(profileView);
        frame.setVisible(true);
    }

    //Helper method to switchViews within the masterView constructor
    private void switchView(JPanel newView) {
        panel.removeAll();
        panel.add(newView);
        panel.revalidate();
        panel.repaint();
    }

    // Helper method to create a button with an image icon
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