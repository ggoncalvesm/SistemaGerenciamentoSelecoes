/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.sgs.model.concurso;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author alexf
 */
public class Selecao {
    private int id;
    private String titulo;
    private String descricao;
    private String responsavel;
    private String area;
    private int vagasRemuneradas;
    private int vagasVoluntarias;

    public Selecao(String titulo, String descricao, String responsavel, String area, int vagasRemuneradas, int vagasVoluntarias) {
        setTitulo(titulo);
        setDescricao(descricao);
        setResponsavel(responsavel);
        setArea(area);
        setVagasRemuneradas(vagasRemuneradas);
        setVagasVoluntarias(vagasVoluntarias);
    }

    public Selecao(int id_selecao, String titulo, String descricao, String responsavel, String area, int vagasRemuneradas, int vagasVoluntarias) {
        this(titulo, descricao, responsavel, area, vagasRemuneradas, vagasVoluntarias);
        this.id = id_selecao;
       
    }
    
    public int getId(){
        return id;
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

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        if(area != null)
            this.area = area;
    }
    
    public int getVagasRemuneradas() {
        return vagasRemuneradas;
    }

    public void setVagasRemuneradas(int vagasRemuneradas) {
        this.vagasRemuneradas = vagasRemuneradas;
    }

    public int getVagasVoluntarias() {
        return vagasVoluntarias;
    }

    public void setVagasVoluntarias(int vagasVoluntarias) {
        this.vagasVoluntarias = vagasVoluntarias;
    }
    
    
}
