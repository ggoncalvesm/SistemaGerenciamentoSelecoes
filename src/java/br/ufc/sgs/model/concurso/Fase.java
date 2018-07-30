/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.sgs.model.concurso;

import br.ufc.sgs.model.concursos.criteriosDeSelecao.CriterioDeSelecao;
import br.ufc.sgs.modelo.usuario.Avaliador;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Collection;
import java.util.Locale;

/**
 *
 * @author alexf
 */
public class Fase {
    private final int id;
    private String titulo;
    private String descricao;
    private LocalDate dataInicio;
    private LocalDate dataTermino;
    private CriterioDeSelecao criterioDeSelecao;
    private Collection<String> documentosRequisitados;
    private Collection<Participante> participantes;
    private Collection<Avaliador> avaliadores;
    private boolean isDivulgadoResultado = false;

    public Fase(int id, String titulo, String descricao, LocalDate dataInicio, LocalDate dataTermino, CriterioDeSelecao criterioDeSelecao, Collection<String> documentosRequisitados, Collection<Participante> participantes, Collection<Avaliador> avaliadores) {
        this.id = id;
        setTitulo(titulo);
        setDescricao(descricao);
        setDataInicio(dataInicio);
        setDataTermino(dataTermino);
        setCriterioDeSelecao(criterioDeSelecao);
        setDocumentosRequisitados(documentosRequisitados);
        setParticipantes(participantes);
        setAvaliadores(avaliadores);
    }
    public Fase(int id, String titulo, String descricao, LocalDate dataInicio, LocalDate dataTermino, CriterioDeSelecao criterioDeSelecao, Collection<String> documentosRequisitados, Collection<Participante> participantes, Collection<Avaliador> avaliadores, boolean isDivulgadoResultado) {
        this(id, titulo, descricao, dataInicio, dataTermino, criterioDeSelecao, documentosRequisitados, participantes, avaliadores);
        this.isDivulgadoResultado = isDivulgadoResultado;
    }
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {

        this.dataInicio = dataInicio;
    }

    public LocalDate getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(LocalDate dataTermino) {
        this.dataTermino = dataTermino;
    }

    public CriterioDeSelecao getCriterioDeSelecao() {
        return criterioDeSelecao;
    }

    public void setCriterioDeSelecao(CriterioDeSelecao criterioDeSelecao) {
        this.criterioDeSelecao = criterioDeSelecao;
    }

    public Collection<String> getDocumentosRequisitados() {
        return documentosRequisitados;
    }

    public void setDocumentosRequisitados(Collection<String> documentosRequisitados) {
        this.documentosRequisitados = documentosRequisitados;
    }

    public Collection<Participante> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(Collection<Participante> participantes) {
        this.participantes = participantes;
    }

    public Collection<Avaliador> getAvaliadores() {
        return avaliadores;
    }

    public void setAvaliadores(Collection<Avaliador> avaliadores) {
        this.avaliadores = avaliadores;
    }

    public int getId() {
        return id;
    }

    public boolean isIsDivulgadoResultado() {
        return isDivulgadoResultado;
    }

    public boolean isFinalizada(){
        LocalDate atual = LocalDate.now();
        return (atual.isAfter(getDataTermino()));
    }
    public boolean isIniciada(){
         LocalDate atual = LocalDate.now();
        return (atual.isAfter(getDataInicio()) && atual.isBefore(getDataTermino()));       
    }
    
}
