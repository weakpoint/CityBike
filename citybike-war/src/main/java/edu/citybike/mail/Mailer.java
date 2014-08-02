package edu.citybike.mail;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.citybike.database.DatabaseFacadeImpl;
import edu.citybike.model.view.MailMessage;

public class Mailer {
	private static final Logger logger = LoggerFactory.getLogger(Mailer.class);
	public static void sendMessage(MailMessage mail) {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(mail.getAddressFrom(), mail.getNameFrom()));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(mail.getAddressTo(), mail.getNameTo()));
			msg.setSubject(mail.getSubject());
			msg.setText(mail.getMessageBody());
			Transport.send(msg);

		} catch (MessagingException | UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
	}

}