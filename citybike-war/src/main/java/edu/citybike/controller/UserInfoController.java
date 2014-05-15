package edu.citybike.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.citybike.database.DatabaseFacade;
import edu.citybike.database.exception.PersistenceException;
import edu.citybike.model.Credentials;
import edu.citybike.model.User;
import edu.citybike.model.view.UserInfo;

@Controller
public class UserInfoController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserInfoController.class);
	private DatabaseFacade facade;

	public DatabaseFacade getFacade() {
		return facade;
	}

	public void setFacade(DatabaseFacade facade) {
		this.facade = facade;
	}

	@RequestMapping("/userInfo")
	public String showAddBikeForm() {

		return "userdata";
	}
	
	@ModelAttribute("userInfo")
	public UserInfo addUserModel(HttpSession session){
		User currentUser = ((User) session.getAttribute("currentUser"));
		
		if(currentUser == null){
			logger.error("Current user is null");
			 currentUser = new User();
		}
		Credentials credential = null;
		try {
			credential = facade.getCredentials(currentUser.getEmailAddress(), currentUser.getEmailAddress());
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
		}
		
		return new UserInfo(currentUser, credential);
		
	}
}
