package myUtil;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

import model.Book;
import model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Server
{
	private static final String TAG = "Server";
	private static String SERVER_URL = "http://node.citethrough.com:18080/";
	private static final String sessionKey = "nodeSessionID";
	private static String sessionValue = "";
	private static CookieManager manager = new CookieManager();
	{

	}

	public static void post(final String path, final String[] args, final Callable callback) {
		// manage cookie
		CookieHandler.setDefault(new CookieManager());
		Thread thread = new Thread(new Runnable()
		{
			@Override
			public void run() {
				Object j = Server.postSync(path, args);
				callback.callback(j);

			}

		});
		thread.start();
	}

	public static Object postSync(String path, String[] args) {
		final String TAG = "Server.postSync";

		// init handling cookie
		manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
		CookieHandler.setDefault(manager);

		// assemble url parameters
		String urlParameters;
		urlParameters = "param=" + Str.encodeURIComponent(Server.string_arr_encode(args));

		// assemble request url
		String request = Server.SERVER_URL + path;

		URL url;
		try {
			url = new URL(request);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
		Log.i(TAG, "url: " + request);
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) url.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "IO Exception " + e.getMessage());
			return null;
		}
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setInstanceFollowRedirects(false);
		try {
			connection.setRequestMethod("POST");
		} catch (ProtocolException e) {
			Log.e(TAG, "Protocol Exception " + e.getMessage());
			return null;
		}
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		connection.setRequestProperty("charset", "utf-8");
		connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
		connection.setRequestProperty("Cookie", make_cookie());
		connection.setUseCaches(false);

		DataOutputStream wr = null;
		try {
			wr = new DataOutputStream(connection.getOutputStream());
			wr.writeBytes(urlParameters);
			Log.i(TAG, "url paramaters: " + urlParameters);
			wr.flush();
			wr.close();
		} catch (IOException e1) {
			Log.e(TAG, "Write Exception: "+e1.getMessage());
			return null;
		}

		String response = "";
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			String current;
			while ((current = in.readLine()) != null) {
				response += current;
			}
		} catch (IOException e2) {
			Log.e(TAG, "Read Exception " + e2.getMessage());
			return null;
		}

		// store cookie
		store_cookie();

		Object rtn;
		Log.i(TAG, response);
		try {
			// test if this is an JSON object
			rtn = new JSONObject(response);
		} catch (JSONException e) {
			try {
				// test if this is an JSON array
				rtn = new JSONArray(response);
			} catch (JSONException e1) {
				return null;
			}

		}

		// if this is an JSON object, check if it contains "error"
		if (rtn instanceof JSONObject) {
			JSONObject jsonR = (JSONObject) rtn;
			if (jsonR.has("error")) {
				try {
					Log.e(TAG, "server responses an error: " + jsonR.getString("error"));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				return null;
			}
		}

		return rtn;
	}

	private static String string_arr_encode(String[] arr) {
		String[] toImplode = new String[arr.length];
		for (int i = 0; i < arr.length; i++) {
			String str = arr[i];
			if(str == null) {
				// handle the case when the string is null
				toImplode[i] = "null";
			}
			else {
				toImplode[i] = "\"" + arr[i].replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
			}	
		}
		return "[" + Str.join(toImplode, ",") + "]";

	}

	private static void store_cookie() {
		CookieStore cookieJar = manager.getCookieStore();
		List<HttpCookie> cookies = cookieJar.getCookies();
		for (HttpCookie cookie : cookies) {
			if (cookie.getName().equals(sessionKey)) {
				sessionValue = cookie.getValue();
			}
		}
	}

	private static String make_cookie() {
		return Server.sessionKey + "=" + Server.sessionValue;
	}

	public static void server_do_debug() {
		//Log.d(TAG, "doing debug work");
		
	}

}
