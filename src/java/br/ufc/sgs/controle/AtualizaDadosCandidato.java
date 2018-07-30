package br.ufc.sgs.controle;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import br.ufc.sgs.dao.CandidatoDAO;
import br.ufc.sgs.dao.ConexaoFactory;
import br.ufc.sgs.model.exceptions.DadoInvalidoException;
import br.ufc.sgs.model.exceptions.ServletControllerException;
import br.ufc.sgs.modelo.usuario.Candidato;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 *
 * @author Gabriel
 */
public class AtualizaDadosCandidato implements Logica{

    @Override
    public String executa(HttpServletRequest request, HttpServletResponse response) throws ServletControllerException {
        ConexaoFactory conexao = new ConexaoFactory();
        try{
            String senhaAtual = request.getParameter("senhaAtual");
            Candidato candidato = (Candidato)request.getSession().getAttribute("usuario");
            if(senhaAtual.equals(candidato.getSenha())){
                String nome = request.getParameter("nome");
                String email = request.getParameter("email");
                String senha = request.getParameter("senha");
                int matricula = Integer.parseInt(request.getParameter("matricula"));
                String curso = request.getParameter("curso");
                float ano_entrada = Float.parseFloat(request.getParameter("ano_entrada"));
                candidato = new Candidato(nome, email, senha, matricula, curso, ano_entrada);
                CandidatoDAO candidatoDAO = new CandidatoDAO(conexao.abreConexao());
                if(candidatoDAO.atualizaDados(candidato)){
                    request.getSession().setAttribute("usuario", candidato);
                    return "/WEB-INF/jsp_candidato/principal.jsp";
                }else{
                    return "/WEB-INF/jsp_candidato/atualizaDados.jsp";
                }
            }else{
                throw new DadoInvalidoException("A senha informada não está correta");
            }
        }catch(DadoInvalidoException ex){
            request.getSession().setAttribute("mensagem", ex.getMessage());
            return "/WEB-INF/jsp.candidato/atualizaDados.jsp";
        } catch (SQLException ex) {
            request.getSession().setAttribute("mensagem", "Erro no SQL");
            System.out.println("Erro no código SQL");
            return "/WEB-INF/jsp_candidato/principal.jsp";
        }finally{
            conexao.fechaConexao();
        }
    }
    
}
