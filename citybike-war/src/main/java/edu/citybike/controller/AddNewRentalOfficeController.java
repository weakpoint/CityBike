package edu.citybike.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.citybike.database.DatabaseFacade;
import edu.citybike.database.exception.PersistenceException;
import edu.citybike.model.RentalOffice;

@Controller
public class AddNewRentalOfficeController{

	private static final Logger logger = LoggerFactory.getLogger(AddNewRentalOfficeController.class);
	private DatabaseFacade facade;
	private String referer;

	public DatabaseFacade getFacade() {
		return facade;
	}

	public void setFacade(DatabaseFacade facade) {
		this.facade = facade;
	}
	
	@ModelAttribute("newRentalOffice")
	public RentalOffice addNewRentalOffice() {
		return new RentalOffice();
	}
	
	@RequestMapping(value ="/addNewRentalOffice")
	public String showNewRentalOfficeForm(@RequestHeader(value = "referer", required = false) String referer) {
		this.referer = referer;
		return "addnewrentaloffice";
	}
	
	@RequestMapping(value = "/addNewRentalOffice", method = RequestMethod.POST)
	public String addNewRentalOffice(@ModelAttribute("newRentalOffice") RentalOffice rentalOffice) {
		
		try {
			facade.add(rentalOffice);
		} catch (PersistenceException e) {
			logger.error("Error during rental office storing: "+e.getMessage());
		}
		if(referer != null){
		return "redirect:"+referer;
		} else {
			return "redirect:/";
		}
	}
}
