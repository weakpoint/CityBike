package edu.citybike.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.citybike.database.DatabaseFacade;
import edu.citybike.database.exception.PersistenceException;
import edu.citybike.model.Credentials;
import edu.citybike.model.User;

@Controller
public class AddNewEmployeeController {
	private static final Logger logger = LoggerFactory.getLogger(AddNewEmployeeController.class);
	private DatabaseFacade facade;

	public DatabaseFacade getFacade() {
		return facade;
	}

	public void setFacade(DatabaseFacade facade) {
		this.facade = facade;
	}
	
	@RequestMapping(value = "/addNewEmployee", method = RequestMethod.POST)
	public String addNewEmployee(@ModelAttribute("newEmployee") User employee, HttpSession session) {
		logger.info("Imie: " + employee.getName() + " \nNazwisko: "
				+ employee.getLastName()+" \nStanowisko: "+employee.getRole());
		employee.setRentalNetworkCode((String)session.getAttribute("rentalNetworkCode"));
		
		try {
			facade.add(employee);
			Credentials cred = new Credentials();
			cred.setEmailAddress(employee.getEmailAddress());
			cred.setPassword("123456");
			cred.setRentalNetworkCode(employee.getRentalNetworkCode());
			facade.add(cred);
		} catch (PersistenceException e) {
			logger.error("Error during employee storing: "+e.getMessage());
		}
		return "redirect:/addNewEmployee";
	}
	
	@RequestMapping("/addNewEmployee")
	public ModelAndView showNewEmployeeForm(HttpSession session) {
		Map<String, String> employeeRoleList = new HashMap<String, String>();
		User user = (User)session.getAttribute("currentUser");
		
		if(User.SUPERADMIN.equals(user.getRole())){
			employeeRoleList.put(User.ADMINISTRATOR, "Administrator");
		} else {
			employeeRoleList.put(User.EMPLOYEE, "Employee");
		}
		
		ModelAndView modelAndView = new ModelAndView("addnewemployee");
		modelAndView.addObject("employeeRoleList", employeeRoleList);
		modelAndView.addObject("newEmployee", new User());
		modelAndView.addObject("currentUser", user);
		return modelAndView;
	}
	
}
