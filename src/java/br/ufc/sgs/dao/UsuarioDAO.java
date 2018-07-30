/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.sgs.dao;

import br.ufc.sgs.model.exceptions.DadoInvalidoException;
import br.ufc.sgs.modelo.usuario.Usuario;
import java.sql.Connection;

/**
 *
 * @author alexf
 */
public class UsuarioDAO extends ExecutaSQL{
    
    public UsuarioDAO(Connection conexao) {
        super(conexao);
    }
    
    public Usuario getUsuario(String email, String senha){
        try{
            Usuario usuario;
            usuario = new GerenteDAO(getConexao()).getGerente(email, senha);
            if(usuario == null){
                usuario = new CandidatoDAO(getConexao()).getCandidato(email, senha);
                if(usuario == null)
                    usuario = new AvaliadorDAO(getConexao()).getAvaliador(email, senha);
            }
            return usuario;
        }catch(DadoInvalidoException ex){
            return null;
        }       
    }
}
