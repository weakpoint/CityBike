package edu.citybike.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.citybike.database.DatabaseFacade;
import edu.citybike.model.Credentials;

@Controller
public class UserAuthenticationController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserAuthenticationController.class);
	private DatabaseFacade facade;

	public DatabaseFacade getFacade() {
		return facade;
	}

	public void setFacade(DatabaseFacade facade) {
		this.facade = facade;
	}
	
	@ModelAttribute("credentials")
	public Credentials addUserObject(){
		Credentials c = new Credentials();
		return c;
	}
	
	@RequestMapping(value = "/login.do")
	public String verifyUser(HttpSession session){
		return "authenticationPage";
	}

}
