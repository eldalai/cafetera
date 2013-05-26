package com.productos;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.application.exceptions.BusinessException;
import com.productos.entities.MateriaPrima;
import com.productos.repository.MateriaPrimaRepository;

@Stateless
@LocalBean
public class MateriaPrimaServiceValidations {
	@EJB
	MateriaPrimaRepository materiaPrimaRepository;

	public void validarRecargarMateriaPrima(Integer materiaPrimaId, int cantidad) throws BusinessException {
		MateriaPrima materiaPrima = materiaPrimaRepository.get(materiaPrimaId);
		if (materiaPrima == null) {
			throw new BusinessException("materiaPrimaId", "La materia prima indicada no existe en la base de datos.");
		}
		
		if(cantidad < 0) {
			throw new BusinessException("cantidad", "No se puede cargar la cantidad indicada.");
		}
	}

	public void validarConsumirMateriaPrima(Integer materiaPrimaId, int cantidad) {
		MateriaPrima materiaPrima = materiaPrimaRepository.get(materiaPrimaId);
		if (materiaPrima == null) {
			throw new BusinessException("materiaPrimaId", "La materia prima indicada no existe en la base de datos.");
		}

		if(cantidad < 0 || cantidad > materiaPrima.getCantidadActual()) {
			throw new BusinessException("cantidad", "No se puede consumir la cantidad indicada.");
		}
	}

}
