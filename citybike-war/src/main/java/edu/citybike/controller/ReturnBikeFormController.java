package edu.citybike.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.datanucleus.store.types.sco.backed.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.citybike.database.DatabaseFacade;
import edu.citybike.database.exception.PersistenceException;
import edu.citybike.model.Rent;
import edu.citybike.model.User;

@Controller
public class ReturnBikeFormController {

	private static final Logger logger = LoggerFactory.getLogger(ReturnBikeFormController.class);
	private DatabaseFacade facade;
	private List<Rent> rentList;

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
		String rentalNetworkCode = ((User) session.getAttribute("currentUser")).getRentalNetworkCode();
		try {
			rentList = facade.getUserRentList(userCode, rentalNetworkCode);
			ArrayList<Rent> activeRent = new ArrayList<Rent>();
			for (Rent rent : rentList) {
				if (rent.isActive()) {
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
		String[] indexes = selected.split(",");
		logger.info("Ilosc zamowien " + indexes.length);

		setRentTemporarilyInactive(indexes); //make them inactive
		
		//ceny i zatwierdzenie oplacenia (update na bazie)
		String payment = "120 zł";
		String s = new Gson().toJson(payment);
		logger.info(s);
		return s;
	}
	
	private void setRentTemporarilyInactive(String[] selected){
		for(String i : selected){
			rentList.get(Integer.parseInt(i)).setActive(false);
		}
	}
}
