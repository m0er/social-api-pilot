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
		facebookService.me(accessToken);
		Facebook facebook = facebookService.getStatus(accessToken);
		logger.info("facebook statuses: " + facebook.toString());
		model.addAttribute("statusList", facebook.getData());
		return "facebook/main";
	}
	
	@RequestMapping(value="/form", method=RequestMethod.GET)
	public String form() {
		return "facebook/form";
	}
	
	@RequestMapping(value="/form", method=RequestMethod.POST)
	public String statusUpdate(Facebook facebook, @ModelAttribute("accessToken") Token accessToken) {
		logger.info(facebook.toString());
		facebookService.statusUpdate(facebook, accessToken);
		return "redirect:/facebook/index";
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
