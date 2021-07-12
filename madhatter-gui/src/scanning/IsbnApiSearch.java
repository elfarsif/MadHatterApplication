package scanning;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.*;


public class IsbnApiSearch {
	
	public static void main(String[] args) throws IOException, JSONException {  
		//read json
		readJSON("9780307743688");
		
	        
	}
	
	//methods to read api
	private static String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	  }

	public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
	    InputStream is = new URL(url).openStream();
	    try {
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	      String jsonText = readAll(rd);
	      JSONObject json = new JSONObject(jsonText);
	      return json;
	    } finally {
	      is.close();
	    }
	  }
	
	public static void readJSON(String scannedIsbn) throws IOException, JSONException {
		
		//initilize variables
		String title = null;
    	String year = null;
    	String isbn = null;
    	//search google book api
    	try {
			JSONObject json = readJsonFromUrl("https://www.googleapis.com/books/v1/volumes?q=isbn:"+ scannedIsbn);
			
			JSONArray items = json.getJSONArray("items");
			JSONObject volumeInfo = items.getJSONObject(0).getJSONObject("volumeInfo");
			isbn = scannedIsbn;
			title = volumeInfo.getString("title");
			year = volumeInfo.getString("publishedDate");
			String[] authors = {"",""};
			for(int i =0; i<volumeInfo.getJSONArray("authors").length();i++) {
				authors[i] = volumeInfo.getJSONArray("authors").getString(i);
			}
			System.out.println(isbn+", "+title+", "+year+", "+authors[0]+", ");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	    
	}
	
	


}
