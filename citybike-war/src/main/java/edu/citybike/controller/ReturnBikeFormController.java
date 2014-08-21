package edu.citybike.controller;

import java.util.Date;

import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
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
	private AbstractMessageSource messageSource;
	private Validator validator;
	
	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	public void setFacade(DatabaseFacade facade) {
		this.facade = facade;
	}
	
	public void setMessageSource(AbstractMessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@RequestMapping(value = "/returnBike")
	public ModelAndView showReturnForm() {
		ModelAndView model = new ModelAndView("returnbikeform");
		return model;
	}

	@ModelAttribute("returnBike")
	public RentalBikeView addNewRentObject(HttpServletRequest request) {
		CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
		RentalBikeView rentalView = new RentalBikeView();
		User user;
		try {
			user = facade.getUserByLogin(currentUser.getUsername());
			Rent rent = facade.getUserActiveRental(user.getKey());
			rentalView.setUserKey(KeyFactory.keyToString(currentUser.getUserKey()));
			rentalView.setName(user.getName());
			rentalView.setLastName(user.getLastName());
			rentalView.setBikeKey((rent.getBikeCode() != null) ? KeyFactory.keyToString(rent.getBikeCode()) : "");
			rentalView.setRentalOfficeKey((rent.getRentalOfficeCode() != null) ? KeyFactory.keyToString(rent
					.getRentalOfficeCode()) : "");
		} catch (PersistenceException e) {
			logger.error("Error during return bike model preparation");
		}
			
		return rentalView;
	}
	
	@RequestMapping(value = "/returnApporval")
	public  ModelAndView returnApproval(@ModelAttribute("returnBike") RentalBikeView rentalView, BindingResult result) {
		
		validator.validate(rentalView, result);
		if (result.hasErrors()) {
			return new ModelAndView("returnbikeform", "returnBike", rentalView);
		}
		
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
				mail.setMessageBody(messageSource.getMessage("mail.negativeBalance.body", null, LocaleContextHolder.getLocale()));
				mail.setNameTo(user.getName()+" "+user.getLastName());
				mail.setSubject(messageSource.getMessage("mail.negativeBalance.subject", null, LocaleContextHolder.getLocale()));
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
			logger.error("Something went wrong: "+e.getMessage());
		}
	
		return new ModelAndView("redirect:/returnBike");
	}

}
