package grails.plugin.dynamic.parameters.http.client.exception;

/**
 * @author mlabarinas
 */
public class RestClientException extends Exception {

	private static final long serialVersionUID = 202813599126731425L;

	public RestClientException(String message) {
		super(message);
	}
	
	public RestClientException(String message, Throwable e) {
		super(message, e);
	}
	
}