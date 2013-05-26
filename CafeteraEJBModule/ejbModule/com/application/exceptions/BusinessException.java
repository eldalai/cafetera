package com.application.exceptions;

import javax.ejb.ApplicationException;

/**
 * Representa una ecepcion del negocio.
 * Agrega una propedad que identifica el componente en donde se origina el error.
 * Asi mismo define que es una transaccion que debe realizar rollbak en la transaccion.
 * 
 * @author Nestor
 *
 */
@ApplicationException(rollback = true)
public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = -445890439850367761L;
	private String componente;

	public BusinessException(String componente, String message) {
		super(message);
		this.componente = componente;
	}

	public BusinessException(String message) {
		super(message);
	}

	public String getComponente() {
		return componente;
	}

	public void setComponente(String componente) {
		this.componente = componente;
	}

}
