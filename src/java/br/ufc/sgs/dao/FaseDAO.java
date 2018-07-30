/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.sgs.dao;

import br.ufc.sgs.model.concurso.Concurso;
import br.ufc.sgs.model.concurso.Fase;
import br.ufc.sgs.model.concurso.Participante;
import br.ufc.sgs.model.concursos.criteriosDeSelecao.CriterioDeSelecao;
import br.ufc.sgs.modelo.usuario.Avaliador;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alexf
 */
public class FaseDAO extends ExecutaSQL{
    
    public FaseDAO(Connection conexao) {
        super(conexao);
    }

    public Fase getFase(int id_fase) throws SQLException{
        String sql = "SELECT id_concurso, titulo, descricao, dataInicio, dataTermino, criterioDeSelecao, isDivulgadoResultado FROM FASE WHERE id = ?" ; 
        PreparedStatement ps = getConexao().prepareStatement(sql);
        ps.setInt(1, id_fase);
        ResultSet rs = ps.executeQuery();
        ParticipanteDAO participanteDAO = new ParticipanteDAO(getConexao());
        AvaliadorDAO avaliadoresDAO = new AvaliadorDAO(getConexao());
        if(rs.next()){
            int id_concurso = rs.getInt("id_concurso");
            String titulo = rs.getString("titulo");
            String descricao = rs.getString("descricao");
            LocalDate dataInicio = rs.getDate("dataInicio").toLocalDate(); 
            LocalDate dataTermino = rs.getDate("dataTermino").toLocalDate();
            String criterioDeSelecao = rs.getString("criterioDeSelecao");
            boolean isDivulgadoResultado = rs.getBoolean("isDivulgadoResultado");
            
            Collection<Participante> participantes = participanteDAO.getParticipantes(id_fase);
            Collection<Avaliador> avaliadores = avaliadoresDAO.getAvaliadores(id_fase);
            Collection<String> documentosRequisitados = new DocumentosRequisitadosDAO(getConexao()).getDocumentosRequisitados(id_fase);
            return new Fase(id_fase, titulo, descricao, dataInicio, dataTermino, getCriterio(criterioDeSelecao), documentosRequisitados, participantes, avaliadores, isDivulgadoResultado);
        }
        return null;
    }
    
    public boolean cadastra(Fase fase, int id_concurso) throws SQLException{
        String sql = "INSERT INTO FASE(titulo, descricao, dataInicio, dataTermino, criterioDeSelecao, isDivulgadoResultado, id_concurso) VALUES (?,?,?,?,?,?,?)" ; 
        PreparedStatement ps = getConexao().prepareStatement(sql);
        
        ps.setString(1, fase.getTitulo());
        ps.setString(2, fase.getDescricao());
        ps.setDate(3, new Date(fase.getDataInicio().getYear() - 1900, fase.getDataInicio().getMonthValue() - 1, fase.getDataInicio().getDayOfMonth()));
        ps.setDate(4, new Date(fase.getDataTermino().getYear() - 1900, fase.getDataTermino().getMonthValue() - 1, fase.getDataTermino().getDayOfMonth()));
        ps.setString(5, fase.getCriterioDeSelecao().getClass().getSimpleName()); // Modificar isso daqui em atualizar também!
        ps.setBoolean(6, false);
        ps.setInt(7, id_concurso);
        
        return ps.executeUpdate() == 1;
    } 
    
    public boolean atualiza(Fase fase, int id_concurso) throws SQLException{
        String sql = "UPDATE FASE SET titulo=?, descricao=?, dataInicio=?, dataTermino=?, criterioDeSelecao=?, id_concurso=? WHERE id=?";
        PreparedStatement ps = getConexao().prepareStatement(sql);
        
        ps.setString(1, fase.getTitulo());
        ps.setString(2, fase.getDescricao());
        ps.setDate(3, new Date(fase.getDataInicio().toEpochDay()));
        ps.setDate(4, new Date(fase.getDataTermino().toEpochDay()));
        ps.setString(5, " ");
        ps.setInt(6, id_concurso);
        ps.setInt(7, fase.getId());
        //Olhe essa parte de setar o Criterio de Selecao e o Id do concurso
        return ps.executeUpdate() == 1;
        
    }
    
    public Collection<Fase> getFasesDoConcurso(int id_concurso) {
       try {
            String sql = "SELECT id, titulo, descricao, dataInicio, dataTermino, criterioDeSelecao, isDivulgadoResultado FROM FASE WHERE id_concurso = ?";
            PreparedStatement ps = getConexao().prepareStatement(sql);
            ps.setInt(1 , id_concurso);
            
            ResultSet rs = ps.executeQuery();
            ArrayList<Fase> fases = new ArrayList<>();
            ParticipanteDAO participanteDAO = new ParticipanteDAO(getConexao());
            AvaliadorDAO avaliadoresDAO = new AvaliadorDAO(getConexao());
            while(rs.next()){
                int id_fase = rs.getInt("id");
                String titulo = rs.getString("titulo");
                String descricao = rs.getString("descricao");
                LocalDate dataInicio = rs.getDate("dataInicio").toLocalDate(); 
                LocalDate dataTermino = rs.getDate("dataTermino").toLocalDate();
                String criterioDeSelecao = rs.getString("criterioDeSelecao");
                boolean isDivulgadoResultado = rs.getBoolean("isDivulgadoResultado");
                
                Collection<Participante> participantes = participanteDAO.getParticipantes(id_fase);
                Collection<Avaliador> avaliadores = avaliadoresDAO.getAvaliadores(id_fase);
                Collection<String> documentosRequisitados = new DocumentosRequisitadosDAO(getConexao()).getDocumentosRequisitados(id_fase);
                fases.add(new Fase(id_fase, titulo, descricao, dataInicio, dataTermino, getCriterio(criterioDeSelecao), documentosRequisitados, participantes, avaliadores, isDivulgadoResultado));
            }
            return fases;
        } catch (SQLException ex) {
            System.err.println("Erro com a sintaxe SQL no metodo de consulta. FaseDAO");    
        }
        return null;                  
    }

    private CriterioDeSelecao getCriterio(String criterioDeSelecao){
        try {
            return (CriterioDeSelecao) Class.forName("br.ufc.sgs.model.concursos.criteriosDeSelecao."+ criterioDeSelecao.trim()).newInstance();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FaseDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(FaseDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(FaseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
