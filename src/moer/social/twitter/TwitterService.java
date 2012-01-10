package moer.social.twitter;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("twitterService")
public class TwitterService {
	private static String HOME_TIMELINE_URL = "https://api.twitter.com/1/statuses/home_timeline.xml";
	private static String TWEET_UPDATE_URL = "https://api.twitter.com/1/statuses/update.json";
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired OAuthService twitterOAuthService;
	SAXReader reader = new SAXReader();
	
	public Token getRequestToken() {
		return twitterOAuthService.getRequestToken();
	}
	
	public String requestOAuth(Token requestToken) {
		String authorizationUrl = twitterOAuthService.getAuthorizationUrl(requestToken);
		logger.info(authorizationUrl);
		return authorizationUrl;
	}

	public Token getAccessToken(Token requestToken, String oauthVerifier) {
		Verifier verifier = new Verifier(oauthVerifier);
		Token accessToken = twitterOAuthService.getAccessToken(requestToken, verifier);
		return accessToken;
	}

	public List<Twitter> getTimeline(Token accessToken) {
		OAuthRequest request = new OAuthRequest(Verb.GET, HOME_TIMELINE_URL);
		twitterOAuthService.signRequest(accessToken, request);
		Response response = request.send();
		
		List<Twitter> twitterList = new ArrayList<Twitter>();
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
		twitterOAuthService.signRequest(accessToken, request);
		request.send();
	}
	
	
}
