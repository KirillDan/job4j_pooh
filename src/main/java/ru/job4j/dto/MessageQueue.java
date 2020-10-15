package ru.job4j.dto;

public class MessageQueue {
	private String queue;
	private String text;
	/**
	 * 
	 * @param queue
	 * @param text
	 */
	public MessageQueue(final String queue, final String text) {
		this.queue = queue;
		this.text = text;
	}
	/**
	 * 
	 */
	public MessageQueue() {
	}
	/**
	 * 
	 * @return queue
	 */
	public String getQueue() {
		return queue;
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
	 * @param queue
	 */
	public void setQueue(final String queue) {
		this.queue = queue;
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
		return "MessageQueue [queue=" + queue + ", text=" + text + "]";
	}
}
