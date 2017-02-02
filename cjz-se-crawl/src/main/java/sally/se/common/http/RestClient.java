package sally.se.common.http;

import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.web.client.RestTemplate;

public class RestClient extends GenericObjectPool<RestTemplate> {

	public RestClient(PooledObjectFactory<RestTemplate> factory) {
		super(factory);
	}
	
	public RestClient(PooledObjectFactory<RestTemplate> factory, GenericObjectPoolConfig config) {
		super(factory, config);
	}
	
}
