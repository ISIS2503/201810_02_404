package lambda;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

public class Speed {

	public Speed(String data) throws ClientProtocolException, IOException {

		HttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost("localhost:8080/alert");
		String a = null;
		
		httppost.setEntity((HttpEntity) new StringEntity(a,
				ContentType.create("plain/text", Consts.UTF_8)));

		//Execute and get the response.
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity entity = response.getEntity();

		if (entity != null) {
			InputStream instream = entity.getContent();
			try {
			} finally {
				instream.close();
			}
		}
	}
}
