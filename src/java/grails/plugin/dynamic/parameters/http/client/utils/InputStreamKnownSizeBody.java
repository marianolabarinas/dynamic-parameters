package grails.plugin.dynamic.parameters.http.client.utils;

import org.apache.http.entity.mime.content.InputStreamBody;

import java.io.InputStream;

/**
 * @author mlabarinas
 */
public class InputStreamKnownSizeBody extends InputStreamBody {
	private int lenght;

	public InputStreamKnownSizeBody(final InputStream in, final int lenght, final String mimeType, final String filename) {
		super(in, mimeType, filename);
		
		this.lenght = lenght;
	}

	@Override
	public long getContentLength() {
		return this.lenght;
	}
	
}