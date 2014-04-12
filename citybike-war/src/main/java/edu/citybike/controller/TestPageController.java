package edu.citybike.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
 
@Controller
@RequestMapping("/test")
public class TestPageController {
 
	//DI via Spring
	String message;
	EntityGenerator entityGen;
	
	public void setEntityGen(EntityGenerator entityGen){
		this.entityGen = entityGen;
	}
 
	@RequestMapping(method = RequestMethod.GET)
	public String getMovie(ModelMap model) {

		entityGen.doit();
		
		model.addAttribute("message", this.message);
		model.addAttribute("user", entityGen.getUser("01", "0001"));
 
		return "test";
 
	}
 
	public void setMessage(String message) {
		this.message = message;
	}
 


}
