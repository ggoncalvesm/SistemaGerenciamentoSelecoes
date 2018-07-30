/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.sgs.dao;

import br.ufc.sgs.model.concurso.Fase;
import br.ufc.sgs.model.concurso.Selecao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author alexf
 */
public class SelecaoDAO extends ExecutaSQL{
    
    public SelecaoDAO(Connection conexao) {
        super(conexao);
    }
    
    public boolean cadastra(Selecao s, int id_concurso) throws SQLException{
        String sql = "INSERT INTO SELECAO (id_concurso, titulo, descricao, responsavel, area, vagasRemuneradas, vagasVoluntarias) VALUES (?,?,?,?,?,?,?)";
        PreparedStatement ps = getConexao().prepareStatement(sql);
        ps.setInt(1, id_concurso);
        ps.setString(2, s.getTitulo());
        ps.setString(3, s.getDescricao());
        ps.setString(4, s.getResponsavel());
        ps.setString(5, s.getArea());
        ps.setInt(6, s.getVagasRemuneradas());
        ps.setInt(7, s.getVagasVoluntarias());
        return ps.executeUpdate() == 1;
    }

    public Collection<Selecao> getSelecoesDoConcurso(int id_concurso) {
        try {
            String sql = "SELECT id, titulo, descricao, responsavel, area, vagasRemuneradas, vagasVoluntarias FROM SELECAO WHERE id_concurso = ?";
            PreparedStatement ps = getConexao().prepareStatement(sql);
            ps.setInt(1 , id_concurso);
            
            ResultSet rs = ps.executeQuery();
            ArrayList<Selecao> selecoes = new ArrayList<>();
            while(rs.next()){
                int id_selecao = rs.getInt("id");
                String titulo = rs.getString("titulo");
                String area = rs.getString("area");
                String responsavel = rs.getString("responsavel");
                String descricao = rs.getString("descricao");
                int vagasRemuneradas = rs.getInt("vagasRemuneradas");
                int vagasVoluntarias = rs.getInt("vagasVoluntarias");
                selecoes.add(new Selecao(id_selecao, titulo, descricao, responsavel, area, vagasRemuneradas, vagasVoluntarias));
            }
            return selecoes;
        } catch (SQLException ex) {
            System.err.println("Erro com a sintaxe SQL no metodo de consulta. selecaoDAO");    
        }
        return null;
    }
    
        public boolean atualiza(Selecao selecao, int id_concurso) throws SQLException {
        String sql = "UPDATE 'selecao' SET id_concurso = '?', titulo = '?', descricao = '?' , responsavel = '?', area = '?', vagasRemuneradas = '?', vagasVoluntarias = '?' WHERE 'id'= ?";
        PreparedStatement ps = getConexao().prepareStatement(sql);
        ps.setInt(1, id_concurso);
        ps.setString(2, selecao.getTitulo());
        ps.setString(3, selecao.getDescricao());
        ps.setString(4, selecao.getResponsavel());
        ps.setString(5, selecao.getArea());
        ps.setInt(6, selecao.getVagasRemuneradas());
        ps.setInt(7, selecao.getVagasVoluntarias());
        ps.setInt(8, selecao.getId());
        return ps.executeUpdate() == 1;
    }
    
}
