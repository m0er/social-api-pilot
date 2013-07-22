package moer.social.facebook;

import org.scribe.model.*;
import org.scribe.oauth.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.google.gson.*;

@Service("facebookService")
public class FacebookService {
	private static String STATUSES = "https://graph.facebook.com/me/statuses";
	private static String CREATE_LINK = "https://graph.facebook.com/me/feed";
	private static String ME = "https://graph.facebook.com/me";
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired private OAuthService facebookOAuthService;
	@Autowired private Gson gson;
	
	public String getAuthorizationUrl() {
		return facebookOAuthService.getAuthorizationUrl(null);
	}

	public Token getAccessToken(Verifier verifier) {
		return facebookOAuthService.getAccessToken(null, verifier);
	}
	
	public void me(Token accessToken) {
		OAuthRequest request = new OAuthRequest(Verb.GET, ME);
		facebookOAuthService.signRequest(accessToken, request);
		Response response = request.send();
		
		logger.info("Code: " + response.getCode());
		logger.info("Body: " + response.getBody());
		
	}
	
	public Facebook getStatus(Token accessToken) {
		OAuthRequest request = new OAuthRequest(Verb.GET, STATUSES);
		facebookOAuthService.signRequest(accessToken, request);
		Response response = request.send();
		String responseBody = response.getBody();
		logger.info("response: " + responseBody);
		
		return gson.fromJson(responseBody, Facebook.class);
	}

	public void statusUpdate(Facebook facebook, Token accessToken) {
		OAuthRequest request = new OAuthRequest(Verb.POST, CREATE_LINK);
		request.addBodyParameter("link", facebook.getLink());
		request.addBodyParameter("message", facebook.getMessage());
		facebookOAuthService.signRequest(accessToken, request);
		Response response = request.send();
		String responseBody = response.getBody();
		logger.info("response: " + responseBody);
	}
}
