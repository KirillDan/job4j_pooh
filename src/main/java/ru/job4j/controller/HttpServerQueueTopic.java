package ru.job4j.controller;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import com.sun.net.httpserver.HttpServer;
import ru.job4j.constaints.HttpPaths;
import ru.job4j.handlers.QueueHttpHandler;
import ru.job4j.handlers.TopicHttpHandler;

public class HttpServerQueueTopic {
	private static final Jsonb JSONB = JsonbBuilder.create();
	/**
	 * 
	 * @throws IOException
	 */
	public void startHttpServer() throws IOException {
		HttpServer server = HttpServer.create(new InetSocketAddress(HttpPaths.HOST, HttpPaths.PORT), 0);
		server.createContext(HttpPaths.QUEUE, new QueueHttpHandler(this.JSONB));
		server.createContext(HttpPaths.TOPIC, new TopicHttpHandler(this.JSONB));
		ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors
				.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		server.setExecutor(threadPoolExecutor);
		server.start();
	}
}
