package edu.citybike.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import edu.citybike.database.DatabaseFacade;
import edu.citybike.exceptions.ModelNotExistsException;
import edu.citybike.exceptions.PersistenceException;
import edu.citybike.model.Bike;
import edu.citybike.model.Rent;
import edu.citybike.model.RentalOffice;
import edu.citybike.model.view.Coordinates;
import edu.citybike.model.view.CurrentUser;
import edu.citybike.utilities.ControllerUtilities;

@Controller
public class RentalInformationController {
	private static final Logger logger = LoggerFactory.getLogger(RentalInformationController.class);
	private DatabaseFacade facade;

	public void setFacade(DatabaseFacade facade) {
		this.facade = facade;
	}

	@ModelAttribute("coordinates")
	public List<Coordinates> addCoordinates(HttpSession session) {

		List<RentalOffice> rentalOfficeList = null;

		rentalOfficeList = facade.getRentalOfficeList();

		if (rentalOfficeList == null) {
			rentalOfficeList = new ArrayList<RentalOffice>();
		}
		List<Coordinates> coordinates = new ArrayList<Coordinates>();
		for (RentalOffice ro : rentalOfficeList) {
			Coordinates c = new Coordinates();
			c.latitude = ro.getLatitude();
			c.longitude = ro.getLongitude();
			coordinates.add(c);
		}
		return coordinates;

	}

	@RequestMapping(value = "/rentalInformation")
	public ModelAndView showUserRentalInformation(HttpSession session) {
		ModelAndView model = new ModelAndView("rentalinformation");
		CurrentUser user = ((CurrentUser) session.getAttribute("currentUser"));

		List<Rent> userRentList = null;
		long overallTime = 0;
		double overallCost = 0;
		long actualTime = 0;
		double actualCost = 0;

		try {
			userRentList = facade.getUserRentListDesc(user.getUserKey());
			overallTime = calculateOveralRentalTime(userRentList);
			overallCost = calculateOveralRentalCost(userRentList);
			actualTime = 0;
			actualCost = 0;
		} catch (PersistenceException e) {
			logger.error("Error during getting user rent list");
		}

		Rent lastUserRent = null;
		try {
			lastUserRent = facade.getUserActiveRental(user.getUserKey());

			if (lastUserRent.isActive()) {
				actualTime = (new Date().getTime() - lastUserRent.getStartDate().getTime()) / (1000 * 60);
				actualCost = ControllerUtilities.calculatePayment(facade.getFeeList(), actualTime);
				overallTime += actualTime;
				overallCost += actualCost;
				model.addObject("hasActive", "true");

				Bike bike = facade.getBikeByKey(lastUserRent.getBikeCode());
				if (bike != null) {
					model.addObject("bikeInfo", bike.getTechnicalDetails().getName());
				} else {
					throw new ModelNotExistsException("Bike not found");
				}
			}

		} catch (PersistenceException e) {
			logger.error(e.getMessage());
		}
		model.addObject("actualRentTime", actualTime);
		model.addObject("actualRentCost", actualCost);
		model.addObject("overallRentalTime", overallTime);
		model.addObject("overallRentalCost", overallCost);

		return model;
	}

	private long calculateOveralRentalTime(List<Rent> rentList) {
		long time = 0;
		for (Rent rent : rentList) {
			time += rent.getRentDuration();
		}
		return time;
	}

	private double calculateOveralRentalCost(List<Rent> rentList) {
		double cost = 0;
		for (Rent rent : rentList) {
			cost += rent.getRentCost();
		}
		return cost;
	}
}
