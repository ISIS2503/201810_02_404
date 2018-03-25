import java.io.IOException;
import java.io.InputStream;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

public class Speed {
	ResponseHandler<String> rh = new ResponseHandler<String>() {

	    @Override
	    public String handleResponse(
	            final HttpResponse response) throws IOException {
	        StatusLine statusLine = response.getStatusLine();
	        HttpEntity entity = response.getEntity();
	        if (statusLine.getStatusCode() != 200) {
	            throw new HttpResponseException(
	                    statusLine.getStatusCode(),
	                    statusLine.getReasonPhrase());
	        }
	        if (entity == null) {
	            throw new ClientProtocolException("Response contains no content");
	        }
			return null;
	    }
	};
	public Speed(int asd) throws ClientProtocolException, IOException {
	HttpClient httpclient = HttpClients.createDefault();
	HttpPost httppost = new HttpPost("http://www.a-domain.com/foo/");
	String a = null;
	if(asd==0) {
		a="ALERTA 0 = TIEMPO MAXIMO DE APERTURA SOBREPASADO";
	}
	else if(asd==1) {
		a="ALERTA 1 = INTENTOS DE APERTURA SOBREPASADOS";
	}
	else {a="ALERTA 2 = SENSOR DE PRESENCIA ACTIVADO";}
	httppost.setEntity((HttpEntity) new StringEntity(a,
	        ContentType.create("plain/text", Consts.UTF_8)));
	httpclient.execute(httppost, rh);
}
}
