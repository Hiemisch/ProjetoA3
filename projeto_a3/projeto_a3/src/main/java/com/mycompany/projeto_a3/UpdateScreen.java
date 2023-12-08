package com.mycompany.projeto_a3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class UpdateScreen extends JFrame {

    private JTextField usuarioField;
    private JPasswordField senhaField;
    private JPanel fieldPanel;
    private final Tela menu;
    private String tableOfSelection;
    private final Map<String, JTextField> fieldMap = new HashMap<>();
    private final Map<String, String> tableMappings = new HashMap<>();

    public UpdateScreen(Tela menu) {
        super("Interface");
        setTitle("Update");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        tableMappings.put("Categoria de Grupo", "tipo_grupos");
        tableMappings.put("Cliente", "clientes");
        tableMappings.put("Categoria de Cliente", "tipo_clientes");
        tableMappings.put("Grupo Econômico", "grupo_clientes");

        this.menu = menu;
        fieldPanel = new JPanel(new GridBagLayout());

        updateFieldsForTable("Categoria de Grupo");
        tableOfSelection = "tipo_grupos";

        JButton changeTableButton = new JButton("Selecionar Tabela");
        changeTableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedTable = (String) JOptionPane.showInputDialog(
                        UpdateScreen.this,
                        "Select a table:",
                        "Select Table",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new String[]{"Categoria de Grupo", "Cliente", "Categoria de Cliente", "Grupo Econômico"},
                        "tipo_grupos"
                );

                updateFieldsForTable(selectedTable);
                tableOfSelection = tableMappings.get(selectedTable);
            }
        });

        JButton openTelaButton = new JButton("Voltar ao menu");
        openTelaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu.setVisible(true);
                dispose();
            }
        });

        JButton updateButton = new JButton("Atualizar");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Projeto_a3 projetoInstance = new Projeto_a3();
        
                projetoInstance.updateInDB(tableOfSelection, fieldMap.get("Campo a ser alterado:").getText(),fieldMap.get("Valor atualizado:").getText(),fieldMap.get("Campo Filtro:").getText(),fieldMap.get("Valor do Filtro:").getText());
    }
        });

        // Add components to the frame
        setLayout(new BorderLayout());
        
        // Add buttons at the top
        JPanel topButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topButtonPanel.add(changeTableButton);
        topButtonPanel.add(openTelaButton);
        add(topButtonPanel, BorderLayout.NORTH);

        // Add fieldPanel in the center
        add(fieldPanel, BorderLayout.CENTER);

        // Add buttons at the bottom
        JPanel bottomButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomButtonPanel.add(updateButton);
        add(bottomButtonPanel, BorderLayout.SOUTH);
    }

    private void updateFieldsForTable(String selectedTable) {
        // Clear the fieldPanel
        fieldPanel.removeAll();

        // Set constraints for GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Call the method to create fields dynamically based on the selected table
        createFieldsForTable(fieldPanel, gbc, selectedTable);

        // Repaint the panel
        revalidate();
        repaint();
    }

    private void createFieldsForTable(JPanel panel, GridBagConstraints gbc, String selectedTable) {
        if ("Categoria de Grupo".equals(selectedTable)) {
            addField(panel, gbc, "Campo a ser alterado:");
            addField(panel, gbc, "Valor atualizado:");
            addField(panel, gbc, "Campo Filtro:");
            addField(panel, gbc, "Valor do Filtro:");
            // Add more fields as needed
        } else if ("Cliente".equals(selectedTable)) {
            addField(panel, gbc, "Campo a ser alterado:");
            addField(panel, gbc, "Valor atualizado:");
            addField(panel, gbc, "Campo Filtro:");
            addField(panel, gbc, "Valor do Filtro:");
        } else if ("Categoria de Cliente".equals(selectedTable)) {
            addField(panel, gbc, "Campo a ser alterado:");
            addField(panel, gbc, "Valor atualizado:");
            addField(panel, gbc, "Campo Filtro:");
            addField(panel, gbc, "Valor do Filtro:");
        } else if ("Grupo Econômico".equals(selectedTable)) {
            addField(panel, gbc, "Campo a ser alterado:");
            addField(panel, gbc, "Valor atualizado:");
            addField(panel, gbc, "Campo Filtro:");
            addField(panel, gbc, "Valor do Filtro:");
        }
    }

    private void addField(JPanel panel, GridBagConstraints gbc, String labelText) {
        JLabel label = new JLabel(labelText);
        JTextField textField = new JTextField(20);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(textField, gbc);
        fieldMap.put(labelText, textField);
    }
    public Map<String, JTextField> getFieldMap() {
    return fieldMap;
    }
}