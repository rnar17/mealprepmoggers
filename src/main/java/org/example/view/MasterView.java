package org.example.view;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;
import org.example.model.SpoonacularClient;
import org.example.model.URLImageButton;

import java.util.Arrays;
import java.util.List;

public class MasterView {
    // Define theme colors
    private static final Color PASTEL_GREEN = new Color(183, 223, 177);
    private static final Color DARKER_GREEN = new Color(141, 196, 133);
    private static final Color LIGHT_GREEN = new Color(220, 237, 218);
    private static final Color ACCENT_GREEN = new Color(106, 168, 96);
    private static final Color TEXT_COLOR = new Color(58, 77, 57);

    private JFrame frame;
    private JPanel panel;
    private List<SpoonacularClient.Recipe> savedRecipes = new ArrayList<>();

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
            createButtonWithIcon("Home", "/meal_options_icon.png"),
            createButtonWithIcon("Profile", "/profile_icon.png"),
            createButtonWithIcon("Fitness Goal", "/fitness_goals_icon.png"),
            createButtonWithIcon("Grocery List", "/grocery_icon.png")
        };

        // Add action listeners
        buttons[0].addActionListener(e -> switchView(createMealView()));
        buttons[1].addActionListener(e -> switchView(createProfileView()));
        buttons[2].addActionListener(e -> switchView(createFitnessGoalView()));
        buttons[3].addActionListener(e -> switchView(createGroceryListView()));

        // Add buttons to control panel
        for (JButton button : buttons) {
            controlPanel.add(button);
        }

        frame.add(controlPanel, BorderLayout.NORTH);
        switchView(createMealView());
        frame.setVisible(true);
    }

    private JPanel createMealView() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(LIGHT_GREEN);

        JLabel titleLabel = new JLabel("Meal Options");
        styleTitleLabel(titleLabel);
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Add recipe buttons if there are saved recipes
        if (!savedRecipes.isEmpty()) {
            for (SpoonacularClient.Recipe recipe : savedRecipes) {
                String recipeURL = recipe.image;
                JButton recipeButton = URLImageButton.createImageButton(recipeURL, 100, 100, URLImageButton.FitMode.STRETCH);
                recipeButton.setAlignmentX(Component.CENTER_ALIGNMENT);

                recipeButton.addActionListener(e -> showRecipeDetails(recipe));

                panel.add(recipeButton);
                panel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        } else {
            JLabel noMealsLabel = new JLabel("No meals generated yet. Go to Grocery List to generate meals!");
            noMealsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            noMealsLabel.setForeground(TEXT_COLOR);
            panel.add(noMealsLabel);
        }

        return panel;
    }

    private void showRecipeDetails(SpoonacularClient.Recipe recipe) {
        JDialog dialog = new JDialog(frame, recipe.title, true);
        dialog.setSize(400, 500);
        dialog.setLocationRelativeTo(frame);

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

    private JPanel createProfileView() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(LIGHT_GREEN);

        JLabel titleLabel = new JLabel("Profile Overview");
        styleTitleLabel(titleLabel);

        // Create styled text fields
        JTextField[] fields = {
            new JTextField(20),  // name
            new JTextField(20),  // age
            new JTextField(20),  // weight
            new JTextField(20)   // height
        };

        for (JTextField field : fields) {
            styleTextField(field);
        }

        JButton saveButton = new JButton("Save");
        styleButton(saveButton);
        saveButton.setMaximumSize(new Dimension(200, 40));

        saveButton.addActionListener(e -> {
            try {

                String userName = fields[0].getText();
                int age = Integer.parseInt(fields[1].getText());
                int weight = Integer.parseInt(fields[2].getText());
                int height = Integer.parseInt(fields[3].getText());

                JOptionPane.showMessageDialog(frame,
                    "Profile saved successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame,
                    "Please enter valid numbers for weight and height",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        String[] labels = {"Name:", "Age:", "Weight (kg):", "Height (cm):"};

        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setForeground(TEXT_COLOR);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(label);
            panel.add(Box.createRigidArea(new Dimension(0, 5)));
            panel.add(fields[i]);
            panel.add(Box.createRigidArea(new Dimension(0, 15)));
        }

        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(saveButton);

        return panel;
    }

    private JPanel createFitnessGoalView() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(LIGHT_GREEN);

        JLabel titleLabel = new JLabel("Fitness Goals");
        styleTitleLabel(titleLabel);

        String[] goals = {
            "Weight Loss",
            "Muscle Gain",
            "Maintenance"
        };

        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 40)));

        for (String goal : goals) {
            JButton button = new JButton(goal);
            styleButton(button);
            button.setMaximumSize(new Dimension(250, 50));
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(button);
            panel.add(Box.createRigidArea(new Dimension(0, 20)));
        }

        return panel;
    }

    private JPanel createGroceryListView() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(LIGHT_GREEN);

        JLabel titleLabel = new JLabel("Grocery List");
        styleTitleLabel(titleLabel);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        inputPanel.setMaximumSize(new Dimension(400, 50));
        inputPanel.setBackground(LIGHT_GREEN);

        JTextField itemInput = new JTextField(20);
        styleTextField(itemInput);

        JButton addButton = new JButton("Add Item");
        styleButton(addButton);

        JPanel checkboxPanel = new JPanel();
        checkboxPanel.setLayout(new BoxLayout(checkboxPanel, BoxLayout.Y_AXIS));
        checkboxPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(checkboxPanel);
        scrollPane.setPreferredSize(new Dimension(300, 200));
        scrollPane.setMaximumSize(new Dimension(400, 300));
        scrollPane.setBorder(BorderFactory.createLineBorder(DARKER_GREEN));

        // Create a scroll pane for recipe results
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

        java.util.List<JCheckBox> checkBoxList = new ArrayList<>();

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
            checkBoxList.removeIf(checkbox -> checkbox.isSelected());
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
                    if (ingredient.contains(" ")){
                        ingredient = ingredient.replaceAll(" ", "%20");
                    }
                    selectedIngredients.add(ingredient);
                }
            }

            if (selectedIngredients.isEmpty()) {
                JOptionPane.showMessageDialog(panel,
                    "Please select some ingredients first!",
                    "No Ingredients Selected",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            recipeResults.setText("Searching for recipes...");

            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() {
                    try {
                        SpoonacularClient client = new SpoonacularClient(System.getenv("KEY"));
                        List<SpoonacularClient.Recipe> recipes = client.findRecipesByIngredients(selectedIngredients, 2, 1);

                        // Get full recipe information for each recipe
                        List<SpoonacularClient.Recipe> fullRecipes = new ArrayList<>();
                        for (SpoonacularClient.Recipe recipe : recipes) {
                            SpoonacularClient.Recipe fullRecipe = client.getRecipeById(recipe.id);
                            fullRecipe.usedIngredients = recipe.usedIngredients;
                            fullRecipe.missedIngredients = recipe.missedIngredients;
                            fullRecipes.add(fullRecipe);
                        }

                        // Update saved recipes and UI
                        SwingUtilities.invokeLater(() -> {
                            savedRecipes = fullRecipes;
                            StringBuilder resultText = new StringBuilder();
                            resultText.append("Generated ").append(recipes.size())
                                .append(" recipes! Check the Home page to view them.\n\n");

                            for (SpoonacularClient.Recipe recipe : fullRecipes) {
                                resultText.append("- ").append(recipe.title).append("\n");
                            }

                            recipeResults.setText(resultText.toString());
                            recipeResults.setCaretPosition(0);

                            // Show confirmation dialog
                            JOptionPane.showMessageDialog(frame,
                                "Recipes generated successfully! Go to Home page to view them.",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                        });

                    } catch (Exception ex) {
                        SwingUtilities.invokeLater(() -> {
                            recipeResults.setText("Error fetching recipes: " + ex.getMessage());
                            ex.printStackTrace();
                        });
                    }
                    return null;
                }
            };
            worker.execute();
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

        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(inputPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(scrollPane);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(buttonPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(recipeScrollPane);

        return panel;
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
            Image img = ImageIO.read(getClass().getResource(iconPath)); // This is the correct way

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

    //Helper methods to help style elements in views
    private void styleTitleLabel(JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 28));
        label.setForeground(TEXT_COLOR);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
    }

    private void styleTextField(JTextField field) {
        field.setMaximumSize(new Dimension(300, 35));
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBackground(Color.WHITE);
        field.setForeground(TEXT_COLOR);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(DARKER_GREEN),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
    }

    private void styleCheckBox(JCheckBox checkBox) {
        checkBox.setBackground(Color.WHITE);
        checkBox.setForeground(TEXT_COLOR);
        checkBox.setFont(new Font("Arial", Font.PLAIN, 14));
        checkBox.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        checkBox.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private void styleButton(JButton button) {
        button.setBackground(DARKER_GREEN);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setFont(new Font("Arial", Font.BOLD, 12));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(ACCENT_GREEN);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(DARKER_GREEN);
            }
        });
    }
}