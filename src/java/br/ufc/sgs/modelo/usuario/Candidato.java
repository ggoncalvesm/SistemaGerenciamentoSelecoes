/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.sgs.modelo.usuario;

import br.ufc.sgs.model.exceptions.DadoInvalidoException;

/**
 *
 * @author alexf
 */
public class Candidato extends Usuario{
    private int matricula;
    private String curso;
    private float ano_entrada;

    public Candidato(String nome, String email, String senha, int matricula, String curso, float ano_entrada) throws DadoInvalidoException{
        super(nome, email, senha);
        setMatricula(matricula);
        setCurso(curso);
        setAno_entrada(ano_entrada);
    }
    
    public int getMatricula() {
        return matricula;
    }

    private void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getCurso() {
        return curso;
    }

    private void setCurso(String curso) throws DadoInvalidoException {
        if(curso == null) throw new DadoInvalidoException("Nome do curso não especificado");
        this.curso = curso;
    }

    public float getAno_entrada() {
        return ano_entrada;
    }

    private void setAno_entrada(float ano_entrada){
        this.ano_entrada = ano_entrada;
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
        final Candidato other = (Candidato) obj;
        if (this.matricula != other.matricula) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Candidato{" + "matricula=" + matricula + ", curso=" + curso + ", ano_entrada=" + ano_entrada + '}';
    }
    
    

}
