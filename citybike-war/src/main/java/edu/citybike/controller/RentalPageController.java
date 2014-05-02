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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.citybike.database.DatabaseFacade;
import edu.citybike.database.exception.PersistenceException;
import edu.citybike.model.Rent;
import edu.citybike.model.RentalOffice;
import edu.citybike.model.User;

@Controller
public class RentalPageController {
	private static final Logger logger = LoggerFactory.getLogger(RentalPageController.class);
	private DatabaseFacade facade;

	public DatabaseFacade getFacade() {
		return facade;
	}

	public void setFacade(DatabaseFacade facade) {
		this.facade = facade;
	}

	@ModelAttribute("rentalOfficeCodeList")
	public Map<String, String> createRentalOfficeCodeMap(HttpSession session) {
		String rentalNetworkCode = ((User) session.getAttribute("currentUser")).getRentalNetworkCode();
		List<RentalOffice> rentalOfficeList = new ArrayList<>();
		Map<String, String> rentalOfficeMap = new HashMap<String, String>();

		try {
			rentalOfficeList = facade.getRentalOfficeList(rentalNetworkCode);
			for (RentalOffice rentalOffice : rentalOfficeList) {
				String address = rentalOffice.getAddress().getCity() + ", " + rentalOffice.getAddress().getStreet()
						+ " " + rentalOffice.getAddress().getHouseNumber();
				rentalOfficeMap.put(rentalOffice.getRentalOfficeCode(), address);
			}
		} catch (PersistenceException e) {
			logger.error("Error during rental office map creation: " + e.getMessage());
			rentalOfficeMap.put("", "---");
		}
		return rentalOfficeMap;
	}

	@ModelAttribute("newRental")
	public Rent addNewRent() {
		return new Rent();
	}

	@RequestMapping(value = "/rentBike")
	public String showRentalForm() {

		return "rentalformPage";
	}

	@RequestMapping(value = "/rentBike", method = RequestMethod.POST)
	public String showRentalForm(@ModelAttribute("newRental") Rent rent, HttpSession session) {
		logger.info("Nowa wypozyczenie");
		String rentalNetworkCode = ((User) session.getAttribute("currentUser")).getRentalNetworkCode();
		rent.setRentalNetworkCode(rentalNetworkCode);

		try {
			facade.save(rent);
		} catch (PersistenceException e) {
			logger.error("Error during rental office storing: " + e.getMessage());
		}
		return "redirect:/";
	}

}
