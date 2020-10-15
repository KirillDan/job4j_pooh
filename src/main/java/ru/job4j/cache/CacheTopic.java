package ru.job4j.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

import ru.job4j.dto.MessageTopic;

public final class CacheTopic {
	
	private static final CacheTopic INSTANCE = new CacheTopic();
	private static final ConcurrentMap<String, CopyOnWriteArrayList<MessageTopic>> MAP
		= new ConcurrentHashMap<String, CopyOnWriteArrayList<MessageTopic>>();
	private static final ConcurrentHashMap<Integer, Integer> CLIENTIDS
		= new ConcurrentHashMap<Integer, Integer>();
	
	private CacheTopic() {
	    }
	/**
	 * 
	 * @return CacheQueue
	 */
	public static CacheTopic getInstance() {
		return INSTANCE;
	}
	/**
	 * 
	 * @param messageTopic
	 * @return boolean
	 */
	public boolean add(final MessageTopic messageTopic) {
		boolean res = false;
		String topicName = messageTopic.getTopic();
		if (this.MAP.containsKey(topicName)) {
			CopyOnWriteArrayList<MessageTopic> topic = this.MAP.get(topicName);
			topic.add(messageTopic);
			res = true;
		} else {
			CopyOnWriteArrayList<MessageTopic> topic = 
					new CopyOnWriteArrayList<MessageTopic>();
			topic.add(messageTopic);
			this.MAP.put(topicName, topic);
			res = true;
		}
		return res;
	}
	/**
	 * 
	 * @param topicName
	 * @param clientId
	 * @return MessageTopic
	 */
	public MessageTopic get(final String topicName, final Integer clientId) {
		MessageTopic messageTopic = null;
		Integer messageCount;
		if (this.CLIENTIDS.containsKey(clientId)) {
			messageCount = this.CLIENTIDS.compute(clientId, (k, v) -> {
				v += 1;
				return v;
			});
			messageTopic = getFromTopic(topicName, messageCount);
			if (messageTopic == null) {
				this.CLIENTIDS.compute(clientId, (k, v) -> {
					v -= 1;
					return v;
				});
			}
		} else {
			messageCount = this.CLIENTIDS.putIfAbsent(clientId, -1);
			this.get(topicName, clientId);
		}
		return messageTopic;
	}
	
	private MessageTopic getFromTopic(final String topicName, final Integer messageCount) {
		MessageTopic message = null;
		if (this.MAP.containsKey(topicName)) {	
			CopyOnWriteArrayList<MessageTopic> topic = this.MAP.get(topicName);
			try {
				message = topic.get(messageCount);
			} catch (IndexOutOfBoundsException e) {
				return null;
			}			
		}
		return message;
	}
	
	@Override
	public String toString() {
		return "[" + this.MAP.toString() + "]";
	}
}
