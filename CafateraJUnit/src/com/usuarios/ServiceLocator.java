package com.usuarios;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.seguridad.UsuariosServiceRemote;

public class ServiceLocator {
	private static ServiceLocator instance;

	protected Context context = null;

	private ServiceLocator() throws NamingException {
		final Hashtable<String, String> jndiProperties = new Hashtable<>();
		jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
		jndiProperties.put(Context.PROVIDER_URL, "remote://localhost:4447");
		jndiProperties.put(Context.SECURITY_PRINCIPAL, "nestor");
		jndiProperties.put(Context.SECURITY_CREDENTIALS, "123");
		jndiProperties.put("jboss.naming.client.ejb.context", "true");
		jndiProperties.put("jboss.naming.client.connect.options.org.xnio.Options.SASL_POLICY_NOPLAINTEXT", "false");
		jndiProperties.put("javax.security.sasl.policy.noplaintext", "false");
		
		context = new InitialContext(jndiProperties);
	}

	public UsuariosServiceRemote getSeguridadaServiceRemote() throws NamingException {
		return (UsuariosServiceRemote) (context.lookup("CateferiaEAR/CafeteraEJBModule/SeguridadService!com.seguridad.SeguridadServiceRemote"));
	}

	public static ServiceLocator getCurrentInstance() throws NamingException {
		if (instance == null) {
			instance = new ServiceLocator();
		}
		return instance;
	}

}
