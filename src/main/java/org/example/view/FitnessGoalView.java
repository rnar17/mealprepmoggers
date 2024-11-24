package org.example.view;

import javax.swing.*;
import java.awt.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import static org.example.view.ViewUtility.*;

public class FitnessGoalView extends JPanel{
    public FitnessGoalView(){
        String selectedGoal = "Maintenance"; //set default to maintainance -Mo
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(LIGHT_GREEN);

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

        String[] goals = {
                "Weight Loss",
                "Muscle Gain",
                "Maintenance",
        };

        add(titleLabel);
        add(Box.createRigidArea(new Dimension(0, 40)));

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
}
