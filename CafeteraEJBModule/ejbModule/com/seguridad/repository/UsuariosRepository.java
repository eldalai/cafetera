package com.seguridad.repository;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.application.repository.Repository;
import com.seguridad.entities.Usuario;

/**
 * Repositorio de Almacenamiento de Usuarios del Sistema.
 * 
 * @author Nestor
 * 
 */
@Stateless
@LocalBean
public class UsuariosRepository implements Repository<String, Usuario> {
	@PersistenceContext(unitName = "cafeteraDS")
	private EntityManager entityManager;

	@Override
	public void add(Usuario newOne) {

		entityManager.persist(newOne);
	}

	@Override
	public void remove(Usuario toDelete) {
		entityManager.remove(toDelete);
	}

	@Override
	public Usuario get(String login) {
		return entityManager.find(Usuario.class, login);
	}

	@Override
	public List<Usuario> getAll() {
		String q = "SELECT p from " + Usuario.class.getName() + " p ";
		TypedQuery<Usuario> query = entityManager.createQuery(q, Usuario.class);

		List<Usuario> result = query.getResultList();
		if (result == null) {
			result = new ArrayList<Usuario>();
		}
		return result;
	}

	@Override
	public long size() {
		String q = "SELECT count(p) from " + Usuario.class.getName() + " p";
		return (Long) entityManager.createQuery(q).getSingleResult();
	}
}
