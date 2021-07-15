package scanning;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.*;

public class IsbnApiSearch {
	
	 private static HttpURLConnection con;
	 
	 public static void main(String[] args) throws MalformedURLException,
	            ProtocolException, IOException, JSONException {
	 
	        String url = "https://api2.isbndb.com/book/9780439362139";
	 
	        try {
	 
	            URL myurl = new URL(url);
	            con = (HttpURLConnection) myurl.openConnection();
	            con.setRequestProperty("Content-Type", "application/json");
	            con.setRequestProperty("Authorization", "46339_235f92c72a2b3806631c09ec94464081");
	            con.setRequestMethod("GET");
	 
	            StringBuilder content;
	 
	            try (BufferedReader in = new BufferedReader(
	                    new InputStreamReader(con.getInputStream()))) {
	 
	                String line;
	                content = new StringBuilder();
	 
	                while ((line = in.readLine()) != null) {
	                    content.append(line);
	                    content.append(System.lineSeparator());
	                }
	            }
	            
	            //get attributes from api, an vscode example exists when this needs to be improved
	            
	            
	            try {
	            	
					String jsonString = content.toString();
					JSONObject obj = new JSONObject(jsonString);
					String isbn13 = obj.getJSONObject("book").getString("isbn13");
					String title = obj.getJSONObject("book").getString("title");
					String date_published = obj.getJSONObject("book").getString("date_published");
					String msrp = obj.getJSONObject("book").getString("msrp");
					
					JSONArray arr = obj.getJSONObject("book").getJSONArray("authors");
					ArrayList<String> authors = new ArrayList<String>();
					for (int i = 0; i < arr.length(); i++)
					{
						authors.add(arr.getString(i));
					}
					System.out.println(jsonString+"\n\n Publisher : "+isbn13+"\n\n title : "+title+"\n\n Date published : "+date_published+"\n\n MSRP : "+msrp+"\n\n Autors : "+authors);
					
				} catch (JSONException e) {
					System.out.println("Isbn Api Search value in search doesnt exist, fix with a while try catch loop and an arraylist\n\n");
					e.printStackTrace();
				}
	            
	            
	            
	            
	 
	        } finally {
	 
	            con.disconnect();
	        }
	 
	 }
	 



}
