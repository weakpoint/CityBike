package edu.citybike.controller;

import java.io.IOException;
import java.util.Properties;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.api.server.spi.ServiceContext;

import edu.citybike.exceptions.PersistenceException;
import edu.citybike.mail.Mailer;
import edu.citybike.model.view.MailMessage;

@Controller
public class IndexPageController {

	@RequestMapping("/")
	public String showIndexView() {
		 
		return "index";
	}
	
	@RequestMapping("/mail")
	public String send() throws PersistenceException {
		MailMessage mail = new MailMessage();
		mail.setAddressTo("emil.1990@interia.pl");
		mail.setMessageBody("Taka tam wiadomosc");
		mail.setNameTo("Użytkownik");
		mail.setSubject("Wiadomosc testowa");
		
		 Mailer.sendMessage(mail);
		return "index";
	}
	


}
