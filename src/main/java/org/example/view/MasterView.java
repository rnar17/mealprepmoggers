package org.example.view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MasterView {
    private JFrame frame;
    private JPanel panel;

    public MasterView(){
        // Set up the JFrame
        frame = new JFrame("Main View with Swappable Panels");
        frame.setSize(500, 400);
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

        // Add action listeners to buttons to switch views
        homeButton.addActionListener(e -> switchView(createMealView()));
        settingsButton.addActionListener(e -> switchView(createSettingsView()));
        profileButton.addActionListener(e -> switchView(createProfileView()));

        // Add buttons to the control panel
        controlPanel.add(homeButton);
        controlPanel.add(settingsButton);
        controlPanel.add(profileButton);

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


    private JPanel createMealPanel(String mealName, String imagePath) {
        JPanel mealPanel = new JPanel();
        mealPanel.setLayout(new BorderLayout());

        // Meal image
        JLabel mealImage = new JLabel();
        mealImage.setIcon(new ImageIcon(imagePath)); // Load image from path
        mealImage.setHorizontalAlignment(SwingConstants.CENTER);

        // Meal name label
        JLabel mealLabel = new JLabel(mealName);
        mealLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Add image and label to meal panel
        mealPanel.add(mealImage, BorderLayout.CENTER);
        mealPanel.add(mealLabel, BorderLayout.SOUTH);

        return mealPanel;
    }

    private JPanel createMealView() {
        // Create a panel with a 2x2 grid layout for meals
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10)); // 2 rows, 2 columns, with spacing

        // Create meal panels
        JPanel meal1 = createMealPanel("Spaghetti Bolognese", "path/to/spaghetti.jpg");
        JPanel meal2 = createMealPanel("Grilled Chicken Salad", "path/to/salad.jpg");
        JPanel meal3 = createMealPanel("Veggie Stir-Fry", "path/to/stirfry.jpg");
        JPanel meal4 = createMealPanel("Beef Tacos", "path/to/tacos.jpg");

        // Add each meal panel to the grid
        panel.add(meal1);
        panel.add(meal2);
        panel.add(meal3);
        panel.add(meal4);

        // Add some padding around the grid
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

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
        panel.add(new JLabel("This is your Profile View."));
        return panel;
    }
}
