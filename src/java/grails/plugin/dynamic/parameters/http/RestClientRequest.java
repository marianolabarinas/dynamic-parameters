package grails.plugin.dynamic.parameters.http;

/**
 * @author mlabarinas
 */
public class RestClientRequest {

	private String url;
	private RestClientParameters parameters;
	private String body;
	
	public RestClientRequest(String url) {
		this.url = url;
		this.parameters = new RestClientParameters();
	}
	
	public RestClientRequest(String url, String body) {
		this.url = url;
		this.parameters = new RestClientParameters();
		this.body = body;
	}
	
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void addParameter(String key, String value) {
        parameters.add(key, value);
	}
	
	public String getUrl() {
		return url + parameters.toString();
	}
	
}