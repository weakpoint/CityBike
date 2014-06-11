package edu.citybike.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.appengine.api.datastore.Text;

import edu.citybike.database.DatabaseFacade;
import edu.citybike.database.exception.PersistenceException;
import edu.citybike.model.Address;
import edu.citybike.model.Credentials;
import edu.citybike.model.Fee;
import edu.citybike.model.Rent;
import edu.citybike.model.RentalOffice;
import edu.citybike.model.User;

@Controller
public class StarterController {

	@Autowired
	private DatabaseFacade facade;

	public DatabaseFacade getFacade() {
		return facade;
	}

	public void setFacade(DatabaseFacade facade) {
		this.facade = facade;
	}
	
	@RequestMapping(value = "/startup", method = RequestMethod.GET)
	public String setUpDatabase(){
		List<Object> list = new ArrayList<Object>();
		
		//Credentials
		Credentials c = new Credentials();
		c.setEmailAddress("test");
		c.setPassword("pass");
		c.setRentalNetworkCode("0001");
		list.add(c);
		
		//user
		User u = new User();
		Address address = new Address();
		address.setCity("Dobra");
		address.setHouseNumber("50");
		address.setPostalCode("95-010");
		address.setStreet("Witanówek");
		u.setAddress(address);
		u.setEmailAddress("test");
		u.setLastName("Płuciennikowski");
		u.setName("Emil");
		u.setNotes(new Text("Goooood guy!"));
		u.setPhoneNumber("500 000 000");
		u.setRentalNetworkCode("0001");
		u.setUserCode("1");
		u.setRole(User.USER);
		list.add(u);
		
		//Fee
		Fee f1 = new Fee();
		f1.setFee(1);
		f1.setTime(20);
		
		Fee f2 = new Fee();
		f2.setFee(2);
		f2.setTime(40);
		
		Fee f3 = new Fee();
		f3.setFee(4);
		f3.setTime(120);
		
		list.add(f1);
		list.add(f2);
		list.add(f3);
		
		// rental office
		RentalOffice office = new RentalOffice();
		office.setRentalNetworkCode("0001");
		office.setRentalOfficeCode("01");
		office.setLatitude("19.156177");
		office.setLongitude("51.704632");
		address = new Address();
		address.setCity("Lodz");
		address.setStreet("Strykowska");
		address.setHouseNumber("33/43");
		address.setFlatNumber("");
		address.setPostalCode("92-233");
		office.setAddress(address);
		
		list.add(office);
		
		//rent
		Rent r = new Rent();
		r.setUserCode("00");
		r.setActive(true);
		r.setBikeCode("00");
		r.setStartDate(new Date(System.currentTimeMillis()-60*45000));
		
		Rent r2 = new Rent();
		r2.setUserCode("00");
		r2.setActive(true);
		r2.setBikeCode("01");
		r2.setStartDate(new Date(System.currentTimeMillis()-60*30000));
		
		Rent r3 = new Rent();
		r3.setUserCode("00");
		r3.setActive(false);
		r3.setBikeCode("02");
		r3.setStartDate(new Date(System.currentTimeMillis()-60*60000));
		
		list.add(r);
		list.add(r2);
		list.add(r3);
		
		
		for(Object model : list){
			try {
				facade.add(model);
			} catch (PersistenceException e) {
				System.out.println(e);
			}
		}
		
		return "redirect:/";		
	}
}
