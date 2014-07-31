package edu.citybike.controller;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.servlet.ModelAndView;

import edu.citybike.database.DatabaseFacade;
import edu.citybike.exceptions.PersistenceException;
import edu.citybike.model.User;
import edu.citybike.model.view.CurrentUser;
import edu.citybike.model.view.ExternalLoginUser;
import edu.citybike.model.view.UserInfo;

public abstract class AbstractExternalLoginController {
	private DatabaseFacade facade;
	private PasswordEncoder encoder;
	private AuthenticationDetailsSource ads = new WebAuthenticationDetailsSource();
	private static final Logger logger = LoggerFactory.getLogger(AbstractExternalLoginController.class);

	public PasswordEncoder getEncoder() {
		return encoder;
	}

	public void setEncoder(PasswordEncoder encoder) {
		this.encoder = encoder;
	}

	public DatabaseFacade getFacade() {
		return facade;
	}

	public void setFacade(DatabaseFacade facade) {
		this.facade = facade;
	}

	protected ModelAndView manageExternalLoginAttempt(HttpServletRequest request, ExternalLoginUser externalUser)
			throws LoginException {
		ModelAndView mav = null;

		User localUser;
		try {
			localUser = facade.getUserByLogin(externalUser.getEmail());

			if (encoder.matches(externalUser.getId(), localUser.getExternalID())) {
				//user is currently registered
				List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
				roles.add(new SimpleGrantedAuthority(localUser.getRole()));

				CurrentUser currentUser = new CurrentUser(localUser.getKey(), externalUser.getEmail(), "", roles);
				PreAuthenticatedAuthenticationToken token = new PreAuthenticatedAuthenticationToken(currentUser, "", roles);
		        token.setDetails(ads.buildDetails(request));

		          // Setup the security context
		          SecurityContextHolder.getContext().setAuthentication(token);
		          
		          HttpSession session = request.getSession(true);
		          session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
		          request.getSession().setAttribute("currentUser", currentUser);

				mav = new ModelAndView("redirect:/");
				return mav;
			} else {
				throw new LoginException("Login duplicated!");
			}
		} catch (PersistenceException e) {
			logger.debug("Unregistered login: "+externalUser.getEmail());
			UserInfo userInfo = new UserInfo();
			userInfo.setEmailAddress(externalUser.getEmail());
			userInfo.setExternalID(encoder.encode(externalUser.getId()));
			userInfo.setName(externalUser.getFirst_name());
			userInfo.setLastName(externalUser.getLast_name());
		
			mav = new ModelAndView("forward:/registration");
			mav.addObject("userInfo", userInfo);
			return mav;
		}
 
			
		

	}
}
