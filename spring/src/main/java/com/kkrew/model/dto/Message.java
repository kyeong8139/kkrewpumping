package com.kkrew.model.dto;

public class Message {
	private String fromUser = "kkrewpumping";
	private String fromDateTime;
	private String toUser;
	private String content;
	
	public String getFromUser() {
		return fromUser;
	}
	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}
	public String getFromDateTime() {
		return fromDateTime;
	}
	public void setFromDateTime(String fromDateTime) {
		this.fromDateTime = fromDateTime;
	}
	public String getToUser() {
		return toUser;
	}
	public void setToUser(String toUser) {
		this.toUser = toUser;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
