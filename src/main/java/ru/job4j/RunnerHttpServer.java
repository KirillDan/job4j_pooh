package ru.job4j;

import java.io.IOException;

import ru.job4j.controller.HttpServerQueueTopic;

public class RunnerHttpServer {
	protected RunnerHttpServer() { }
	/**
	 * 
	 * @param args
	 */
	public static void main(final String[] args) {
		HttpServerQueueTopic httpServerQueueTopic = new HttpServerQueueTopic();
		try {
			httpServerQueueTopic.startHttpServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
