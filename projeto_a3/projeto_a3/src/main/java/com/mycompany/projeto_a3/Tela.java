package com.mycompany.projeto_a3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;



public class Tela extends JFrame {

    public final JTextField idField, nomeField, cpfField, tipoField, idGrupoField, campoField, valorField;
    public final JComboBox<String> tableSelector;
    public String nomeTabela;
    public String campoAlterado;
    public Object valor;
    public String ref;
    public int id;
    public String campo;
    public String filtro;
    public tabelasDB tabela = new tabelasDB();
    private final JTextField filtroField;
    
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

    public Tela() {
        super("Interface");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);  // Centraliza a janela na tela

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel tableLabel = new JLabel("Tabela:");
        JLabel idLabel = new JLabel("ID:");
        JLabel nomeLabel = new JLabel("Nome:");
        JLabel cpfLabel = new JLabel("CPF:");
        JLabel tipoLabel = new JLabel("Tipo:");
        JLabel idGrupoLabel = new JLabel("ID Grupo:");
        JLabel campoLabel = new JLabel("Campo:");
        JLabel valorLabel = new JLabel("Valor:");
        JLabel filtroLabel = new JLabel("Filtro:");
        
        String[] tableNames = {"tipo_clientes", "grupo_clientes", "clientes", "tipo_grupos"};
        tableSelector = new JComboBox<>(tableNames);

        idField = new JTextField(20);
        nomeField = new JTextField(20);
        cpfField = new JTextField(20);
        tipoField = new JTextField(20);
        idGrupoField = new JTextField(20);
        campoField = new JTextField(20);
        valorField = new JTextField(20);
        filtroField = new JTextField(20);

        JButton createButton = new JButton("Criar");
        JButton readButton = new JButton("Ler");
        JButton updateButton = new JButton("Atualizar");
        JButton deleteButton = new JButton("Excluir");

        JFrame frame = new JFrame("Database Result");
        frame.setSize(600, 400);

        createButton.addActionListener((ActionEvent e) -> {
            if (null != (String) tableSelector.getSelectedItem() ) switch ((String) tableSelector.getSelectedItem()) {
                case "clientes" -> {
                    cliente client = new cliente(1, nomeField.getText(),cpfField.getText(), Integer.parseInt(tipoField.getText()), Integer.parseInt(idGrupoField.getText()));
                    Projeto_a3.insertInDB((String) tableSelector.getSelectedItem(),client);
                }
                case "grupo_clientes" -> {
                    grupoCliente grupoClient = new grupoCliente(1, nomeField.getText(),cpfField.getText(), Integer.parseInt(tipoField.getText()));
                    Projeto_a3.insertInDB((String) tableSelector.getSelectedItem(),grupoClient);
                }
                case "tipo_clientes" -> {
                    tipoCliente tipoClient = new tipoCliente(0,nomeField.getText());
                    Projeto_a3.insertInDB((String) tableSelector.getSelectedItem(),tipoClient);
                }
                case "tipo_grupos" -> {
                    tipoGrupo tipoGroup = new tipoGrupo(1,nomeField.getText());
                    Projeto_a3.insertInDB((String) tableSelector.getSelectedItem(),tipoGroup);
                }
                default -> {
                }
            }
        });

        readButton.addActionListener((ActionEvent e) -> {
            java.util.List<Map<String, String>> results = new ArrayList<>();
            nomeTabela = (String)tableSelector.getSelectedItem();
            if (null != (String) tableSelector.getSelectedItem() ) switch ((String) tableSelector.getSelectedItem()) {
                case "clientes" -> {
                    if (!"".equals(idField.getText())){
                        String QUERY = String.format("SELECT * FROM %s WHERE id_cliente = %d", tableSelector.getSelectedItem(), Integer.parseInt(idField.getText()));
                        results = Projeto_a3.selectInDB(tabela.getIndexofDB((String) tableSelector.getSelectedItem()), (String) tableSelector.getSelectedItem(), QUERY);
                    }
                    else if (!"".equals(cpfField.getText())){
                        String QUERY = String.format("SELECT * FROM %s WHERE cpf_cliente = %d", nomeTabela, Integer.parseInt(cpfField.getText()));
                        results = Projeto_a3.selectInDB(tabela.getIndexofDB((String) tableSelector.getSelectedItem()), (String) tableSelector.getSelectedItem(), QUERY);
                    }
                    else if (!"".equals(idField.getText()) && !"".equals(cpfField.getText())){
                        String QUERY = String.format("SELECT * FROM %s WHERE cpf_cliente = %d AND id_cliente = %d", nomeTabela, Integer.parseInt(idField.getText()), Integer.parseInt(cpfField.getText()));
                        results = Projeto_a3.selectInDB(tabela.getIndexofDB((String) tableSelector.getSelectedItem()), (String) tableSelector.getSelectedItem(), QUERY);
                    } else {
                    String QUERY = String.format("SELECT * FROM %s ", nomeTabela);
                    results = Projeto_a3.selectInDB(tabela.getIndexofDB((String) tableSelector.getSelectedItem()), (String) tableSelector.getSelectedItem(), QUERY);
                    }
                }
                case "grupo_clientes" -> {
                    if (!"".equals(idField.getText())){
                        String QUERY = String.format("SELECT * FROM %s WHERE id_grupo = %d", nomeTabela, Integer.parseInt(idField.getText()));
                        results = Projeto_a3.selectInDB(tabela.getIndexofDB((String) tableSelector.getSelectedItem()), (String) tableSelector.getSelectedItem(), QUERY);
                    }
                    else if (!"".equals(cpfField.getText())){
                        String QUERY = String.format("SELECT * FROM %s WHERE cpf_raiz = %d", nomeTabela, Integer.parseInt(cpfField.getText()));
                        results = Projeto_a3.selectInDB(tabela.getIndexofDB((String) tableSelector.getSelectedItem()), (String) tableSelector.getSelectedItem(), QUERY);
                    }
                    else if (!"".equals(idField.getText()) && !"".equals(cpfField.getText())){
                        String QUERY = String.format("SELECT * FROM %s WHERE cpf_raiz = %d AND id_grupo = %d", nomeTabela, Integer.parseInt(idField.getText()), Integer.parseInt(cpfField.getText()));
                        results = Projeto_a3.selectInDB(tabela.getIndexofDB((String) tableSelector.getSelectedItem()), (String) tableSelector.getSelectedItem(), QUERY);
                    } else {
                    String QUERY = String.format("SELECT * FROM %s ", nomeTabela);
                    results = Projeto_a3.selectInDB(tabela.getIndexofDB((String) tableSelector.getSelectedItem()), (String) tableSelector.getSelectedItem(), QUERY);
                    }
                }
                case "tipo_clientes" -> {
                    if (!"".equals(idField.getText())){
                        String QUERY = String.format("SELECT * FROM %s WHERE id_tipo = %d", nomeTabela, Integer.parseInt(idField.getText()));
                        results = Projeto_a3.selectInDB(tabela.getIndexofDB((String) tableSelector.getSelectedItem()), (String) tableSelector.getSelectedItem(), QUERY);
                    } else {
                    String QUERY = String.format("SELECT * FROM %s ", nomeTabela);
                    results = Projeto_a3.selectInDB(tabela.getIndexofDB((String) tableSelector.getSelectedItem()), (String) tableSelector.getSelectedItem(), QUERY);
                    }
                }
                case "tipo_grupos" -> {
                    if (!"".equals(idField.getText())){
                        String QUERY = String.format("SELECT * FROM %s WHERE id_tipo_grupo = %d", nomeTabela, Integer.parseInt(idField.getText()));
                        results = Projeto_a3.selectInDB(tabela.getIndexofDB((String) tableSelector.getSelectedItem()), (String) tableSelector.getSelectedItem(), QUERY);
                    } else {
                    String QUERY = String.format("SELECT * FROM %s ", nomeTabela);
                    results = Projeto_a3.selectInDB(tabela.getIndexofDB((String) tableSelector.getSelectedItem()), (String) tableSelector.getSelectedItem(), QUERY);
                    }
                }
                default -> {
                    String QUERY = String.format("SELECT * FROM %s ", nomeTabela);
                    results = Projeto_a3.selectInDB(tabela.getIndexofDB((String) tableSelector.getSelectedItem()), (String) tableSelector.getSelectedItem(), QUERY);
                }
            }
            
            // Create a new frame instance
            JFrame frames = new JFrame("Database Result");
            frames.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frames.setSize(600, 400);

            DefaultTableModel model = new DefaultTableModel();
            JTable table = new JTable(model);

            if (!results.isEmpty()) {
                Map<String, String> firstRow = results.get(0);
                Set<String> columnNames = firstRow.keySet();

                // Add columns to the model dynamically
                for (String columnName : columnNames) {
                    model.addColumn(columnName);
                }

                // Add rows to the model
                for (Map<String, String> row : results) {
                    model.addRow(row.values().toArray());
                }

                JScrollPane scrollPane = new JScrollPane(table);
                frames.add(scrollPane);

                JButton exportButton = new JButton("Exportar para CSV");
                exportButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        exportToCSV(model);
                    }
                });

                // Add button to JFrame
                frames.add(exportButton);
                frames.setLayout(new BoxLayout(frames.getContentPane(), BoxLayout.Y_AXIS));
            } else {
                // Handle the case when there are no results (empty list)
                JOptionPane.showMessageDialog(null, "No results found", "Information", JOptionPane.INFORMATION_MESSAGE);
            }

            frames.setVisible(true);
        });

        updateButton.addActionListener((ActionEvent e) -> {
            Projeto_a3.updateInDB((String) tableSelector.getSelectedItem(), campoField.getText(), valorField.getText(), filtroField.getText(), Integer.parseInt(idField.getText()));
        });

        deleteButton.addActionListener((ActionEvent e) -> {
            Projeto_a3.deleteInDB((String) tableSelector.getSelectedItem(), campoField.getText(), filtroField.getText());
        }); 
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(tableLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(tableSelector, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(idLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(idField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(nomeLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(nomeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(cpfLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(cpfField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(tipoLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(tipoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(idGrupoLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(idGrupoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(campoLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        panel.add(campoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(valorLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
        panel.add(valorField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 8;
        panel.add(filtroLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 8;
        panel.add(filtroField, gbc);

        
        gbc.gridx = 0;
        gbc.gridy = 9;
        panel.add(createButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 9;
        panel.add(readButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 9;
        panel.add(updateButton, gbc);

        gbc.gridx = 3;
        gbc.gridy = 9;
        panel.add(deleteButton, gbc);

        add(panel, BorderLayout.NORTH);

        setVisible(true);
    }
}