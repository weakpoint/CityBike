package edu.citybike.controller;

import java.util.Date;

import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.appengine.api.datastore.KeyFactory;

import edu.citybike.bank.BankService;
import edu.citybike.database.DatabaseFacade;
import edu.citybike.exceptions.ModelNotExistsException;
import edu.citybike.exceptions.NegativeBalanceException;
import edu.citybike.exceptions.PersistenceException;
import edu.citybike.mail.Mailer;
import edu.citybike.model.Bike;
import edu.citybike.model.Bike.STATUS;
import edu.citybike.model.Rent;
import edu.citybike.model.User;
import edu.citybike.model.view.CurrentUser;
import edu.citybike.model.view.MailMessage;
import edu.citybike.model.view.RentalBikeView;
import edu.citybike.utilities.ControllerUtilities;

@Controller
public class ReturnBikeFormController {

	private static final Logger logger = LoggerFactory.getLogger(ReturnBikeFormController.class);
	private DatabaseFacade facade;
	
	public DatabaseFacade getFacade() {
		return facade;
	}

	public void setFacade(DatabaseFacade facade) {
		this.facade = facade;
	}

	@RequestMapping(value = "/returnBike")
	public ModelAndView showReturnForm() {
		ModelAndView model = new ModelAndView("returnbikeform");
		return model;
	}

	@ModelAttribute("returnBike")
	public RentalBikeView addNewRentObject(HttpServletRequest request) {
		CurrentUser currentUser = (CurrentUser)request.getSession().getAttribute("currentUser");
		RentalBikeView rentalView = new RentalBikeView();
		User user;
			try {
				user = facade.getUserByLogin(currentUser.getUsername());
				rentalView.setUserKey(KeyFactory.keyToString(currentUser.getUserKey()));
				rentalView.setName(user.getName());
				rentalView.setLastName(user.getLastName());
			} catch (PersistenceException e) {
				logger.error("Error during return bike model preparation");
			}
			
		return rentalView;
	}
	
	@RequestMapping(value = "/returnApporval")
	public String returnApproval(@ModelAttribute("returnBike") RentalBikeView rentalView) {
		User user = null;
		try {
		if(rentalView.getUserKey() != null && rentalView.getBikeKey() != null && rentalView.getRentalOfficeKey() != null){
			user = facade.getUserByKey(KeyFactory.stringToKey(rentalView.getUserKey()));
			
			if(user == null){
				throw new ModelNotExistsException("User does not exist");
			} 
			}
			
			if(!user.hasActiveRental()){
				throw new Exception("User currently do not have any bike rented");
			}
			
			Rent rent = facade.getUserActiveRental(user.getKey());
				
			rent.setActive(false);
			rent.setEndDate(new Date());
			rent.setRentCost(ControllerUtilities.calculatePayment(facade.getFeeList(), rent.getRentDuration()));
			
			Bike bike = facade.getBikeByKey(rent.getBikeCode());
			bike.setStatus(STATUS.AVAILABLE);
			
			user.setActiveRental(false);
			
			try{
				BankService.doOperation(user.getKey(), -rent.getRentCost());
			}catch(NegativeBalanceException e){
				MailMessage mail = new MailMessage();
				mail.setAddressTo(user.getEmailAddress());
				mail.setMessageBody("Twój stan konta jest ujemny, nie będziesz mógł wypożyczyć następnego roweru.");
				mail.setNameTo(user.getName()+" "+user.getLastName());
				mail.setSubject("Ujemny stan konta");
				Mailer.sendMessage(mail);
			}
			
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
		catch (Exception e) {
			logger.error("Something went wrong");
		}
	
		return "redirect:/returnBike";
	}

}
