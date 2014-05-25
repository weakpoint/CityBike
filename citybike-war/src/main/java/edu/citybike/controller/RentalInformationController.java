package edu.citybike.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import edu.citybike.database.DatabaseFacade;
import edu.citybike.database.exception.PersistenceException;
import edu.citybike.model.Fee;
import edu.citybike.model.Rent;
import edu.citybike.model.User;
import edu.citybike.utilities.ControllerUtilities;

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
	public ModelAndView showUserRentalInformation(HttpSession session) {
		//obliczeni i wyswietlenie czasu i kosztu aktualnego wypozyczenia
		//sciagnienie ogolnego i dodanie (tylko do wyswietlenia) aktualnego czasu i kosztu
		ModelAndView model = new ModelAndView("rentalinformation");
		User user = (User)session.getAttribute("currentUser");
		
		long overallTime = user.getOverallRentalTime();
		double overallCost = user.getOverallRentalCost();
		long actualTime = 0;
		double actualCost = 0;
		
		Rent userRent = null;
		try {
			userRent = facade.getLastUserRent(user.getRentalNetworkCode(), user.getUserCode());
		
		if(userRent  == null || (userRent.getEndDate().compareTo(new Date())) < 0){
			//schowac panel aktualnych danych
			model.addObject("hideActualSection", "hidden");
			
		} else {
			actualTime = (userRent.getStartDate().getTime() - new Date().getTime())/(1000*60);
			actualCost = ControllerUtilities.calculatePayment(facade.getFeeList(user.getRentalNetworkCode()), actualTime);
			overallTime += actualTime;
			overallCost += actualCost;
			model.addObject("hideActualSection", "");
		}
		
		model.addObject("actualRentTime", actualTime);
		model.addObject("actualRentCost", actualCost);
		model.addObject("overallRentalTime", overallTime);
		model.addObject("overallRentalCost", overallCost);
		
		} catch (PersistenceException e) {
			logger.error(e.getMessage(),e);
		}				
		return model;
	}


}
