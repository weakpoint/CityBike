package edu.citybike.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import edu.citybike.model.view.CurrentUser;
import edu.citybike.model.view.UserInfo;
import edu.citybike.utilities.ControllerUtilities;

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

		CurrentUser currentUser = (CurrentUser)request.getSession().getAttribute("currentUser");
		if(currentUser == null){
			//!!!!!!!!!!
		}
		
		User user = facade.getUserByKey(currentUser.getUserKey());

		Credentials credential = null;
		try {
			credential = facade.getCredentials(user.getEmailAddress());
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
		}
		UserInfo userInfo = new UserInfo(user, credential);

		map.addAttribute("userInfo", userInfo);
		
		map.addAttribute("formAction", "/userInfo");
		return "userdata";
	}
	
	@RequestMapping(value="/userInfo", method= RequestMethod.POST)
	public ModelAndView saveForm(@ModelAttribute("userInfo") UserInfo user,ModelMap map, HttpSession session) {
		ModelAndView mav = new ModelAndView("userdata");
		try {
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			User userAfterChanges = ControllerUtilities.retriveChangedUser(facade, user);
			facade.update(userAfterChanges);
			System.out.println("???????????????????????????????");
			System.out.println("USER INFO "+user);
			//facade.update(user.getCredentials());
			System.out.println("creden "+user.getCredentials());
			mav.addObject("userInfo", new UserInfo(userAfterChanges, user.getCredentials()));
			//session.setAttribute("currentUser", user.getUser());
		} catch (PersistenceException e) {
			logger.error(e.getMessage(), e);
		}

		return mav;
	}
}
