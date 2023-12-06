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
public class tipoGrupo {
    
    private final int id;
    private final String nome;

    public tipoGrupo(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
    
    public Map<String, String> object2Dict(){
        Map<String, String> dictionary = new HashMap<>();
        dictionary.put("ID", Integer.toString(this.id));
        dictionary.put("Nome", this.nome);
        return dictionary;
    }
}
