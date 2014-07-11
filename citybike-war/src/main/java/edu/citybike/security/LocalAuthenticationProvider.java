package edu.citybike.security;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import edu.citybike.database.DatabaseFacade;
import edu.citybike.database.DatabaseFacadeImpl;
import edu.citybike.database.exception.PersistenceException;
import edu.citybike.model.User;
import edu.citybike.model.view.CurrentUser;

@Component
public class LocalAuthenticationProvider implements AuthenticationProvider {
	@Autowired
    private UserDetailsService userService;
	private DatabaseFacade facade;

	public DatabaseFacade getFacade() {
		return facade;
	}


	public void setFacade(DatabaseFacade facade) {
		this.facade = facade;
	}


	public UserDetailsService getUserService() {
		return userService;
	}


	public void setUserService(UserDetailsService userService) {
		this.userService = userService;
	}


	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = (String) authentication.getCredentials();

        CurrentUser user = (CurrentUser) userService.loadUserByUsername(email);

        if (user == null) {
            throw new BadCredentialsException("Username not found.");
        }

        if (!password.equals(user.getPassword())) {
        	try {
				User possibleUser = facade.getUserByLogin(email);
				possibleUser.setLastFailedLogin(new Date());
				facade.update(possibleUser);
			} catch (PersistenceException e) {
			}
        	
            throw new BadCredentialsException("Wrong password.");
        }
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
       
        return new UsernamePasswordAuthenticationToken(user, password, authorities);
    }
	

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

}
