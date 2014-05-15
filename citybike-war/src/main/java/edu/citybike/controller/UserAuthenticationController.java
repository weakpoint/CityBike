package edu.citybike.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.citybike.database.DatabaseFacade;
import edu.citybike.database.exception.PersistenceException;
import edu.citybike.model.Credentials;
import edu.citybike.model.User;

@Controller
@SessionAttributes("currentUser")
public class UserAuthenticationController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserAuthenticationController.class);
	private DatabaseFacade facade;
	private String RENTAL_NETWORK_CODE	= "0001";

	public DatabaseFacade getFacade() {
		return facade;
	}

	public void setFacade(DatabaseFacade facade) {
		this.facade = facade;
	}
	
	@ModelAttribute("credentials")
	public Credentials addUserObject(){
		Credentials c = new Credentials();
		c.setEmailAddress("emil.1990@interia.pl");
		c.setPassword("test");
		return c;
	}
	
	@RequestMapping(value = "/login.do")
	public String verifyUser(HttpSession session){
		session.setAttribute("rentalNetworkCode", RENTAL_NETWORK_CODE);
		return "authenticationPage";
	}
	
	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public String verifyUser(@ModelAttribute("credentials") Credentials credentials, ModelMap model){
		Credentials crd = null;
		try {
			crd = facade.getCredentials(RENTAL_NETWORK_CODE, credentials.getEmailAddress());
		} catch (PersistenceException e) {
			logger.error("Wrong e-mail/password");
		}
		User user = null;
		if(crd.getPassword().equals(credentials.getPassword())){
			try {
				user = facade.getUser(crd);
			} catch (PersistenceException e) {
				logger.error(e.getMessage());
			}
		} else{
			logger.error("Wrong e-mail/password");
			return "redirect:/login.do";
		}
		logger.info("User: "+user+" verified");
		model.addAttribute("currentUser", user);
		return "redirect:/";
	}
}
