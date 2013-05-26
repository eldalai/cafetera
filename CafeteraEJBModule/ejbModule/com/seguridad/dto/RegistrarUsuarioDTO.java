package com.seguridad.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RegistrarUsuarioDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String login;
	private String encriptedPassword;
	private String plainTextPassword;
	private List<String> roles;
	private String nombre;
	private String email;
	private Date inicioVigencia;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEncriptedPassword() {
		return encriptedPassword;
	}

	public void setEncriptedPassword(String encriptedPassword) {
		this.encriptedPassword = encriptedPassword;
	}

	public String getPlainTextPassword() {
		return plainTextPassword;
	}

	public void setPlainTextPassword(String plainTextPassword) {
		this.plainTextPassword = plainTextPassword;
	}

	public List<String> getRoles() {
		if (roles == null) {
			roles = new ArrayList<String>();
		}
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getInicioVigencia() {
		return inicioVigencia;
	}

	public void setInicioVigencia(Date inicioVigencia) {
		this.inicioVigencia = inicioVigencia;
	}

}
