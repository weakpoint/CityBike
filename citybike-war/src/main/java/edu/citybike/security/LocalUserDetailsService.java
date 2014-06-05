package edu.citybike.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import edu.citybike.database.DatabaseFacade;
import edu.citybike.database.exception.PersistenceException;
import edu.citybike.model.Credentials;
import edu.citybike.model.User;
import edu.citybike.model.view.UserInfo;

public class LocalUserDetailsService implements UserDetailsService{

	@Autowired
	private DatabaseFacade facade;

	public DatabaseFacade getFacade() {
		return facade;
	}

	public void setFacade(DatabaseFacade facade) {
		this.facade = facade;
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		String rentalNetworkCode = "0001"; //!!
		Credentials credentials = null;
		User user = null;
		try {
			credentials = facade.getCredentials(rentalNetworkCode, email);
			user = facade.getUser(credentials);
		} catch (PersistenceException e) {
			throw new UsernameNotFoundException("Username not found!");
		}
		return new UserInfo(user, credentials);
	}

}
