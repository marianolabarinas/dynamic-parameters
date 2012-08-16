package grails.plugin.dynamic.parameters.http;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author mlabarinas
 */
public class RestClientParameters {

	private Map<String, String> params;
	
	public RestClientParameters() {
		this.params = new HashMap<String, String>();
	}
	
	public RestClientParameters add(String key, String value) {
		params.put(key, value);
		
		return this;
	}
	
	public RestClientParameters remove(String key) {
		params.remove(key);
		
		return this;
	}
	
	public String toString() {
		StringBuilder queryString = new StringBuilder();
		
		if(params.size() > 0) {
			queryString.append("?");
		
		} else {
			return "";
		}
		
		int i = 0;
		for(Entry<String, String> entry : params.entrySet()) {
			if(i == 0) {
				queryString.append(entry.getKey()).append("=").append(entry.getValue());
			
			} else {
				queryString.append("&").append(entry.getKey()).append("=").append(entry.getValue());
			}
			
			i++;
		}
		
		return queryString.toString();
	}
	
}