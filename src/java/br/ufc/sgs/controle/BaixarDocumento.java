/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.sgs.controle;

import br.ufc.sgs.dao.CandidatoDAO;
import br.ufc.sgs.dao.ConexaoFactory;
import br.ufc.sgs.dao.DocumentoDAO;
import br.ufc.sgs.model.concurso.Documento;
import br.ufc.sgs.model.exceptions.ServletControllerException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
  Referência : http://andvicoso.blogspot.com.br/2013/02/como-fazer-upload-e-download-de.html
 */
public class BaixarDocumento implements Logica{

    @Override
    public String executa(HttpServletRequest request, HttpServletResponse response) throws ServletControllerException {
        int id = Integer.parseInt(request.getParameter("id"));
        System.out.println(id);
        String tipo = request.getParameter("tipo");
        System.out.println(tipo);
        if(id <= 0){     
           
        }

        ConexaoFactory conexao = new ConexaoFactory();
        DocumentoDAO docDAO = new DocumentoDAO(conexao.abreConexao());
        Documento documento = null;
        if(tipo.equals("documentosdoparticipante")){
            documento = docDAO.getDocumentoDoParticipante(id);
        }else if(tipo.equals("edital")){
            documento = docDAO.getEdital(id);
        }else if(tipo.equals("documentosdoconcurso")){
            documento = docDAO.getDocumentoDoConcurso(id);
        }
        conexao.fechaConexao();
        
        if (documento != null) {
            try{
                File file = documento.getArquivo();
                response.setContentType("pdf");
                response.setHeader("Content-Disposition", "attachment; filename=\"" +    documento.getNome() + "\"");

                OutputStream out = response.getOutputStream();
                InputStream in = new FileInputStream(file);
                byte[] buffer = new byte[4096];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
                in.close();
                out.flush();
            }catch(IOException ex){
              return null;
            }finally{
                conexao.fechaConexao();
            }
            
        }
        return null;
    }
    
}
