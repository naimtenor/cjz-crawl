package sally.se.crawl.web.ent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sally.se.common.http.RestClientSupport;
import sally.se.crawl.Resource;
import sally.se.crawl.ResourceCrawler;

@Component
public class EntWebCrawler implements ResourceCrawler {
	
	private static final String URL_LINK_PATTERN_BASE = "^(<a href=\")(.*)%s(.*)(</a>)$";
	
	private static final Pattern CONTENT_PATTERN = Pattern.compile("^(<p>)(.*)(</p>)$");
	
	private static final Pattern TAG_PATTERN = Pattern.compile("<(\"[^\"]*\"|\'[^\']*\'|[^\'\">])*>");
	
	@Autowired
	private RestClientSupport restClientSupport;

	@Override
	public List<Resource> explorerSubSources(String baseSource) {
		try {
			String response = restClientSupport.sendRequest(baseSource);
			String[] responseWithNewLineArray = response.split(System.lineSeparator());
			
			String trimedBaseUrl = baseSource.replaceFirst("http://", "");
			Pattern urlLinkPattern = Pattern.compile(String.format(URL_LINK_PATTERN_BASE, trimedBaseUrl));
			
			List<Resource> resources = new ArrayList<>();
			Set<String> subUris = new HashSet<>();
			
			for (String line : responseWithNewLineArray) {
				Resource resource = getSubSourcePatternMatchStrings(line, urlLinkPattern);
		    	if (resource != null) {
		    		if (!subUris.contains(resource.getSource())) {
		    			resources.add(resource);
		    			subUris.add(resource.getSource());
		    		}
		    	}
			}
		    
		    return resources;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}

	@Override
	public List<Resource> crawl(List<Resource> subSources) {
		
		List<Resource> crawledResources = new ArrayList<>();
		
		for (Resource subSource : subSources) {
			try {
				String response = restClientSupport.sendRequest(subSource.getSource());
				String[] responseWithNewLineArray = response.split(System.lineSeparator());
				
				StringBuffer contentSb = new StringBuffer();
				for (String line : responseWithNewLineArray) {
					String content = checkContentString(line);
					if (!"".equals(content)) {
						contentSb.append(content);
						contentSb.append(System.lineSeparator());
					}
				}
				if (contentSb.length() > 0) {
					crawledResources.add(new Resource(subSource.getSource(), subSource.getSubject(), subSource.getTimestamp(), contentSb.toString()));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return crawledResources;
	}
	
	private String checkContentString(String contents) {
		Matcher mc = CONTENT_PATTERN.matcher(contents.trim());
		if (mc.matches()) {
			String content = mc.group();
			Matcher mr = TAG_PATTERN.matcher(content);
			return mr.replaceAll("");
		} else {
			return "";
		}
	}

	private Resource getSubSourcePatternMatchStrings(String contents, Pattern urlLinkPattern) {
		Matcher mc = urlLinkPattern.matcher(contents.trim());
		if (mc.matches()) {
			String content = mc.group();
			String originalContent = content;
			content = content.replace("</a>", "");
			content = content.replaceFirst("<a href=\"", "");
			String[] contentArr = content.split("\">");
			if (!contentArr[0].contains("?")) {
				Resource resource = new Resource(contentArr[0], contentArr[1], System.currentTimeMillis(), originalContent);
				return resource;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

}
