package edu.citybike.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import edu.citybike.bank.BankService;
import edu.citybike.database.DatabaseFacade;
import edu.citybike.mail.Mailer;
import edu.citybike.model.view.CurrentUser;
import edu.citybike.model.view.MailMessage;
import edu.citybike.model.view.UserInfo;

@Controller
public class RegistrationController {

	private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

	private DatabaseFacade facade;
	private PasswordEncoder encoder;
	private AuthenticationDetailsSource authenticationDetailsSource;
	private AuthenticationManager authenticationManager;
	private AbstractMessageSource messageSource;
	private Validator validator;

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	public void setEncoder(PasswordEncoder encoder) {
		this.encoder = encoder;
	}

	public void setFacade(DatabaseFacade facade) {
		this.facade = facade;
	}
	
	public void setAuthenticationDetailsSource(AuthenticationDetailsSource authenticationDetailsSource) {
		this.authenticationDetailsSource = authenticationDetailsSource;
	}

	public void setMessageSource(AbstractMessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	@RequestMapping("/registration")
	public ModelAndView showAddRegistrationForm(HttpServletRequest request, ModelMap map, @ModelAttribute("formAction") String formAction, @ModelAttribute("userInfo") UserInfo userInfo, BindingResult result) {

		//UserInfo userInfo = (UserInfo) request.getAttribute("userInfo");		
System.out.println("user info"+userInfo);
System.out.println("form "+formAction);
		if (userInfo == null || formAction == "") {
			userInfo = new UserInfo();
			ModelAndView mav = new ModelAndView("userdata", "userInfo", userInfo);
			mav.addObject("formAction", "/registration");
			return mav;
		} else {
			System.out.println("validator");
			validator.validate(userInfo, result);
			System.out.println(result);
			if (result.hasErrors()) {
				return new ModelAndView("userdata", "userInfo", userInfo);
			}
			
			return new ModelAndView("redirect:/register.do", "userInfo", userInfo);
		}

	}

	@RequestMapping(value = "/register.do", method = RequestMethod.POST)
	public ModelAndView registerUser(@ModelAttribute("userInfo") UserInfo userInfo, BindingResult result, HttpServletRequest request,
			HttpServletResponse response) {

		validator.validate(userInfo, result);
		
		if (result.hasErrors()) {
			return new ModelAndView("userdata", "userInfo", userInfo);
		}
		
		EntityTransaction tr = facade.getTransaction();
		try {
			tr.begin();
			String pass = userInfo.getPassword();
			Key key = KeyFactory.createKey("User", new Random().nextLong());
			userInfo.getUser().setKey(key);
			userInfo.getCredentials().setPassword(encoder.encode(userInfo.getCredentials().getPassword()));
			facade.add(userInfo.getCredentials());
			userInfo.getUser().setRegistrationDate(new Date());
			facade.add(userInfo.getUser());

			BankService.createBankAccount(key);
			tr.commit();

			loginCurrentUser(userInfo, pass, request);
			
			//sending welcome e-mail
			Mailer.sendMessage(createWelcomeMessage(userInfo.getName()+" "+userInfo.getLastName(), userInfo.getEmailAddress()));
		} catch (Exception e) {
			logger.error("Error during registration: " + e.getMessage());
			tr.rollback();
		}
		
		return new ModelAndView("redirect:/");
	}
	
	private MailMessage createWelcomeMessage(String name, String emailAddress){
		MailMessage mail = new MailMessage();
		mail.setAddressTo(emailAddress);
		mail.setMessageBody(messageSource.getMessage("mail.welcome.body", null, LocaleContextHolder.getLocale()));
		mail.setNameTo(name);
		mail.setSubject(messageSource.getMessage("mail.welcome.subject", null, LocaleContextHolder.getLocale()));
		return mail;
	}
	
	private void loginCurrentUser(UserInfo userInfo,String plainPassword, HttpServletRequest request){
		List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
		roles.add(new SimpleGrantedAuthority(userInfo.getRole()));

		CurrentUser currentUser = new CurrentUser(userInfo.getUser().getKey(), userInfo.getEmailAddress(), plainPassword, roles,
				userInfo.getExternalID());

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		PreAuthenticatedAuthenticationToken token = new PreAuthenticatedAuthenticationToken(
				currentUser.getUsername(), plainPassword, roles);
		
		token.setDetails(authenticationDetailsSource.buildDetails(request));

		authentication = authenticationManager.authenticate(token);
		// Setup the security context
		SecurityContextHolder.getContext().setAuthentication(authentication);

		HttpSession session = request.getSession(true);
		session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
		request.getSession().setAttribute("currentUser", currentUser);
	}
}
