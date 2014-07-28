package edu.citybike.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.citybike.bank.BankService;
import edu.citybike.database.DatabaseFacade;
import edu.citybike.exceptions.PersistenceException;
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
		double balance = 0;
		try {
			credential = facade.getCredentials(user.getEmailAddress());
			balance = BankService.checkBalance(user.getKey());
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
		}
		UserInfo userInfo = new UserInfo(user, credential, balance);

		map.addAttribute("userInfo", userInfo);
		
		map.addAttribute("formAction", "/userInfo");
		return "userdata";
	}
	
	@RequestMapping(value="/userInfo", method= RequestMethod.POST)
	public ModelAndView saveForm(@ModelAttribute("userInfo") UserInfo user, ModelMap map) {
		ModelAndView mav = new ModelAndView("userdata");
		try {
			User userAfterChanges = ControllerUtilities.retriveChangedUser(facade, user);
			facade.update(userAfterChanges);
			mav.addObject("userInfo", new UserInfo(userAfterChanges, user.getCredentials(), BankService.checkBalance(userAfterChanges.getKey())));
		} catch (PersistenceException e) {
			logger.error(e.getMessage(), e);
		}

		return mav;
	}
}
