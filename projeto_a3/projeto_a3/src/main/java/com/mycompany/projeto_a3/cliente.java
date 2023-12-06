/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projeto_a3;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Vic
 */
public class cliente {
    private final int id;
    private final String nome;
    private final String cpf_cliente;
    private final int tipo;
    private final int grupo_cliente;

    public cliente(int id, String nome, String cpf_cliente, int tipo, int grupo_cliente) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.cpf_cliente = cpf_cliente;
        this.grupo_cliente = grupo_cliente;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
    
    public String getCpf(){
        return cpf_cliente;
    }
    
    public int getTipo(){
        return tipo;
    }
    
    public int getGrupo(){
        return grupo_cliente;
    }
    
    public Map<String, String> object2Dict(){
        Map<String, String> dictionary = new HashMap<>();
        dictionary.put("ID", Integer.toString(this.id));
        dictionary.put("Nome", this.nome);
        dictionary.put("CPF", this.cpf_cliente);
        dictionary.put("Grupo", Integer.toString(this.grupo_cliente));
        dictionary.put("Tipo", Integer.toString(this.tipo));
        return dictionary;
    }
}
