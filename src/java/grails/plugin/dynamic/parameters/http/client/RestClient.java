package grails.plugin.dynamic.parameters.http.client;

import grails.plugin.dynamic.parameters.http.RestClientRequest;
import grails.plugin.dynamic.parameters.http.RestClientResponse;
import grails.plugin.dynamic.parameters.http.client.exception.RestClientException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

/**
 * @author mlabarinas
 */
public class RestClient extends AbstractRestClient {
	
	private static final String HTTP_CLIENT_ERROR = "Can't execute request";
	
	public RestClient() {
		super();
	}

	public RestClientResponse doPost(RestClientRequest request) throws RestClientException {
		HttpResponse response = null;
		
		try {
			HttpPost post = new HttpPost(request.getUrl());
			post.setEntity(new StringEntity(request.getBody(), "UTF-8"));
			response = super.doPost(post);
			
			return new RestClientResponse(response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity()));
		
		} catch (Exception e) {
			throw new RestClientException(HTTP_CLIENT_ERROR + " POST " + request.getUrl(), e);
		}
	}

	public RestClientResponse doGet(RestClientRequest request) throws RestClientException {
		HttpResponse response = null;
		
		try {
			HttpGet get = new HttpGet(request.getUrl());
			response = super.doGet(get);
			
			return new RestClientResponse(response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity()));
		
		} catch (Exception e) {
			throw new RestClientException(HTTP_CLIENT_ERROR + " GET " + request.getUrl(), e);
		}
	}
	
	public RestClientResponse doPut(RestClientRequest request) throws RestClientException {
		HttpResponse response = null;
		try {
			HttpPut put = new HttpPut(request.getUrl());
			put.setEntity(new StringEntity(request.getBody(), "UTF-8"));
			response = super.doPut(put);
			
			return new RestClientResponse(response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity()));
		
		} catch (Exception e) {
			throw new RestClientException(HTTP_CLIENT_ERROR + " PUT " + request.getUrl(), e);
		}
	}

	public RestClientResponse doDelete(RestClientRequest request) throws RestClientException {
		HttpResponse response = null;
		try {
			HttpDelete delete = new HttpDelete(request.getUrl());
			response = super.doDelete(delete);
			
			return new RestClientResponse(response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity()));
		
		} catch (Exception e) {
			throw new RestClientException(HTTP_CLIENT_ERROR + " DELETE " + request.getUrl(), e);
		}
	}	

}