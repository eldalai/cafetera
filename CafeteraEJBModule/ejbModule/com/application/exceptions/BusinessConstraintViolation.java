package com.application.exceptions;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.metadata.ConstraintDescriptor;

/**
 * Define un error en una propiedad del bean validado como incorrecta.
 * 
 * @author Nestor
 *
 * @param <T>
 */
public class BusinessConstraintViolation<T> implements ConstraintViolation<T>{
	
	private String message;
	private Path propertyPath;
	private Object invalidValue;
	
	public BusinessConstraintViolation(String propertyPath, String message, Object invalidValue) {
		this.propertyPath = new BusinessConstraintViolationPath(propertyPath);
		this.message = message;
		this.invalidValue = invalidValue;
	}
	
	@Override
	public ConstraintDescriptor<?> getConstraintDescriptor() {
		return null;
	}

	@Override
	public Object getInvalidValue() {
		return invalidValue;
	}

	@Override
	public Object getLeafBean() {
		return null;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public String getMessageTemplate() {
		return message;
	}

	@Override
	public Path getPropertyPath() {
		return propertyPath;
	}

	@Override
	public T getRootBean() {
		return null;
	}

	@Override
	public Class<T> getRootBeanClass() {
		return null;
	}
}
