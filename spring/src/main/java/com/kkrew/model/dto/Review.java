package com.kkrew.model.dto;
public class Review {
	private int id;
	private int challengeId;
	private String writer;
	private String content;
	
	public Review() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public int getChallengeId() {
		return challengeId;
	}

	public void setChallengeId(int challengeId) {
		this.challengeId = challengeId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Review [id=" + id + ", challengeId=" + challengeId + ", writer=" + writer + ", content=" + content
				+ "]";
	}
}
