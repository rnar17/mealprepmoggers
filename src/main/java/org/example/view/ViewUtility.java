package org.example.view;

import javax.swing.*;
import java.awt.*;

/**
 * Utility class for consistent styling of Swing UI components in the meal prep application.
 * <p>
 *
 * Provides static methods and constants for standardizing the visual design
 * across different views.
 *
 * <p>
 *
 * Representation Invariant:
 * 1. All color constants are non-null
 * 2. Styling methods modify component appearance without changing core functionality
 */
public class ViewUtility extends JPanel {

    // Colours for the green colour scheme of our app
    public static final Color PASTEL_GREEN = new Color(183, 223, 177);
    public static final Color DARKER_GREEN = new Color(141, 196, 133);
    public static final Color LIGHT_GREEN = new Color(220, 237, 218);
    public static final Color ACCENT_GREEN = new Color(106, 168, 96);
    public static final Color TEXT_COLOR = new Color(58, 77, 57);

    /**
     * Styles a title label to match the color scheme.
     *
     * @param label the JLabel to be styled
     */
    public static void styleTitleLabel(JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 28));
        label.setForeground(TEXT_COLOR);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
    }

    /**
    * Styles a label to match the color scheme.
    *
    * @param the label JLabel to be styled
    */
    public static void styleLabel(JLabel label){
        label.setForeground(TEXT_COLOR);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    /**
     * Styles a text field to match the color scheme.
     *
     * @param field the JTextField to be styled
     */
    public static void styleTextField(JTextField field) {
        field.setMaximumSize(new Dimension(300, 35));
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBackground(Color.WHITE);
        field.setForeground(TEXT_COLOR);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(DARKER_GREEN),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
    }

    /**
     * Styles a checkbox to match the color scheme.
     *
     * @param checkBox the JCheckBox to be styled
     */
    public static void styleCheckBox(JCheckBox checkBox) {
        checkBox.setBackground(Color.WHITE);
        checkBox.setForeground(TEXT_COLOR);
        checkBox.setFont(new Font("Arial", Font.PLAIN, 14));
        checkBox.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        checkBox.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    /**
     * Styles a button to match the color scheme.
     *
     * @param button the JButton to be styled.
     */
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
        jpanel.setLayout(new BoxLayout(jpanel, BoxLayout.Y_AXIS));
        jpanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        jpanel.setBackground(LIGHT_GREEN);
    }
}
