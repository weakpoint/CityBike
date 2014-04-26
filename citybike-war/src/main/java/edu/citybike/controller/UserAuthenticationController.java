package edu.citybike.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.citybike.database.DatabaseFacade;
import edu.citybike.model.User;

@Controller
@SessionAttributes("currentUser")
public class UserAuthenticationController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserAuthenticationController.class);
	private DatabaseFacade facade;

	public DatabaseFacade getFacade() {
		return facade;
	}

	public void setFacade(DatabaseFacade facade) {
		this.facade = facade;
	}
	
	@ModelAttribute("currentUser")
	public User addUserObject(){
		return new User();
	}
	
	@RequestMapping(value = "/login.do")
	public String verifyUser(){
		
		return "authenticationPage";
	}
	
	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public String verifyUser(@ModelAttribute("currentUser") User user){
		logger.info("User: "+user+" verified");
		user.setName("verified");
		
		return "redirect:/";
	}
}
