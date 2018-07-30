/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.sgs.model.concursos.criteriosDeSelecao;

import br.ufc.sgs.model.concurso.Participante;
import br.ufc.sgs.model.concurso.Resultado;
import br.ufc.sgs.model.exceptions.DadoInvalidoException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alexf
 */
public class Manual_CS implements CriterioDeSelecao{

    @Override
    public Resultado avalia(Object obj, String motivo) {
        Resultado resultado = null;
        if(obj instanceof Float){
            float nota = (Float) obj;
            if(nota < 5.0){
                try {
                    resultado = new Resultado(false, nota, motivo);
                } catch (DadoInvalidoException ex) {
                    Logger.getLogger(Manual_CS.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                try {
                    resultado = new Resultado(true, nota, motivo);
                } catch (DadoInvalidoException ex) {
                    Logger.getLogger(Manual_CS.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return resultado;
    }

    @Override
    public String toString() {
        return "Manual";
    }

    
}
