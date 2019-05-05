package ua.tqs.projects.individual.utils;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.URL;
import java.net.HttpURLConnection;

import java.nio.charset.Charset;

import java.util.Map;
import java.util.function.Function;

import org.json.JSONObject;

public class Requester
{
	public final static Function<String, String> AS_STRING = (String value) -> value;
	public final static Function<String, JSONObject> AS_JSON = (String value) -> new JSONObject(value);
	public final static Function<String, Map<String, Object>> AS_MAP = (String value) -> new JSONObject(value).toMap();
	
	public static <T> T Get(String urlString, Function<String, T> transform)
	{
		URL url;
		HttpURLConnection connection;
		try
		{
			url = new URL(urlString);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			if (connection.getResponseCode() == 200)
			{
				String inputLine;
				BufferedReader in;
				StringBuilder builder;
				builder = new StringBuilder();
				in = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
				while ((inputLine = in.readLine()) != null)
					builder.append(inputLine);
				return transform.apply(builder.toString());
			}
			else
			{
				return null;
			}
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	public static JSONObject Get(String urlString) 
	{
		return Get(urlString, AS_JSON);
	}
}
