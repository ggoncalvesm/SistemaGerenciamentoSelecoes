/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.sgs.controle;

import br.ufc.sgs.model.exceptions.ServletControllerException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author alexf
 */
public interface Logica {
    String executa(HttpServletRequest request, HttpServletResponse response) throws ServletControllerException;
}
