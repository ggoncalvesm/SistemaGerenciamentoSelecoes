/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.sgs.controle;

import br.ufc.sgs.dao.ConexaoFactory;
import br.ufc.sgs.dao.FaseDAO;
import br.ufc.sgs.dao.SelecaoDAO;
import br.ufc.sgs.model.concurso.Fase;
import br.ufc.sgs.model.concurso.Selecao;
import br.ufc.sgs.model.exceptions.ServletControllerException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.util.converter.LocalDateStringConverter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Lavinia
 */
public class CadastraSelecao implements Logica{

    @Override
    public String executa(HttpServletRequest request, HttpServletResponse response) throws ServletControllerException {
        ConexaoFactory conexao = new ConexaoFactory();
        try{
            int id_concurso = Integer.parseInt(request.getParameter("id_concurso"));
            String titulo = request.getParameter("titulo");
            String descricao = request.getParameter("descricao");
            String responsavel = request.getParameter("responsavel");
            String area = request.getParameter("area");
            int vagasRemuneradas = Integer.parseInt(request.getParameter("vagasRemuneradas"));
            int vagasVoluntarias = Integer.parseInt(request.getParameter("vagasVoluntarias"));
            
            Selecao selecao = new Selecao(id_concurso, titulo, responsavel, descricao, area, vagasRemuneradas, vagasVoluntarias);
            SelecaoDAO selecaoDAO = new SelecaoDAO(conexao.abreConexao());
            if(selecaoDAO.cadastra(selecao, id_concurso)){
                return "jsp_gerente/concurso.jsp?id="+request.getParameter("id_concurso");
            }else{
                request.getSession().setAttribute("mensagem", "Cadastro não realizado!");
                return "jsp_gerente/cadastrar_selecao.jsp";
            }
        }catch (SQLException ex) {
            request.getSession().setAttribute("mensagem", "Erro no SQL");
            System.out.println("Erro no código SQL");
            return "login.jsp";
        }finally{
            conexao.fechaConexao();
        }
    }
    
}
