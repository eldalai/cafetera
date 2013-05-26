package com.productos.dto;

import com.productos.entities.MateriaPrima;

/**
 * Abstract Factory para conversion de Etidad a DTO de Materias Primas.
 * 
 * @author Nestor
 *
 */
public class MateriaPrimaDTOFactory {
	
	/**
	 * Retorna un ProductoDTO populado con toda la informacion posible.
	 * 
	 * @param producto
	 * @return
	 */
	public static MateriaPrimaDTO getAllDataFromProducto(MateriaPrima producto) {
		MateriaPrimaDTO result = new MateriaPrimaDTO();
		result.setCantidadActual(producto.getCantidadActual());
		result.setId(producto.getId());
		result.setNombre(producto.getNombre());
		return result;
}
}
