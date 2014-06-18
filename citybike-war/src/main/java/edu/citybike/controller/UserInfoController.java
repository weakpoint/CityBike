package edu.citybike.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
	public String showUserInfoForm(ModelMap map, HttpServletRequest request) {

User currentUser = ((User) request.getSession().getAttribute("currentUser"));


		if(currentUser == null){
			logger.error("Current user is null");
			 currentUser = new User();
		}
		Credentials credential = null;
		try {
			credential = facade.getCredentials(currentUser.getRentalNetworkCode(), currentUser.getEmailAddress());
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
		}
		UserInfo userInfo = new UserInfo(currentUser, credential);

		map.addAttribute("userInfo", userInfo);
		
		map.addAttribute("formAction", "/userInfo");
		return "userdata";
	}
	
	@RequestMapping(value="/userInfo", method= RequestMethod.POST)
	public ModelAndView saveForm(@ModelAttribute("userInfo") UserInfo user,ModelMap map, HttpSession session) {
		Object uu = map.get("UserInfo");
		System.out.println("PPPPP: "+uu);
		ModelAndView mav = new ModelAndView("userdata");
		try {
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			System.out.println("user: "+user.getUser());
			facade.update(user.getUser());
			System.out.println("???????????????????????????????");
			facade.update(user.getCredentials());

			mav.addObject("userInfo", user);
			session.setAttribute("currentUser", user.getUser());
		} catch (PersistenceException e) {
			logger.error(e.getMessage(), e);
		}

		return mav;
	}
/*	
	@ModelAttribute("userInfo")
	public UserInfo addUserModel(HttpSession session){
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		User currentUser = ((User) session.getAttribute("currentUser"));
		
		if(currentUser == null){
			logger.error("Current user is null");
			 currentUser = new User();
		}
		Credentials credential = null;
		try {
			credential = facade.getCredentials(currentUser.getRentalNetworkCode(), currentUser.getEmailAddress());
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
		}
		return new UserInfo(currentUser, credential);
		
	}
	*/
}
