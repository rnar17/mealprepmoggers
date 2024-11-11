package org.example.view;

import javax.swing.*;

public class ProfileView extends JPanel {
    String userName;
    String age;
    int weight;
    int height;

    public ProfileView(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //TODO set the fields to take specific data types
        JTextField nameField = new JTextField("userName");
        JTextField ageField = new JTextField("age");
        JTextField weightField = new JTextField("weight");
        JTextField heightField = new JTextField("height");
        JButton saveButton = new JButton("Save");

        add(new JLabel("Overview"));
        add(new JLabel("userName:"));
        add(nameField);
        add(new JLabel("age:"));
        add(ageField);
        add(new JLabel("weight:"));
        add(weightField);
        add(new JLabel("height:"));
        add(heightField);
        add(saveButton);


        if(saveButton.getModel().isPressed()){
            userName = nameField.getText();
            age = ageField.getText();
            weight = Integer.parseInt(weightField.getText());
            height = Integer.parseInt(heightField.getText());
        }
    }

    public String getUserName() {
        return userName;
    }

    public String getUserAge() {
        return age;
    }

    public int getUserWeight() {
        return weight;
    }

    public int getUserHeight() {
        return height;
    }
}
