package com.mycompany.projeto_a3;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class CreateScreen extends JFrame {

    private JTextField usuarioField;
    private JPasswordField senhaField;
    private JPanel fieldPanel;
    private final Tela menu;
    private String tableOfSelection;
    private final Map<String, JTextField> fieldMap = new HashMap<>();
    private final Map<String, String> tableMappings = new HashMap<>();


    public CreateScreen(Tela menu) {
        super("Interface");
        setTitle("Create");
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
                        CreateScreen.this,
                        "Select a table:",
                        "Select Table",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new String[]{"Categoria de Grupo", "Cliente", "Categoria de Cliente", "Grupo Econômico"},
                        "Categoria de Grupo"
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

        JButton createButton = new JButton("Inserir");
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (tableOfSelection != null) {
                        switch (tableOfSelection) {
                            case "clientes" -> {
                                cliente client = new cliente(1, fieldMap.get("Nome:").getText(), fieldMap.get("CNPJ/CPF:").getText(), Integer.parseInt(fieldMap.get("Tipo Cliente:").getText()), Integer.parseInt(fieldMap.get("Id Grupo:").getText()));
                                Projeto_a3.insertInDB(tableOfSelection, client);
                                System.out.println("FUNFOU: " + changeTableButton.getText());
                            }
                            case "grupo_clientes" -> {
                                grupoCliente grupoClient = new grupoCliente(1, fieldMap.get("Nome do Grupo:").getText(), fieldMap.get("CNPJ/CPF:").getText(), Integer.parseInt(fieldMap.get("Tipo Grupo:").getText()));
                                Projeto_a3.insertInDB(tableOfSelection, grupoClient);
                            }
                            case "tipo_clientes" -> {
                                tipoCliente tipoClient = new tipoCliente(0, fieldMap.get("Nome:").getText());
                                Projeto_a3.insertInDB(tableOfSelection, tipoClient);
                            }
                            case "tipo_grupos" -> {
                                tipoGrupo tipoGroup = new tipoGrupo(1, fieldMap.get("Nome:").getText());
                                Projeto_a3.insertInDB(tableOfSelection, tipoGroup);
                            }
                            default -> {
                            }
                        }
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(CreateScreen.this, "Por favor, verifique o preenchimento correto dos campos", "Erro", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
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
        bottomButtonPanel.add(createButton);
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
            addField(panel, gbc, "Nome:");
        } else if ("Cliente".equals(selectedTable)) {
            addField(panel, gbc, "Nome:");
            addField(panel, gbc, "CNPJ/CPF:");
            addField(panel, gbc, "Tipo Cliente:");
            addField(panel, gbc, "Id Grupo:");
        } else if ("Categoria de Cliente".equals(selectedTable)) {
            addField(panel, gbc, "Nome:");
        } else if ("Grupo Econômico".equals(selectedTable)) {
            addField(panel, gbc, "Nome do Grupo:");
            addField(panel, gbc, "CNPJ/CPF:");
            addField(panel, gbc, "Tipo Grupo:");
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

