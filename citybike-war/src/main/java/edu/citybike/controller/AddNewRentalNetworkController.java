package edu.citybike.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.citybike.database.DatabaseFacade;
import edu.citybike.database.exception.ModelAlreadyExistsException;
import edu.citybike.database.exception.PersistenceException;
import edu.citybike.model.RentalNetwork;
import edu.citybike.model.User;

@Controller
public class AddNewRentalNetworkController {
	private static final Logger logger = LoggerFactory.getLogger(AddNewRentalNetworkController.class);
	@Autowired
	private DatabaseFacade facade;

	public DatabaseFacade getFacade() {
		return facade;
	}

	public void setFacade(DatabaseFacade facade) {
		this.facade = facade;
	}

	@ModelAttribute("newRentalNetwork")
	public RentalNetwork addRentalNetwork() {
		return new RentalNetwork();

	}

	@RequestMapping("/addRentalNetwork")
	public String showNewRentalNetworkForm() {

		return "addnewrentalnetwork";

	}

	@RequestMapping(value = "/addRentalNetwork", method = RequestMethod.POST)
	public String showNewRentalNetworkForm(@ModelAttribute("currentUser") User currentUser,
			@ModelAttribute("newRentalNetwork") RentalNetwork newRentalNetwork) {
		
		if (currentUser.getRole().equals(User.SUPERADMIN)) {
			try {
				facade.add(newRentalNetwork);
			} catch (ModelAlreadyExistsException e) {
				//juz taki jest ustawic error message
				logger.error("Error during new rental network creation: " + e.getMessage(), e);
			} catch (PersistenceException e) {
				logger.error("Error during new rental network creation: " + e.getMessage(), e);
			}
			return "/addRentalNetwork";
		}
		
		return "redirect:/";

	}

}
