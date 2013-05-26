package com.seguridad;

import java.util.HashSet;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;

import org.jboss.crypto.CryptoUtil;

import com.application.exceptions.BusinessException;
import com.seguridad.dto.ActualizarUsuarioDTO;
import com.seguridad.dto.RegistrarUsuarioDTO;
import com.seguridad.entities.Usuario;
import com.seguridad.repository.UsuariosRepository;

/**
 * Esta clase implementa todas las validaciones necesarias relacionadas con UsuariosService.
 * 
 * @author Nestor
 *
 */
@Stateless
@LocalBean
public class UsuariosServiceValidations {
	@EJB
	private UsuariosRepository usuariosRepository;

	/**
	 * Valida la insercion de un usuario nuevo en la base de datos.
	 * 
	 * @param usuario
	 * @throws BusinessException
	 */
	public void validarRegistrarUsuario(RegistrarUsuarioDTO usuario) throws BusinessException {
		if (usuario.getRoles().size() == 0) {
			throw new BusinessException("roles", "Al menos debe definir un rol para el usuario.");
		}

		if (usuario.getLogin() == null || usuario.getLogin().trim().isEmpty()) {
			throw new BusinessException("login", "Debe especificar un login de acceso al sistema.");
		}
		
		if (usuariosRepository.get(usuario.getLogin()) != null) {
			throw new BusinessException("login", "El usuario ya existe en la base de datos.");
		}
	}

	/**
	 * Valida la insercion de un usuario nuevo en la base de datos.
	 * 
	 * @param usuario
	 * @throws BusinessException
	 */
	public void validarActualizarUsuario(ActualizarUsuarioDTO usuario) throws BusinessException {
		if (usuario.getRoles().size() == 0) {
			throw new BusinessException("roles", "Al menos debe definir un rol para el usuario.");
		}

		if (usuario.getLogin() == null || usuario.getLogin().trim().isEmpty()) {
			throw new BusinessException("login", "Debe especificar un login de acceso al sistema.");
		}
		
		if (usuariosRepository.get(usuario.getLogin()) == null) {
			throw new BusinessException("login", "El usuario no existe en la base de datos.");
		}
	}
	
	/**
	 * Valida el cambio de contraseña.
	 * 
	 * @param login
	 * @param passwordViejo
	 * @param passwordNuevo
	 */
	public void validarCambioPassword(String login, String passwordViejo, String passwordNuevo) {
		Usuario usr = usuariosRepository.get(login);
		if (usr == null) {
			throw new BusinessException("login", "El usuario no existe en la base de datos.");
		}

		String pw = CryptoUtil.createPasswordHash("MD5", "Base64", "UTF-8", usr.getLogin(), passwordViejo);

		if (!pw.equals(usr.getPassword())) {
			throw new BusinessException("passwordViejo", "La contraseña actual es incorrecta.");
		}
	}
	
	/**
	 * Valida 
	 * 
	 * @param usuarioNuevo
	 * @throws ConstraintViolationException
	 */
	public void validarEntityBeanUsuario(Usuario usuarioNuevo) throws ConstraintViolationException {
		Set<ConstraintViolation<Usuario>> errors = Validation.buildDefaultValidatorFactory().getValidator().validate(usuarioNuevo);
		if (!errors.isEmpty()) {
			throw new ConstraintViolationException("Errores de validacion", new HashSet<ConstraintViolation<?>>(errors));
		}
	}

	/**
	 * Valida la desactivacion de un usuario.
	 * 
	 * @param login
	 */
	public void validarActivarUsuario(String login) {
		Usuario usr = usuariosRepository.get(login);
		if (usr == null) {
			throw new BusinessException("login", "El usuario no existe en la base de datos.");
		}
	}

	/**
	 * Valida la activacion de un usuario.
	 * 
	 * @param login
	 */
	public void validarDesactivarUsuario(String login) {
		Usuario usr = usuariosRepository.get(login);
		if (usr == null) {
			throw new BusinessException("login", "El usuario no existe en la base de datos.");
		}
	}
}
