package application.classes;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class NftHttpRequest {
	public String getRequest(String urlRequest)
	{
    	HttpRequest request = HttpRequest.newBuilder()
    		    .uri(URI.create(urlRequest))
    		    .header("Accept", "application/json")
    		    .method("GET", HttpRequest.BodyPublishers.noBody())
    		    .build();
		HttpResponse<String> response;
		try {
			response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
			return response.body();
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
}
