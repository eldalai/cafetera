package com.productos;

import java.util.Collection;

import javax.ejb.Remote;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.productos.dto.MateriaPrimaDTO;

@Remote
public interface MateriaPrimaServiceRemote {
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public abstract MateriaPrimaDTO findById(Integer materiaPrimaId);

	public abstract Collection<MateriaPrimaDTO> listAll();

	MateriaPrimaDTO addMateriaPrima(MateriaPrimaDTO usuario);

	void recargarMateriaPrima(Integer materiaPrimaId, int cantidad);

	void consumirMateriaPrima(Integer materiaPrimaID, int cantidad);

}