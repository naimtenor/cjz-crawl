package sally.se;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import sally.se.crawl.Resource;
import sally.se.crawl.web.ent.EntWebCrawler;

@SpringBootApplication
public class CjzSeCrawlApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(CjzSeCrawlApplication.class, args);
		
		EntWebCrawler c = (EntWebCrawler) ctx.getBean("entWebCrawler");
//		List<Resource> rs = c.explorerSubSources("http://www.yonhapnews.co.kr/entertainment/");
		List<Resource> rs = c.explorerSubSources("http://news.joins.com/star/");
		
		List<Resource> crs = c.crawl(rs);
		
		for (Resource rrrr : crs) {
			System.out.println(rrrr.toString());
		}
	}
}
