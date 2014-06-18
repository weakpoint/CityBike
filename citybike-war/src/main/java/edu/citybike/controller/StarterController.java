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
import edu.citybike.model.Bike;
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
		c.setEmailAddress("admin");
		c.setPassword("admin");
		c.setRentalNetworkCode("0001");
		list.add(c);
		
		//user
		User admin = new User();
		Address adminaddress = new Address();
		adminaddress.setCity("Dobra");
		adminaddress.setHouseNumber("50");
		adminaddress.setPostalCode("95-010");
		adminaddress.setStreet("Witanówek");
		admin.setAddress(adminaddress);
		admin.setEmailAddress("admin");
		admin.setLastName("Płuciennikowski");
		admin.setName("Emil");
		admin.setNotes(new Text("Goooood guy!"));
		admin.setPhoneNumber("500 000 000");
		admin.setRentalNetworkCode("0001");
		admin.setUserCode("1");
		admin.setRole(User.ADMINISTRATOR);
		list.add(admin);
		
		// Credentials
		Credentials c2 = new Credentials();
		c2.setEmailAddress("user");
		c2.setPassword("user");
		c2.setRentalNetworkCode("0001");
		list.add(c2);

		// user
		User user = new User();
		Address useraddress = new Address();
		useraddress.setCity("User City");
		useraddress.setHouseNumber("500");
		useraddress.setPostalCode("00-010");
		useraddress.setStreet("Słóń");
		user.setAddress(useraddress);
		user.setEmailAddress("user");
		user.setLastName("Nazwisko Usera");
		user.setName("Imię Usera");
		user.setNotes(new Text(""));
		user.setPhoneNumber("500 600 700");
		user.setRentalNetworkCode("0001");
		user.setUserCode("2");
		user.setRole(User.USER);
		list.add(user);
		
		// Credentials
		Credentials c3 = new Credentials();
		c3.setEmailAddress("super");
		c3.setPassword("super");
		c3.setRentalNetworkCode("0001");
		list.add(c3);

		// user
		User superuser = new User();
		Address superaddress = new Address();
		superaddress.setCity("Super City");
		superaddress.setHouseNumber("150");
		superaddress.setPostalCode("99-010");
		superaddress.setStreet("Super ulica");
		superuser.setAddress(superaddress);
		superuser.setEmailAddress("super");
		superuser.setLastName("Superuser");
		superuser.setName("Super");
		superuser.setNotes(new Text("!"));
		superuser.setPhoneNumber("500 100 100");
		superuser.setRentalNetworkCode("0001");
		superuser.setUserCode("3");
		superuser.setRole(User.SUPERADMIN);
		list.add(superuser);
		
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
		adminaddress = new Address();
		adminaddress.setCity("Lodz");
		adminaddress.setStreet("Strykowska");
		adminaddress.setHouseNumber("33/43");
		adminaddress.setFlatNumber("");
		adminaddress.setPostalCode("92-233");
		office.setAddress(adminaddress);
		
		list.add(office);
		
		//Bike
		Bike bike = new Bike();
		bike.setBikeCode("00");
		
		list.add(bike);
		
		//rent
		Rent r = new Rent();
		r.setUserCode("1");
		r.setActive(true);
		r.setBikeCode("00");
		r.setStartDate(new Date(System.currentTimeMillis()-60*45000));
		
		Rent r2 = new Rent();
		r2.setUserCode("1");
		r2.setActive(true);
		r2.setBikeCode("01");
		r2.setStartDate(new Date(System.currentTimeMillis()-60*30000));
		
		Rent r3 = new Rent();
		r3.setUserCode("4");
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
