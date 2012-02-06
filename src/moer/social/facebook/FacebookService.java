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
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired private OAuthService facebookOAuthService;
	@Autowired private Gson gson;
	
	public String getAuthorizationUrl() {
		return facebookOAuthService.getAuthorizationUrl(null);
	}

	public Token getAccessToken(Verifier verifier) {
		return facebookOAuthService.getAccessToken(null, verifier);
	}
	
	public Facebook getStatus(Token accessToken) {
		OAuthRequest request = new OAuthRequest(Verb.GET, STATUSES);
		facebookOAuthService.signRequest(accessToken, request);
		Response response = request.send();
		String responseBody = response.getBody();
		logger.info("response: " + responseBody);
		
		return gson.fromJson(responseBody, Facebook.class);
	}
}
