package edu.citybike.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.google.appengine.api.datastore.KeyFactory;

import edu.citybike.database.DatabaseFacade;
import edu.citybike.exceptions.PersistenceException;
import edu.citybike.model.Bike;
import edu.citybike.model.RentalOffice;
import edu.citybike.model.view.NewBike;

@Controller
@SessionAttributes
public class AddNewBikeController {
	private static final Logger logger = LoggerFactory.getLogger(AddNewBikeController.class);
	private DatabaseFacade facade;
	private List<RentalOffice> rentalOfficeList;
	private Validator validator;

	public void setFacade(DatabaseFacade facade) {
		this.facade = facade;
	}

	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	@RequestMapping(value = "/admin/addNewBike", method = RequestMethod.POST)
	public ModelAndView addNewBike(@ModelAttribute("newBike") NewBike newBike, BindingResult result) {

		validator.validate(newBike, result);
		if (result.hasErrors()) {
			return new ModelAndView("addnewbike", "newBike", newBike);
		}
		Bike bike = new Bike();
		bike.setTechnicalDetails(newBike.getTechnicalDetails());
		bike.setRentalOfficeKey(KeyFactory.stringToKey(newBike.getRentalOfficeCode()));

		try {
			facade.add(bike);
		} catch (PersistenceException e) {
			logger.error("Error during bike storing: " + e.getMessage());
		}

		return new ModelAndView("redirect:/");
	}

	@RequestMapping("/admin/addNewBike")
	public String showAddBikeForm() {
		return "addnewbike";
	}

	@ModelAttribute("rentalOfficeCodeList")
	public Map<String, String> createRentalOfficeCodeMap(HttpSession session) {
		rentalOfficeList = new ArrayList<RentalOffice>();
		Map<String, String> rentalOfficeMap = new HashMap<String, String>();
		rentalOfficeList = facade.getRentalOfficeList();
		RentalOffice rentalOffice;
		for (int i = 0; i < rentalOfficeList.size(); i++) {
			rentalOffice = rentalOfficeList.get(i);
			String address = rentalOffice.getAddress().getCity() + ", " + rentalOffice.getAddress().getStreet() + " "
					+ rentalOffice.getAddress().getHouseNumber();
			rentalOfficeMap.put(KeyFactory.keyToString(rentalOffice.getKey()), address);
		}
		return rentalOfficeMap;
	}

	@ModelAttribute("newBike")
	public NewBike addNewBike() {
		return new NewBike();
	}
}
