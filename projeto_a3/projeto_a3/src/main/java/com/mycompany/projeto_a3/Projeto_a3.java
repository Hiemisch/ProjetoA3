package com.mycompany.projeto_a3;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Victor Leme Beltran & Gabriel Hiemisch Cordeiro
 */
public class Projeto_a3 {

    static final String DB_URL = "jdbc:mysql://localhost/base_a3";
    static final String USER = "root";
    static final String PASS = "password";
    static final tabelasDB tabelas = new tabelasDB();
    
    public static List<Map<String, String>> selectInDB (int TABLE_ID, String nomeTabela, String QUERY){
        System.out.println("EL ID ÉS " + TABLE_ID);
        System.out.println("Lets see how the query goes " + QUERY);
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(QUERY)) {
            
            List<Map<String, String>> results = new ArrayList<>();

            queryHandler handler = new queryHandler();
            switch (TABLE_ID){
                case 0 -> {
                    results = handler.handleQueryCliente(rs);
                }
                case 1 -> {
                    results = handler.handleQueryTipoCliente(rs);
                }
                case 2 -> {
                    results = handler.handleQueryGrupoCliente(rs);
                }
                case 3 -> {
                    results = handler.handleQueryTipoGrupo(rs);
                }
            }
            stmt.close();
            return results;
            
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    };
    
    public static int updateInDB(String nomeTabela, String campoAlterado, Object valor, String ref, int id) {
        String query;
        
        if (valor instanceof String) {
            query = String.format("UPDATE %s SET %s = ? WHERE %s = ?", nomeTabela, campoAlterado, ref);
        } else {
            query = String.format("UPDATE %s SET %s = ? WHERE %s = ?", nomeTabela, campoAlterado, ref);
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setObject(1, valor);
            pstmt.setInt(2, id);
            int rowsAffected = pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Registro alterado com sucesso!", "Success", JOptionPane.INFORMATION_MESSAGE);
            return rowsAffected > 0 ? 200 : 500;

        } catch (SQLException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Não foi possivel alterar seu registro, verifique os campos!", "Error", JOptionPane.ERROR_MESSAGE);
            return 500;
        }
    }

    public static int insertInDB(String nomeTabela, Object objeto) {
        String query = null;

        if (objeto instanceof cliente) {
            query = String.format("INSERT INTO %s (nome_cliente, cpf_cliente, tipo_cliente, id_grupo_cliente) VALUES (?, ?, ?, ?)", 
                    nomeTabela);
        } else if (objeto instanceof grupoCliente) {
            query = String.format("INSERT INTO %s (nome_grupo, cpf_raiz, tipo_grupo) VALUES (?, ?, ?)", 
                    nomeTabela);
        } else if (objeto instanceof tipoCliente) {
            query = String.format("INSERT INTO %s (nome_tipo) VALUES (?)", 
                    nomeTabela);
        } else if (objeto instanceof tipoGrupo) {
            query = String.format("INSERT INTO %s (nome) VALUES (?)", 
                    nomeTabela);
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            if (objeto instanceof cliente cliente) {
                pstmt.setString(1, cliente.getNome());
                pstmt.setString(2, cliente.getCpf());
                pstmt.setInt(3, cliente.getTipo());
                pstmt.setInt(4, cliente.getGrupo());
            } else if (objeto instanceof grupoCliente grupoCliente) {
                pstmt.setString(1, grupoCliente.getNome());
                pstmt.setString(2, grupoCliente.getCpf());
                pstmt.setInt(3, grupoCliente.getTipo());
            } else if (objeto instanceof tipoCliente tipoCliente) {
                pstmt.setString(1, tipoCliente.getNome());
            } else if (objeto instanceof tipoGrupo tipoGrupo) {
                pstmt.setString(1, tipoGrupo.getNome());
            }
            int rowsAffected = 0;
            try {rowsAffected = pstmt.executeUpdate();

            } catch (Exception e){
                JOptionPane.showMessageDialog(null, "Erro, verifique os campos!", "Error", JOptionPane.ERROR_MESSAGE);
                return 500;
            }
            JOptionPane.showMessageDialog(null, "Registro incluido com sucesso", "Success", JOptionPane.INFORMATION_MESSAGE);
            return rowsAffected > 0 ? 200 : 500;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro, verifique os campos!", "Error", JOptionPane.ERROR_MESSAGE);
            return 500;
        }
        
    };
    
    public static boolean deleteInDB(String nomeTabela, String campo, String filtro) {
        String query = String.format("DELETE FROM %s WHERE %s = ?", nomeTabela, campo);

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, filtro);

            int rowsAffected = pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Registro excluido com sucesso", "Success", JOptionPane.INFORMATION_MESSAGE);
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Erro, verifique os campos!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static void main(String[] args) {
//        // ISTO É UM TESTE
//        int TABLE_ID = 3;
//        String nomeTabela = tabelas.getDataBaseName(TABLE_ID);
//        
          Tela tela = new Tela();
//        
//        // inputString.replaceAll("[^a-zA-Z0-9]", "");
//        List<Map<String, String>> retorno = selectInDB(TABLE_ID, nomeTabela);
//        
//                // Printing the contents of the retorno variable
//        for (Map<String, String> map : retorno) {
//            System.out.println("Map:");
//            for (Map.Entry<String, String> entry : map.entrySet()) {
//                System.out.println(entry.getKey() + ": " + entry.getValue());
//            }
//            System.out.println();
//        }
//        
//        //inputString.replaceAll("[^a-zA-Z0-9]", "");
//        //insertInDB (String nomeTabela, Object objeto)
//        tipoGrupo grupo = new tipoGrupo(5, "GrupoTeste");
//        int returnInsert = insertInDB(nomeTabela, grupo);
//        System.out.println(returnInsert);
//        
//        //updateInDB (String nomeTabela, String campoAlterado, Object valor, String ref, int id)
//        int returnupdate = updateInDB(nomeTabela, "nome", "GIGA", "id_tipo_grupo", 1);
//        System.out.println(returnupdate);
//        
//        //deleteInDB (String nomeTabela, String campo, String filtro)
//        boolean returndelete = deleteInDB(nomeTabela, "nome", "GIGA");
//        System.out.println(returndelete);
    }
}


