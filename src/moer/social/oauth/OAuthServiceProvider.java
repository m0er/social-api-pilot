package moer.social.oauth;


import org.scribe.builder.ServiceBuilder;
import org.scribe.oauth.OAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OAuthServiceProvider {
	private OAuthServiceConfig config;
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public OAuthServiceProvider() {
	}
	
	public OAuthServiceProvider(OAuthServiceConfig config) {
		this.config = config;
	}

	public OAuthService getService() {
		logger.info(config.toString());
		return new ServiceBuilder().provider(config.getApiClass())
							.apiKey(config.getApiKey())
						    .apiSecret(config.getApiSecret())
						    .callback(config.getCallback())
						    .build();
	}
	
	public OAuthService getServiceWithScope() {
		logger.info(config.toString());
		return new ServiceBuilder().provider(config.getApiClass())
							.apiKey(config.getApiKey())
						    .apiSecret(config.getApiSecret())
						    .callback(config.getCallback())
						    .scope(config.getScope())
						    .build();
	}
	
}
