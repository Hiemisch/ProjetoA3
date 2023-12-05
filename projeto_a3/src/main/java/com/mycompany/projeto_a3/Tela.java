package com.mycompany.projeto_a3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Tela extends JFrame {

    public final JTextField idField, nomeField, cpfField, tipoField, idGrupoField;
    public final JTextArea resultArea;
    public final JComboBox<String> tableSelector;
    public String nomeTabela;
    public String campoAlterado;
    public Object valor;
    public String ref;
    public int id;
    public String campo;
    public String filtro;
    public tabelasDB tabela = new tabelasDB();

    public Tela() {
        super("Tela F#oda");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        JPanel panel = new JPanel(new GridLayout(7, 2));

        JLabel tableLabel = new JLabel("Tabela:");
        JLabel idLabel = new JLabel("ID:");
        JLabel nomeLabel = new JLabel("Nome:");
        JLabel cpfLabel = new JLabel("CPF:");
        JLabel tipoLabel = new JLabel("Tipo:");
        JLabel idGrupoLabel = new JLabel("ID Grupo:");

        String[] tableNames = {"tipo_clientes", "grupo_clientes", "clientes", "tipo_grupos"};
        tableSelector = new JComboBox<>(tableNames);

        idField = new JTextField();
        nomeField = new JTextField();
        cpfField = new JTextField();
        tipoField = new JTextField();
        idGrupoField = new JTextField();

        JButton createButton = new JButton("Criar");
        JButton readButton = new JButton("Ler");
        JButton updateButton = new JButton("Atualizar");
        JButton deleteButton = new JButton("Excluir");

        resultArea = new JTextArea();

        createButton.addActionListener((ActionEvent e) -> {
            Projeto_a3.insertInDB(nomeTabela, ABORT);
        });

        readButton.addActionListener((ActionEvent e) -> {
            Projeto_a3.selectInDB(tabela.getIndexofDB((String) tableSelector.getSelectedItem()), (String) tableSelector.getSelectedItem());
        });

        updateButton.addActionListener((ActionEvent e) -> {
            Projeto_a3.updateInDB(nomeTabela, campoAlterado, valor, ref, id);
        });

        deleteButton.addActionListener((ActionEvent e) -> {
            Projeto_a3.deleteInDB(nomeTabela, campo, filtro);
        });

        panel.add(tableLabel);
        panel.add(tableSelector);
        panel.add(idLabel);
        panel.add(idField);
        panel.add(nomeLabel);
        panel.add(nomeField);
        panel.add(cpfLabel);
        panel.add(cpfField);
        panel.add(tipoLabel);
        panel.add(tipoField);
        panel.add(idGrupoLabel);
        panel.add(idGrupoField);
        panel.add(createButton);
        panel.add(readButton);
        panel.add(updateButton);
        panel.add(deleteButton);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        setVisible(true);
    }

//    public Connection getConnection() throws SQLException {
//        return DriverManager.getConnection("jdbc:mysql://localhost:3306/base_a3", "root", "310801");
//    }

//    public void criarRegistro() {
//        try (Connection connection = getConnection()) {
//            String tableName = (String) tableSelector.getSelectedItem();
//            List<String> columns = getColumnsForTable(tableName);
//
//            String sql = "INSERT INTO " + tableName + " (";
//            for (String column : columns) {
//                sql += column + ",";
//            }
//            sql = sql.substring(0, sql.length() - 1) + ") VALUES (";
//            for (int i = 0; i < columns.size(); i++) {
//                sql += "?,";
//            }
//            sql = sql.substring(0, sql.length() - 1) + ")";
//
//            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//                int parameterIndex = 1;
//                for (String column : columns) {
//                    preparedStatement.setString(parameterIndex++, getColumnValueFromField(column));
//                }
//
//                preparedStatement.executeUpdate();
//
//                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
//                if (generatedKeys.next()) {
//                    int generatedId = generatedKeys.getInt(1);
//                    resultArea.setText("Registro criado com sucesso. ID gerado: " + generatedId);
//                }
//            }
//        } catch (SQLException | NumberFormatException ex) {
//            resultArea.setText("Erro ao criar registro: " + ex.getMessage());
//        }
//    }
//
//    public void lerRegistros() {
//        try (Connection connection = getConnection()) {
//            String tableName = (String) tableSelector.getSelectedItem();
//            List<String> columns = getColumnsForTable(tableName);
//
//            String sql = "SELECT * FROM " + tableName;
//            try (Statement statement = connection.createStatement()) {
//                ResultSet resultSet = statement.executeQuery(sql);
//                StringBuilder resultText = new StringBuilder();
//
//                while (resultSet.next()) {
//                    for (String column : columns) {
//                        resultText.append(column).append(": ").append(resultSet.getString(column)).append(", ");
//                    }
//                    resultText.append("\n");
//                }
//
//                resultArea.setText(resultText.toString());
//            }
//        } catch (SQLException ex) {
//            resultArea.setText("Erro ao ler registros: " + ex.getMessage());
//        }
//    }
//
//    public void atualizarRegistro() {
//        try (Connection connection = getConnection()) {
//            String tableName = (String) tableSelector.getSelectedItem();
//            List<String> columns = getColumnsForTable(tableName);
//
//            int id = Integer.parseInt(idField.getText());
//
//            String setClause = "";
//            for (String column : columns) {
//                setClause += column + " = ?, ";
//            }
//            setClause = setClause.substring(0, setClause.length() - 2);
//
//            String sql = "UPDATE " + tableName + " SET " + setClause + " WHERE id_cliente = ?";
//            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//                int parameterIndex = 1;
//                for (String column : columns) {
//                    preparedStatement.setString(parameterIndex++, getColumnValueFromField(column));
//                }
//                preparedStatement.setInt(parameterIndex, id);
//
//                int rowsAffected = preparedStatement.executeUpdate();
//                if (rowsAffected > 0) {
//                    resultArea.setText("Registro atualizado com sucesso.");
//                } else {
//                    resultArea.setText("Registro não encontrado.");
//                }
//            }
//        } catch (SQLException | NumberFormatException ex) {
//            resultArea.setText("Erro ao atualizar registro: " + ex.getMessage());
//        }
//    }
//
//    public void excluirRegistro() {
//        try (Connection connection = getConnection()) {
//            String tableName = (String) tableSelector.getSelectedItem();
//            int id = Integer.parseInt(idField.getText());
//
//            String sql = "DELETE FROM " + tableName + " WHERE id_cliente = ?";
//            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//                preparedStatement.setInt(1, id);
//
//                int rowsAffected = preparedStatement.executeUpdate();
//                if (rowsAffected > 0) {
//                    resultArea.setText("Registro excluído com sucesso.");
//                } else {
//                    resultArea.setText("Registro não encontrado.");
//                }
//            }
//        } catch (SQLException | NumberFormatException ex) {
//            resultArea.setText("Erro ao excluir registro: " + ex.getMessage());
//        }
//    }
//
//    public List<String> getColumnsForTable(String tableName) {
//        List<String> columns = new ArrayList<>();
//        try (Connection connection = getConnection()) {
//            DatabaseMetaData metaData = connection.getMetaData();
//            ResultSet resultSet = metaData.getColumns(null, null, tableName, null);
//
//            while (resultSet.next()) {
//                columns.add(resultSet.getString("COLUMN_NAME"));
//            }
//        } catch (SQLException e) {
//            resultArea.setText("Erro ao obter colunas: " + e.getMessage());
//        }
//        return columns;
//    }
//
//    private String getColumnValueFromField(String columnName) {
//        return switch (columnName) {
//            case "nome_cliente" -> nomeField.getText();
//            case "cpf_cliente" -> cpfField.getText();
//            case "tipo_cliente" -> tipoField.getText();
//            case "id_grupo_cliente" -> idGrupoField.getText();
//            default -> null;
//        };
//    }
}
