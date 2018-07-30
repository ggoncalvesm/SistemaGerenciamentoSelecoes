/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.sgs.dao;

import java.sql.*;

/**
 *
 * @author alexf
 */
public class ConexaoFactory {
    private Connection conexao;
    public Connection abreConexao(){
        if(conexao != null) fechaConexao();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String caminhoServidor = "jdbc:mysql://localhost/sgs2";
            conexao = DriverManager.getConnection(caminhoServidor, "root", "cavaleirosz");
            return conexao;
        }catch(SQLException e){
            throw new RuntimeException(e);
        } catch (ClassNotFoundException ex) {
            System.out.println("Erro com o driver do JDBC");
        }
        return null;
    }
    
    public void fechaConexao(){
        if(conexao != null){
            try {
                conexao.close();
            } catch (SQLException e) {
               throw new RuntimeException(e);
            }            
        }

    }
}
