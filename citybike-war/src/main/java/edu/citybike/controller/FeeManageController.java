package edu.citybike.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.citybike.database.DatabaseFacade;
import edu.citybike.database.exception.PersistenceException;
import edu.citybike.model.Fee;

@Controller
public class FeeManageController {

	private static final Logger logger = LoggerFactory.getLogger(FeeManageController.class);
	private DatabaseFacade facade;

	public DatabaseFacade getFacade() {
		return facade;
	}

	public void setFacade(DatabaseFacade facade) {
		this.facade = facade;
	}
	
	@RequestMapping("/feeManager")
	public String feeManager() {
		return "feeManagePage";
	}
	
	@ModelAttribute("feeList")
	public List<Fee> addFeeList(HttpSession session) {
		String rentalNetworkCode = (String)session.getAttribute("rentalNetworkCode");
		List<Fee> feeList = new ArrayList<Fee>();
		try {
			feeList = facade.getFeeList(rentalNetworkCode);
		} catch (PersistenceException e) {
			logger.error("Error during getting fee list: "+e.getMessage());
		}
		return feeList;
	}
	
	@RequestMapping(value = "/feeManager", method = RequestMethod.POST)
	public String submitForm(){
		
		return "redirect:/";
	}

}
