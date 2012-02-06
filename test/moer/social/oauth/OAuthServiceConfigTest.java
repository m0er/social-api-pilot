package moer.social.oauth;


import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import moer.social.oauth.OAuthServiceConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.scribe.builder.api.*;
import org.scribe.builder.api.TwitterApi.Authenticate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class OAuthServiceConfigTest {
	@Autowired OAuthServiceConfig twitterOAuthServiceConfig;
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Test
	public void create() throws Exception {
		assertThat(twitterOAuthServiceConfig, is(notNullValue()));
	}
	
	@Test
	public void confirmReadProperties() throws Exception {
		logger.info(twitterOAuthServiceConfig.getApiKey());
		logger.info(twitterOAuthServiceConfig.getApiSecret());
		logger.info(twitterOAuthServiceConfig.getApiKey());
	}
	
	@Test
	public void typeConversion() throws Exception {
		logger.info(Authenticate.class.getName());
		@SuppressWarnings("unchecked")
		Class<Api> cls = (Class<Api>) Class.forName("org.scribe.builder.api.TwitterApi$Authenticate");
		twitterOAuthServiceConfig.setApiClass(cls);
		assertThat(twitterOAuthServiceConfig.getApiClass().getName(), is("org.scribe.builder.api.TwitterApi$Authenticate"));
	}

}
