package com.example.zappossearchandtext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.HttpContext;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.json.simple.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.google.gson.Gson;

import zappos.Response;

import android.app.Activity;
import android.net.Credentials;
import android.util.Base64;
import android.util.Log;

public class httpGet extends Activity {

	private static final String USER_AGENT = null;

	// Make URL request;
	public static String getRequest(String url) throws IOException {

		HttpURLConnection conn;
		URL urlz;
		DefaultHttpClient client = new DefaultHttpClient();
		urlz = new URL(url);
		conn = (HttpURLConnection) urlz.openConnection();
		HttpGet request = new HttpGet(url);
        conn.setRequestMethod("GET");
        client.getParams().setParameter(CoreProtocolPNames.USER_AGENT, System.getProperty("http.agent"));
		request.setHeader(new BasicHeader("Authorization", "Basic " + new String(Base64.encodeToString(("user:password").getBytes(), Base64.NO_WRAP))));
		request.setHeader("User-Agent", USER_AGENT);
		request.setHeader("Accept-Language", "en-US,en;q=0.5");
		HttpResponse response = client.execute(request);
		Log.e("error","error");
		HttpRequestInterceptor preemptiveAuth = null;
		client.addRequestInterceptor(preemptiveAuth, 0);
		client.getCredentialsProvider().setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("user:password"));
		client.addRequestInterceptor(new HttpRequestInterceptor() {
		    public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
		        AuthState state = (AuthState) context.getAttribute(ClientContext.TARGET_AUTH_STATE);
		        if (state.getAuthScheme() == null) {
		            BasicScheme scheme = new BasicScheme();
		            CredentialsProvider credentialsProvider = (CredentialsProvider) context.getAttribute(ClientContext.CREDS_PROVIDER);
		            Credentials credentials = (Credentials) credentialsProvider.getCredentials(AuthScope.ANY);
		            if (credentials == null) {
		                throw new HttpException();
		            }
		            state.setAuthScope(AuthScope.ANY);
		            state.setAuthScheme(scheme);
		            state.setCredentials((org.apache.http.auth.Credentials) credentials);
		        }
		    }
		}, 0); 
		conn.connect();
	
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		rd.close();
		conn.disconnect();

		return result.toString();
	}
	// Get the search reults for the search value entered.
	// Returns an array list of the items.
	public static ArrayList<Items> getResult(String message) throws IOException, JSONException{
		ArrayList<Items> pro = new ArrayList<Items>();
		String search = "http://api.zappos.com/Search?search_term="; //API search call
		String key = "&key=a73121520492f88dc3d33daf2103d7574f1a3166"; // key that was provided to me

		String res = httpGet.getRequest(search + message + key);
		Object jsonResult = JSONValue.parse(res);
		JSONObject resultArray = (JSONObject)jsonResult;
		JSONArray a =(JSONArray) resultArray.get("results");
		ArrayList<JSONObject> objectList = new ArrayList<JSONObject>();
		for(int i = 0; i < a.size(); i++){
			JSONObject b = (JSONObject)a.get(i);
			objectList.add(b);
		}
		ArrayList<Items> items = new ArrayList<Items>();
		for(int i = 0; i < objectList.size(); i++ ){
			items.add((Items) objectList.get(i).get("results"));

		}

		return items;

	}
	public static Items getPro(ArrayList<Items> item) {
		String search = "http://api.zappos.com/Search?search_term="; //API search call
		String key = "&key=a73121520492f88dc3d33daf2103d7574f1a3166"; // key that was provided to me
		Gson g = new Gson();
		Items pro = new Items();
		try {
			
			String res = httpGet.getRequest(search + item + key);
			Items i = g.fromJson(res,Items.class);
			pro = s.getResults();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return pro;
		
	}
}
