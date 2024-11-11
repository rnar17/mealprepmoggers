package org.example.view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class MasterView {
    private JFrame frame;
    private JPanel panel;
    String userName;
    String age;
    int weight;
    int height;

    public MasterView(){
        // Set up the JFrame
        frame = new JFrame("Main Prep App");
        frame.setSize(600, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Use BorderLayout for the main frame
        frame.setLayout(new BorderLayout());

        // Initialize the main panel where views will be displayed
        panel = new JPanel();
        frame.add(panel, BorderLayout.CENTER);

        // Set up the control panel with buttons to switch views
        JPanel controlPanel = new JPanel();
        JButton homeButton = new JButton("Home");
        JButton settingsButton = new JButton("Settings");
        JButton profileButton = new JButton("Profile");
        JButton fitnessGoalButton = new JButton("FitnessGoal");
        JButton groceryListButton = new JButton("GroceryList");

        // Add action listeners to buttons to switch views
        homeButton.addActionListener(e -> switchView(createMealView()));
        settingsButton.addActionListener(e -> switchView(createSettingsView()));
        profileButton.addActionListener(e -> switchView(createProfileView()));
        fitnessGoalButton.addActionListener(e -> switchView(createFitnessGoalView()));
        groceryListButton.addActionListener((e -> switchView(createGroceryListView())));

        // Add buttons to the control panel
        controlPanel.add(homeButton);
        controlPanel.add(settingsButton);
        controlPanel.add(profileButton);
        controlPanel.add(fitnessGoalButton);
        controlPanel.add(groceryListButton);

        // Add the control panel to the top of the frame
        frame.add(controlPanel, BorderLayout.NORTH);

        // Set the initial view to the Home view
        switchView(createMealView());

        // Show the frame
        frame.setVisible(true);
    }

    private void switchView(JPanel newView) {
        panel.removeAll();            // Remove the current view
        panel.add(newView);            // Add the new view
        panel.revalidate();            // Refresh the panel
        panel.repaint();               // Repaint the panel
    }


//    private JPanel createMealPanel(String mealName, String imagePath) {
//        JPanel mealPanel = new JPanel();
//        mealPanel.setLayout(new BorderLayout());
//
//        // Meal image
//        JLabel mealImage = new JLabel();
//        mealImage.setIcon(new ImageIcon(imagePath)); // Load image from path
//        mealImage.setHorizontalAlignment(SwingConstants.CENTER);
//
//        // Meal name label
//        JLabel mealLabel = new JLabel(mealName);
//        mealLabel.setHorizontalAlignment(SwingConstants.CENTER);
//
//        // Add image and label to meal panel
//        mealPanel.add(mealImage, BorderLayout.CENTER);
//        mealPanel.add(mealLabel, BorderLayout.SOUTH);
//
//        return mealPanel;
//    }

    private JPanel createMealView() {
        // Create main panel with padding and vertical layout
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));


        // Create title label
        JLabel titleLabel = new JLabel("Meal Options");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Create meal buttons
        JButton spaghettiButton = new JButton("Spaghetti Bolognese");
        JButton saladButton = new JButton("Grilled Chicken Salad");
        JButton stirFryButton = new JButton("Veggie Stir-Fry");
        JButton tacosButton = new JButton("Beef Tacos");

        // Set button properties
        Dimension buttonSize = new Dimension(200, 50);
        spaghettiButton.setPreferredSize(buttonSize);
        saladButton.setPreferredSize(buttonSize);
        stirFryButton.setPreferredSize(buttonSize);
        tacosButton.setPreferredSize(buttonSize);

        spaghettiButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saladButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        stirFryButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        tacosButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add components to panel with spacing
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 40)));
        panel.add(spaghettiButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(saladButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(stirFryButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(tacosButton);

        return panel;
    }

    // Settings view
    private JPanel createSettingsView() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("This is your Setting View."));
        return panel;
    }

    // Profile view
    private JPanel createProfileView() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create title
        JLabel titleLabel = new JLabel("Profile Overview");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Initialize fields with better styling
        JTextField nameField = new JTextField(20);
        JTextField ageField = new JTextField(20);
        JTextField weightField = new JTextField(20);
        JTextField heightField = new JTextField(20);
        JButton saveButton = new JButton("Save");

        // Set maximum size for text fields
        Dimension maxSize = new Dimension(300, 30);
        nameField.setMaximumSize(maxSize);
        ageField.setMaximumSize(maxSize);
        weightField.setMaximumSize(maxSize);
        heightField.setMaximumSize(maxSize);

        // Add save button functionality
        saveButton.addActionListener(e -> {
            try {
                userName = nameField.getText();
                age = ageField.getText();
                weight = Integer.parseInt(weightField.getText());
                height = Integer.parseInt(heightField.getText());

                // Show success message
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

        // Helper method to add form fields
        Action addFormField = (label, field) -> {
            JLabel jLabel = new JLabel(label);
            jLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(jLabel);
            panel.add(Box.createRigidArea(new Dimension(0, 5)));
            field.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(field);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
        };

        // Add components with proper spacing
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        addFormField.execute("Name:", nameField);
        addFormField.execute("Age:", ageField);
        addFormField.execute("Weight (kg):", weightField);
        addFormField.execute("Height (cm):", heightField);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(saveButton);

        return panel;
    }

    // Interface for the addFormField action
    interface Action {
        void execute(String labelText, JTextField field);
    }

    private JPanel createFitnessGoalView() {
        // Create a main panel with some padding
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create title label
        JLabel titleLabel = new JLabel("Fitness Goals");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Create buttons
        JButton weightLossButton = new JButton("Weight Loss");
        JButton muscleGainButton = new JButton("Muscle Gain");
        JButton maintenanceButton = new JButton("Maintenance");

        // Set button properties
        Dimension buttonSize = new Dimension(200, 50);

        weightLossButton.setPreferredSize(buttonSize);
        muscleGainButton.setPreferredSize(buttonSize);
        maintenanceButton.setPreferredSize(buttonSize);

        weightLossButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        muscleGainButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        maintenanceButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add components to panel with spacing
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 40)));
        panel.add(weightLossButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(muscleGainButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(maintenanceButton);

        // Add panel to frame
        return panel;
    }

    private JPanel createGroceryListView() {
        // Create main panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create title label
        JLabel titleLabel = new JLabel("Grocery List");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Create input panel for new items
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        inputPanel.setMaximumSize(new Dimension(400, 50));

        JTextField itemInput = new JTextField(20);
        JButton addButton = new JButton("Add Item");

        // Create panel to hold the checkboxes
        JPanel checkboxPanel = new JPanel();
        checkboxPanel.setLayout(new BoxLayout(checkboxPanel, BoxLayout.Y_AXIS));

        // Create scroll pane for checkbox panel
        JScrollPane scrollPane = new JScrollPane(checkboxPanel);
        scrollPane.setPreferredSize(new Dimension(300, 200));
        scrollPane.setMaximumSize(new Dimension(400, 300));

        // List to store checkboxes
        java.util.List<JCheckBox> checkBoxList = new ArrayList<>();

        // Function to add a new checkbox item
        ActionListener addItem = e -> {
            String newItem = itemInput.getText().trim();
            if (!newItem.isEmpty()) {
                int nextNum = checkBoxList.size() + 1;
                JCheckBox checkBox = new JCheckBox(nextNum + ". " + newItem);
                checkBox.setAlignmentX(Component.LEFT_ALIGNMENT);
                checkBoxList.add(checkBox);
                checkboxPanel.add(checkBox);
                checkboxPanel.revalidate();
                checkboxPanel.repaint();
                itemInput.setText("");  // Clear input field
            }
        };

        // Add action listeners
        addButton.addActionListener(addItem);
        itemInput.addActionListener(addItem); // Allow adding by pressing enter

        // Create remove button
        JButton removeButton = new JButton("Remove Selected");
        removeButton.addActionListener(e -> {
            checkBoxList.removeIf(checkbox -> checkbox.isSelected());
            checkboxPanel.removeAll();
            // Renumber and re-add remaining items
            for (int i = 0; i < checkBoxList.size(); i++) {
                JCheckBox checkbox = checkBoxList.get(i);
                String itemName = checkbox.getText().substring(checkbox.getText().indexOf(".") + 2);
                checkbox.setText((i + 1) + ". " + itemName);
                checkboxPanel.add(checkbox);
            }
            checkboxPanel.revalidate();
            checkboxPanel.repaint();
        });

        // Create generate meal button
        JButton generateMealButton = new JButton("Generate Meal");
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
                // Add some simple meal suggestions based on ingredients
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

        // Add default items
        String[] defaultItems = {"Milk", "Potatoes", "Salt and Pepper", "Soy Sauce"};
        for (String item : defaultItems) {
            int nextNum = checkBoxList.size() + 1;
            JCheckBox checkBox = new JCheckBox(nextNum + ". " + item);
            checkBox.setAlignmentX(Component.LEFT_ALIGNMENT);
            checkBoxList.add(checkBox);
            checkboxPanel.add(checkBox);
        }

        // Create button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(removeButton);
        buttonPanel.add(generateMealButton);

        // Add components to input panel
        inputPanel.add(itemInput);
        inputPanel.add(addButton);

        // Add all components to main panel
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(inputPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(scrollPane);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(buttonPanel);

        return panel;
    }

}
