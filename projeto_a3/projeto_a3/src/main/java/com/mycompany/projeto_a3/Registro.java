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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Registro extends JFrame {
    private final JTextField usuarioField;
    private final JPasswordField senhaField;
    private final JPasswordField confirmarSenhaField;
    private final telaLogin loginReference;

    public Registro(telaLogin loginReference) {
        super("Registro");
        this.loginReference = loginReference;
        setTitle("Tela de Registro");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel usuarioLabel = new JLabel("Usuário:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(usuarioLabel, constraints);

        usuarioField = new JTextField(20);
        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        panel.add(usuarioField, constraints);

        JLabel senhaLabel = new JLabel("Senha:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(senhaLabel, constraints);

        senhaField = new JPasswordField(20);
        constraints.gridx = 1;
        panel.add(senhaField, constraints);

        JLabel confirmSenhaLabel = new JLabel("Confirme a senha:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(confirmSenhaLabel, constraints);

        confirmarSenhaField = new JPasswordField(20);
        constraints.gridx = 1;
        panel.add(confirmarSenhaField, constraints);

        JButton registrarButton = new JButton("Registrar-se");
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        panel.add(registrarButton, constraints);

        registrarButton.addActionListener((ActionEvent e) -> {
            registrar();
        });

        add(panel);
    }

    private void registrar() {
        String usuario = usuarioField.getText();
        char[] senhaChars = senhaField.getPassword();
        char[] confirmSenhaChars = confirmarSenhaField.getPassword();

        String senha = new String(senhaChars);
        String confirmSenha = new String(confirmSenhaChars);

        if (!senha.equals(confirmSenha)) {
            JOptionPane.showMessageDialog(this, "Verifique a senha inserida.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String salt = gerarSalt();
        String hashedPassword = hashMD5(senha + salt);

        try {
            String url = "jdbc:mysql://localhost/base_a3";
            String user = "root";
            String dbPassword = "password";

            try (Connection connection = DriverManager.getConnection(url, user, dbPassword)) {
                String query = "INSERT INTO usuarios (username, password, salt) VALUES (?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, usuario);
                    preparedStatement.setString(2, hashedPassword);
                    preparedStatement.setString(3, salt);
                    
                    preparedStatement.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Registro realizado com sucesso!");
                    
                    loginReference.setVisible(true);
                    dispose();
                }
            }

        } catch (HeadlessException | SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao registrar usuário.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String gerarSalt() {
        byte[] saltBytes = new byte[16];
        new SecureRandom().nextBytes(saltBytes);

        StringBuilder hexString = new StringBuilder();
        for (byte b : saltBytes) {
            hexString.append(String.format("%02X", b));
        }

        return hexString.toString();
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
