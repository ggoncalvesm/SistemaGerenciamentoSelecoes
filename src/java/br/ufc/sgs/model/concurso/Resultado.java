/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.sgs.model.concurso;

import br.ufc.sgs.model.exceptions.DadoInvalidoException;

/**
 *
 * @author alexf
 */
public class Resultado {
    private int id;
    private boolean isAprovado;
    private float nota;
    private String motivo; 

    public Resultado(int id, boolean isAprovado, float nota, String motivo) throws DadoInvalidoException {
        this.id = id;
        setIsAprovado(isAprovado);
        setNota(nota);
        setMotivo(motivo);
    }
    public Resultado(boolean isAprovado, float nota, String motivo) throws DadoInvalidoException {
        setIsAprovado(isAprovado);
        setNota(nota);
        setMotivo(motivo);
    }
    
    public float getNota() {
        return nota;
    }

    public void setNota(float nota) throws DadoInvalidoException{
        if(nota < 0.0 || nota > 10.0) throw new DadoInvalidoException("Nota não pertence ao conjunto de valores válidos");
        else this.nota = nota;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public boolean isIsAprovado() {
        return isAprovado;
    }

    public void setIsAprovado(boolean isAprovado) {
        this.isAprovado = isAprovado;
    }

    public int getId() {
        return id;
    }
    
}
