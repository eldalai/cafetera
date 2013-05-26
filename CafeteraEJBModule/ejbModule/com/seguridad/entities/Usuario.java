package com.seguridad.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.crypto.CryptoUtil;

import com.seguridad.dto.RolSeguridad;

/**
 * Usuarios del sistema, esta entidad es utilizada por los mecanismos de
 * seguridad para realizar el login en el sistema.
 * 
 * Cada usuario posee una lista de roles del sistema que tiene habilitados.
 */
@Entity
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@NotEmpty()
	@Size(max = 128)
	private String login;

	@NotEmpty()
	private String password;

	@NotEmpty()
	@Size(max = 128)
	private String nombre;

	@Size(max = 128)
	private String email;

	@Temporal(TemporalType.DATE)
	private Date finVigencia;

	@NotNull()
	@Temporal(TemporalType.DATE)
	private Date inicioVigencia;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "usuarioId")
	private List<Rol> roles;

	public Usuario() {
		super();
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPlainTextPassword(String login, String password) {
		this.password = CryptoUtil.createPasswordHash("MD5", "Base64", "UTF-8", login, password);
	}

	public List<RolSeguridad> getRoles() {
		ArrayList<RolSeguridad> retorno = new ArrayList<RolSeguridad>();
		if (roles != null) {
			for (Rol r : roles) {
				retorno.add(RolSeguridad.valueOf(r.getNombre()));
			}
		}
		return retorno;
	}

	public void addRol(RolSeguridad r) {
		if (roles == null) {
			roles = new ArrayList<Rol>();
		}
		for (Rol rol : roles) {
			if (rol.getNombre().equals(r.toString())) {
				return;
			}
		}
		Rol newOne = new Rol();
		newOne.setNombre(r.toString());
		roles.add(newOne);
	}

	public void removeRol(RolSeguridad r) {
		if (roles == null) {
			roles = new ArrayList<Rol>();
		}
		for (Rol rol : roles) {
			if (rol.getNombre().equals(r.toString())) {
				roles.remove(rol);
				return;
			}
		}
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

	public Date getFinVigencia() {
		return finVigencia;
	}

	public void setFinVigencia(Date finVigencia) {
		this.finVigencia = finVigencia;
	}

	public Date getInicioVigencia() {
		return inicioVigencia;
	}

	public void setInicioVigencia(Date inicioVigencia) {
		this.inicioVigencia = inicioVigencia;
	}
}
