package com.mycompany.projeto_a3;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class ReadScreen extends JFrame {

    private JTextField usuarioField;
    private JPasswordField senhaField;
    private JPanel fieldPanel;
    private final Tela menu;
    private String tableOfSelection;
    private final Map<String, JTextField> fieldMap = new HashMap<>();
    private final Map<String, String> tableMappings = new HashMap<>();
    
    private static void exportToCSV(DefaultTableModel model) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salvar como CSV");
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            try (FileWriter writer = new FileWriter(fileChooser.getSelectedFile() + ".csv")) {
                // Write column names to CSV
                for (int i = 0; i < model.getColumnCount(); i++) {
                    writer.append(model.getColumnName(i));
                    if (i < model.getColumnCount() - 1) {
                        writer.append(",");
                    }
                }
                writer.append("\n");

                // Write data to CSV
                for (int row = 0; row < model.getRowCount(); row++) {
                    for (int col = 0; col < model.getColumnCount(); col++) {
                        writer.append(model.getValueAt(row, col).toString());
                        if (col < model.getColumnCount() - 1) {
                            writer.append(",");
                        }
                    }
                    writer.append("\n");
                }

                // Show a success message
                JOptionPane.showMessageDialog(null, "Data exported to CSV successfully!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error exporting data to CSV!");
            }
        }
    }

    public ReadScreen(Tela menu) {
        super("Interface");
        setTitle("Read");
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
                        ReadScreen.this,
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

        JButton lerButton = new JButton("Ler");
        lerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                java.util.List<Map<String, String>> results = new ArrayList<>();
                try {
                    if (tableOfSelection != null) {
                        switch (tableOfSelection) {
                            case "clientes" -> {
                                String nome = fieldMap.get("Nome:").getText();
                                String cpfCliente = fieldMap.get("CNPJ/CPF:").getText();
                                int idTipoCliente;
                                int idGrupoCliente;
                                try {
                                    idTipoCliente = Integer.parseInt(fieldMap.get("Tipo Cliente:").getText());
                                    idGrupoCliente = Integer.parseInt(fieldMap.get("Id Grupo:").getText());
                                } catch (NumberFormatException eex) {
                                    idTipoCliente = -1;
                                    idGrupoCliente = -1;
                                }
                                String query = "";
                                if (!nome.isEmpty()){
                                    query = String.format("SELECT * FROM %s WHERE nome_cliente = '%s'", tableOfSelection, nome);
                                }else if(!cpfCliente.isEmpty()){
                                    query = String.format("SELECT * FROM %s WHERE cpf_cliente = '%s'", tableOfSelection, cpfCliente);
                                }else if(idTipoCliente >= 0){
                                    query = String.format("SELECT * FROM %s WHERE id_grupo_cliente = %d", tableOfSelection, idTipoCliente);
                                }else if (idGrupoCliente >= 0){
                                    query = String.format("SELECT * FROM %s WHERE cpf_cliente = %d", tableOfSelection, idGrupoCliente);
                                } else {
                                    query = String.format("SELECT * FROM %s", tableOfSelection);
                                }

                                results = Projeto_a3.selectInDB(0, tableOfSelection, query);
                            }
                            case "grupo_clientes" -> {
                                 String nomeGrupo = fieldMap.get("Nome do Grupo:").getText();
                                 String cnpjCpfGrupo = fieldMap.get("CNPJ/CPF:").getText();
                                 int idTipoGrupo;
                                 int idGrupo;
                                 try {
                                    idTipoGrupo = Integer.parseInt(fieldMap.get("Tipo Grupo:").getText());
                                    idGrupo = Integer.parseInt(fieldMap.get("ID:").getText());
                                 } catch (NumberFormatException eex){ 
                                    idTipoGrupo = -1;
                                    idGrupo = -1;
                                 }
                                 String query = "";
                                 if (!nomeGrupo.isEmpty()) {
                                     query = String.format("SELECT * FROM %s WHERE nome_grupo = '%s'", tableOfSelection, nomeGrupo);
                                 } else if (!cnpjCpfGrupo.isEmpty()) {
                                     query = String.format("SELECT * FROM %s WHERE cpf_raiz = '%s'", tableOfSelection, cnpjCpfGrupo);
                                 } else if (idTipoGrupo >= 0) {
                                     query = String.format("SELECT * FROM %s WHERE tipo_grupo = %d", tableOfSelection, idTipoGrupo);
                                 } else if (idGrupo >= 0) {
                                     query = String.format("SELECT * FROM %s WHERE id_grupo = %d", tableOfSelection, idGrupo);
                                 }else {
                                    query = String.format("SELECT * FROM %s", tableOfSelection);
                                }

                                 results = Projeto_a3.selectInDB(2, tableOfSelection, query);
                            }
                            case "tipo_clientes" -> {
                                int idTipoCliente;
                                try {
                                    idTipoCliente = Integer.parseInt(fieldMap.get("ID:").getText());
                                } catch (NumberFormatException eex){
                                    idTipoCliente = -1;
                                }
                                String nomeTipoCliente = fieldMap.get("Nome:").getText();
                                String query = "";
                                if (!nomeTipoCliente.isEmpty()) {
                                    query = String.format("SELECT * FROM %s WHERE nome_tipo = '%s'", tableOfSelection, nomeTipoCliente);
                                } else if (idTipoCliente >= 0) {
                                    query = String.format("SELECT * FROM %s WHERE id_tipo = %d", tableOfSelection, idTipoCliente);
                                }else {
                                    query = String.format("SELECT * FROM %s", tableOfSelection);
                                }

                                results = Projeto_a3.selectInDB(1, tableOfSelection, query);
                            }
                            case "tipo_grupos" -> {
                                int idTipoGrupo;
                                try {
                                    idTipoGrupo = Integer.parseInt(fieldMap.get("ID:").getText());
                                } catch (NumberFormatException eex) {
                                   idTipoGrupo = -1;
                                }
                                 String nomeTipoGrupo = fieldMap.get("Nome:").getText();
                                 String query = "";
                                 if (!nomeTipoGrupo.isEmpty()) {
                                     query = String.format("SELECT * FROM %s WHERE nome = '%s'", tableOfSelection, nomeTipoGrupo);
                                 } else if (idTipoGrupo >= 0) {
                                     query = String.format("SELECT * FROM %s WHERE id_tipo_grupo = %d", tableOfSelection, idTipoGrupo);
                                 }else {
                                    query = String.format("SELECT * FROM %s", tableOfSelection);
                                }

                                 results = Projeto_a3.selectInDB(3, tableOfSelection, query);
                             }
                        }
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(ReadScreen.this, "Por favor, verifique o preenchimento correto dos campos", "Erro", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
                
                JFrame frames = new JFrame("Resultado de busca");
                frames.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frames.setSize(600, 400);

                DefaultTableModel model = new DefaultTableModel();
                JTable table = new JTable(model);

                if (!results.isEmpty()) {
                    Map<String, String> firstRow = results.get(0);
                    Set<String> columnNames = firstRow.keySet();

                    for (String columnName : columnNames) {
                        model.addColumn(columnName);
                    }

                    for (Map<String, String> row : results) {
                        model.addRow(row.values().toArray());
                    }

                    JScrollPane scrollPane = new JScrollPane(table);
                    frames.add(scrollPane);

                    JButton exportButton = new JButton("Exportar para CSV");
                    exportButton.addActionListener((ActionEvent e1) -> {
                        exportToCSV(model);
                    });

                    frames.add(exportButton);
                    frames.setLayout(new BoxLayout(frames.getContentPane(), BoxLayout.Y_AXIS));
                } else {
                    JOptionPane.showMessageDialog(null, "No results found", "Information", JOptionPane.INFORMATION_MESSAGE);
                }

                frames.setVisible(true);
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
        bottomButtonPanel.add(lerButton);
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
            addField(panel, gbc, "ID:");
            addField(panel, gbc, "Nome:");
        } else if ("Cliente".equals(selectedTable)) {
            addField(panel, gbc, "Nome:");
            addField(panel, gbc, "CNPJ/CPF:");
            addField(panel, gbc, "Tipo Cliente:");
            addField(panel, gbc, "ID Grupo:");
        } else if ("Categoria de Cliente".equals(selectedTable)) {
            addField(panel, gbc, "ID:");
            addField(panel, gbc, "Nome:");
        } else if ("Grupo Econômico".equals(selectedTable)) {
            addField(panel, gbc, "Nome do Grupo:");
            addField(panel, gbc, "CNPJ/CPF:");
            addField(panel, gbc, "Tipo Grupo:");
            addField(panel, gbc, "ID:");
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

