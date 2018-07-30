/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.sgs.modelo.usuario;

import br.ufc.sgs.model.exceptions.DadoInvalidoException;

/**
 *
 * @author alexf
 */
public abstract class Usuario {
    private String nome;
    private String email;
    private String senha;
    
    public Usuario(String nome, String email, String senha) throws DadoInvalidoException {
        setNome(nome);
        setEmail(email);
        setSenha(senha);
    }

    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws DadoInvalidoException {
        if(nome == null) throw new DadoInvalidoException("Nome do usuário não especificado");
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws DadoInvalidoException {
        if(email == null) throw new DadoInvalidoException("Nome do usuário não especificado");
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) throws DadoInvalidoException {
        if(senha == null) throw new DadoInvalidoException("Nome do usuário não especificado");
        this.senha = senha;
    }

    
}
