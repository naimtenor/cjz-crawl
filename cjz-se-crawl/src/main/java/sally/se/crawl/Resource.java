package sally.se.crawl;

public class Resource {

	private String source;
	
	private String subject;
	
	private long timestamp;
	
	private String originalContent;
	
	public Resource(String source, String subject, long timestamp, String originalContent) {
		this.source = source;
		this.subject = subject;
		this.timestamp = timestamp;
		this.originalContent = originalContent;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getOriginalContent() {
		return originalContent;
	}

	public void setOriginalContent(String originalContent) {
		this.originalContent = originalContent;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[source=" + source + ", subject=" + subject + ", timestamp=" + timestamp + "]");
		sb.append(System.lineSeparator());
		sb.append("------------------------------------------------------------------------------------------");
		sb.append(System.lineSeparator());
		sb.append(originalContent);
		sb.append(System.lineSeparator());
		sb.append("------------------------------------------------------------------------------------------");
		
		return sb.toString();
	}
	
}
