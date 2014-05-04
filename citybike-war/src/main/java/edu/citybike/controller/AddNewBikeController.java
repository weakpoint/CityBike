package edu.citybike.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.citybike.database.DatabaseFacade;
import edu.citybike.database.exception.PersistenceException;
import edu.citybike.model.Bike;
import edu.citybike.model.RentalOffice;
import edu.citybike.model.User;

@Controller
@SessionAttributes
public class AddNewBikeController {
	private static final Logger logger = LoggerFactory.getLogger(AddNewBikeController.class);
	private DatabaseFacade facade;
	private List<RentalOffice> rentalOfficeList;

	public DatabaseFacade getFacade() {
		return facade;
	}

	public void setFacade(DatabaseFacade facade) {
		this.facade = facade;
	}

	@RequestMapping(value = "/addNewBike", method = RequestMethod.POST)
	public String addNewBike(@ModelAttribute("newBike") Bike bike, HttpSession session) {
		//here rentalofficecode is an index of rentalofficelist
		bike.setRentalOfficeCode(rentalOfficeList.get(Integer.parseInt(bike.getRentalOfficeCode())).getRentalOfficeCode());
		bike.setRentalNetworkCode(((User) session.getAttribute("currentUser")).getRentalNetworkCode());
		
		logger.info("Nazwa: " + bike.getTechnicalDetails().getName() + " \nNumer wypozyczalni: "
				+ bike.getRentalOfficeCode());
		
		try {
			facade.save(bike);
		} catch (PersistenceException e) {
			logger.error("Error during bike storing: " + e.getMessage());
		}
		return "redirect:/addNewBike";
	}

	@RequestMapping("/addNewBike")
	public String showAddBikeForm() {
		return "addnewbike";
	}

	@ModelAttribute("rentalOfficeCodeList")
	public Map<Integer, String> createRentalOfficeCodeMap(HttpSession session) {
		String rentalNetworkCode = ((User) session.getAttribute("currentUser")).getRentalNetworkCode(); 
		rentalOfficeList = new ArrayList<RentalOffice>();
		Map<Integer, String> rentalOfficeMap = new HashMap<Integer, String>();

		try {
			rentalOfficeList = facade.getRentalOfficeList(rentalNetworkCode);
			RentalOffice rentalOffice;
			for (int i = 0; i < rentalOfficeList.size();i++) {
				rentalOffice = rentalOfficeList.get(i);
				String address = rentalOffice.getAddress().getCity() + ", " + rentalOffice.getAddress().getStreet()
						+ " " + rentalOffice.getAddress().getHouseNumber();
				rentalOfficeMap.put(i, address);
			}
		} catch (PersistenceException e) {
			logger.error("Error during rental office map creation: " + e.getMessage());
		}
		return rentalOfficeMap;
	}

	@ModelAttribute("newBike")
	public Bike addNewBike() {
		return new Bike();
	}
}
