package moer.social.twitter.test;

import org.scribe.model.*;
import org.scribe.oauth.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service("twitterTestService")
public class TwitterTestService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired OAuthService twitterOAuthService;
	
	public Token getRequestToken() {
		return twitterOAuthService.getRequestToken();
	}
	
	public String authorize(Token requestToken) {
		String authorizationUrl = twitterOAuthService.getAuthorizationUrl(requestToken);
		logger.info("Twitter Authorization URL: " + authorizationUrl);
		return authorizationUrl;
	}

	public Token getAccessToken(Token requestToken, String oauthVerifier) {
		Verifier verifier = new Verifier(oauthVerifier);
		Token accessToken = twitterOAuthService.getAccessToken(requestToken, verifier);
		return accessToken;
	}

}
