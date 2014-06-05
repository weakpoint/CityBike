package edu.citybike.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import edu.citybike.database.DatabaseFacade;
import edu.citybike.database.exception.PersistenceException;
import edu.citybike.model.Credentials;
import edu.citybike.model.User;

public class SimpleAuthenticationSuccessHandler implements AuthenticationSuccessHandler{
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	private DatabaseFacade facade;

	public DatabaseFacade getFacade() {
		return facade;
	}

	public void setFacade(DatabaseFacade facade) {
		this.facade = facade;
	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {

		if(authentication.getPrincipal() instanceof User){
			request.getSession().setAttribute("currentUser", authentication.getPrincipal());
		} else {
			Credentials credentials = new Credentials();
			credentials.setEmailAddress(authentication.getName());
			credentials.setPassword((String) authentication.getCredentials());
			
			try {
				request.getSession().setAttribute("currentUser",facade.getUser(credentials));
			} catch (PersistenceException e) {
			}
		}
		redirectStrategy.sendRedirect(request, response, "/");
	}

}
