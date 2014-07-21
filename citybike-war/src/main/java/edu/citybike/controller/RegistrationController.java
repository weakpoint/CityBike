package edu.citybike.controller;

import java.util.Date;

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
	public String showAddRegistrationForm(ModelMap map) {
		map.addAttribute("formAction", "/register.do");
		map.addAttribute("userInfo", new UserInfo());
		return "userdata";
	}
	
/*	@ModelAttribute("userInfo")
	public UserInfo addUserModel(){
		return new UserInfo();
	}	
*/	
	@RequestMapping(value = "/register.do", method = RequestMethod.POST)
	public String registerUser(@ModelAttribute("userInfo") UserInfo userInfo){
		
		//sprawdzanie czy mail sie nie powtarza
		
		//
System.out.println("register");
		EntityTransaction tr = facade.getTransaction();
		try {
			System.out.println("IS ACTIVE1: "+tr.isActive());
			tr.begin();
			System.out.println("IS ACTIVE2: "+tr.isActive());
			System.out.println(userInfo.getCredentials());
			facade.add(userInfo.getCredentials());
			userInfo.getUser().setRegistrationDate(new Date());
			System.out.println(userInfo.getUser());
			facade.add(userInfo.getUser());
			tr.commit();
			
		} catch (PersistenceException e) {
			logger.error("Error during registration: "+e.getMessage());
			tr.rollback();
		}

		return "redirect:/";

	}
}
