package edu.citybike.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.appengine.api.datastore.KeyFactory;

import edu.citybike.bank.BankService;
import edu.citybike.database.DatabaseFacade;
import edu.citybike.exceptions.ModelNotExistsException;
import edu.citybike.exceptions.NegativeBalanceException;
import edu.citybike.exceptions.PersistenceException;
import edu.citybike.model.Bike;
import edu.citybike.model.Bike.STATUS;
import edu.citybike.model.Rent;
import edu.citybike.model.RentalOffice;
import edu.citybike.model.User;
import edu.citybike.model.view.CurrentUser;
import edu.citybike.model.view.RentalBikeView;

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

	@ModelAttribute("bikeMap")
	public Map<String, String> createBikeMap() {
		List<Bike> bikeList = null;
		Map<String, String> bikeMap = new HashMap<String, String>();
		
		try {
			bikeList = facade.getBikeList();
			for (Bike bike : bikeList) {
				System.out.println(bike.getTechnicalDetails().getName()+" "+bike.getStatusString());
				if(STATUS.AVAILABLE.compareTo(bike.getStatus()) == 0){
					bikeMap.put(KeyFactory.keyToString(bike.getKey()), bike.getTechnicalDetails().getName());
				}
			}
		} catch (PersistenceException e) {
			logger.error("Error during bike map creation: " + e.getMessage());
		}
		return bikeMap;
	}
	
	@ModelAttribute("rentalOfficeMap")
	public Map<String, String> createRentalOfficeMap(HttpSession session) {

		List<RentalOffice> rentalOfficeList = new ArrayList<RentalOffice>();
		Map<String, String> rentalOfficeMap = new HashMap<String, String>();

			rentalOfficeList = facade.getRentalOfficeList();
			
			for (RentalOffice rentalOffice :rentalOfficeList) {
				String address = rentalOffice.getAddress().getCity() + ", " + rentalOffice.getAddress().getStreet()
						+ " " + rentalOffice.getAddress().getHouseNumber();
				rentalOfficeMap.put(KeyFactory.keyToString(rentalOffice.getKey()), address);
			}

		return rentalOfficeMap;
	}

	@ModelAttribute("newRental")
	public RentalBikeView addNewRent(HttpServletRequest request) {
		CurrentUser currentUser = (CurrentUser)request.getSession().getAttribute("currentUser");
		RentalBikeView rentalView = new RentalBikeView();
		User user;
		try {
			user = facade.getUserByLogin(currentUser.getUsername());
			rentalView.setUserKey(KeyFactory.keyToString(currentUser.getUserKey()));
			rentalView.setName(user.getName());
			rentalView.setLastName(user.getLastName());
		} catch (PersistenceException e) {
		}
		
		return rentalView;
	}

	@RequestMapping(value = "/rentBike")
	public String showRentalForm() {
		
		
		return "rentalformPage";
	}

	@RequestMapping(value = "/rentBike", method = RequestMethod.POST)
	public String rentBikeForm(@ModelAttribute("newRental") RentalBikeView rentalView) {
		
		try {
			if(rentalView.getUserKey() != null && rentalView.getBikeKey() != null && rentalView.getRentalOfficeKey() != null){
				User user = facade.getUserByKey(KeyFactory.stringToKey(rentalView.getUserKey()));
				
				if(user == null){
					throw new ModelNotExistsException("User does not exist");
				}
				
				if(user.hasActiveRental()){
					throw new Exception("User currently has bike rented");
				}
				
				if(BankService.checkBalance(user.getKey()) < 0){
					throw new NegativeBalanceException();
				}
				
				Bike bike = facade.getBikeByKey(KeyFactory.stringToKey(rentalView.getBikeKey()));
				RentalOffice rentalOffice = facade.getRentalOfficeByKey(KeyFactory.stringToKey(rentalView.getRentalOfficeKey()));
				
				if(bike == null){
					throw new ModelNotExistsException("Bike does not exist");
				}
				bike.setStatus(STATUS.RENTED);
				bike.setRentalCount(bike.getRentalCount()+1);
				user.setActiveRental(true);
				
				Rent rent = new Rent();
				rent.setUserCode(user.getKey());
				rent.setBikeCode(bike.getKey());
				rent.setRentalOfficeCode(rentalOffice.getKey());
				rent.setStartDate(new Date());
				rent.setActive(true);
				
				EntityTransaction tr = facade.getTransaction();
				try{
					tr.begin();
					facade.update(user);
					facade.update(bike);
					facade.add(rent);
					tr.commit();
				}catch(Exception e){
					tr.rollback();
				}
			}

		} catch (Exception e) {
			logger.error("Error during geting bike: " + e.getMessage());
		}
		return "redirect:/";
	}

}
