package edu.citybike.security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import edu.citybike.model.view.UserInfo;

@Component
public class LocalAuthenticationProvider implements AuthenticationProvider {
	@Autowired
    private UserDetailsService userService;

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

        UserInfo user = (UserInfo) userService.loadUserByUsername(email);

        if (user == null) {
            throw new BadCredentialsException("Username not found.");
        }
 
        if (!password.equals(user.getPassword())) {
            throw new BadCredentialsException("Wrong password.");
        }
 
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        return new UsernamePasswordAuthenticationToken(user.getUser(), password, authorities);
    }
	

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

}
