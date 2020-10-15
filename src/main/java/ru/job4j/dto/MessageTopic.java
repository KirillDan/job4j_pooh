package ru.job4j.dto;

public class MessageTopic {
	private String topic;
	private String text;
	/**
	 * 
	 * @param topic
	 * @param text
	 */
	public MessageTopic(final String topic, final String text) {
		this.topic = topic;
		this.text = text;
	}
	/**
	 * 
	 */
	public MessageTopic() {
	}
	/**
	 * 
	 * @return topic
	 */
	public String getTopic() {
		return topic;
	}
	/**
	 * 
	 * @param topic
	 */
	public void setTopic(final String topic) {
		this.topic = topic;
	}
	/**
	 * 
	 * @return text
	 */
	public String getText() {
		return text;
	}
	/**
	 * 
	 * @param text
	 */
	public void setText(final String text) {
		this.text = text;
	}
	@Override
	public String toString() {
		return "MessageTopic [topic=" + topic + ", text=" + text + "]";
	}
	
}
