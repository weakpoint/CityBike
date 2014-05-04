package edu.citybike.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import edu.citybike.database.DatabaseFacade;
import edu.citybike.database.exception.PersistenceException;
import edu.citybike.model.Bike.STATUS;
import edu.citybike.model.Fee;
import edu.citybike.model.Rent;
import edu.citybike.model.User;
import edu.citybike.utilities.ControllerUtilities;

@Controller
public class ReturnBikeFormController {

	private static final Logger logger = LoggerFactory.getLogger(ReturnBikeFormController.class);
	private DatabaseFacade facade;
	String rentalNetworkCode;
	private List<Rent> rentList;
	private String[] indexes;

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

	@RequestMapping(value = "/getRent", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getRent(@RequestBody String userCode, HttpSession session) {
		userCode = userCode.replace("{\"userCode\":\"", "").replace("\"}", "").trim();
		logger.info("User code: " + userCode);
		rentalNetworkCode = ((User) session.getAttribute("currentUser")).getRentalNetworkCode();
		try {
			rentList = facade.getUserRentList(userCode, rentalNetworkCode);
			ArrayList<Rent> activeRent = new ArrayList<Rent>();
			for (Rent rent : rentList) {
				if (rent.isActive()) {
					rent.setEndDate(new Date());
					activeRent.add(rent);
				}
			}
			rentList = activeRent;
			
		} catch (PersistenceException e) {
			logger.error("Error during getting rent list: " + e.getMessage());
		}

		String s = new Gson().toJson(rentList);
		logger.info(s);
		return s;
	}

	@RequestMapping(value = "/summarize.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String summarize(@RequestBody String selected) {
		selected = selected.replace("{\"selected\":\"", "").replace("\"}", "").trim();
		indexes = selected.split(",");
		logger.info("Ilosc zamowien " + indexes.length);

		setRentTemporarilyInactive(indexes); //make them inactive
		List<Fee> feeList = new ArrayList<Fee>();
		try {
			feeList = getSortedFeeList(rentalNetworkCode);
		} catch (PersistenceException e) {
			logger.error("Error getting fee list: "+e.getMessage());
		}
		double payment = 0;
		
		for(String i : indexes){
			int index = Integer.parseInt(i);
			double pay = calculatePayment(feeList, rentList.get(index).getRentDuration());
			rentList.get(index).setRentCost(pay);
			payment += pay;	
		}

		String s = new Gson().toJson(payment+" zł");
		logger.info(s);
		return s;
	}
	
	@RequestMapping(value = "/returnApporval")
	public String returnApproval() {
		ControllerUtilities utils = new ControllerUtilities();
		for(String i : indexes){
			int index = Integer.parseInt(i);
			try {
				utils.changeBikeStatus(rentList.get(index).getBikeCode(), rentList.get(index).getRentalNetworkCode(), STATUS.AVAILABLE);
				facade.update(rentList.get(index));
			} catch (PersistenceException e) {
				logger.error("Error during bike returning: "+e.getMessage());
			}
			
		}
		//podsumowanie
		return "redirect:/returnBike";
	}
	
	private void setRentTemporarilyInactive(String[] selected){
		for(String i : selected){
			int index = Integer.parseInt(i);
			rentList.get(index).setActive(false);
			
		}
	}
	
	private List<Fee> getSortedFeeList(String rentalNetworkCode) throws PersistenceException{
		List<Fee> feeList = facade.getFeeList(rentalNetworkCode);
		
		Collections.sort(feeList, new Comparator<Fee>(){

			@Override
			public int compare(Fee fee1, Fee fee2) {			
				return fee2.getTime() - fee1.getTime();
			}
			
		});
		return feeList;
	}
	
	private double calculatePayment(List<Fee> feeList, int rentDuration){
		int i  = feeList.size()-1;
		double payment = 0;
		boolean found = false;
		
		if(rentDuration >= feeList.get(i).getTime()){
			return feeList.get(i).getFee();
		}
		
		for(; i < 0;i--){
			if(found){
				payment += payment = Math.ceil((feeList.get(i).getTime() - feeList.get(i-1).getTime())/60)*feeList.get(i-1).getFee();
			}
			
			if(feeList.get(i).getTime() > rentDuration && feeList.get(i-1).getTime() > rentDuration && !found){
				found = true;
				payment = Math.ceil((rentDuration - feeList.get(i-1).getTime())/60)*feeList.get(i-1).getFee();
			}			
		}
		return payment;
	}
	

}
