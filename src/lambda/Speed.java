package lambda;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

public class Speed {

	public Speed(String data) throws ClientProtocolException, IOException {
        StringEntity entity = new StringEntity(data,
                ContentType.APPLICATION_JSON);
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost("http://172.24.42.74:8080/alert");
        request.setEntity(entity);
        HttpResponse response = httpClient.execute(request);
        System.out.println(response.getStatusLine().getStatusCode());
	}
}
