package com.productos.dto;

/**
 * DTO de materias Primas..
 * 
 * @author Nestor
 *
 */
public class MateriaPrimaDTO {
	private Integer id;
	private String nombre;
	private int cantidadActual;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCantidadActual() {
		return cantidadActual;
	}

	public void setCantidadActual(int cantidadPorPorcion) {
		this.cantidadActual = cantidadPorPorcion;
	}

}
