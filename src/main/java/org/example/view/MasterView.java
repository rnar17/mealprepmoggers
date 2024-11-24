package org.example.view;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;
import org.example.model.SpoonacularClient;
import static org.example.view.ViewUtility.*;
import java.util.List;

public class MasterView {
    private final JFrame frame;
    private final JPanel panel;
    private List<SpoonacularClient.Recipe> savedRecipes = new ArrayList<>();

    //Views
    ProfileView profileView = new ProfileView();
    MealView mealView = new MealView(savedRecipes);
  
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
        buttons[3].addActionListener(e -> switchView(mealView));
        buttons[0].addActionListener(e -> switchView(profileView));
        buttons[1].addActionListener(e -> switchView(createFitnessGoalView()));
        buttons[2].addActionListener(e -> switchView(createGroceryListView()));

        // Add buttons to control panel
        for (JButton button : buttons) {
            controlPanel.add(button);
        }

        frame.add(controlPanel, BorderLayout.NORTH);
        switchView(profileView);
        frame.setVisible(true);
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