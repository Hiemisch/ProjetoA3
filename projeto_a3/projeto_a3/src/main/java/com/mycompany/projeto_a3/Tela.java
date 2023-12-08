
package com.mycompany.projeto_a3;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Tela extends JFrame {

    public Tela() {
setTitle("Menu");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a label for the menu
        JLabel menuLabel = new JLabel("Menu");
        menuLabel.setHorizontalAlignment(JLabel.CENTER);

        // Create buttons for each operation
        JButton createButton = createOperationButton("Create");
        JButton readButton = createOperationButton("Read");
        JButton updateButton = createOperationButton("Update");
        JButton deleteButton = createOperationButton("Delete");

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 0, 10)); // 4 rows, 1 column, with vertical gap
        buttonPanel.add(createButton);
        buttonPanel.add(readButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        // Create a panel for the entire content
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(menuLabel, BorderLayout.NORTH);
        contentPanel.add(buttonPanel, BorderLayout.CENTER);

        // Add the content panel to the frame
        add(contentPanel);

        // Set up listeners for each button
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateScreen createScreen = new CreateScreen(Tela.this);
                createScreen.setVisible(true);
                setVisible(false);
            }
        });

        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReadScreen readScreen = new ReadScreen(Tela.this);
                readScreen.setVisible(true);
                setVisible(false);
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UpdateScreen updateScreen = new UpdateScreen(Tela.this);
                updateScreen.setVisible(true);
                setVisible(false);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteScreen deleteScreen = new DeleteScreen(Tela.this);
                deleteScreen.setVisible(true);
                setVisible(false);
            }
        });
    }

    private JButton createOperationButton(String operationName) {
        JButton button = new JButton(operationName);
        // Set button properties for size and alignment
        button.setPreferredSize(new Dimension(150, 40)); // Adjust the size as needed
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        return button;
    }
}
