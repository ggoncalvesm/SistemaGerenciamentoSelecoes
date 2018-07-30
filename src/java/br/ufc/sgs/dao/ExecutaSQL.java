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
// Esta classe está responsável por armazenar a conexão com o banco
public abstract class ExecutaSQL {
    private Connection conexao;
    public ExecutaSQL(Connection conexao){
        setConexao(conexao);
    }
   
    public Connection getConexao(){
        return this.conexao;
    }
    
    public void setConexao(Connection conexao){
        if(conexao != null)
            this.conexao = conexao;
    }
     
}
