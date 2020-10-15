package ru.job4j.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

import ru.job4j.dto.MessageQueue;

public final class CacheQueue {

	private static final CacheQueue INSTANCE = new CacheQueue();
	private static final ConcurrentMap<String, ConcurrentLinkedQueue<MessageQueue>> MAP
		= new ConcurrentHashMap<String, ConcurrentLinkedQueue<MessageQueue>>();
	
	private CacheQueue() {
	    }
	/**
	 * 
	 * @return CacheQueue
	 */
	public static CacheQueue getInstance() {
		return INSTANCE;
	}
	/**
	 * 
	 * @param messageQueue
	 * @return boolean
	 */
	public boolean add(final MessageQueue messageQueue) {
		boolean res = false;
		String queueName = messageQueue.getQueue();
		if (this.MAP.containsKey(queueName)) {
			ConcurrentLinkedQueue<MessageQueue> queue = this.MAP.get(queueName);
			queue.add(messageQueue);
			res = true;
		} else {
			ConcurrentLinkedQueue<MessageQueue> queue = 
					new ConcurrentLinkedQueue<MessageQueue>();
			queue.add(messageQueue);
			this.MAP.put(queueName, queue);
			res = true;
		}
		return res;
	}
	/**
	 * 
	 * @param queueName
	 * @return MessageQueue
	 */
	public MessageQueue get(final String queueName) {
		MessageQueue message = null;
		if (this.MAP.containsKey(queueName)) {	
			ConcurrentLinkedQueue<MessageQueue> queue = this.MAP.get(queueName);
			message = queue.poll();
		}
		return message;
	}
	
	@Override
	public String toString() {
		return "[" + this.MAP.toString() + "]";
	}
}
