package application.classes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpRequest {
	private static HttpURLConnection conn;
	
	public HttpRequest() {
		// TODO Auto-generated constructor stub
	}
	
	public String getRequest(String urlRequest)
	{
		BufferedReader reader;
        String line;
        StringBuilder responseContent = new StringBuilder();
        try{
        	 URL url = new URL(urlRequest);
            conn = (HttpURLConnection) url.openConnection();

           
            // Request setup
            conn.setRequestMethod("GET");
            //conn.setConnectTimeout(5000);// 5000 milliseconds = 5 seconds
            //conn.setReadTimeout(5000);
            


            // Test if the response from the server is successful
            int status = conn.getResponseCode();

            if (status >= 300) {
                reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }
            else {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }
            //log.info("response code: " + status);
            return responseContent.toString();
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            conn.disconnect();
            
        }
        return "";
	}
}
