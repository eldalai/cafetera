package com.security;

import java.util.Collection;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.seguridad.UsuariosService;
import com.seguridad.dto.UsuarioDTO;

/**
 * Formulario que muestra la grilla de usuarios.
 * 
 * @author Nestor
 *
 */
@ManagedBean
@RequestScoped
public class UsuarioForm {
	@EJB
	private UsuariosService usuarioService;

	public Collection<UsuarioDTO> getUsuarios() {
		return usuarioService.listAll();
	}
	
	public String desactivarUsuario(String login) {
		usuarioService.desactivarUsuario(login);
		return null;
	}
	
	public String activarUsuario(String login) {
		usuarioService.activarUsuario(login);
		return null;
	}
}
