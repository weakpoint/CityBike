package edu.citybike.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.appengine.api.datastore.Text;

import edu.citybike.database.DatabaseFacade;
import edu.citybike.database.exception.PersistenceException;
import edu.citybike.model.Address;
import edu.citybike.model.Credentials;
import edu.citybike.model.RentalOffice;
import edu.citybike.model.User;
import edu.citybike.model.view.Coordinates;

@Controller
public class IndexPageController {
	
	private DatabaseFacade facade;

	public DatabaseFacade getFacade() {
		return facade;
	}

	public void setFacade(DatabaseFacade facade) {
		this.facade = facade;
	}
	
	@RequestMapping("/")
	public String showIndexView() throws PersistenceException {
		try {
			facade.getUser("0001", "1");
		} catch (PersistenceException e) {
			User u = new User();
			Address address = new Address();
			address.setCity("Dobra");
			address.setHouseNumber("50");
			address.setPostalCode("95-010");
			address.setStreet("Witanówek");
			u.setAddress(address);
			u.setEmailAddress("test");
			u.setLastName("Płuciennikowski");
			u.setName("Emil");
			u.setNotes(new Text("Goooood guy!"));
			u.setPhoneNumber("500 000 000");
			u.setRentalNetworkCode("0001");
			u.setUserCode("1");
			u.setRole(User.USER);
			facade.add(u);
			
			Credentials cred = new Credentials();
			cred.setEmailAddress(u.getEmailAddress());
			cred.setPassword("test");
			cred.setRentalNetworkCode(u.getRentalNetworkCode());
			facade.add(cred);
		}

		return "index";
	}
	


}
