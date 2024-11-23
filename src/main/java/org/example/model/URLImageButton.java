package org.example.model;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class URLImageButton {
    public enum FitMode {
        STRETCH,    // Stretch image to fill button completely
        FIT,        // Fit image while maintaining aspect ratio
        FILL        // Fill button while maintaining aspect ratio (may crop)
    }

    /**
     * Creates a JButton with an image from a URL that fits the button
     */
    public static JButton createImageButton(String imageUrl, int buttonWidth, int buttonHeight, FitMode fitMode) {
        JButton button = new JButton();

        try {
            // Load the image from URL
            URL url = new URL(imageUrl);
            BufferedImage originalImage = ImageIO.read(url);

            if (originalImage != null) {
                // Get original image dimensions
                int imgWidth = originalImage.getWidth();
                int imgHeight = originalImage.getHeight();

                // Calculate new dimensions based on fit mode
                int newWidth = buttonWidth;
                int newHeight = buttonHeight;

                if (fitMode != FitMode.STRETCH) {
                    double imgAspect = (double) imgWidth / imgHeight;
                    double buttonAspect = (double) buttonWidth / buttonHeight;

                    if (fitMode == FitMode.FIT) {
                        if (buttonAspect < imgAspect) {
                            // Button is taller than image aspect
                            newWidth = buttonWidth;
                            newHeight = (int) (buttonWidth / imgAspect);
                        } else {
                            // Button is wider than image aspect
                            newWidth = (int) (buttonHeight * imgAspect);
                            newHeight = buttonHeight;
                        }
                    } else if (fitMode == FitMode.FILL) {
                        if (buttonAspect < imgAspect) {
                            // Button is taller than image aspect
                            newWidth = (int) (buttonHeight * imgAspect);
                            newHeight = buttonHeight;
                        } else {
                            // Button is wider than image aspect
                            newWidth = buttonWidth;
                            newHeight = (int) (buttonWidth / imgAspect);
                        }
                    }
                }

                // Scale the image
                Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                ImageIcon icon = new ImageIcon(scaledImage);

                // Configure button
                button.setIcon(icon);
                button.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
                button.setBorderPainted(false);
                button.setContentAreaFilled(false);
                button.setFocusPainted(false);

                // Center the image in the button
                button.setHorizontalAlignment(SwingConstants.CENTER);
                button.setVerticalAlignment(SwingConstants.CENTER);
            }

        } catch (Exception e) {
            button.setText("Image Load Error");
            e.printStackTrace();
        }

        return button;
    }
}