package sally.se.common.http;

import java.nio.charset.Charset;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Util class for http request.
 * 
 * @author naimtenor
 * @since 2017.01.23.
 *
 */
@Component
public class RestClientSupport implements InitializingBean {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RestClientSupport.class);
	
	private final ObjectMapper OM = new ObjectMapper();

	/**
	 * for singleton
	 * 
	 * @author naimtenor
	 *
	 */
	private static class HttpFactoryHolder {
		public static final RestClientSupport INSTANCE = new RestClientSupport();
	}

	/**
	 * Get {@link FRSupport} instance.
	 * 
	 * @return
	 */
	public static RestClient getInstance() {
		return HttpFactoryHolder.INSTANCE.restClient;
	}
	
	private RestClient restClient; 
	
	/**
	 * Send rest request to remote url.
	 * 
	 * @param uri
	 * @return
	 * @throws Exception
	 */
	public String sendRequest(String uri) throws Exception {
		Assert.notNull(uri, "'uri' MUST NOT BE NULL");
		RestTemplate restTemplate = restClient.borrowObject();
		restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
		String result = restTemplate.getForObject(uri, String.class);
		restClient.returnObject(restTemplate);
		return result;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMaxIdle(1);
        config.setMaxTotal(5);
		config.setTestOnBorrow(true);
		config.setTestOnReturn(true);
		
		restClient = new RestClient(new RestClientPoolFactory(), config);
	}
}
