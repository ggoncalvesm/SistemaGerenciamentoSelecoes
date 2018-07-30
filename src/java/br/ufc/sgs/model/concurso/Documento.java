/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.sgs.model.concurso;

import java.io.File;

/**
 *
 * @author alexf
 */
public class Documento {
    private final int id;
    private String nome;
    private File arquivo;

    public Documento(int id, String nome, File arquivo) {
        this.id = id;
        this.nome = nome;
        this.arquivo = arquivo;
    }

    public Documento() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public File getArquivo() {
        return arquivo;
    }

    public void setArquivo(File arquivo) {
        this.arquivo = arquivo;
    }

    
}
