package ru.job4j.client;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class QueueClient {
	protected QueueClient() {
	}
	/**
	 * 
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static void postQueue() throws MalformedURLException, IOException {
		HttpURLConnection connection = 
				(HttpURLConnection) new URL("http://localhost:8001/queue").openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setConnectTimeout(1000);
		connection.setDoOutput(true);
		try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream())) {
			String responseBody = "{\"queue\" : \"weather\", \"text\" : \"temperature +18 C\"}";
			writer.write(responseBody);
			writer.flush();
		}
		if (connection.getResponseCode() != 200) {
			System.out.println("Error");
		}
	}
	
	/**
	 * 
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static void getQueue() throws MalformedURLException, IOException {
		HttpURLConnection connection = 
				(HttpURLConnection) new URL("http://localhost:8001/queue/weather").openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setConnectTimeout(1000);
		try (InputStreamReader reader = new InputStreamReader(connection.getInputStream())) {
			int data;
			String resultData = "";
			while ((data = reader.read()) != -1) {
				resultData += (char) data;
			}
			System.out.println(resultData);
		}
		if (connection.getResponseCode() != 200) {
			System.out.println("Error");
		}
	}
	
	/**
	 * 
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static void postTopic() throws MalformedURLException, IOException {
		HttpURLConnection connection = 
				(HttpURLConnection) new URL("http://localhost:8001/topic").openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setConnectTimeout(1000);
		connection.setDoOutput(true);
		try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream())) {
			String responseBody = "{\"topic\" : \"weather\", \"text\" : \"temperature +18 C\"}";
			writer.write(responseBody);
			writer.flush();
		}
		if (connection.getResponseCode() != 200) {
			System.out.println("Error");
		}
	}
	/**
	 * 
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static void getTopic() throws MalformedURLException, IOException {
		HttpURLConnection connection = 
				(HttpURLConnection) new URL("http://localhost:8001/topic/weather").openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setRequestProperty("Client-Id", "1");
		connection.setConnectTimeout(10000);
		try (InputStreamReader reader = new InputStreamReader(connection.getInputStream())) {
			int data;
			String resultData = "";
			while ((data = reader.read()) != -1) {
				resultData += (char) data;
			}
			System.out.println(resultData);
		}
		if (connection.getResponseCode() != 200) {
			System.out.println("Error");
		}
	}
	
	/**
	 * 
	 * @param args
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	public static void main(final String[] args) throws MalformedURLException, IOException {
		postQueue();
		getQueue();
		postTopic();
		getTopic();
	}

}
