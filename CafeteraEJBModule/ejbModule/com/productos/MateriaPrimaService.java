package com.productos;

import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.application.exceptions.BusinessException;
import com.productos.dto.MateriaPrimaDTO;
import com.productos.dto.MateriaPrimaDTOFactory;
import com.productos.entities.MateriaPrima;
import com.productos.repository.MateriaPrimaRepository;

@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
public class MateriaPrimaService implements MateriaPrimaServiceRemote {
	@EJB
	private MateriaPrimaRepository materiaPrimaRepository;
	
	@EJB
	private MateriaPrimaServiceValidations validator;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public MateriaPrimaDTO findById(Integer login) {
		return MateriaPrimaDTOFactory.getAllDataFromProducto(materiaPrimaRepository.get(login));
	}

	@Override
	public Collection<MateriaPrimaDTO> listAll() {
		ArrayList<MateriaPrimaDTO> result = new ArrayList<MateriaPrimaDTO>();
		for (MateriaPrima u : materiaPrimaRepository.getAll()) {
			result.add(MateriaPrimaDTOFactory.getAllDataFromProducto(u));
		}
		return result;
	}

	@Override
	public MateriaPrimaDTO addMateriaPrima(MateriaPrimaDTO producto) {
		MateriaPrima productoNuevo = new MateriaPrima();
		productoNuevo.setCantidadActual(producto.getCantidadActual());
		productoNuevo.setNombre(producto.getNombre());
		materiaPrimaRepository.add(productoNuevo);
		
		return MateriaPrimaDTOFactory.getAllDataFromProducto(productoNuevo);
	}

	@Override
	public void recargarMateriaPrima(Integer materiaPrimaId, int cantidad) throws BusinessException {
		validator.validarRecargarMateriaPrima(materiaPrimaId, cantidad);
		
		MateriaPrima materiaPrima = materiaPrimaRepository.get(materiaPrimaId);
		materiaPrima.setCantidadActual(materiaPrima.getCantidadActual() + cantidad);
	}
	
	@Override
	public void consumirMateriaPrima(Integer materiaPrimaId, int cantidad) {
		validator.validarConsumirMateriaPrima(materiaPrimaId, cantidad);
		
		MateriaPrima materiaPrima = materiaPrimaRepository.get(materiaPrimaId);
		materiaPrima.setCantidadActual(materiaPrima.getCantidadActual() - cantidad);
	}
}
