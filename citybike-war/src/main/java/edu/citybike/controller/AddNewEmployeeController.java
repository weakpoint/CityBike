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
			facade.save(employee);
		} catch (PersistenceException e) {
			logger.error("Error during employee storing: "+e.getMessage());
		}
		return "redirect:/addNewEmployee";
	}
	
	@RequestMapping("/addNewEmployee")
	public ModelAndView showAddBikeForm() {
		Map<String, String> employeeRoleList = new HashMap<String, String>();
		
		employeeRoleList.put("EMPLOYEE", "Employee");
		employeeRoleList.put("ADMINISTRATOR", "Administrator");

		ModelAndView modelAndView = new ModelAndView("addnewemployee");
		modelAndView.addObject("employeeRoleList", employeeRoleList);
		modelAndView.addObject("newEmployee", new User());
		return modelAndView;
	}
	
}
