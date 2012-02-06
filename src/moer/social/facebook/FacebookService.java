package moer.social.facebook;

import org.scribe.model.*;
import org.scribe.oauth.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service("facebookService")
public class FacebookService {
	private static String STATUSES = "https://graph.facebook.com/me/statuses";
	@Autowired OAuthService facebookOAuthService;
	
	public String getAuthorizationUrl() {
		return facebookOAuthService.getAuthorizationUrl(null);
	}

	public Token getAccessToken(Verifier verifier) {
		return facebookOAuthService.getAccessToken(null, verifier);
	}
	
	public String statuses(Token accessToken) {
		OAuthRequest request = new OAuthRequest(Verb.GET, STATUSES);
		facebookOAuthService.signRequest(accessToken, request);
		Response response = request.send();
		return response.getBody();
	}
}
