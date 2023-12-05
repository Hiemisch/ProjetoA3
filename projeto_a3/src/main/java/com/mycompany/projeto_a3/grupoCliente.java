/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projeto_a3;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author usuario
 */
public class grupoCliente {
    private final int id;
    private final String nome;
    private final String cpf_raiz;
    private final int tipo;

    public grupoCliente(int id, String nome, String cpf_raiz, int tipo) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.cpf_raiz = cpf_raiz;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
    
    public String getCpf(){
        return cpf_raiz;
    }
    
    public int getTipo(){
        return tipo;
    }
    
    public Map<String, String> object2Dict(){
        Map<String, String> dictionary = new HashMap<>();
        dictionary.put("ID", Integer.toString(this.id));
        dictionary.put("Nome", this.nome);
        dictionary.put("CPF Raiz", this.cpf_raiz);
        dictionary.put("Tipo", Integer.toString(this.tipo));
        return dictionary;
    }
}

