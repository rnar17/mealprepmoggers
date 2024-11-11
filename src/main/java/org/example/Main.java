package org.example;
import org.example.view.MasterView;

import javax.swing.*;
import java.awt.BorderLayout;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MasterView::new);
    }
}