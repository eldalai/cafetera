package com.productos.repository;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.application.repository.Repository;
import com.productos.entities.MateriaPrima;

/**
 * Repositorio de Almacenamiento de Productos del Sistema.
 * 
 * @author Nestor
 * 
 */
@Stateless
@LocalBean
public class MateriaPrimaRepository implements Repository<Integer, MateriaPrima> {
	@PersistenceContext(unitName = "cafeteraDS")
	private EntityManager entityManager;

	@Override
	public void add(MateriaPrima newOne) {
		entityManager.persist(newOne);
	}

	@Override
	public void remove(MateriaPrima toDelete) {
		entityManager.remove(toDelete);
	}

	@Override
	public MateriaPrima get(Integer login) {
		return entityManager.find(MateriaPrima.class, login);
	}

	@Override
	public List<MateriaPrima> getAll() {
		String q = "SELECT p from " + MateriaPrima.class.getName() + " p ";
		TypedQuery<MateriaPrima> query = entityManager.createQuery(q, MateriaPrima.class);
		
		List<MateriaPrima> result = query.getResultList();
		if(result == null) {
			result = new ArrayList<MateriaPrima>();
		}
		return result;
	}

	@Override
	public long size() {
		String q = "SELECT count(p) from " + MateriaPrima.class.getName() + " p";
		return (Long) entityManager.createQuery(q).getSingleResult();
	}
}
