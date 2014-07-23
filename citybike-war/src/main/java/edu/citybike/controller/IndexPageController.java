package edu.citybike.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.citybike.database.DatabaseFacade;
import edu.citybike.exceptions.PersistenceException;

@Controller
public class IndexPageController {
	
	private DatabaseFacade facade;

	public DatabaseFacade getFacade() {
		return facade;
	}

	public void setFacade(DatabaseFacade facade) {
		this.facade = facade;
	}
	
	@RequestMapping("/")
	public String showIndexView() throws PersistenceException {
		 
		return "index";
	}
	


}
