package moer.social.twitter;

import java.util.*;

import javax.servlet.http.*;

import org.scribe.model.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

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
	
	@RequestMapping("/anywhere")
	public String anywhere() {
		return "twitter/anywhere";
	}
	
	@RequestMapping("/verify")
	public String verifyCredentials(@ModelAttribute("accessToken") Token accessToken) {
		Response response = twitterService.verifyCredentials(accessToken);
		logger.info(response.getHeaders().toString());
		logger.info(response.getBody());
		
		if (response.getHeader("Status").contains("Unauthorized")) {
			return "redirect:/twitter/oauth";
		}
		
		return "redirect:/twitter/index";
	}
	
}
