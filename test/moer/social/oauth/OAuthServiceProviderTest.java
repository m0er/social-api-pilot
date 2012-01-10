package moer.social.oauth;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class OAuthServiceProviderTest {
	@Autowired OAuthServiceProvider twitterOAuthServiceProvider;
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Test
	public void create() throws Exception {
		assertThat(twitterOAuthServiceProvider, is(notNullValue()));
	}
	
	@Test
	public void everyTokenIsUnique() throws Exception {
		String token1 = twitterOAuthServiceProvider.getService().getRequestToken().toString();
		String token2 = twitterOAuthServiceProvider.getService().getRequestToken().toString();
		
		assertThat(token1, is(not(token2)));
	}
	
}
