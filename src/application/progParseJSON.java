package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.json.simple.JSONObject;


public class progParseJSON {

	private static HttpURLConnection conn;

	public static void main(String[] args) {
		String req = getRequest();
	
		System.out.println(req);
		
		Object obj = JSONValue.parse(req);
	    JSONArray arr = (JSONArray)obj;
	    
        for (int i = 0; i < arr.size(); i++) {
            String post_id = (String)((JSONObject)arr.get(i)).get("symbol");
            System.out.println(post_id);
        }
	}

	private static String getRequest() {
		BufferedReader reader;
		String line;
		StringBuilder responseContent = new StringBuilder();
		try {
			URL url = new URL("https://api-mainnet.magiceden.dev/v2/collections?offset=0&limit=20");
			conn = (HttpURLConnection) url.openConnection();

			// Request setup
			conn.setRequestMethod("GET");
			// conn.setConnectTimeout(5000);// 5000 milliseconds = 5 seconds
			// conn.setReadTimeout(5000);

			// Test if the response from the server is successful
			int status = conn.getResponseCode();

			if (status >= 300) {
				reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
				while ((line = reader.readLine()) != null) {
					responseContent.append(line);
				}
				reader.close();
			} else {
				reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				while ((line = reader.readLine()) != null) {
					responseContent.append(line);
				}
				reader.close();
			}
			// log.info("response code: " + status);
			return responseContent.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			conn.disconnect();

		}
		return "";
	}
}
