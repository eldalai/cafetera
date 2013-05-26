package com.application.repository;

import java.util.List;

/**
 * Interfaz Repository de acceso a datos.
 * 
 * @author Nestor
 *
 * @param <K> Tipo de datos utilizado como identidad del objeto.
 * @param <T> Tipo de objeto de la entidad administrada.
 */
public interface Repository<K, T> {
	/**
	 * Consulta en el repositorio por la entidad cuyo ID coincide con el parametro.
	 * 
	 * @param id El id a buscar.
	 * @return La entidad buscada o bien null si no existe.
	 */
	T get(K id);

	/**
	 * Devuelve un listado con todas las entidades contenidas en el repositorio.
	 * 
	 * @return Listado de entidades, null si esta vacia.
	 */
	List<T> getAll();

	/**
	 * Agrega un objeto al repositorio.
	 * 
	 * @param newOne Objeto a agregar.
	 * @return Objeto agregado.
	 */
	void add(T newOne);

	/**
	 * Elimina el objeto del repositorio.
	 * 
	 * @param toRemove Objeto a eliminar.
	 */
	void remove(T toRemove);

	/**
	 * Devuelve la cantidad de objetos contenidos en el repositorio.
	 * 
	 * @return Cantidad de objetos en el repositorio.
	 */
	long size();
}
