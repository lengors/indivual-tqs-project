package ua.tqs.projects.individual.utils;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.URL;
import java.net.HttpURLConnection;

import java.nio.charset.StandardCharsets;

import java.util.Map;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import org.json.JSONObject;

public class Requester
{
	public static final UnaryOperator<String> AS_STRING = value -> value;
	public static final Function<String, JSONObject> AS_JSON = JSONObject::new;
	public static final Function<String, Map<String, Object>> AS_MAP = value -> new JSONObject(value).toMap();
	
	private Requester()
	{
	}
	
	public static <T> T get(String urlString, Function<String, T> transform)
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
				in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
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
			throw new RethrowException(e);
		}
	}
	
	public static JSONObject get(String urlString) 
	{
		return get(urlString, AS_JSON);
	}
}
