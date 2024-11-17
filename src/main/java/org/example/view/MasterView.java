package org.example.view;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;

public class MasterView {
    // Define theme colors
    private static final Color PASTEL_GREEN = new Color(183, 223, 177);
    private static final Color DARKER_GREEN = new Color(141, 196, 133);
    private static final Color LIGHT_GREEN = new Color(220, 237, 218);
    private static final Color ACCENT_GREEN = new Color(106, 168, 96);
    private static final Color TEXT_COLOR = new Color(58, 77, 57);

    private JFrame frame;
    private JPanel panel;

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

        String[] mealOptions = {
            "Spaghetti Bolognese",
            "Grilled Chicken Salad",
            "Veggie Stir-Fry",
            "Beef Tacos"
        };

        JButton[] mealButtons = new JButton[mealOptions.length];
        for (int i = 0; i < mealOptions.length; i++) {
            mealButtons[i] = new JButton(mealOptions[i]);
            styleButton(mealButtons[i]);
            mealButtons[i].setAlignmentX(Component.CENTER_ALIGNMENT);
            mealButtons[i].setMaximumSize(new Dimension(250, 50));
        }

        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 40)));

        for (JButton button : mealButtons) {
            panel.add(button);
            panel.add(Box.createRigidArea(new Dimension(0, 20)));
        }

        return panel;
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
        JButton generateMealButton = new JButton("Generate Meal");
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
            StringBuilder selectedItems = new StringBuilder("Selected ingredients:\n");
            boolean hasSelected = false;

            for (JCheckBox checkbox : checkBoxList) {
                if (checkbox.isSelected()) {
                    selectedItems.append(checkbox.getText()).append("\n");
                    hasSelected = true;
                }
            }

            if (hasSelected) {
                selectedItems.append("\nSuggested meal: ");
                if (checkBoxList.stream().anyMatch(cb -> cb.isSelected() &&
                    cb.getText().toLowerCase().contains("potato"))) {
                    selectedItems.append("Mashed Potatoes");
                } else {
                    selectedItems.append("Simple Stir Fry");
                }

                JOptionPane.showMessageDialog(panel, selectedItems.toString(),
                    "Meal Suggestion", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(panel, "Please select some ingredients first!",
                    "No Ingredients Selected", JOptionPane.WARNING_MESSAGE);
            }
        });

        String[] defaultItems = {"Milk", "Potatoes", "Salt and Pepper", "Soy Sauce"};
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