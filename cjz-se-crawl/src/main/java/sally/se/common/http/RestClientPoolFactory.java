package sally.se.common.http;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.springframework.web.client.RestTemplate;

public class RestClientPoolFactory extends BasePooledObjectFactory<RestTemplate> {

	@Override
	public RestTemplate create() throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate;
	}

	@Override
	public PooledObject<RestTemplate> wrap(RestTemplate restTemplate) {
		return new DefaultPooledObject<RestTemplate>(restTemplate);
	}

}
