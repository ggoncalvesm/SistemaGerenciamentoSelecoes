/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.sgs.controle;

import br.ufc.sgs.dao.CandidatoDAO;
import br.ufc.sgs.dao.ConcursoDAO;
import br.ufc.sgs.dao.ConexaoFactory;
import br.ufc.sgs.dao.FaseDAO;
import br.ufc.sgs.dao.ParticipanteDAO;
import br.ufc.sgs.dao.ResultadoDAO;
import br.ufc.sgs.model.concurso.Concurso;
import br.ufc.sgs.model.concurso.Participante;
import br.ufc.sgs.model.concurso.Resultado;
import br.ufc.sgs.model.concursos.criteriosDeSelecao.CriterioDeSelecao;
import br.ufc.sgs.model.concursos.criteriosDeSelecao.Nota_CS;
import br.ufc.sgs.model.exceptions.DadoInvalidoException;
import br.ufc.sgs.model.exceptions.ServletControllerException;
import br.ufc.sgs.modelo.usuario.Candidato;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Lavinia
 */
public class DivulgarConcurso implements Logica{

    @Override
    public String executa(HttpServletRequest request, HttpServletResponse response) throws ServletControllerException {
        ConexaoFactory conexao = new ConexaoFactory();
        Connection con = conexao.abreConexao();
        int id_concurso = Integer.parseInt(request.getParameter("id_concurso"));
        Concurso concurso = new ConcursoDAO(con).consultaConcurso(id_concurso);
        concurso.setIsDivulgado(true);
        new ConcursoDAO(con).divulgaConcurso(id_concurso);
        conexao.fechaConexao();
        request.getSession().setAttribute("mensagem", "O concurso '" + concurso.getTitulo() + "' acaba de ser divulgado!");
        return "jsp_gerente/concurso.jsp?id="+id_concurso;
    }
    
}
