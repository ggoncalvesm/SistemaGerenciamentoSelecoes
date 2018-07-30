/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.sgs.model.concurso;

import br.ufc.sgs.modelo.usuario.Candidato;
import java.util.Collection;
import java.util.Objects;

/**
 *
 * @author alexf
 */
public class Participante {
    private int id_participante;
    private Candidato candidato;
    private Collection<Documento> documentos;
    private Resultado resultado;
    private Selecao selecao;

    public Participante(Candidato candidato, Collection<Documento> documentos, Resultado resultado) {
        setCandidato(candidato);
        setDocumentos(documentos);
        setResultado(resultado);
    }
    
    public Participante(int id_participante, Candidato candidato, Collection<Documento> documentos, Resultado resultado){
        this(candidato, documentos, resultado);
        this.id_participante = id_participante;
    }

    public Candidato getCandidato() {
        return candidato;
    }

    public void setCandidato(Candidato candidato) {
        this.candidato = candidato;
    }

    public Collection<Documento> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(Collection<Documento> documentos) {
        this.documentos = documentos;
    }

    public Resultado getResultado() {
        return resultado;
    }

    public void setResultado(Resultado resultado) {
        this.resultado = resultado;
    }

    public int getId(){
        return id_participante;
    }

    public Selecao getSelecao() {
        return selecao;
    }

    public void setSelecao(Selecao selecao) {
        this.selecao = selecao;
    }
    
    public boolean isAvaliado(){
        return getResultado() != null;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Participante other = (Participante) obj;
        if (!Objects.equals(this.candidato, other.candidato)) {
            return false;
        }
        return true;
    }
    
}
