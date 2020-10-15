package ru.job4j.handlers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.json.bind.Jsonb;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import ru.job4j.cache.CacheTopic;
import ru.job4j.constaints.HttpPaths;
import ru.job4j.dto.MessageTopic;

public class TopicHttpHandler implements HttpHandler {
	private Jsonb jsonb;
	/**
	 * 
	 * @param jsonb
	 */
	public TopicHttpHandler(final Jsonb jsonb) {
		this.jsonb = jsonb;
	}

	@Override
	public void handle(final HttpExchange httpExchange) throws IOException {
		if ("POST".equals(httpExchange.getRequestMethod())
				&& HttpPaths.TOPIC.equals(httpExchange.getRequestURI().toString())) {
			InputStream inputStream = httpExchange.getRequestBody();
			int data;
			StringBuilder output = new StringBuilder("");
			while ((data = inputStream.read()) > 0) {
				output.append((char) data);
			}
			MessageTopic messageTopic = this.jsonb.fromJson(output.toString(), MessageTopic.class);
			CacheTopic cacheTopic = CacheTopic.getInstance();
			cacheTopic.add(messageTopic);
			httpExchange.sendResponseHeaders(200, messageTopic.toString().length());
			inputStream.close();
			OutputStream outputStream = httpExchange.getResponseBody();
			outputStream.write(messageTopic.toString().getBytes());
			outputStream.flush();
			outputStream.close();
		} else if ("GET".equals(httpExchange.getRequestMethod())) {
			String topicName = 
					httpExchange.getRequestURI().toString().substring(HttpPaths.TOPIC.length() + 1);
			Integer clientId = Integer.valueOf(httpExchange.getRequestHeaders().getFirst("Client-Id"));
			CacheTopic cacheTopic = CacheTopic.getInstance();
			MessageTopic messageTopic = cacheTopic.get(topicName, clientId);
			String message = "";
			if (messageTopic != null) {
				message = this.jsonb.toJson(messageTopic);
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
