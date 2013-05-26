package com.security;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import com.application.exceptions.BusinessException;
import com.seguridad.UsuariosService;
import com.seguridad.dto.RolSeguridad;
import com.seguridad.dto.RegistrarUsuarioDTO;

/**
 * Bean de control del formulario addUsuario
 * 
 * @author Nestor
 * 
 */
@ManagedBean
@ViewScoped
public class RegistrarUsuarioForm {
	@EJB
	private UsuariosService seguridadService;

	private RegistrarUsuarioDTO usuarioEditado;

	private String plainTextPassword;
	private String plainTextPassword2;

	/**
	 * Cuando se incializa el formulario podemos obtener un login del request,
	 * si lo obtenemos es porque se quiere editar un usuario existente, si no se
	 * obtiene el login, es porque se quiere crear un usuario nuevo.
	 * 
	 */
	@PostConstruct
	private void initialize() {
		if (usuarioEditado == null) {
			usuarioEditado = new RegistrarUsuarioDTO();
		}
	}

	/**
	 * Se procesa guardar el login. Si se producen errores se vuelve a la vista
	 * original, mostrando los errores.
	 * 
	 * @throws IOException
	 */
	public String save() throws IOException {
		if (!validateSave()) {
			return null;
		}

		try {
			seguridadService.registrarUsuario(usuarioEditado);
			return "usuario";
		} catch (BusinessException be) {
			processBusinessException(be);
		} catch (ConstraintViolationException cve) {
			processConstraintViolationException(cve);
		} catch (EJBException e) {
			if (e.getCause().getClass().isAssignableFrom(ConstraintViolationException.class)) {
				processConstraintViolationException((ConstraintViolationException) e.getCause());
			} else if (e.getCause().getClass().isAssignableFrom(BusinessException.class)) {
				processBusinessException((BusinessException) e.getCause());
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado del sistema. No se pudo agregar el usuario.", e.getMessage()));
		}
		return null;
	}

	/**
	 * Procesa y revisa las ecepciones de negocio obtenidas.
	 * 
	 * @param e
	 */
	private void processBusinessException(BusinessException e) {
		BusinessException be = (BusinessException) e.getCause();
		FacesContext.getCurrentInstance().addMessage("form:" + be.getComponente(), new FacesMessage(FacesMessage.SEVERITY_ERROR, be.getMessage(), be.getMessage()));
	}

	/**
	 * Procesa y revisa las ecepciones de negocio obtenidas.
	 * 
	 * @param cve
	 */
	private void processConstraintViolationException(ConstraintViolationException cve) {
		for (ConstraintViolation<?> v : cve.getConstraintViolations()) {
			FacesContext.getCurrentInstance().addMessage("form:" + v.getPropertyPath().toString(), new FacesMessage(FacesMessage.SEVERITY_ERROR, v.getMessage(), v.getMessage()));
		}
	}

	/**
	 * Validacion de los datos ingresados en el formulario.
	 * 
	 * @return
	 */
	public boolean validateSave() {
		if (plainTextPassword.trim().length() > 0 || plainTextPassword2.trim().length() > 0) {
			if (!plainTextPassword.equals(plainTextPassword2)) {
				FacesContext.getCurrentInstance().addMessage("form:plainTextPassword", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Las contraseñas no coinciden.", "Las contraseñas no coinciden."));
			} else {
				usuarioEditado.setPlainTextPassword(plainTextPassword);
			}
		}

		return FacesContext.getCurrentInstance().getMessageList().isEmpty();
	}

	public RegistrarUsuarioDTO getUsuarioEditado() {
		return usuarioEditado;
	}

	public void setUsuarioEditado(RegistrarUsuarioDTO usuarioEditado) {
		this.usuarioEditado = usuarioEditado;
	}

	public RolSeguridad[] getRolesDisponibles() {
		return RolSeguridad.values();
	}

	public String getPlainTextPassword() {
		return plainTextPassword;
	}

	public void setPlainTextPassword(String plainTextPassword) {
		this.plainTextPassword = plainTextPassword;
	}

	public String getPlainTextPassword2() {
		return plainTextPassword2;
	}

	public void setPlainTextPassword2(String plainTextPassword2) {
		this.plainTextPassword2 = plainTextPassword2;
	}
}
