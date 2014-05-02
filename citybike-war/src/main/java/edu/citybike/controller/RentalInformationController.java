package edu.citybike.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import edu.citybike.database.DatabaseFacade;

@Controller
public class RentalInformationController {
	private static final Logger logger = LoggerFactory.getLogger(RentalInformationController.class);
	private DatabaseFacade facade;

	public DatabaseFacade getFacade() {
		return facade;
	}

	public void setFacade(DatabaseFacade facade) {
		this.facade = facade;
	}
	
	@RequestMapping(value ="/rentalInformation")
	public ModelAndView showUserRentalInformation() {
		ModelAndView model = new ModelAndView("rentalinformation");
		model.addObject("actualRentTime", "2:23");
		model.addObject("actualRentCost", "150");
		model.addObject("overallRentalTime", "15:15");
		model.addObject("overallRentalCost", "5115");
								
		return model;
	}

}
