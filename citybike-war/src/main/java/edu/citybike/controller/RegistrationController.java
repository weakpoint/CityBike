package edu.citybike.controller;

import javax.persistence.EntityTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.citybike.database.DatabaseFacade;
import edu.citybike.database.exception.PersistenceException;
import edu.citybike.model.view.UserInfo;

@Controller
public class RegistrationController {

	private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

	private DatabaseFacade facade;

	public DatabaseFacade getFacade() {
		return facade;
	}

	public void setFacade(DatabaseFacade facade) {
		this.facade = facade;
	}
	
	@RequestMapping("/registration")
	public String showAddBikeForm(ModelMap map) {
		map.addAttribute("formAction", "/register.do");
		return "userdata";
	}
	
	@ModelAttribute("userInfo")
	public UserInfo addUserModel(){
		return new UserInfo();
	}	
	
	@RequestMapping(value = "/register.do", method = RequestMethod.POST)
	public String registerUser(@ModelAttribute("user") UserInfo userInfo, ModelMap map){
		
		//sprawdzanie czy mail sie nie powtarza
		
		//
		EntityTransaction tr = facade.getTransaction();
		try {
			facade.add(userInfo.getCredentials());
			facade.add(userInfo.getUser());
			tr.commit();
			return "redirect:/";
		} catch (PersistenceException e) {
			logger.error("Error during registration: "+e.getMessage());
			tr.rollback();
		}
		map.addAttribute("userInfo", userInfo);
		return "userData";

	}
}
