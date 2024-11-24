package org.example.view;

import javax.swing.*;
import java.awt.*;
import static org.example.view.ViewUtility.*;

public class FitnessGoalView extends JPanel{
    public FitnessGoalView(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(LIGHT_GREEN);

        JLabel titleLabel = new JLabel("Fitness Goals");
        styleTitleLabel(titleLabel);

        String[] goals = {
                "Weight Loss",
                "Muscle Gain",
                "Maintenance"
        };

        add(titleLabel);
        add(Box.createRigidArea(new Dimension(0, 40)));

        for (String goal : goals) {
            JButton button = new JButton(goal);
            styleButton(button);
            button.setMaximumSize(new Dimension(250, 50));
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(button);
            add(Box.createRigidArea(new Dimension(0, 20)));
        }

    }
}
