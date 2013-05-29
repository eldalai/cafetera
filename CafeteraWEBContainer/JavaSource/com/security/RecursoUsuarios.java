package com.security;

import java.net.URI;
import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.seguridad.UsuariosService;
import com.seguridad.dto.RegistrarUsuarioDTO;
import com.seguridad.dto.UsuarioDTO;

@Stateless
@Path(value="usuarios")
@Produces(value={MediaType.APPLICATION_JSON})
public class RecursoUsuarios {

	@EJB
	private UsuariosService usuarioService;

	@GET
	public Response getUsuarios() {
		Collection<UsuarioDTO> listAll = usuarioService.listAll();
		return Response.ok(listAll).build();
	}
	
	@GET
	@Path(value="/{usuario}")
	public Response getUsuario(@DefaultValue("") @PathParam("usuario") final String loginUsuario) {
		UsuarioDTO usuarioDTO = usuarioService.findByLogin(loginUsuario);
		return Response.ok(usuarioDTO).build();
	}
	
	@POST
	public Response registrarUsuario(RegistrarUsuarioDTO registrarUsuario) {
		usuarioService.registrarUsuario(registrarUsuario);
		
		URI location = UriBuilder.fromResource(this.getClass()).path(registrarUsuario.getLogin()).build();;
		return Response.created(location).build();
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
