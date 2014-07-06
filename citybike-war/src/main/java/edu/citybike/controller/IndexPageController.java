package edu.citybike.controller;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.citybike.database.DatabaseFacade;
import edu.citybike.database.exception.PersistenceException;
import edu.citybike.model.Test;

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
		 EntityManager entityManager = facade.getEntityManagerFactory().createEntityManager();  
		  entityManager.getTransaction().begin();  
		  entityManager.persist(new Test(new Date()));  
		  entityManager.getTransaction().commit();  
 
		  List<Test> resultList =  entityManager.createQuery("select r from Test r")  
		    .getResultList();  
		  for (Test number : resultList) {  
		   System.out.println(number.getId().toString() + " " + number.getRequestTime() + "\n");  
		  }  
		  entityManager.close();  
		 
		return "index";
	}
	


}
