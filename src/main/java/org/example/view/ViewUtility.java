package org.example.view;

import javax.swing.*;
import java.awt.*;

public class ViewUtility extends JPanel {

    public static final Color PASTEL_GREEN = new Color(183, 223, 177);
    public static final Color DARKER_GREEN = new Color(141, 196, 133);
    public static final Color LIGHT_GREEN = new Color(220, 237, 218);
    public static final Color ACCENT_GREEN = new Color(106, 168, 96);
    public static final Color TEXT_COLOR = new Color(58, 77, 57);

    //Helper methods to help style elements in views
    public static void styleTitleLabel(JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 28));
        label.setForeground(TEXT_COLOR);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
    }

    public static void styleTextField(JTextField field) {
        field.setMaximumSize(new Dimension(300, 35));
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBackground(Color.WHITE);
        field.setForeground(TEXT_COLOR);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(DARKER_GREEN),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
    }

    public static void styleCheckBox(JCheckBox checkBox) {
        checkBox.setBackground(Color.WHITE);
        checkBox.setForeground(TEXT_COLOR);
        checkBox.setFont(new Font("Arial", Font.PLAIN, 14));
        checkBox.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        checkBox.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    public static void styleButton(JButton button) {
        button.setBackground(DARKER_GREEN);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setFont(new Font("Arial", Font.BOLD, 12));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(ACCENT_GREEN);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(DARKER_GREEN);
            }
        });
    }

    public static void initializePanel(JPanel jpanel){
        jpanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        jpanel.setLayout(new BoxLayout(jpanel, BoxLayout.Y_AXIS));
        jpanel.setBackground(LIGHT_GREEN);
    }
}
