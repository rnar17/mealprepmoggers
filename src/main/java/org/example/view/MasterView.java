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
        homeButton.addActionListener(e -> switchView(createHomeView()));
        settingsButton.addActionListener(e -> switchView(createSettingsView()));
        profileButton.addActionListener(e -> switchView(createProfileView()));

        // Add buttons to the control panel
        controlPanel.add(homeButton);
        controlPanel.add(settingsButton);
        controlPanel.add(profileButton);

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
}
