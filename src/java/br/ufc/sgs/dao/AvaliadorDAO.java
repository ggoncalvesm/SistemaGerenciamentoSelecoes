/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.sgs.dao;

import br.ufc.sgs.model.exceptions.DadoInvalidoException;
import br.ufc.sgs.modelo.usuario.Avaliador;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alexf
 */
public class AvaliadorDAO extends ExecutaSQL{
    
    public AvaliadorDAO(Connection conexao) {
        super(conexao);
    }
    
    public Avaliador getAvaliador(String email, String senha) throws DadoInvalidoException{
        try {
            String sql = "SELECT id, nome FROM avaliador WHERE email=? AND senha=?";
            PreparedStatement ps = getConexao().prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, senha);
            
            ResultSet rs = ps.executeQuery();
            if(rs != null){
                rs.next();
                int id = rs.getInt(1);
                String nome = rs.getString(2);
                return new Avaliador(id, nome, email, senha);
            }
        } catch (SQLException ex) {
            System.err.println("Erro com a sintaxe SQL no metodo de consulta. AvaliadorDAO");    
        }
        return null;
    }

    public Avaliador getAvaliador(int aInt) throws DadoInvalidoException {
        try {
            String sql = "SELECT nome, email, senha FROM avaliador WHERE id=?";
            PreparedStatement ps = getConexao().prepareStatement(sql);
            ps.setInt(1, aInt);
            
            ResultSet rs = ps.executeQuery();
            if(rs != null){
                rs.next();
                String nome = rs.getString(1);
                String email = rs.getString(2);
                String senha = rs.getString(3);
                return new Avaliador(aInt, nome, email, senha);
            }
        } catch (SQLException ex) {
            System.err.println("Erro com a sintaxe SQL no metodo de consulta. AvaliadorDAO");    
        }
        return null;
    }

    public Collection<Avaliador> getAvaliadores(int id_fase) {
        try {
            String sql = "SELECT id, nome, email, senha FROM AVALIADOR WHERE id_fase = ?";
            PreparedStatement ps = getConexao().prepareStatement(sql);
            ps.setInt(1, id_fase);
            
            ResultSet rs = ps.executeQuery();
            ArrayList<Avaliador> avaliadores = new ArrayList<>();
            while(rs.next()){
                int id = rs.getInt(1);
                String nome = rs.getString(2);
                String email = rs.getString(3);
                String senha = rs.getString(4);
                 avaliadores.add(new Avaliador(id, nome, email, senha));
            }
            return avaliadores;
        } catch (SQLException ex) {
            System.err.println("Erro com a sintaxe SQL no metodo de consulta. AvaliadorDAO");    
        } catch (DadoInvalidoException ex) {
            return null;
        }
        return null;        
    }
    
        public Collection<Avaliador> getAvaliadores() {
        try {
            String sql = "SELECT id, nome, email, senha FROM AVALIADOR";
            PreparedStatement ps = getConexao().prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            ArrayList<Avaliador> avaliadores = new ArrayList<>();
            while(rs.next()){
                int id = rs.getInt(1);
                String nome = rs.getString(2);
                String email = rs.getString(3);
                String senha = rs.getString(4);
                avaliadores.add(new Avaliador(id, nome, email, senha));
            }
            return avaliadores;
        } catch (SQLException ex) {
            System.err.println("Erro com a sintaxe SQL no metodo de consulta. AvaliadorDAO");    
        } catch (DadoInvalidoException ex) {
            return null;
        }
        return null;        
    }
    
}
