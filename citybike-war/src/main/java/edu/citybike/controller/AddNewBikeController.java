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

import com.google.appengine.api.datastore.KeyFactory;

import edu.citybike.database.DatabaseFacade;
import edu.citybike.exceptions.PersistenceException;
import edu.citybike.model.Bike;
import edu.citybike.model.RentalOffice;
import edu.citybike.model.User;
import edu.citybike.model.view.NewBike;

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
	public String addNewBike(@ModelAttribute("newBike") NewBike newBike) {
		Bike bike = new Bike();
		bike.setTechnicalDetails(newBike.getTechnicalDetails());
		bike.setRentalOfficeKey(KeyFactory.stringToKey(newBike.getRentalOfficeCode()));
		
		try {
			facade.add(bike);
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
	public Map<String, String> createRentalOfficeCodeMap(HttpSession session) {
		rentalOfficeList = new ArrayList<RentalOffice>();
		Map<String, String> rentalOfficeMap = new HashMap<String, String>();
			rentalOfficeList = facade.getRentalOfficeList();
			RentalOffice rentalOffice;
			for (int i = 0; i < rentalOfficeList.size();i++) {
				rentalOffice = rentalOfficeList.get(i);
				String address = rentalOffice.getAddress().getCity() + ", " + rentalOffice.getAddress().getStreet()
						+ " " + rentalOffice.getAddress().getHouseNumber();
				rentalOfficeMap.put(KeyFactory.keyToString(rentalOffice.getKey()), address);
			}
		return rentalOfficeMap;
	}

	@ModelAttribute("newBike")
	public NewBike addNewBike() {
		return new NewBike();
	}
}
