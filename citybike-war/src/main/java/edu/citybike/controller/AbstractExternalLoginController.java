package edu.citybike.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.citybike.database.DatabaseFacade;
import edu.citybike.model.view.ExternalLoginUser;

public abstract class AbstractExternalLoginController {
	private DatabaseFacade facade;
	private static final Logger logger = LoggerFactory.getLogger(AbstractExternalLoginController.class);

	public DatabaseFacade getFacade() {
		return facade;
	}

	public void setFacade(DatabaseFacade facade) {
		this.facade = facade;
	}
	
	protected String manageExternalLoginAttempt(ExternalLoginUser externalUser){
		//szyfrowanie id ?
	
		//jesli jest w bazie to sciagnac usera i porownac externalID, opakowac w current user i wywolac SimpleAuthenticationSuccessHandler
		//jesli nie ma (nie ma tu pomylki w nazwie) to wyswietlic formularz logowania z wypelnionymi danymi z fejsa
		return "redirect:/";
		
	}
}
