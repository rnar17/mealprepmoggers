package org.example.view;

import com.google.gson.Gson;
import org.example.model.UserModel.Profile;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import static org.example.view.viewUtility.*;

public class ProfileView extends JPanel {
    String userName;
    String age;
    int weight;
    int height;
    double maintenanceCalories;

    public ProfileView(){
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(LIGHT_GREEN);

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
            viewUtility.styleTextField(field);
        }

        //TODO Set existing values if they exist

        // Create a label for displaying maintenance calories
        JLabel caloriesLabel = new JLabel();
        caloriesLabel.setForeground(TEXT_COLOR);
        caloriesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        if (maintenanceCalories > 0) {
            caloriesLabel.setText(String.format("Maintenance Calories: %.0f kcal/day", maintenanceCalories));
        }

        JButton saveButton = new JButton("Save");
        styleButton(saveButton);
        saveButton.setMaximumSize(new Dimension(200, 40));

        saveButton.addActionListener(e -> {
            try {
                // Save the values
                String userName = fields[0].getText();
                int userAge = Integer.parseInt(fields[1].getText());
                int userWeight = Integer.parseInt(fields[2].getText());
                int userHeight = Integer.parseInt(fields[3].getText());

                // Calculate maintenance calories using the formula:
                // ((10 × weight in kg) + (6.25 × height in cm) - (5 × age in years)) * 1.3
                maintenanceCalories = ((10 * userWeight) + (6.25 * userHeight) - (5 * userAge)) * 1.37;

                // Update the calories label
                caloriesLabel.setText(String.format("Maintenance Calories: %.0f kcal/day", maintenanceCalories));

                //update profile json file
                Gson gson = new Gson();
                String profilePath = "src/main/User/Profile.json";
                try (FileWriter writer = new FileWriter(profilePath)) {
                    gson.toJson(new Profile(userName,userAge,userWeight, userHeight), writer);
                } catch (IOException d) {
                    d.printStackTrace();
                }

                JOptionPane.showMessageDialog(this,
                        String.format("Profile saved successfully!\nYour maintenance calories: %.0f kcal/day", maintenanceCalories),
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
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
