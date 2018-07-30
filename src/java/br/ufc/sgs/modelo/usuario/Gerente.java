/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.sgs.modelo.usuario;

import br.ufc.sgs.model.concurso.Concurso;
import br.ufc.sgs.model.exceptions.DadoInvalidoException;
import java.util.Collection;

/**
 *
 * @author alexf
 */
public class Gerente extends Usuario{
    private Collection<Concurso> concursos;
    private int id;
    
    public Gerente(String nome, String email, String senha) throws DadoInvalidoException {
        super(nome, email, senha);
    }
    
    public Gerente(int id,String nome, String email, String senha) throws DadoInvalidoException {
        super(nome, email, senha);
        setId(id);
    }

    
    public Collection<Concurso> getConcursos() {
        return concursos;
    }    

    public void setId(int id){
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
}
