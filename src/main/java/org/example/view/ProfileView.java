package org.example.view;

import com.google.gson.Gson;
import org.example.controller.ProfileController;
import org.example.model.UserModel.Profile;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;

import static org.example.controller.FitnessController.calculateMaintainanceCalories;
import static org.example.view.ViewUtility.*;

public class ProfileView extends JPanel {
   Profile user;
    double maintenanceCalories;

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
        styleTextField(nameField);
        styleTextField(ageField);
        styleTextField(weightField);
        styleTextField(heightField);

        //Set text based on existing Profile
        if(user.name.isBlank()){
                    nameField.setText("Your Name");
                    ageField.setText("Your Age");
                    weightField.setText("Your Weight In kg");
                    heightField.setText("Your Height In c");
        }
        else{
            nameField.setText(user.name);
            ageField.setText(String.valueOf(user.age));
            weightField.setText(String.valueOf(user.age));
            heightField.setText(String.valueOf(user.weight));
        }

        JButton saveButton = new JButton("Save");
        styleButton(saveButton);
        saveButton.setMaximumSize(new Dimension(200, 40));

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

        String[] labels = {"Name:", "Age:", "Weight (kg):", "Height (cm):"};

        add(titleLabel);
        add(Box.createRigidArea(new Dimension(0, 20)));

        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setForeground(TEXT_COLOR);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(label);
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
