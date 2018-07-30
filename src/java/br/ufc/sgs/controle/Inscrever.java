package br.ufc.sgs.controle;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import br.ufc.sgs.dao.CandidatoDAO;
import br.ufc.sgs.dao.ConcursoDAO;
import br.ufc.sgs.dao.ConexaoFactory;
import br.ufc.sgs.dao.FaseDAO;
import br.ufc.sgs.dao.ParticipanteDAO;
import br.ufc.sgs.model.concurso.Concurso;
import br.ufc.sgs.model.concurso.Fase;
import br.ufc.sgs.model.concurso.Participante;
import br.ufc.sgs.model.exceptions.DadoInvalidoException;
import br.ufc.sgs.model.exceptions.ServletControllerException;
import br.ufc.sgs.modelo.usuario.Candidato;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.converter.LocalDateStringConverter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 *
 * @author Gabriel
 */
public class Inscrever implements Logica{

    @Override
    public String executa(HttpServletRequest request, HttpServletResponse response) throws ServletControllerException {
        ConexaoFactory conexao = new ConexaoFactory();
        try{
            Connection connec = conexao.abreConexao();
            int id_concurso = Integer.parseInt(request.getParameter("id_fase"));
            int id_candidato = Integer.parseInt(request.getParameter("id_candidato"));
            Candidato c = new CandidatoDAO(connec).getCandidato(id_candidato);
            Concurso conc = new ConcursoDAO(connec).consultaConcurso(id_concurso);
            Participante par = new Participante(c, null, null);
            if(new ParticipanteDAO(connec).cadastra(par, id_concurso)){
                request.getSession().setAttribute("mensagem", "Inscrição realizada com sucesso!");
                return "jsp_candidato/concurso.jsp?id="+request.getParameter("id_concurso");
            }else{
                request.getSession().setAttribute("mensagem", "Cadastro não realizado!");
                return "jsp_candidato/principal.jsp";
            }
            
        } catch (DadoInvalidoException ex) {
            Logger.getLogger(Inscrever.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Inscrever.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            conexao.fechaConexao();
        }
        return null;
    }
    
}
