/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projeto_a3;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author usuario
 */
public class queryHandler {
    
    public queryHandler() {
    }
    
    public List<Map<String, String>> handleQueryTipoCliente(ResultSet resultadoQuery){
        List<tipoCliente> listaRetorno = new ArrayList<>();
        List<Map<String, String>> listaRetornoDict = new ArrayList<>();

        try {
            while (resultadoQuery.next()) {
                // Retrieve by column name
                int id = resultadoQuery.getInt("id_tipo");
                String nome = resultadoQuery.getString("nome_tipo");

                // Create TipoCliente object and add to the list
                tipoCliente tipoCliente = new tipoCliente(id, nome);
                listaRetorno.add(tipoCliente);
            }
        } catch (SQLException ex) {
            Logger.getLogger(queryHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

            // Display the retrieved data
            for (tipoCliente tipoCliente : listaRetorno) {
                System.out.println("ID: " + tipoCliente.getId() + ", Nome: " + tipoCliente.getNome());
                listaRetornoDict.add(tipoCliente.object2Dict());
            }
        return listaRetornoDict;
    }
    
    public List<Map<String, String>> handleQueryCliente(ResultSet resultadoQuery){
        List<cliente> listaRetorno = new ArrayList<>();
        List<Map<String, String>> listaRetornoDict = new ArrayList<>();

        try {
            while (resultadoQuery.next()) {
                // Retrieve by column name
                int id = resultadoQuery.getInt("id_cliente");
                String nome = resultadoQuery.getString("nome_cliente");
                String cpf_cliente = resultadoQuery.getString("cpf_cliente");
                int tipo = resultadoQuery.getInt("tipo_cliente");
                int grupo_cliente = resultadoQuery.getInt("grupo_cliente");
                

                // Create TipoCliente object and add to the list
                cliente client = new cliente(id, nome, cpf_cliente, tipo, grupo_cliente);
                listaRetorno.add(client);
            }
        } catch (SQLException ex) {
            Logger.getLogger(queryHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

            // Display the retrieved data
            for (cliente clientrec : listaRetorno) {
                System.out.println("ID: " + clientrec.getId() + ", Nome: " + clientrec.getNome());
                listaRetornoDict.add(clientrec.object2Dict());
            }
        return listaRetornoDict;
    }
        
    public List<Map<String, String>> handleQueryGrupoCliente(ResultSet resultadoQuery){
        List<grupoCliente> listaRetorno = new ArrayList<>();
        List<Map<String, String>> listaRetornoDict = new ArrayList<>();
        
        try {
            while (resultadoQuery.next()) {
                // Retrieve by column name
                int id = resultadoQuery.getInt("id_grupo");
                String nome = resultadoQuery.getString("nome_grupo");
                String cpf_raiz = resultadoQuery.getString("cpf_raiz");
                int tipo = resultadoQuery.getInt("tipo_grupo");

                // Create TipoCliente object and add to the list
                grupoCliente grupocliente = new grupoCliente(id, nome, cpf_raiz, tipo);
                listaRetorno.add(grupocliente);
            }
        } catch (SQLException ex) {
            Logger.getLogger(queryHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

            // Display the retrieved data
            for (grupoCliente grupoclient : listaRetorno) {
                System.out.println("ID: " + grupoclient.getId() + ", Nome: " + grupoclient.getNome());
                listaRetornoDict.add(grupoclient.object2Dict());
            }
        return listaRetornoDict;
    }
        
    public List<Map<String, String>> handleQueryTipoGrupo(ResultSet resultadoQuery){
        List<tipoGrupo> listaRetorno = new ArrayList<>();
        List<Map<String, String>> listaRetornoDict = new ArrayList<>();
        
        try {
            while (resultadoQuery.next()) {
                // Retrieve by column name
                int id = resultadoQuery.getInt("id_tipo_grupo");
                String nome = resultadoQuery.getString("nome");

                // Create TipoCliente object and add to the list
                tipoGrupo tipogrupo = new tipoGrupo(id, nome);
                listaRetorno.add(tipogrupo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(queryHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

            // Display the retrieved data
            for (tipoGrupo tipogroup : listaRetorno) {
                System.out.println("ID: " + tipogroup.getId() + ", Nome: " + tipogroup.getNome());
                listaRetornoDict.add(tipogroup.object2Dict());
            }
        return listaRetornoDict;
    }
}
