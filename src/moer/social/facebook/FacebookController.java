package moer.social.facebook;

import org.scribe.model.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("facebook")
@SessionAttributes("accessToken")
public class FacebookController {
	@Autowired FacebookService facebookService;
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@RequestMapping("/index")
	public String index(@ModelAttribute("accessToken") Token accessToken, Model model) {
		String statuses = facebookService.statuses(accessToken);
		logger.info(statuses);
		model.addAttribute("statuses", statuses);
		return "main";
	}
	
	@RequestMapping("/oauth")
	public String startOAuth() {
		return "redirect:" + facebookService.getAuthorizationUrl();
	}
	
	@RequestMapping("/oauth_callback")
	public String oauthCallback(@RequestParam("code") String code, Model model) {
		logger.info("verifier: " + code);
		Verifier verifier = new Verifier(code);
		Token accessToken = facebookService.getAccessToken(verifier);
		logger.info("Facebook access token: " + accessToken);
		model.addAttribute("accessToken", accessToken);
		return "redirect:/facebook/index/";
	}
}
