package com.seguridad;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.application.exceptions.BusinessException;
import com.seguridad.dto.UsuarioDTO;

/**
 * Servicio de seguridad.
 * 
 * @author Nestor
 *
 */
@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
public class SeguridadService implements SeguridadServiceRemote {
	@EJB
	private UsuariosService usuarioService;

	/**
	 * Realiza el login de usuario.
	 * 
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public UsuarioDTO login(String login) throws BusinessException {
		UsuarioDTO user = usuarioService.findByLogin(login);
		if(user.getInicioVigencia() != null && user.getInicioVigencia().after(new java.util.Date())) {
			throw new BusinessException("El usuario no esa vigente.");
		}
		if(user.getFinVigencia() != null && user.getFinVigencia().before(new java.util.Date())) {
			throw new BusinessException("El usuario no esa vigente.");
		}
		return user;
	}

}
