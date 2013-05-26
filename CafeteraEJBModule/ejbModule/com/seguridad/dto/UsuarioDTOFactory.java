package com.seguridad.dto;

import com.seguridad.entities.Usuario;

/**
 * Abstract Factory para conversion de Usuario a UsuarioDTO.
 * 
 * @author Nestor
 * 
 */
public class UsuarioDTOFactory {

	/**
	 * Retorna un UsuarioDTO populado con toda la informacion posible.
	 * 
	 * @param u
	 * @return
	 */
	public static ActualizarUsuarioDTO getActualizarDataFromUsaurio(Usuario u) {
		if (u == null) {
			return null;
		}
		ActualizarUsuarioDTO result = new ActualizarUsuarioDTO();
		result.setLogin(u.getLogin());
		result.setEmail(u.getEmail());
		result.setInicioVigencia(u.getInicioVigencia());
		result.setNombre(u.getNombre());
		
		for (RolSeguridad r : u.getRoles()) {
			result.getRoles().add(r.toString());
		}

		return result;
	}
	
	/**
	 * Retorna un UsuarioDTO populado con toda la informacion posible.
	 * 
	 * @param u
	 * @return
	 */
	public static RegistrarUsuarioDTO getRegistrarDataFromUsaurio(Usuario u) {
		if (u == null) {
			return null;
		}
		RegistrarUsuarioDTO result = new RegistrarUsuarioDTO();
		result.setLogin(u.getLogin());
		result.setEncriptedPassword(u.getPassword());
		result.setEmail(u.getEmail());
		result.setInicioVigencia(u.getInicioVigencia());
		result.setNombre(u.getNombre());
		
		for (RolSeguridad r : u.getRoles()) {
			result.getRoles().add(r.toString());
		}

		return result;
	}


	/**
	 * Retorna un UsuarioDTO populado con toda la informacion posible.
	 * 
	 * @param u
	 * @return
	 */
	public static UsuarioDTO getAllDataFromUsaurio(Usuario u) {
		if (u == null) {
			return null;
		}
		UsuarioDTO result = new UsuarioDTO();
		result.setLogin(u.getLogin());
		result.setEmail(u.getEmail());
		result.setFinVigencia(u.getFinVigencia());
		result.setInicioVigencia(u.getInicioVigencia());
		result.setNombre(u.getNombre());
		result.setActivo((u.getInicioVigencia() == null || u.getInicioVigencia().before(new java.util.Date())
				&& (u.getFinVigencia() == null || u.getFinVigencia().after(new java.util.Date()))));
		
		for (RolSeguridad r : u.getRoles()) {
			result.getRoles().add(r.toString());
		}

		return result;
	}
}
