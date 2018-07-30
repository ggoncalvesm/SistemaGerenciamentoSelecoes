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
public class Avaliador extends Usuario{
    private int id;
    public Avaliador(String nome, String email, String senha) throws DadoInvalidoException {
        super(nome, email, senha);
    }
    
    public Avaliador(int id, String nome, String email, String senha) throws DadoInvalidoException {
        super(nome, email, senha);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Avaliador other = (Avaliador) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
}
