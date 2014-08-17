package edu.citybike.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.appengine.api.datastore.KeyFactory;

import edu.citybike.bank.BankService;
import edu.citybike.database.DatabaseFacade;
import edu.citybike.exceptions.PersistenceException;
import edu.citybike.model.Address;
import edu.citybike.model.Bike;
import edu.citybike.model.Credentials;
import edu.citybike.model.Fee;
import edu.citybike.model.Rent;
import edu.citybike.model.RentalOffice;
import edu.citybike.model.TechnicalDetails;
import edu.citybike.model.User;

@Controller
public class StarterController {

	@Autowired
	private DatabaseFacade facade;
	private PasswordEncoder encoder;

	public PasswordEncoder getEncoder() {
		return encoder;
	}

	public void setEncoder(PasswordEncoder encoder) {
		this.encoder = encoder;
	}

	public void setFacade(DatabaseFacade facade) {
		this.facade = facade;
	}

	@RequestMapping(value = "/startup", method = RequestMethod.GET)
	public ModelAndView setUpDatabase() {
		List<Object> list = new ArrayList<Object>();

		// Credentials
		Credentials c = new Credentials();
		c.setUsername("admin@admin.pl");
		c.setPassword(encoder.encode("admin"));
		list.add(c);

		// user
		User admin = new User();
		Address adminaddress = new Address();
		adminaddress.setCity("Dobra");
		adminaddress.setHouseNumber("50");
		adminaddress.setPostalCode("95-010");
		adminaddress.setStreet("Witanówek");
		admin.setAddress(adminaddress);
		admin.setEmailAddress("admin@admin.pl");
		admin.setLastName("Płuciennikowski");
		admin.setName("Emil");
		admin.setPhoneNumber("500 000 000");
		admin.setRole(User.ADMINISTRATOR);
		admin.setRegistrationDate(new Date());
		admin.setKey(KeyFactory.createKey("User", new Random().nextLong()));
		list.add(admin);

		// Credentials
		Credentials c2 = new Credentials();
		c2.setUsername("user@user.pl");
		c2.setPassword(encoder.encode("user"));
		list.add(c2);

		// user
		User user = new User();
		Address useraddress = new Address();
		useraddress.setCity("User City");
		useraddress.setHouseNumber("500");
		useraddress.setPostalCode("00-010");
		useraddress.setStreet("Słóń");
		user.setAddress(useraddress);
		user.setEmailAddress("user@user.pl");
		user.setLastName("Nazwisko Usera");
		user.setName("Imię Usera");
		user.setPhoneNumber("500 600 700");
		user.setRegistrationDate(new Date());
		user.setRole(User.USER);
		user.setKey(KeyFactory.createKey("User", new Random().nextLong()));
		list.add(user);

		// Fee
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
		office.setLatitude("51.704632");
		office.setLongitude("19.156177");
		adminaddress = new Address();
		adminaddress.setCity("Lodz");
		adminaddress.setStreet("Strykowska");
		adminaddress.setHouseNumber("33/43");
		adminaddress.setFlatNumber("");
		adminaddress.setPostalCode("92-233");
		office.setAddress(adminaddress);

		list.add(office);

		// Bike
		Bike bike = new Bike();
		TechnicalDetails td = new TechnicalDetails();
		td.setName("Cross 1");
		bike.setTechnicalDetails(td);
		bike.setKey(KeyFactory.createKey("Bike", new Random().nextLong()));
		list.add(bike);

		// rent
		Rent r = new Rent();
		r.setActive(false);
		r.setStartDate(new Date(System.currentTimeMillis() - 60 * 60000));
		r.setEndDate(new Date(System.currentTimeMillis() - 60 * 46000));
		r.setRentCost(1);
		r.setBikeCode(bike.getKey());
		r.setUserCode(user.getKey());

		Rent r2 = new Rent();
		r2.setActive(false);
		r2.setStartDate(new Date(System.currentTimeMillis() - 60 * 45000));
		r2.setEndDate(new Date(System.currentTimeMillis() - 60 * 31000));
		r2.setRentCost(2);
		r2.setBikeCode(bike.getKey());
		r2.setUserCode(user.getKey());

		Rent r3 = new Rent();
		r3.setActive(true);
		r3.setStartDate(new Date(System.currentTimeMillis() - 60 * 30000));
		r3.setRentCost(8);
		r3.setBikeCode(bike.getKey());
		r3.setUserCode(user.getKey());
		
		Rent r4 = new Rent();
		r4.setActive(false);
		r4.setStartDate(new GregorianCalendar(2013,0,31).getTime());
		r4.setEndDate(new Date(r4.getStartDate().getTime() + (60000 * 30))); //+30 min
		r4.setRentCost(8);
		r4.setBikeCode(bike.getKey());
		r4.setUserCode(user.getKey());
		
		Rent r5 = new Rent();
		r5.setActive(false);
		r5.setStartDate(new GregorianCalendar(2014,0,31).getTime());
		r5.setEndDate(new Date(r5.getStartDate().getTime() + (60000 * 30)));
		r5.setRentCost(8);
		r5.setBikeCode(bike.getKey());
		r5.setUserCode(user.getKey());

		list.add(r);
		list.add(r2);
		list.add(r3);
		list.add(r4);
		list.add(r5);
		
ModelAndView mav = new ModelAndView("redirect:/");

		for (Object model : list) {
			try {
				facade.add(model);
				if(model instanceof User){
					BankService.createBankAccount(((User)model).getKey());
				}
			} catch (PersistenceException e) {
				System.out.println(e);
			}
		}

		return mav;
	}
}
