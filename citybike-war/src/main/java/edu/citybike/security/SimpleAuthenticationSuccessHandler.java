package edu.citybike.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import edu.citybike.database.DatabaseFacade;
import edu.citybike.model.view.CurrentUser;

public class SimpleAuthenticationSuccessHandler implements AuthenticationSuccessHandler{
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	private DatabaseFacade facade;
	private static final Logger logger = LoggerFactory.getLogger(SimpleAuthenticationSuccessHandler.class);

	public DatabaseFacade getFacade() {
		return facade;
	}

	public void setFacade(DatabaseFacade facade) {
		this.facade = facade;
	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		System.out.println(authentication.getPrincipal());
		if(authentication.getPrincipal() instanceof CurrentUser){	
			request.getSession().setAttribute("currentUser", authentication.getPrincipal());			
		} 
		redirectStrategy.sendRedirect(request, response, "/");
	}

}
