package com.seguridad;

import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.application.exceptions.BusinessConstraintViolationException;
import com.application.exceptions.BusinessException;
import com.seguridad.dto.ActualizarUsuarioDTO;
import com.seguridad.dto.RegistrarUsuarioDTO;
import com.seguridad.dto.RolSeguridad;
import com.seguridad.dto.UsuarioDTO;
import com.seguridad.dto.UsuarioDTOFactory;
import com.seguridad.entities.Usuario;
import com.seguridad.repository.UsuariosRepository;

/**
 * Servicio de manejo de usuarios.
 * 
 * @author Nestor
 * 
 */
@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UsuariosService implements UsuariosServiceRemote {
	@EJB
	private UsuariosRepository usuariosRepository;

	@EJB
	private UsuariosServiceValidations seguridadServiceValidations;

	/**
	 * Busca un usuario por login.
	 * 
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public UsuarioDTO findByLogin(String login) {
		return UsuarioDTOFactory.getAllDataFromUsaurio(usuariosRepository.get(login));
	}

	/**
	 * Busca un usuario por login.
	 * 
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ActualizarUsuarioDTO findForEditByLogin(String login) {
		return UsuarioDTOFactory.getActualizarDataFromUsaurio(usuariosRepository.get(login));
	}

	/**
	 * Lista todos los usuarios.
	 * 
	 */
	@Override
	public Collection<UsuarioDTO> listAll() {
		ArrayList<UsuarioDTO> result = new ArrayList<UsuarioDTO>();
		for (Usuario u : usuariosRepository.getAll()) {
			result.add(UsuarioDTOFactory.getAllDataFromUsaurio(u));
		}
		return result;
	}

	/**
	 * Actualiza los datos basicos de un usuario.
	 * 
	 */
	@Override
	public ActualizarUsuarioDTO actualizarUsuario(ActualizarUsuarioDTO usuario) throws BusinessException, BusinessConstraintViolationException {
		seguridadServiceValidations.validarActualizarUsuario(usuario);

		Usuario usuarioEditado = usuariosRepository.get(usuario.getLogin());
			for (RolSeguridad r : usuarioEditado.getRoles()) {
				usuarioEditado.removeRol(r);
			}
			for (String r : usuario.getRoles()) {
				usuarioEditado.addRol(RolSeguridad.valueOf(r));
			}
			usuarioEditado.setEmail(usuario.getEmail());
			usuarioEditado.setInicioVigencia(usuario.getInicioVigencia());
			usuarioEditado.setNombre(usuario.getNombre());

			seguridadServiceValidations.validarEntityBeanUsuario(usuarioEditado);

		return UsuarioDTOFactory.getActualizarDataFromUsaurio(usuarioEditado);
	}

	/**
	 * Registra un usuario en la base de datos.
	 * 
	 */
	@Override
	public RegistrarUsuarioDTO registrarUsuario(RegistrarUsuarioDTO usuario) throws BusinessException, BusinessConstraintViolationException {
		seguridadServiceValidations.validarRegistrarUsuario(usuario);

		Usuario usuarioNuevo = new Usuario();
		usuarioNuevo.setLogin(usuario.getLogin());
		usuarioNuevo.setPlainTextPassword(usuario.getLogin(), usuario.getPlainTextPassword());
		usuarioNuevo.setEmail(usuario.getEmail());
		usuarioNuevo.setInicioVigencia(usuario.getInicioVigencia());
		usuarioNuevo.setNombre(usuario.getNombre());

		for (String r : usuario.getRoles()) {
			usuarioNuevo.addRol(RolSeguridad.valueOf(r));
		}

		seguridadServiceValidations.validarEntityBeanUsuario(usuarioNuevo);

		usuariosRepository.add(usuarioNuevo);
		return UsuarioDTOFactory.getRegistrarDataFromUsaurio(usuarioNuevo);
	}

	/**
	 * Actgualiza la contraseña
	 * 
	 */
	@Override
	public void actualizarPassword(String login, String passwAnterior, String passwNuevo) throws BusinessException, BusinessConstraintViolationException {
		seguridadServiceValidations.validarCambioPassword(login, passwAnterior, passwNuevo);

		Usuario usr = usuariosRepository.get(login);
		usr.setPlainTextPassword(login, passwNuevo);
	}

	/**
	 * Activa el usuario.
	 * 
	 */
	@Override
	public void activarUsuario(String login) {
		seguridadServiceValidations.validarActivarUsuario(login);

		Usuario usr = usuariosRepository.get(login);
		usr.setFinVigencia(null);
	}

	/**
	 * Desactiva el usuario.
	 * 
	 */
	@Override
	public void desactivarUsuario(String login) {
		seguridadServiceValidations.validarDesactivarUsuario(login);

		Usuario usr = usuariosRepository.get(login);
		usr.setFinVigencia(new java.util.Date());
	}
}
