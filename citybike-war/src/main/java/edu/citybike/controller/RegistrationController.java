package edu.citybike.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.citybike.bank.BankService;
import edu.citybike.database.DatabaseFacade;
import edu.citybike.exceptions.PersistenceException;
import edu.citybike.model.User;
import edu.citybike.model.view.CurrentUser;
import edu.citybike.model.view.UserInfo;

@Controller
public class RegistrationController {

	private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

	private DatabaseFacade facade;
	private PasswordEncoder encoder;

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
	
	@RequestMapping("/registration")
	public String showAddRegistrationForm(HttpServletRequest request, ModelMap map) {
		System.out.println("Reg: "+request.getAttribute("userInfo"));
		map.addAttribute("formAction", "/register.do");
		if(request.getAttribute("userInfo") == null){
			map.addAttribute("userInfo", new UserInfo());
		} else {
			map.addAttribute("userInfo",request.getAttribute("userInfo"));
		}
		
		return "userdata";
	}
	
/*	@ModelAttribute("userInfo")
	public UserInfo addUserModel(){
		return new UserInfo();
	}	
*/	
	@RequestMapping(value = "/register.do", method = RequestMethod.POST)
	public String registerUser(@ModelAttribute("userInfo") UserInfo userInfo, HttpServletRequest request){

		EntityTransaction tr = facade.getTransaction();
		try {
			tr.begin();
			userInfo.getCredentials().setPassword(encoder.encode(userInfo.getCredentials().getPassword()));
			facade.add(userInfo.getCredentials());
			userInfo.getUser().setRegistrationDate(new Date());
			facade.add(userInfo.getUser());
			
			User newUser = facade.getUserByLogin(userInfo.getUser().getEmailAddress());
			BankService.createBankAccount(newUser.getKey());
			tr.commit();
			
			List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
			roles.add(new SimpleGrantedAuthority(newUser.getRole()));

			CurrentUser currentUser = new CurrentUser(newUser.getKey(), newUser.getEmailAddress(), "", roles);
			request.getSession().setAttribute("currentUser", currentUser);
		} catch (Exception e) {
			logger.error("Error during registration: "+e.getMessage());
			tr.rollback();
		}

		return "redirect:/";

	}
}
