/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.sgs.dao;

import br.ufc.sgs.model.concurso.Fase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author alexf
 */
public class DocumentosRequisitadosDAO extends ExecutaSQL{
    
    public DocumentosRequisitadosDAO(Connection conexao) {
        super(conexao);
    }

    public Collection<String> getDocumentosRequisitados(int id_fase){
        System.out.println("sakdjsldkajdljsdlkdj");
       try {
            String sql = "SELECT descricao FROM DOCUMENTOSREQUISITADOS WHERE id_fase = ?";
            PreparedStatement ps = getConexao().prepareStatement(sql);
            ps.setInt(1 , id_fase);
            
            ResultSet rs = ps.executeQuery();
            ArrayList<String> documentosRequisitados = new ArrayList<>();
            while(rs.next()){
                
                String descricao = rs.getString("descricao");
                documentosRequisitados.add(descricao);
            }
            return documentosRequisitados;
        } catch (SQLException ex) {
            System.err.println("Erro com a sintaxe SQL no metodo de consulta. DocumentosRequisitadosDAO");    
        }
        return null;           
    }
}
