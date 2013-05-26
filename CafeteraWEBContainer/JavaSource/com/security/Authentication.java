package com.security;

import java.io.IOException;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.application.exceptions.BusinessException;
import com.seguridad.SeguridadService;
import com.seguridad.dto.UsuarioDTO;

@ManagedBean
@RequestScoped
@Path("/session")
public class Authentication {
	@EJB
	private SeguridadService seguridadService;

	private String username;
	private String password;
	private String originalURL;

	@GET
	public Response getSessionId() {
		return Response.ok().entity("sessionID").build();
	}
	
	/**
	 * Procesa el login de la pagina.
	 * 
	 * @throws IOException
	 */
	public String login() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

		try {
			UsuarioDTO user = seguridadService.login(username);
			request.login(username, password);
			externalContext.getSessionMap().put("user", user);
		    return "/index.xhtml?faces-redirect=true";
		} catch (BusinessException e) {
			context.addMessage(null, new FacesMessage(e.getMessage()));
		} catch (Exception e) {
			context.addMessage(null, new FacesMessage("Usuario o contraseña inválido."));
		}
		return null;
	}

	/**
	 * Logout, limpia la sesion.
	 * 
	 * @throws IOException
	 */
	public String logout() throws IOException {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		externalContext.invalidateSession();
	    return "/index.xhtml?faces-redirect=true";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOriginalURL() {
		return originalURL;
	}

	public void setOriginalURL(String originalURL) {
		this.originalURL = originalURL;
	}

}