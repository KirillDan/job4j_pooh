package ru.job4j.handlers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import ru.job4j.cache.CacheQueue;
import ru.job4j.constaints.HttpPaths;
import ru.job4j.dto.MessageQueue;

public class QueueHttpHandler implements HttpHandler {
	private Jsonb jsonb;
	/**
	 * 
	 * @param jsonb
	 */
	public QueueHttpHandler(final Jsonb jsonb) {
		this.jsonb = jsonb;
	}
	
	@Override
	public void handle(final HttpExchange httpExchange) throws JsonbException, IOException {
		if ("POST".equals(httpExchange.getRequestMethod())
				&& HttpPaths.QUEUE.equals(httpExchange.getRequestURI().toString())) {
			InputStream inputStream = httpExchange.getRequestBody();
			int data;
			StringBuilder output = new StringBuilder("");
			while ((data = inputStream.read()) > 0) {
				output.append((char) data);
			}
			MessageQueue messageQueue = this.jsonb.fromJson(output.toString(), MessageQueue.class);
			CacheQueue cacheQueue = CacheQueue.getInstance();
			cacheQueue.add(messageQueue);
			httpExchange.sendResponseHeaders(200, messageQueue.toString().length());
			inputStream.close();
			OutputStream outputStream = httpExchange.getResponseBody();
			outputStream.write(messageQueue.toString().getBytes());
			outputStream.flush();
			outputStream.close();
		} else if ("GET".equals(httpExchange.getRequestMethod())) {
			String queueName = 
					httpExchange.getRequestURI().toString().substring(HttpPaths.QUEUE.length() + 1);
			CacheQueue cacheQueue = CacheQueue.getInstance();
			MessageQueue messageQueue = cacheQueue.get(queueName);
			String message = "";
			if (messageQueue != null) {
				message = this.jsonb.toJson(messageQueue);
			}
			httpExchange.sendResponseHeaders(200, message.toString().length());
			OutputStream outputStream = httpExchange.getResponseBody();
			outputStream.write(message.toString().getBytes());
			outputStream.flush();
			outputStream.close();
		} else {
			String errorMessage = "Error";
			httpExchange.sendResponseHeaders(400, errorMessage.toString().length());
			OutputStream outputStream = httpExchange.getResponseBody();
			outputStream.write(errorMessage.toString().getBytes());
			outputStream.flush();
			outputStream.close();
		}
	}
}
