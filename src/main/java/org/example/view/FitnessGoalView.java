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

/**
 * This view class allows the user to view available fitness goals, select a fitness goal,
 * and see a description of the selected goal.
 * <p>
 * Representation Invariant:
 * 1. user is not null
 * 2. The panel uses BoxLayout in Y_AXIS orientation
 * 3. Contains a title label
 * 4. Contains a ButtonGroup for fitness goals
 * 5. Contains a description area for goal details
 * <p>
 * Abstraction Function:
 * AF(r) = A fitness goal selection and information view where:
 * 1. r.user = the current user profile
 * 2. r.titleLabel = displays "Fitness Goals"
 * 3. r.radioPanel = contains radio buttons for different fitness goals
 * 4. r.descriptionArea = shows detailed information about the selected fitness goal
 */

public class FitnessGoalView extends JPanel {
    Profile user;

    /**
     * Constructs a new FitnessGoalView panel.
     * <p>
     * Initializes the view with:
     * - A title label
     * - Radio buttons for fitness goals
     * - A description area showing goal details
     *
     * @param profileController The controller managing user profile information
     */
    public FitnessGoalView(ProfileController profileController) {
        this.user = profileController.fetchProfile();
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
        for (Enumeration<AbstractButton> buttons = goalButtonGroup.getElements(); buttons.hasMoreElements(); ) {
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
        checkRep();
    }

    /**
     * Creates a radio button for a specific fitness goal.
     * <p>
     * Configures the radio button with:
     * - Appropriate styling
     * - Selection based on current user goal
     * - Action listener to update user's fitness goal
     *
     * @param goal            The fitness goal to create a radio button for
     * @param goalButtonGroup The button group to add the radio button to
     * @param radioPanel      The panel to add the radio button to
     */
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
            for (Enumeration<AbstractButton> buttons = goalButtonGroup.getElements(); buttons.hasMoreElements(); ) {
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
        checkRep();
    }

    /**
     * Applies custom styling to a radio button.
     * <p>
     * Configures the radio button's:
     * - Font
     * - Colors
     * - Size
     * - Hover effects
     *
     * @param radioButton The radio button to style
     */
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
        checkRep();
    }

    /**
     * Checks the representation invariants for the FitnessGoalView.
     * <p>
     * Verifies:
     * - user is not null
     * - Panel uses BoxLayout in Y_AXIS orientation
     * - Contains a title label
     * - Contains a ButtonGroup for fitness goals
     * - Contains a description area
     *
     * @throws AssertionError if any representation invariant is violated
     */
    private void checkRep() {
        // Check user is not null
        assert user != null : "User cannot be null";

        // Check layout
        assert getLayout() instanceof BoxLayout : "Layout must be BoxLayout";
        BoxLayout layout = (BoxLayout) getLayout();
        assert layout.getAxis() == BoxLayout.Y_AXIS : "Layout must be Y_AXIS oriented";

        // Check for title label
        boolean hasTitleLabel = false;
        for (Component comp : getComponents()) {
            if (comp instanceof JLabel && ((JLabel) comp).getText().equals("Fitness Goals")) {
                hasTitleLabel = true;
                break;
            }
        }
        assert hasTitleLabel : "Must have a title label 'Fitness Goals'";

        // Check for radio buttons
        boolean hasRadioButtons = false;
        int radioButtonCount = 0;
        for (Component comp : getComponents()) {
            if (comp instanceof JPanel) {
                for (Component innerComp : ((Container) comp).getComponents()) {
                    if (innerComp instanceof JRadioButton) {
                        hasRadioButtons = true;
                        radioButtonCount++;
                    }
                }
            }
        }
        assert hasRadioButtons : "Must have radio buttons for fitness goals";
        assert radioButtonCount == FitnessGoals.values().length : "Must have a radio button for each fitness goal";

        // Check for description area
        boolean hasDescriptionArea = false;
        for (Component comp : getComponents()) {
            if (comp instanceof JTextArea) {
                hasDescriptionArea = true;
                break;
            }
        }
        assert hasDescriptionArea : "Must have a description area";
    }
}
