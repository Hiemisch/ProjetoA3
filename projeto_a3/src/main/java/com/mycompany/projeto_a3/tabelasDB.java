/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projeto_a3;

import java.util.Arrays;

/**
 *
 * @author Victor
 */
public class tabelasDB {
    private final String[] tabelasDataBase;
    
    public tabelasDB() {
        String[] tabelasDB = new String[4];
        tabelasDB[0] = "clientes";
        tabelasDB[1] = "tipo_clientes";
        tabelasDB[2] = "grupo_clientes";
        tabelasDB[3] = "tipo_grupos";
        this.tabelasDataBase = tabelasDB;
    }
    
    public String getDataBaseName(int indx){
        return this.tabelasDataBase[indx];
    }
    
    public int getIndexofDB(String name){
        return Arrays.asList(this.tabelasDataBase).indexOf(name);
    }
}
