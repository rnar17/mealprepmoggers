package org.example.view;
import com.google.gson.Gson;
import org.example.model.UserModel.Profile;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import org.example.model.SpoonacularClient;
import org.example.model.URLImageButton;
import javax.swing.JEditorPane;
import javax.swing.event.HyperlinkEvent;
import java.awt.Desktop;
import java.net.URI;

import java.util.Arrays;
import java.util.List;

public class MasterView {
    // Define theme colors
    private static final Color PASTEL_GREEN = new Color(183, 223, 177);
    private static final Color DARKER_GREEN = new Color(141, 196, 133);
    private static final Color LIGHT_GREEN = new Color(220, 237, 218);
    private static final Color ACCENT_GREEN = new Color(106, 168, 96);
    private static final Color TEXT_COLOR = new Color(58, 77, 57);

    private final JFrame frame;
    private final JPanel panel;
    private List<SpoonacularClient.Recipe> savedRecipes = new ArrayList<>();

    private String userName;
    private int userAge;
    private int userWeight;
    private int userHeight;
    private double maintenanceCalories;

    private String selectedGoal = null;
    private ButtonGroup goalButtonGroup;
  
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
        buttons[3].addActionListener(e -> switchView(createMealView()));
        buttons[0].addActionListener(e -> switchView(createProfileView()));
        buttons[1].addActionListener(e -> switchView(createFitnessGoalView()));
        buttons[2].addActionListener(e -> switchView(createGroceryListView()));

        // Add buttons to control panel
        for (JButton button : buttons) {
            controlPanel.add(button);
        }

        frame.add(controlPanel, BorderLayout.NORTH);
        switchView(createProfileView());
        frame.setVisible(true);
    }

    private JPanel createMealView() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(LIGHT_GREEN);

        String titleText = "Meals";
        if (selectedGoal != null) {
            titleText += " for " + selectedGoal;
        }
        JLabel titleLabel = new JLabel(titleText);
        styleTitleLabel(titleLabel);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

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
            mainPanel.add(centeringPanel);
        } else {
            JLabel noMealsLabel = new JLabel("No meals generated yet. Go to Grocery List to generate meals!");
            noMealsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            noMealsLabel.setForeground(TEXT_COLOR);
            mainPanel.add(noMealsLabel);
        }

        return mainPanel;
    }

    private void showRecipeDetails(SpoonacularClient.Recipe recipe) {
        JDialog dialog = new JDialog(frame, recipe.title, true);
        dialog.setSize(500, 600);
        dialog.setLocationRelativeTo(frame);

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
        details.append("<p>Recipe: ").append(recipe.title).append("</p>");

        // Add calories if available
        if (recipe.nutrition != null && recipe.nutrition.nutrients != null) {
            double calories = recipe.nutrition.nutrients.stream()
                .filter(n -> n.name.equals("Calories"))
                .findFirst()
                .map(n -> n.amount)
                .orElse(0.0);
            details.append("<p>Calories: ").append(calories).append("</p>");
        }

        // Add clickable source URL
        details.append("<p><b>Source: </b><a href='").append(recipe.sourceUrl).append("'>")
            .append("Link to Recipe").append("</a></p>");

        // List used ingredients
        details.append("<p>Used ingredients:</p><ul>");
        for (SpoonacularClient.Ingredient ingredient : recipe.usedIngredients) {
            details.append("<li>").append(ingredient.original)
                .append(" (").append(ingredient.amount)
                .append(" ").append(ingredient.unit).append(")</li>");
        }
        details.append("</ul>");

        // List missing ingredients
        details.append("<p>Missing ingredients:</p><ul>");
        for (SpoonacularClient.Ingredient ingredient : recipe.missedIngredients) {
            details.append("<li>").append(ingredient.original)
                .append(" (").append(ingredient.amount)
                .append(" ").append(ingredient.unit).append(")</li>");
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

        // Set existing values if they exist
        if (userName != null) fields[0].setText(userName);
        if (userAge > 0) fields[1].setText(String.valueOf(userAge));
        if (userWeight > 0) fields[2].setText(String.valueOf(userWeight));
        if (userHeight > 0) fields[3].setText(String.valueOf(userHeight));

        for (JTextField field : fields) {
            styleTextField(field);
        }

        // Create a label for displaying maintenance calories
        JLabel caloriesLabel = new JLabel();
        caloriesLabel.setForeground(TEXT_COLOR);
        caloriesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        if (maintenanceCalories > 0) {
            caloriesLabel.setText(String.format("Maintenance Calories: %.0f kcal/day", maintenanceCalories));
        }

        JButton saveButton = new JButton("Save");
        styleButton(saveButton);
        saveButton.setMaximumSize(new Dimension(200, 40));

        saveButton.addActionListener(e -> {
            try {
                // Save the values
                String userName = fields[0].getText();
                int userAge = Integer.parseInt(fields[1].getText());
                int userWeight = Integer.parseInt(fields[2].getText());
                int userHeight = Integer.parseInt(fields[3].getText());

                // Calculate maintenance calories using the formula:
                // ((10 × weight in kg) + (6.25 × height in cm) - (5 × age in years)) * 1.3
                maintenanceCalories = ((10 * userWeight) + (6.25 * userHeight) - (5 * userAge)) * 1.37;

                // Update the calories label
                caloriesLabel.setText(String.format("Maintenance Calories: %.0f kcal/day", maintenanceCalories));
              
               //update profile json file
                Gson gson = new Gson();
                String profilePath = "src/main/User/Profile.json";
                try (FileWriter writer = new FileWriter(profilePath)) {
                    gson.toJson(new Profile(userName,userAge,userWeight, userHeight), writer);
                } catch (IOException d) {
                    d.printStackTrace();
                }

                JOptionPane.showMessageDialog(frame,
                        String.format("Profile saved successfully!\nYour maintenance calories: %.0f kcal/day", maintenanceCalories),
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame,
                        "Please enter valid numbers for age, weight, and height",
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

        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(caloriesLabel);

        return panel;
    }

    private JPanel createFitnessGoalView() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(LIGHT_GREEN);

        JLabel titleLabel = new JLabel("Fitness Goals");
        styleTitleLabel(titleLabel);
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Create panel for radio buttons
        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.Y_AXIS));
        radioPanel.setBackground(LIGHT_GREEN);
        radioPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        goalButtonGroup = new ButtonGroup();

        String[] goals = {
                "Weight Loss",
                "Muscle Gain",
                "Maintenance"
        };

        // Create and style radio buttons
        for (String goal : goals) {
            JRadioButton radioButton = new JRadioButton(goal);
            styleRadioButton(radioButton);

            // Set selected if this is the current goal
            if (goal.equals(selectedGoal)) {
                radioButton.setSelected(true);
            }

            // Add action listener
            radioButton.addActionListener(e -> {
                selectedGoal = goal;
                // Update all radio buttons in the group to maintain visual consistency
                for (Enumeration<AbstractButton> buttons = goalButtonGroup.getElements(); buttons.hasMoreElements();) {
                    JRadioButton btn = (JRadioButton) buttons.nextElement();
                    btn.setForeground(Color.WHITE);
                }
                JOptionPane.showMessageDialog(frame,
                        "Fitness goal set to: " + goal + "\nMeals page will reflect your new goal.",
                        "Goal Updated",
                        JOptionPane.INFORMATION_MESSAGE);
            });

            goalButtonGroup.add(radioButton);

            // Create a wrapper panel for spacing
            JPanel buttonWrapper = new JPanel();
            buttonWrapper.setLayout(new FlowLayout(FlowLayout.CENTER));
            buttonWrapper.setBackground(LIGHT_GREEN);
            buttonWrapper.add(radioButton);

            radioPanel.add(buttonWrapper);
            radioPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        panel.add(radioPanel);

        // Add goal descriptions (rest of the code remains the same)
        Map<String, String> goalDescriptions = new HashMap<>();
        goalDescriptions.put("Weight Loss", "• Focus on lower-calorie meals\n• Higher protein content\n• More vegetables and fiber");
        goalDescriptions.put("Muscle Gain", "• Higher protein meals\n• Complex carbohydrates\n• Nutrient-dense ingredients");
        goalDescriptions.put("Maintenance", "• Balanced macronutrients\n• Sustainable portion sizes\n• Variety of nutrients");

        JTextArea descriptionArea = new JTextArea();
        descriptionArea.setEditable(false);
        descriptionArea.setBackground(LIGHT_GREEN);
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionArea.setForeground(TEXT_COLOR);
        descriptionArea.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        descriptionArea.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Update description when radio buttons are clicked
        for (Enumeration<AbstractButton> buttons = goalButtonGroup.getElements(); buttons.hasMoreElements();) {
            JRadioButton button = (JRadioButton) buttons.nextElement();
            button.addActionListener(e -> {
                String goal = button.getText();
                if (goalDescriptions.containsKey(goal)) {
                    descriptionArea.setText(goalDescriptions.get(goal));
                }
            });
        }

        // Set initial description
        if (selectedGoal != null && goalDescriptions.containsKey(selectedGoal)) {
            descriptionArea.setText(goalDescriptions.get(selectedGoal));
        } else {
            descriptionArea.setText("Select a fitness goal to see its description.");
        }

        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(descriptionArea);

        return panel;
    }

    private void styleRadioButton(JRadioButton radioButton) {
        // Set up the basic look
        radioButton.setFont(new Font("Arial", Font.BOLD, 16));
        radioButton.setForeground(Color.WHITE);
        radioButton.setBackground(new Color(141, 196, 133)); // DARKER_GREEN
        radioButton.setFocusPainted(false);
        radioButton.setBorderPainted(false);
        radioButton.setPreferredSize(new Dimension(300, 50));
        radioButton.setMaximumSize(new Dimension(300, 50));
        radioButton.setOpaque(true);

        // Center the text
        radioButton.setHorizontalAlignment(SwingConstants.CENTER);

        // Add hover effect
        radioButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                radioButton.setBackground(new Color(106, 168, 96)); // ACCENT_GREEN
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                radioButton.setBackground(new Color(141, 196, 133)); // DARKER_GREEN
            }
        });
    }

    private JPanel createGroceryListView() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(LIGHT_GREEN);

        JLabel titleLabel = new JLabel("Pantry");
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
                        List<SpoonacularClient.Recipe> recipes = client.findRecipesByIngredients(selectedIngredients, 2, 6);

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
                                "Recipes generated successfully! Go to Meals page to view them.",
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