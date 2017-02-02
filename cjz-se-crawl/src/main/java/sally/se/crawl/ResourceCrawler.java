package sally.se.crawl;

import java.util.List;

public interface ResourceCrawler {

	public List<Resource> explorerSubSources(String baseSource);
	
	public List<Resource> crawl(List<Resource> subSources);
	
}
