package grails.plugin.dynamic.parameters.http.client;

import grails.plugin.dynamic.parameters.utils.DynamicParametersUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

/**
 * @author mlabarinas
 */
public abstract class AbstractRestClient {

	private HttpClient client;
	private HttpContext context = new BasicHttpContext();
	
	public AbstractRestClient() {
		client = new DefaultHttpClient(new ThreadSafeClientConnManager(getDefaultParams(), getScheme()), getDefaultParams());
	}
	
	public HttpResponse doPost(HttpPost httpPost) throws ClientProtocolException, IOException {
		return client.execute(httpPost, context);
	}

	public HttpResponse doGet(HttpGet httpGet) throws ClientProtocolException, IOException {
		return client.execute(httpGet, context);
	}
	
	public HttpResponse doPut(HttpPut httpPut) throws ClientProtocolException, IOException {
		return client.execute(httpPut, context);
	}
	
	public HttpResponse doDelete(HttpDelete httpDelete) throws ClientProtocolException, IOException {
		return client.execute(httpDelete, context);
	}
	
	private SchemeRegistry getScheme() {
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
		return schemeRegistry;
	}
	
	private HttpParams getDefaultParams() {
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, DynamicParametersUtils.getRestDefaultConnectionTimeout());
		HttpConnectionParams.setSoTimeout(params, DynamicParametersUtils.getRestDefaultSocketTimeout());
		return params;
	}
	
}