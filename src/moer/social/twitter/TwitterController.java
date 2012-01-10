package moer.social.twitter;

import java.util.List;

import javax.servlet.http.HttpSession;

import moer.social.oauth.OAuthServiceProvider;

import org.scribe.model.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * 참조 사이트: http://java.dzone.com/articles/spring-mvc-and-scribe-simple
 * @author mOer
 *
 */
@Controller
@RequestMapping("twitter")
@SessionAttributes({"requestToken", "accessToken"})
public class TwitterController {
	@Autowired TwitterService twitterService;
	@Autowired OAuthServiceProvider twitterOAuthServiceProvider;
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@RequestMapping("/index")
	public String list(Model model, HttpSession session) {
		Token requestToken = (Token) session.getAttribute("requestToken");
		Token accessToken = (Token) session.getAttribute("accessToken");
		
		if (requestToken == null || accessToken == null) 
			return "redirect:/twitter/oauth";
		
		List<Twitter> twitter = twitterService.getTimeline(accessToken);
		model.addAttribute("result", twitter);
		return "twitter/main";
	}
	
	@RequestMapping(value="/form", method=RequestMethod.GET)
	public String form() {
		return "twitter/form";
	}
	
	@RequestMapping(value="/form", method=RequestMethod.POST)
	public String twiteeing(Twitter twitter, @ModelAttribute("accessToken") Token accessToken) {
		logger.info(twitter.toString());
		twitterService.tweeting(twitter, accessToken);
		return "redirect:/twitter/index";
	}
	
	@RequestMapping("/oauth")
	public String startOAuth(Model model) {
		Token requestToken = twitterService.getRequestToken();
		model.addAttribute("requestToken", requestToken);
		logger.info("request token: " + requestToken);
		return "redirect:" + twitterService.requestOAuth(requestToken);
	}
	
	@RequestMapping("/oauth_callback")
	public String oauthCallback(@RequestParam("oauth_verifier") String oauthVerifier, @ModelAttribute("requestToken") Token requestToken, Model model) {
		logger.info("OAuth Verifier: " + oauthVerifier);
		logger.info("OAuth request token: " + requestToken);
		
		Token accessToken = twitterService.getAccessToken(requestToken, oauthVerifier);
		model.addAttribute("accessToken", accessToken);
		logger.info("accessToken: " + accessToken);
		return "redirect:/twitter/index";
	}
	
}
