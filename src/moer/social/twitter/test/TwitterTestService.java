package moer.social.twitter.test;

import java.util.*;

import moer.social.twitter.*;

import org.dom4j.*;
import org.dom4j.io.*;
import org.scribe.model.*;
import org.scribe.oauth.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service("twitterTestService")
public class TwitterTestService {
	private static String HOME_TIMELINE_URL = "https://api.twitter.com/1/statuses/home_timeline.xml";
	private static String TWEET_UPDATE_URL = "https://api.twitter.com/1/statuses/update.json";
	private static String VERIFY_CREDENTIALS = "http://api.twitter.com/1/account/verify_credentials.xml";
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired OAuthService twitterTestOAuthService;
	SAXReader reader = new SAXReader();
	
	public Token getRequestToken() {
		return twitterTestOAuthService.getRequestToken();
	}
	
	public String authorize(Token requestToken) {
		String authorizationUrl = twitterTestOAuthService.getAuthorizationUrl(requestToken);
		logger.info("Twitter Authorization URL: " + authorizationUrl);
		return authorizationUrl;
	}

	public Token getAccessToken(Token requestToken, String oauthVerifier) {
		Verifier verifier = new Verifier(oauthVerifier);
		Token accessToken = twitterTestOAuthService.getAccessToken(requestToken, verifier);
		return accessToken;
	}
	
	public Response verifyCredentials(Token accessToken) {
		OAuthRequest request = new OAuthRequest(Verb.GET, VERIFY_CREDENTIALS);
		twitterTestOAuthService.signRequest(accessToken, request);
		return request.send();
	}
	
	public List<Twitter> getTimeline(Token accessToken) {
		OAuthRequest request = new OAuthRequest(Verb.GET, HOME_TIMELINE_URL);
		twitterTestOAuthService.signRequest(accessToken, request);
		Response response = request.send();
		
		List<Twitter> twitterList = new ArrayList<Twitter>();
		logger.info("twitterList: " + twitterList.toString());
		try {
			Document document = reader.read(response.getStream());
			@SuppressWarnings("unchecked")
			List<Node> statusList = document.selectNodes("/statuses/status");
			
			for (Node status : statusList)
				addTwitterByResponse(twitterList, status);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		return twitterList;
	}
	
	private void addTwitterByResponse(List<Twitter> twitterList, Node status) {
		Twitter twitter = new Twitter();
		String currentPath = status.getUniquePath();
		twitter.setText(status.selectSingleNode(currentPath + "/text").getText());
		twitter.setNickname(status.selectSingleNode(currentPath + "/user/screen_name").getText());
		twitter.setProfileImage(status.selectSingleNode(currentPath + "/user/profile_image_url").getText());
		logger.info(twitter.toString());
		twitterList.add(twitter);
	}
	
	public void tweeting(Twitter twitter, Token accessToken) {
		OAuthRequest request = new OAuthRequest(Verb.POST, TWEET_UPDATE_URL);
		request.addBodyParameter("status", twitter.getText());
		twitterTestOAuthService.signRequest(accessToken, request);
		request.send();
	}

}
