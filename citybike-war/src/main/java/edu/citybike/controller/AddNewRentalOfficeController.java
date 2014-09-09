package edu.citybike.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.citybike.database.DatabaseFacade;
import edu.citybike.exceptions.PersistenceException;
import edu.citybike.model.RentalOffice;

@Controller
public class AddNewRentalOfficeController{

	private static final Logger logger = LoggerFactory.getLogger(AddNewRentalOfficeController.class);
	private DatabaseFacade facade;
	private String referer;
	private Validator validator;

	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	public void setFacade(DatabaseFacade facade) {
		this.facade = facade;
	}
	
	@ModelAttribute("newRentalOffice")
	public RentalOffice addNewRentalOffice() {
		return new RentalOffice();
	}
	
	@RequestMapping(value ="/admin/addNewRentalOffice")
	public String showNewRentalOfficeForm(@RequestHeader(value = "referer", required = false) String referer) {
		this.referer = referer;
		return "addnewrentaloffice";
	}
	
	@RequestMapping(value = "/admin/addNewRentalOffice", method = RequestMethod.POST)
	public ModelAndView addNewRentalOffice(@ModelAttribute("newRentalOffice") RentalOffice rentalOffice, BindingResult result) {
		
		//form validation
		validator.validate(rentalOffice, result);
		if (result.hasErrors()) {
			return new ModelAndView("addnewrentaloffice", "newRentalOffice", rentalOffice);
		}
		
		try {
			facade.add(rentalOffice);
		} catch (PersistenceException e) {
			logger.error("Error during rental office storing: "+e.getMessage());
		}
		
		if(referer != null){
		return new ModelAndView("redirect:"+referer);
		} else {
			return new ModelAndView("redirect:/");
		}
	}
}
