package org.example.view;

import org.example.controller.ProfileController;
import org.example.model.UserModel.Profile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static org.example.controller.FitnessController.calculateMaintainanceCalories;
import static org.example.view.ViewUtility.*;

/**
 * This is the class representing the profile tab view on the application view. This tab allows
 * the user to input information about themselves and calculates the recommended calories. Creates
 * textboxes and button for saving and calculating entries
 * <p>
 * Representation Invariant:
 * - user is not null
 * - maintenanceCalories is not null and positive
 * </p>
 * <p>
 * Abstract Function:
 * Profile Tab with personal input to calculate calorie usage:
 * r.user = profile of user that is currently in use
 * r.maintenanceCalories = maintenance calories for current user
 * </p>
 */

public class ProfileView extends JPanel {
    private Profile user;
    double maintenanceCalories;

    /**
     * checkRep for checking if representation invariant is violated
     */
    private void checkRep() {
        if (this.user == null) {
            throw new IllegalStateException("user is null");
        }
        if (this.maintenanceCalories < 0) {
            throw new IllegalStateException("maintenanceCalories is negative");
        }
    }

    /**
     * Profile view that initializes the Profile tab inside the application. Has title box,
     * input boxes for profile input, and button to save profile.
     * @param profileController controller for the profile and interacting with the view
     */
    public ProfileView(ProfileController profileController){
        this.user = profileController.fetchProfile();
        initializePanel(this);

        JLabel titleLabel = new JLabel("Profile Overview");
        styleTitleLabel(titleLabel);

        // Create a label for displaying maintenance calories
        JLabel caloriesLabel = new JLabel();
        caloriesLabel.setForeground(TEXT_COLOR);
        caloriesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        if (maintenanceCalories > 0) {
            caloriesLabel.setText(String.format("Maintenance Calories: %.0f kcal/day", maintenanceCalories));
        }

        // Create styled text fields
        JTextField nameField = new JTextField(20);
        JTextField ageField = new JTextField(20);
        JTextField weightField = new JTextField(20);
        JTextField heightField = new JTextField(20);
        JTextField[] fields = {
            nameField,ageField,weightField,heightField
        };
        String[] placeholder = {"Your Name","Your Age","Your Weight In kg","Your Height In cm"};
        for(int i = 0; i < fields.length; i++){
            int finalI = i;
            fields[i].addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                        fields[finalI].setText("");
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (fields[finalI].getText().isEmpty()) {
                        fields[finalI].setText(placeholder[finalI]);  // Restore placeholder text
                    }
                }
            });
        }



        //Create styled labels
        JLabel nameLabel = new JLabel("Name");
        JLabel ageLabel = new JLabel("Age");
        JLabel weightLabel = new JLabel("Weight (kg)");
        JLabel heightLabel = new JLabel("Height (cm)");
        JLabel[] labels = {
                nameLabel,ageLabel,weightLabel,heightLabel
        };

        //Set text based on existing Profile
        if(user.name.isBlank()){
                    nameField.setText("Your Name");
                    ageField.setText("Your Age");
                    weightField.setText("Your Weight In kg");
                    heightField.setText("Your Height In cm");
        }
        else{
            nameField.setText(user.name);
            ageField.setText(String.valueOf(user.age));
            weightField.setText(String.valueOf(user.age));
            heightField.setText(String.valueOf(user.weight));
        }

        JButton saveButton = new JButton("Save");
        styleButton(saveButton);
        //saveButton.setMaximumSize(new Dimension(200, 40));

        saveButton.addActionListener(e -> {
            try {
                // Save the values
                String userName = nameField.getText();
                int userAge = Integer.parseInt(ageField.getText());
                int userWeight = Integer.parseInt(weightField.getText());
                int userHeight = Integer.parseInt(heightField.getText());

                maintenanceCalories = calculateMaintainanceCalories(userWeight,userHeight,userAge);
                caloriesLabel.setText(String.format("Maintenance Calories: %.0f kcal/day", maintenanceCalories));
                if(profileController.updateProfile(userName, userAge, userWeight, userHeight, user.goal)){
                    JOptionPane.showMessageDialog(this,
                            String.format("Profile saved successfully!\nYour maintenance calories: %.0f kcal/day", maintenanceCalories),
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                }
                else{
                    JOptionPane.showMessageDialog(this,
                            "ERROR, Profile not saved!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Please enter valid numbers for age, weight, and height",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        //Add elements to the panel
        add(titleLabel);
        add(Box.createRigidArea(new Dimension(0, 20)));

        for (int i = 0; i < labels.length; i++) {
            styleLabel(labels[i]);
            add(labels[i]);
            add(Box.createRigidArea(new Dimension(0, 5)));
            add(fields[i]);
            add(Box.createRigidArea(new Dimension(0, 15)));
        }

        add(Box.createRigidArea(new Dimension(0, 10)));
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(saveButton);

        add(Box.createRigidArea(new Dimension(0, 20)));
        add(caloriesLabel);
    }
}
