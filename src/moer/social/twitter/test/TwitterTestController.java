package moer.social.twitter.test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import javax.servlet.http.*;

import moer.social.twitter.*;

import org.scribe.model.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("test/twitter")
@SessionAttributes({"requestToken", "accessToken"})
public class TwitterTestController {
	@Autowired TwitterTestService twitterTestService;
	@Autowired TwitterService twitterService;
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@RequestMapping("/index")
	public String index(HttpSession session, Model model) {
		model.addAttribute("requestToken", session.getAttribute("requestToken"));
		model.addAttribute("accessToken", session.getAttribute("accessToken"));
		return "test/twitter";
	}
	
	@RequestMapping("/timeline/{token}/{secret}")
	public String timeline(@PathVariable("token") String token, @PathVariable("secret") String secret, Model model) {
		Token mockAccessToken = new Token(token, secret);
		model.addAttribute("result", twitterService.getTimeline(mockAccessToken));
		return "twitter/main";
	}
	
	@RequestMapping("/tweeting")
	public String tweeting() {
		return "test/form";
	}
	
	@RequestMapping("/tweeting/{token}/{secret}")
	public String tweeting(Twitter twitter, @PathVariable("token") String token, @PathVariable("secret") String secret) {
		Token mockAccessToken = new Token(token, secret);
		twitterService.tweeting(twitter, mockAccessToken);
		return "redirect:/test/twitter/timeline/" + token + "/" + secret;
	}
	
	@RequestMapping("/oauth")
	public String oauth(Model model) {
		Token requestToken = twitterTestService.getRequestToken();
		logger.info("Request Token: " + requestToken);
		model.addAttribute("requestToken", requestToken);
		return "redirect:" + twitterTestService.authorize(requestToken);
	}
	
	@RequestMapping("/oauth_callback")
	public String oauthCallback(@RequestParam("oauth_verifier") String oauthVerifier, @RequestParam("oauth_token") String oauthToken, HttpSession session, Model model) {
		logger.info("Verifier: " + oauthVerifier);
		logger.info("Request Token: " + oauthToken);
		
		Token requestToken = (Token) session.getAttribute("requestToken");
		assertThat(oauthToken, is(requestToken.getToken()));
		
		Token accessToken = twitterTestService.getAccessToken(requestToken, oauthVerifier);
		model.addAttribute("accessToken", accessToken);
		logger.info("Access Token: " + accessToken);
		return "redirect:/test/twitter/index";
	}
	
}
