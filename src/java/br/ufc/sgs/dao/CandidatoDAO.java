/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.sgs.dao;

import br.ufc.sgs.model.exceptions.DadoInvalidoException;
import br.ufc.sgs.modelo.usuario.Candidato;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alexf
 */
public class CandidatoDAO extends ExecutaSQL{
    
    public CandidatoDAO(Connection conexao) {
        super(conexao);
    }
    
    public boolean cadastra(Candidato candidato) throws SQLException{
        String sql = "INSERT INTO CANDIDATO( matricula, nome, email, senha, curso, ano_entrada) VALUES (?,?,?,?,?,?)" ; 
        PreparedStatement ps = getConexao().prepareStatement(sql);
        
        ps.setInt(1, candidato.getMatricula());
        ps.setString(2, candidato.getNome());
        ps.setString(3, candidato.getEmail());
        ps.setString(4, candidato.getSenha());
        ps.setString(5, candidato.getCurso());
        ps.setFloat(6, candidato.getAno_entrada());
        
        return ps.executeUpdate() == 1;
    }    

    public Candidato getCandidato(String email, String senha) throws DadoInvalidoException{
        try {
            String sql = "SELECT matricula, nome, curso, ano_entrada FROM CANDIDATO WHERE email=? AND senha=?";
            PreparedStatement ps = getConexao().prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, senha);
            
            ResultSet rs = ps.executeQuery();
            if(rs != null){
                rs.next();
                int matricula = rs.getInt("matricula");
                String nome = rs.getString("nome");
                String curso = rs.getString("curso");
                float ano_entrada = rs.getFloat("ano_entrada");
                return new Candidato(nome, email, senha, matricula, curso, ano_entrada);
            }
        } catch (SQLException ex) {
            System.err.println("Erro com a sintaxe SQL no metodo de consulta 1. CandidatoDAO");    
        }
        return null;
    }
    
    public boolean atualizaDados(Candidato c) throws SQLException{
        String sql = "UPDATE CANDIDATO SET  nome = ?, curso = ?, ano_entrada = ?, email = ?, senha = ? FROM  WHERE matricula=?";
        PreparedStatement ps = getConexao().prepareStatement(sql);
        ps.setString(1, c.getNome());
        ps.setString(2, c.getCurso());
        ps.setFloat(3, c.getAno_entrada());
        ps.setString(4, c.getEmail());
        ps.setString(5, c.getSenha());
        ps.setInt(6, c.getMatricula());
        return ps.executeUpdate() == 1;
    }

    public Candidato getCandidato(int matricula) throws DadoInvalidoException {
        System.out.println(matricula);
        try {
            String sql = "SELECT nome, curso, email, senha, ano_entrada FROM CANDIDATO WHERE matricula = ?";
            PreparedStatement ps = getConexao().prepareStatement(sql);
            ps.setInt(1, matricula);
            
            ResultSet rs = ps.executeQuery();
            if(rs != null){
                rs.next();
                String nome = rs.getString("nome");
                String curso = rs.getString("curso");
                String email = rs.getString("email");
                String senha = rs.getString("senha");
                float ano_entrada = rs.getFloat("ano_entrada");
                Candidato candidato = new Candidato(nome, email, senha, matricula, curso, ano_entrada);
                System.out.println(candidato.toString());
                return candidato;
            }
        } catch (SQLException ex) {
            System.err.println("Erro com a sintaxe SQL no metodo de consulta 2. CandidatoDAO\n"+ ex.getMessage());    
        }
        return null;
    }
    
}
