package grails.plugin.dynamic.parameters.http;

import grails.converters.JSON;
import org.codehaus.groovy.grails.web.json.JSONElement;

/**
* @author mlabarinas
*/
public class RestClientResponse {

	private Integer statusCode;
	private String data;
	
	public RestClientResponse(Integer statusCode, String data) {
		this.statusCode =  statusCode;
		this.data = data;
	}
	
	public Integer getStatusCode() {
		return statusCode;
	}
	
	public JSONElement getJson() {
		return JSON.parse(data); 
	}
	
}