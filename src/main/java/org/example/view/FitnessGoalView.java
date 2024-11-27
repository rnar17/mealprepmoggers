package org.example.view;

import com.google.gson.Gson;
import org.example.controller.ProfileController;
import org.example.model.UserModel.FitnessGoals;
import org.example.model.UserModel.Profile;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import static org.example.controller.ProfileController.*;
import static org.example.view.ViewUtility.*;

public class FitnessGoalView extends JPanel{
    Profile user;

    public FitnessGoalView(ProfileController profileController){
        this.user = profileController.user;
        FitnessGoals selectedGoal = profileController.getGoal();
        initializePanel(this);

        JLabel titleLabel = new JLabel("Fitness Goals");
        styleTitleLabel(titleLabel);
        add(titleLabel);

        add(Box.createRigidArea(new Dimension(0, 20)));
        // Create panel for radio buttons
        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.Y_AXIS));
        radioPanel.setBackground(LIGHT_GREEN);
        radioPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        ButtonGroup goalButtonGroup = new ButtonGroup();

        add(titleLabel);
        add(Box.createRigidArea(new Dimension(0, 40)));

        for (FitnessGoals goal : FitnessGoals.values()) {
            createRadioButton(goal, goalButtonGroup, radioPanel);
        }

        add(radioPanel);

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
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(descriptionArea);
    }

    private void createRadioButton(FitnessGoals goal, ButtonGroup goalButtonGroup, JPanel radioPanel) {
        JRadioButton radioButton = new JRadioButton(goal.title);
        styleRadioButton(radioButton);
        // Set selected if this is the current goal
        if (goal.equals(user.goal)) {
            radioButton.setSelected(true);
        }
        // Add action listener
        radioButton.addActionListener(e -> {
            user.setGoal(goal);
            //update profile json file
            Gson gson = new Gson();
            String profilePath = "src/main/User/Profile.json";
            try (FileWriter writer = new FileWriter(profilePath)) {
                gson.toJson(new Profile(user.name,user.age,user.weight, user.height, user.goal), writer);
            } catch (IOException d) {
                d.printStackTrace();
            }

            // Update all radio buttons in the group to maintain visual consistency
            for (Enumeration<AbstractButton> buttons = goalButtonGroup.getElements(); buttons.hasMoreElements();) {
                JRadioButton btn = (JRadioButton) buttons.nextElement();
                btn.setForeground(Color.WHITE);
            }
//                JOptionPane.showMessageDialog(frame,
//                        "Fitness goal set to: " + goal + "\nMeals page will reflect your new goal.",
//                        "Goal Updated",
//                        JOptionPane.INFORMATION_MESSAGE);
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
                radioButton.setBackground(ACCENT_GREEN); // ACCENT_GREEN
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                radioButton.setBackground(DARKER_GREEN); // DARKER_GREEN
            }
        });
    }
}
