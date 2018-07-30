/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.sgs.controle;

import br.ufc.sgs.dao.CandidatoDAO;
import br.ufc.sgs.dao.ConexaoFactory;
import br.ufc.sgs.dao.FaseDAO;
import br.ufc.sgs.dao.ParticipanteDAO;
import br.ufc.sgs.dao.ResultadoDAO;
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
public class AvaliarParticipante implements Logica{

    @Override
    public String executa(HttpServletRequest request, HttpServletResponse response) throws ServletControllerException {
        ConexaoFactory conexao = new ConexaoFactory();
        Connection con = conexao.abreConexao();
        try{
            int id_fase = Integer.parseInt(request.getParameter("id_fase"));
            Participante participante = new ParticipanteDAO(con).getParticipante(Integer.parseInt(request.getParameter("id_participante")));
            CriterioDeSelecao criterio = new FaseDAO(con).getFase(id_fase).getCriterioDeSelecao();
            String motivo = request.getParameter("motivo");
            if(criterio instanceof Nota_CS){
                float nota = Float.parseFloat(request.getParameter("nota"));
                Resultado resultado = criterio.avalia(nota, motivo);
                participante.setResultado(resultado);
                ResultadoDAO resultadoDAO = new ResultadoDAO(con);
                if(resultadoDAO.cadastra(resultado, participante.getId())){
                    int id_resultado = resultadoDAO.getResultado(participante).getId();
                    new ParticipanteDAO(con).atualiza(participante, id_resultado);
                    request.getSession().setAttribute("message", "Resultado atribuido com sucesso");
                    return "jsp_avaliador/avaliar.jsp?id_fase="+id_fase;
                }else{
                    request.getSession().setAttribute("message", "Resultado não foi atribuido");
                    return "jsp_avaliador/principal.jsp";
                }
                
            }

            
        } catch (SQLException ex) {
            Logger.getLogger(AvaliarParticipante.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DadoInvalidoException ex) {
            Logger.getLogger(AvaliarParticipante.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            conexao.fechaConexao();
        }
        return "jsp_avaliador/principal.jsp";
    }
    
}
