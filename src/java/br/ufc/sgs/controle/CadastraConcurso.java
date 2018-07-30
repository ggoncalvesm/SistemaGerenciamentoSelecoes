/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.sgs.controle;

import br.ufc.sgs.dao.ConcursoDAO;
import br.ufc.sgs.dao.ConexaoFactory;
import br.ufc.sgs.model.concurso.Concurso;
import br.ufc.sgs.model.concurso.Documento;
import br.ufc.sgs.model.concurso.Fase;
import br.ufc.sgs.model.concurso.Selecao;
import br.ufc.sgs.model.exceptions.ServletControllerException;
import br.ufc.sgs.modelo.usuario.Gerente;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;    
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class CadastraConcurso implements Logica{
    private boolean isMultipart;
    private String filePath;
    private int maxFileSize = 50 * 1024;
    private int maxMemSize = 4 * 1024;
    private File file ;
    @Override
    public String executa(HttpServletRequest request, HttpServletResponse response) throws ServletControllerException {
        ConexaoFactory conexao = new ConexaoFactory();
        try{
            int id_gerente = ((Gerente) request.getSession().getAttribute("usuario")).getId();
            String titulo = request.getParameter("titulo");
            String descricao = request.getParameter("descricao");
            Documento edital = null;
            String preRequisitos = request.getParameter("preRequisitos");
            String maisInformacoes = request.getParameter("maisInformacoes");
            boolean isDivulgado = Boolean.getBoolean(request.getParameter("isDivulgado"));
            Concurso concurso = new Concurso(titulo, descricao, edital, preRequisitos, maisInformacoes, isDivulgado, new ArrayList<Documento>(), new ArrayList<Selecao>(), new ArrayList<Fase>());
            ConcursoDAO concursoDAO = new ConcursoDAO(conexao.abreConexao());
            if(concursoDAO.cadastraConcurso(concurso, id_gerente)){
                conexao.fechaConexao();
                concursoDAO = new ConcursoDAO(conexao.abreConexao());
                for(Concurso c: concursoDAO.getConcursos()){
                    if(c.getTitulo().equals(titulo)){
                        request.getSession().setAttribute("concurso", c);
                    }
                }
                
                return "jsp_gerente/concurso.jsp";
            }else{
                request.getSession().setAttribute("mensagem", "Cadastro não realizado!");
                return "jsp_gerente/cadastrar_concurso.jsp";
            }
        }catch (SQLException ex) {
            request.getSession().setAttribute("mensagem", "Erro no SQL");
            System.out.println("Erro no código SQL");
            return "login.jsp";
        }finally{
            conexao.fechaConexao();
        }
    }
    
    private Documento uploadDocumento(HttpServletRequest request) throws FileUploadException, ServletException{
        isMultipart = ServletFileUpload.isMultipartContent(request);
        if( !isMultipart ){
           return null;
        }
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(maxMemSize);
        factory.setRepository(new File("c:\\temp"));

        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setSizeMax( maxFileSize );

        try{ 
            List fileItems = upload.parseRequest(request);
            Iterator i = fileItems.iterator();
            if(i.hasNext()) {
                FileItem fi = (FileItem)i.next();
                if ( !fi.isFormField ()){
                    // Get the uploaded file parameters
                    String fieldName = fi.getFieldName();
                    String fileName = fi.getName();
                    String contentType = fi.getContentType();
                    boolean isInMemory = fi.isInMemory();
                    long sizeInBytes = fi.getSize();
                    // Write the file
                    if( fileName.lastIndexOf("\\") >= 0 ){
                       file = new File( filePath + 
                       fileName.substring( fileName.lastIndexOf("\\"))) ;
                    }else{
                       file = new File( filePath + 
                       fileName.substring(fileName.lastIndexOf("\\")+1)) ;
                    }
                    fi.write( file ) ;
                    return new Documento(0, fileName, file);
                }
            }
        }catch(Exception ex) {
            System.out.println(ex);
        }
        return null;
    }
}