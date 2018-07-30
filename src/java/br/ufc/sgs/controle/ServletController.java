package br.ufc.sgs.controle;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import br.ufc.sgs.model.exceptions.ServletControllerException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author alexf
 */
@WebServlet("/ServletController")
public class ServletController extends HttpServlet {

    /**
     * Todas as requisições do sistema serão enviadas para esta classe
     * que está responsável por instânciar os objetos de controle, em que
     * as ações são realizadas.
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String acao = request.getParameter("acao"); // Será verificada qual a ação a ser realizada
        System.out.println(acao);
        if(acao != null){
            try {
                // A ação é instânciada e em seguida executada
                Logica logica = (Logica) Class.forName("br.ufc.sgs.controle."+acao).newInstance();
                String pagina = logica.executa(request, response);
                // Será retornado a página onde o usuário será encaminhado
                response.sendRedirect(pagina);
                //request.getRequestDispatcher(pagina).forward(request, response); // Realiza a operação de redirecionamento
            } catch(ClassNotFoundException | InstantiationException | IllegalAccessException | ServletControllerException ex) {
                request.setAttribute("mensagem", "Desculpe, houve um erro interno, tente novamente mais tarde");
            }
            
        }
        
    }


}
