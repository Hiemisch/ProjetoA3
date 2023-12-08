/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projeto_a3;

/**
 *
 * @author Victor e Hiemisch
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class telaLogin extends JFrame {
    private final JTextField usuarioField;
    private final JPasswordField senhaField;

    public telaLogin() {
        super("Interface");
        setTitle("Login Screen");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel usernameLabel = new JLabel("Usuário:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(usernameLabel, constraints);

        usuarioField = new JTextField(20);
        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        panel.add(usuarioField, constraints);

        JLabel passwordLabel = new JLabel("Senha:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(passwordLabel, constraints);

        senhaField = new JPasswordField(20);
        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        panel.add(senhaField, constraints);

        JButton loginButton = new JButton("Login");
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        panel.add(loginButton, constraints);

        JButton registerButton = new JButton("Registrar-se");
        constraints.gridy = 3;
        panel.add(registerButton, constraints);

        // Add KeyListener to the password field
        senhaField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // Not needed for Enter key press
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // Check if the pressed key is Enter
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    // Call the loginAction() function
                    loginAction();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // Not needed for Enter key press
            }
        });

        loginButton.addActionListener((ActionEvent e) -> {
            loginAction();
        });

        registerButton.addActionListener((ActionEvent e) -> {
            new Registro(telaLogin.this).setVisible(true);
            setVisible(false);
        });

        add(panel);
    }

    private void loginAction() {
       
        String username = usuarioField.getText();
        char[] passwordChars = senhaField.getPassword();
        String enteredPassword = new String(passwordChars);

        try {
            String url = "jdbc:mysql://localhost/base_a3";
            String user = "root";
            String dbPassword = "password";

            try (Connection connection = DriverManager.getConnection(url, user, dbPassword)) {
                String query = "SELECT username, password, salt FROM usuarios WHERE username = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, username);
                    
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            String dbPasswordHash = resultSet.getString("password");
                            String salt = resultSet.getString("salt");
                            
                            String concatenatedPassword = enteredPassword + salt;
                            
                            String enteredPasswordHash = hashMD5(concatenatedPassword);
                            
                            if (dbPasswordHash.equals(enteredPasswordHash)) {
                                JOptionPane.showMessageDialog(this, "Login realizado com sucesso!");
                            } else {
                                JOptionPane.showMessageDialog(this, "Senha incorreta, por favor tente novamente");
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Usuário não encontrado, por favor registre-se");
                        }
                    }
                }
            }
            
            new Tela().setVisible(true);
            setVisible(false);

        } catch (HeadlessException | SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao se conectar com o banco de dados.");
        }
    }

    private String hashMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(String.format("%02X", b));
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}


