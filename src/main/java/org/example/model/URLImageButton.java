package org.example.model;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * Utility class for creating image buttons with flexible image fitting modes.
 *
 * Representation Invariant:
 * 1. FitMode is one of STRETCH, FIT, or FILL
 * 2. Image loading and scaling methods handle various input scenarios
 * 3. Button dimensions are positive integers
 *
 * Abstraction Function:
 * A utility for creating image buttons with:
 * 1. Flexible image scaling strategies
 * 2. Ability to load images from URLs
 * 3. Consistent button sizing and image positioning
 */
public class URLImageButton {

    private String imageUrl;
    private int buttonWidth;
    private int buttonHeight;
    private FitMode fitMode;

    /**
     * A helper method that checks the state of the presentation invariant.
     */
    private void checkRep() {
        // Check that fitMode is not null
        if (fitMode == null) {
            throw new IllegalStateException("FitMode cannot be null");
        }

        // Ensure fitMode is one of the defined enum values
        boolean validFitMode = false;
        for (FitMode mode : FitMode.values()) {
            if (mode == fitMode) {
                validFitMode = true;
                break;
            }
        }
        if (!validFitMode) {
            throw new IllegalStateException("Invalid FitMode: " + fitMode);
        }

        // Check button width is positive
        if (buttonWidth <= 0) {
            throw new IllegalStateException("Button width must be positive: " + buttonWidth);
        }

        // Check button height is positive
        if (buttonHeight <= 0) {
            throw new IllegalStateException("Button height must be positive: " + buttonHeight);
        }
    }

    public enum FitMode {

        /**
         * Defines image fitting strategies for buttons.
         *
         * - STRETCH: Distorts image to exactly fill button
         * - FIT: Scales image to fit within button while maintaining aspect ratio
         * - FILL: Scales image to completely cover button, potentially cropping
         */
        STRETCH,    // Stretch image to fill button completely
        FIT,        // Fit image while maintaining aspect ratio
        FILL        // Fill button while maintaining aspect ratio (may crop)
    }

    /**
     * Requirements:
     * - imageUrl must be a valid, non-null URL to an image
     * - buttonWidth and buttonHeight must be positive integers
     * - fitMode must be a non-null FitMode enum value
     *
     * Exceptions:
     * - Throws Exception if image cannot be loaded or processed
     *
     * @param imageUrl The URL of the image to be displayed
     * @param buttonWidth Desired width of the button
     * @param buttonHeight Desired height of the button
     * @param fitMode Strategy for scaling the image
     *
     * @return A JButton with the specified image and scaling
     */
    public JButton createImageButton(String imageUrl, int buttonWidth, int buttonHeight, FitMode fitMode) {
        this.imageUrl = imageUrl;
        this.buttonWidth = buttonWidth;
        this.buttonHeight = buttonHeight;
        this.fitMode = fitMode;
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