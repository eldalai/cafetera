package com.usuarios;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import com.seguridad.UsuariosServiceRemote;
import com.seguridad.dto.RegistrarUsuarioDTO;
import com.seguridad.dto.RolSeguridad;
import com.seguridad.dto.UsuarioDTO;

public class UsuariosJUnit {
	@Test
	public void test() {
		try {
			UsuariosServiceRemote mts = ServiceLocator.getCurrentInstance().getSeguridadaServiceRemote();

			RegistrarUsuarioDTO nuevo = new RegistrarUsuarioDTO(); 
			nuevo.setLogin("nestor1");
			nuevo.setPlainTextPassword("123");
			ArrayList<String> roles = new ArrayList<String>();
			roles.add(RolSeguridad.ADMIN.toString());
			nuevo.setRoles(roles);
			mts.registrarUsuario(nuevo);

			Collection<UsuarioDTO> usaurios = mts.listAll();
			for (UsuarioDTO m : usaurios) {
				System.out.println(m.getLogin());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}


