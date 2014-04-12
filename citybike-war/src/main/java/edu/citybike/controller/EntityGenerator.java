package edu.citybike.controller;

import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.EmbeddedEntity;
import com.google.appengine.api.datastore.Entity;

import edu.citybike.database.DatabaseFascade;
import edu.citybike.database.exception.ModelNotExistsException;
import edu.citybike.database.exception.PersistenceException;
import edu.citybike.database.exception.UnsupportedModelType;
import edu.citybike.model.User;


public class EntityGenerator {
	
	private DatabaseFascade fascade;
	static final Logger logger = Logger.getLogger(EntityGenerator.class);
	
	public void setFascade(DatabaseFascade fascade){
		this.fascade = fascade;
	}
	
	public void doit(){
		//DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

/*
		Entity employee = new Entity("Employee");
		
		employee.getKind();
		

		employee.setProperty("firstName", "Antonio");
		employee.setProperty("lastName", "Salieri");

		Date hireDate = new Date();
		employee.setProperty("hireDate", hireDate);

		employee.setProperty("attendedHrTraining", true);
		
		EmbeddedEntity en = new EmbeddedEntity();
		en.setProperty("street", "kupa");
		
		employee.setProperty("Address", en);
	*/	
		User u = new User();
		u.setUserCode("0001");
		u.setRentalNetworkCode("01");
		u.setName("Emil");
		u.setLastName("Pl");
		

		
		try {
			//fascade.save(u);
			User user = fascade.getUser("01", "0001");
			user.setName("Wcale nie emil");
			fascade.update(user);
			System.out.println(user);
		} catch (PersistenceException e) {

			e.printStackTrace();
		}

		//datastore.put(employee);
	/*	
		for(Entry<String, Object> e : employee.getProperties().entrySet()){
			System.out.println("---> "+ e.getKey());
			if(e.getValue() instanceof EmbeddedEntity){
				for(Entry<String, Object> ee : ((EmbeddedEntity)e.getValue()).getProperties().entrySet()){
					System.out.println("        ---> "+ ee.getKey());
					System.out.println("        --> "+ee.getValue()+"   "+ ee.getKey().getClass());
				}
			}
			
			System.out.println("    --> "+e.getValue());
		}
*/
	}
	
	public User getUser(String rentalNetworkCode, String userCode){
		try {
			return fascade.getUser("01", "0001");
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new User();
		
	}
	
	
}
