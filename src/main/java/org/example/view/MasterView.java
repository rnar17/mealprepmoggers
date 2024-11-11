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
        JButton fitnessGoalButton = new JButton("FitnessGoal");

        // Add action listeners to buttons to switch views
        homeButton.addActionListener(e -> switchView(createHomeView()));
        settingsButton.addActionListener(e -> switchView(createSettingsView()));
        profileButton.addActionListener(e -> switchView(createProfileView()));
        fitnessGoalButton.addActionListener(e -> switchView(createFitnessGoalView()));

        // Add buttons to the control panel
        controlPanel.add(homeButton);
        controlPanel.add(settingsButton);
        controlPanel.add(profileButton);
        controlPanel.add(fitnessGoalButton);

        // Add the control panel to the top of the frame
        frame.add(controlPanel, BorderLayout.NORTH);

        // Set the initial view to the Home view
        switchView(createHomeView());

        // Show the frame
        frame.setVisible(true);
    }

    private void switchView(JPanel newView) {
        panel.removeAll();            // Remove the current view
        panel.add(newView);            // Add the new view
        panel.revalidate();            // Refresh the panel
        panel.repaint();               // Repaint the panel
    }

    private JPanel createHomeView() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Welcome to the Home View!"));
        return panel;
    }

    // Settings view
    private JPanel createSettingsView() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("This is the Settings View."));
        return panel;
    }

    // Profile view
    private JPanel createProfileView() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("This is your Profile View."));
        return panel;
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
}
