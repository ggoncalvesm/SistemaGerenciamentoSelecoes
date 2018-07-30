/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.sgs.controle;


import br.ufc.sgs.dao.ConexaoFactory;
import br.ufc.sgs.dao.DocumentoDAO;
import br.ufc.sgs.model.concurso.Documento;
import br.ufc.sgs.model.exceptions.ServletControllerException;
import java.io.File;
import java.sql.SQLException;
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

/**
 *
  Referência : http://andvicoso.blogspot.com.br/2013/02/como-fazer-upload-e-download-de.html
 */
public class UploadDocumento implements Logica{

    @Override
    public String executa(HttpServletRequest request, HttpServletResponse response) throws ServletControllerException {

        ConexaoFactory conexao = new ConexaoFactory();
        DocumentoDAO docDAO = new DocumentoDAO(conexao.abreConexao());
        Documento documento;
        try {
            documento = uploadDocumento(request);
            docDAO.cadastraEdital(documento);
        } catch (FileUploadException ex) {
            Logger.getLogger(UploadDocumento.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServletException ex) {
            Logger.getLogger(UploadDocumento.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UploadDocumento.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            conexao.fechaConexao();
        }
        return "jsp_gerente/principal.jsp";
    }
    
   private Documento uploadDocumento(HttpServletRequest request) throws FileUploadException, ServletException{
/*   File file ;
   int maxFileSize = 5000 * 1024;
   int maxMemSize = 5000 * 1024;
   ServletContext context = request.getServletContext();
   String filePath = "C:\\Users\\alexf\\Documents\\GitHub\\SGS-3.0\\documentos\\";

   // Verify the contenfilePatht type
   String contentType = request.getContentType();
   if ((contentType.indexOf("multipart/form-data") >= 0)) {

      DiskFileItemFactory factory = new DiskFileItemFactory();
      // maximum size that will be stored in memory
      factory.setSizeThreshold(maxMemSize);
      // Location to save data that is larger than maxMemSize.
      factory.setRepository(new File("c:\\temp"));

      // Create a new file upload handler
      ServletFileUpload upload = new ServletFileUpload(factory);
      // maximum file size to be uploaded.
      upload.setSizeMax( maxFileSize );
      try{ 
         // Parse the request to get file items.
         List fileItems = upload.parseRequest(request);

         // Process the uploaded file items
         Iterator i = fileItems.iterator();

         while ( i.hasNext () ) 
         {
            FileItem fi = (FileItem)i.next();
            if ( !fi.isFormField () )	
            {
            // Get the uploaded file parameters
            String fieldName = fi.getFieldName();
            String fileName = fi.getName();
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
            return new Documento(0, fileName, file);
            }
         }
      }catch(Exception ex) {
         System.out.println(ex);
      }
   }else{

   }
   return null;
*/
    return new Documento(0, "teste", new File("C:\\Users\\alexf\\Documents\\Cronograma.pdf"));
   }
}
