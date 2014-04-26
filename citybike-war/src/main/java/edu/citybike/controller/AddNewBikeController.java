package edu.citybike.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

@Controller
@SessionAttributes
public class AddNewBikeController {
	private static final Logger logger = LoggerFactory.getLogger(AddNewBikeController.class);
	private DatabaseFacade facade;

	public DatabaseFacade getFacade() {
		return facade;
	}

	public void setFacade(DatabaseFacade facade) {
		this.facade = facade;
	}

	@RequestMapping(value = "/addNewBike", method = RequestMethod.POST)
	public String addNewBike(@ModelAttribute("newBike") Bike bike) {
		logger.info("Nazwa: " + bike.getTechnicalDetails().getName() + " \nNumer wypozyczalni: "
				+ bike.getRentalOfficeCode());
		// System.out.println("Nazwa: "+bike.getTechnicalDetails().getName()+" \nNumer wypozyczalni: "+bike.getRentalOfficeCode());
		try {
			facade.save(bike);
		} catch (PersistenceException e) {
			logger.error("Error during bike storing: "+e.getMessage());
		}
		return "redirect:/addNewBike";
	}
/*
	@RequestMapping("/addNewBike")
	public ModelAndView showAddBikeForm() {
		String rentalNetworkCode = ""; // ------------>TODO rental code
		Map<String, String> rentalOfficeCodeList = new HashMap<String, String>();
		
			rentalOfficeCodeList = createRentalOfficeCodeMap(rentalNetworkCode);
		} catch (PersistenceException e) {
			logger.error("Error during rental office map creation: " + e.getMessage());
			rentalOfficeCodeList.put("", "---");
		}

		ModelAndView modelAndView = new ModelAndView("addnewbike");
		modelAndView.addObject("rentalOfficeCodeList", rentalOfficeCodeList);
		modelAndView.addObject("newBike", new Bike());
		return modelAndView;
	}
*/
@RequestMapping("/addNewBike")
public String showAddBikeForm() {
	return "addnewbike";
}
	@ModelAttribute("rentalOfficeCodeList")
	public Map<String, String> createRentalOfficeCodeMap(String rentalNetworkCode) {
		List<RentalOffice> rentalOfficeList = new ArrayList<>();
		Map<String, String> rentalOfficeMap = new HashMap<String, String>();
		
		try {
		rentalOfficeList = facade.getRentalOfficeList(rentalNetworkCode);
		for (RentalOffice rentalOffice : rentalOfficeList) {
			String address = rentalOffice.getAddress().getCity() + ", " + rentalOffice.getAddress().getStreet() + " "
					+ rentalOffice.getAddress().getHouseNumber();
			rentalOfficeMap.put(rentalOffice.getRentalOfficeCode(), address);
		}
		} catch (PersistenceException e) {
			logger.error("Error during rental office map creation: " + e.getMessage());
			rentalOfficeMap.put("", "---");
		}
		return rentalOfficeMap;
	}
	
	@ModelAttribute("newBike")
	public Bike addNewBike(){
		return new Bike();
	}
}
