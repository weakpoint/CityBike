package edu.citybike.controller;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.FacebookApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;

import edu.citybike.exceptions.ModelNotExistsException;
import edu.citybike.model.view.FacebookExternalLoginUser;

@Controller
public class FacebookExternalLoginController extends AbstractExternalLoginController{
	private OAuthService service = null;
	private static final Logger logger = LoggerFactory.getLogger(FacebookExternalLoginController.class);
	private static String API_KEY = "264491053759343";
	private static String SECRET = "a20d67404e8d6ba989a66037306e0ebc";
	private static String CALLBACK_LINK = "http://lodzcitybike.appspot.com/facebookLoginCallback";
	
	@RequestMapping(value = "/facebookLogin.do")
	public String facebookLogin() {
		
		service = new ServiceBuilder().provider(FacebookApi.class).apiKey(API_KEY).apiSecret(SECRET)
				.callback(CALLBACK_LINK).build();

		String authorizationUrl = service.getAuthorizationUrl(null);
		//link to facebook login page + asking about public profile and email
		return "redirect:" + authorizationUrl + "&scope=email";
	}

	@RequestMapping(value = "/facebookLoginCallback")
	public String facebookLoginCallback(HttpServletRequest httpRequest) {
		try {
			String code = httpRequest.getParameter("code");
			
			if(code == null || code.isEmpty()){
				throw new LoginException(httpRequest.getParameter("error"));
			}
			
			Verifier verifier = new Verifier(code);
			// getting token
			Token accessToken = service.getAccessToken(null, verifier);

			//getting information JSON
			OAuthRequest request = new OAuthRequest(Verb.GET, "https://graph.facebook.com/me");
			service.signRequest(accessToken, request);
			Response response = request.send();

			String responseBody = null;
			if ((response.getCode() == 200) && (responseBody = response.getBody()) != null) {
				
				System.out.println("Response JSON: "+responseBody);
				Gson gson = new Gson();
				FacebookExternalLoginUser externalUser = gson.fromJson(responseBody, FacebookExternalLoginUser.class);
				return manageExternalLoginAttempt(externalUser);
				
			} else {
				throw new ModelNotExistsException("Problem with user data");
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "redirect:/";
	}
}
