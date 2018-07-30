/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.sgs.dao;

import br.ufc.sgs.model.concurso.Documento;
import br.ufc.sgs.model.concurso.Participante;
import br.ufc.sgs.model.concurso.Resultado;
import br.ufc.sgs.model.exceptions.DadoInvalidoException;
import br.ufc.sgs.modelo.usuario.Candidato;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author alexf
 */
public class ResultadoDAO extends ExecutaSQL{
    
    public ResultadoDAO(Connection conexao) {
        super(conexao);
    }

    public Resultado getResultado(int id_resultado) throws DadoInvalidoException {
        try{
            String sql = "SELECT id, isAprovado, nota, motivo FROM RESULTADO WHERE id=?";
            PreparedStatement ps = getConexao().prepareStatement(sql);
            ps.setInt(1, id_resultado);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                boolean isAprovado = rs.getBoolean("isAprovado");
                float nota = rs.getFloat("nota");
                String motivo = rs.getString("motivo");
                return new Resultado(id_resultado, isAprovado, nota, motivo);
            }
            return null;
        } catch(SQLException ex){
            System.err.println("Erro com a sintaxe SQL no metodo de consulta. participantesDAO");    
        }
    
        return null;        
    }
    public Resultado getResultado(Participante participante) throws DadoInvalidoException {
        try{
            String sql = "SELECT id, isAprovado, nota, motivo FROM RESULTADO WHERE id_participante=?";
            PreparedStatement ps = getConexao().prepareStatement(sql);
            ps.setInt(1, participante.getId());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int id_resultado = rs.getInt("id");
                boolean isAprovado = rs.getBoolean("isAprovado");
                float nota = rs.getFloat("nota");
                String motivo = rs.getString("motivo");
                return new Resultado(id_resultado, isAprovado, nota, motivo);
            }
            return null;
        } catch(SQLException ex){
            System.err.println("Erro com a sintaxe SQL no metodo de consulta. participantesDAO");    
        }
    
        return null;        
    }
    public boolean cadastra(Resultado resultado, int id_participante) throws SQLException {
        String sql = "INSERT INTO RESULTADO(isAprovado, nota, motivo, id_participante) VALUES (?,?,?,?)" ; 
        PreparedStatement ps = getConexao().prepareStatement(sql);
        
        
        ps.setBoolean(1, resultado.isIsAprovado());
        ps.setFloat(2, resultado.getNota());
        ps.setString(3, resultado.getMotivo());
        ps.setInt(4, id_participante);
        
        return ps.executeUpdate() == 1;        
    }

        
}
