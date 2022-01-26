package hanze.nl.tijdtools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

public class HTTPFunctions {

	String executeGet(String format) throws ClientProtocolException, IOException {
		String url = "http://localhost:8081/TijdServer?responseType=" + format;
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);
		request.addHeader("Accept", "application/" + format);
		HttpResponse response = client.execute(request);
		BufferedReader rd = new BufferedReader(
				new InputStreamReader(response.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		return result.toString();
	}
}
