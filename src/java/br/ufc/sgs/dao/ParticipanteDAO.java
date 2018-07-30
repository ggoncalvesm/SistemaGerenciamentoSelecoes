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
public class ParticipanteDAO extends ExecutaSQL{
    
    public ParticipanteDAO(Connection conexao) {
        super(conexao);
    }
 
    public Collection<Participante> getParticipantes(int id_fase){
        try{
            String sql = "SELECT id, id_candidato, id_resultado FROM PARTICIPANTE WHERE id_fase=?";
            PreparedStatement ps = getConexao().prepareStatement(sql);
            ps.setInt(1, id_fase);
            ResultSet rs = ps.executeQuery();

            ArrayList<Participante> participantes = new ArrayList<>();
            while(rs.next()){
                int id_participante = rs.getInt("id");
                int id_candidato = rs.getInt("id_candidato");
                int id_resultado = rs.getInt("id_resultado");
                
                Candidato candidato = new CandidatoDAO(getConexao()).getCandidato(id_candidato);
                Collection<Documento> documentos = new DocumentoDAO(getConexao()).getDocumentosDoParticipante(id_participante);
                Resultado resultado = new ResultadoDAO(getConexao()).getResultado(id_resultado);
                participantes.add(new Participante(id_participante, candidato, documentos, resultado));
            }
            return participantes;
        } catch(SQLException ex){
            System.err.println("Erro com a sintaxe SQL no metodo de consulta. participantesDAO");    
        } catch(DadoInvalidoException ex){
            
        }
    
        return null;
    }

    public Participante getParticipante(int id_participante) {
        try{
            String sql = "SELECT id, id_candidato, id_resultado FROM PARTICIPANTE WHERE id=?";
            PreparedStatement ps = getConexao().prepareStatement(sql);
            ps.setInt(1, id_participante);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                int id_candidato = rs.getInt("id_candidato");
                int id_resultado = rs.getInt("id_resultado");
                
                Candidato candidato = new CandidatoDAO(getConexao()).getCandidato(id_candidato);
                Collection<Documento> documentos = new DocumentoDAO(getConexao()).getDocumentosDoParticipante(id_participante);
                Resultado resultado = new ResultadoDAO(getConexao()).getResultado(id_resultado);
                return new Participante(id_participante, candidato, documentos, resultado);
            }
        } catch(SQLException ex){
            System.err.println("Erro com a sintaxe SQL no metodo de consulta. participantesDAO");    
        } catch(DadoInvalidoException ex){
            
        }
        return null;
    }
    
    public boolean atualiza(Participante participante, int id_resultado) throws SQLException{
        String sql = "UPDATE PARTICIPANTE SET id_resultado=? WHERE id=?";
        PreparedStatement ps = getConexao().prepareStatement(sql);
        
        ps.setInt(1, id_resultado);
        ps.setInt(2, participante.getId());

        return ps.executeUpdate() == 1;
        
    }    

    public boolean cadastra(Participante par, int id_fase) throws SQLException {
       String sql = "INSERT INTO participante(id_candidato, id_resultado, id_fase, id_selecao) VALUES (?,?,?,?)" ; 
        PreparedStatement ps = getConexao().prepareStatement(sql);
        
        ps.setInt(1, par.getCandidato().getMatricula());
        ps.setInt(2, 0); // Não avaliado
        ps.setInt(3, id_fase);
        ps.setInt(4, par.getSelecao().getId());
        
        return ps.executeUpdate() == 1;
    }
}
