package com.application.exceptions;

import java.util.Set;

import javax.ejb.ApplicationException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * Permite enviar un detalle de errores en las validaciones de las entidades provistas en la llamada a un servicio del sistema.
 * 
 * Realiza rollback en la transaccion.
 * 
 * @author Nestor
 *
 */
@ApplicationException(rollback = true)
public class BusinessConstraintViolationException extends ConstraintViolationException {
	private static final long serialVersionUID = 1L;

	public BusinessConstraintViolationException(String message, Set<ConstraintViolation<?>> constraintViolations) {
		super(message, constraintViolations);
	}

	public Set<ConstraintViolation<?>> getConstraintVionations() {
		return super.getConstraintViolations();
	}

	public void addConstraintViolation(ConstraintViolation<?> cv) {
		super.getConstraintViolations().add(cv);
	}
}
