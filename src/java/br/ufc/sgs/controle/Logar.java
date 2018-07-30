/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.sgs.controle;

import br.ufc.sgs.dao.AvaliadorDAO;
import br.ufc.sgs.dao.CandidatoDAO;
import br.ufc.sgs.dao.ConexaoFactory;
import br.ufc.sgs.dao.GerenteDAO;
import br.ufc.sgs.dao.UsuarioDAO;
import br.ufc.sgs.model.exceptions.DadoInvalidoException;
import br.ufc.sgs.model.exceptions.ServletControllerException;
import br.ufc.sgs.modelo.usuario.Candidato;
import br.ufc.sgs.modelo.usuario.Gerente;
import br.ufc.sgs.modelo.usuario.Usuario;
import java.sql.Connection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author alexf
 */
public class Logar implements Logica{

    @Override
    public String executa(HttpServletRequest request, HttpServletResponse response) throws ServletControllerException{
        String email = request.getParameter("email"); // O email preenchido pelo usuário é armazenado
        String senha = request.getParameter("senha"); // A senha preenchida pelo usuário é armazenado
        
        // São verificados os parâmetros são válidos, ou seja, se não são nulos ou vazios
        if((email != null) || (! email.isEmpty()) && ((senha != null) || (! senha.isEmpty()))){
            ConexaoFactory conexao = new ConexaoFactory(); // É instanciado um objeto para a conexão com o banco de dados
            // O objeto DAO de usuario é instanciado e assim solicitado um registro no banco que correponda ao email e a senha informada
            Usuario usuario = new UsuarioDAO(conexao.abreConexao()).getUsuario(email, senha); 
            conexao.fechaConexao(); // A conexão com o banco é finalizada
            if(usuario == null){ // É verificado se a consulta no banco retornou algum registro
                // Caso não seja encontrado, será informada uma mensagem de que houve um erro
                request.getSession().setAttribute("mensagem", "Login ou senha inválidos");
                return "index.jsp"; // Será retornado a página inicial do sistema
            }else{ 
                // Caso um registro válido tenha sido encontrado
                request.getSession().setAttribute("usuario", usuario); // Será armazenado o objeto na sessão do usuário
                // Em seguida, será verificado o tipo de usuário que está sendo logado no sistema para assim retornar as suas devidas páginas inicias
                if(usuario instanceof Gerente){ 
                    return "jsp_gerente/principal.jsp";
                }else if(usuario instanceof Candidato){
                    return "jsp_candidato/principal.jsp";
                }else{
                    return "jsp_avaliador/principal.jsp";
                }
            }
            
        }else{
            // Caso haja erro na validação dos parametros, uma mensagem de erro será informada e será retornado a página inicial do sistema
            request.getSession().setAttribute("mensagem", "Os campos de login ou senha estão inválidos");
            return "index.jsp";
        }
        
        
    }
 }
