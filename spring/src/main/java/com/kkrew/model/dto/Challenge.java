package com.kkrew.model.dto;

import java.util.ArrayList;
import java.util.List;

public class Challenge {
	private int id;
	private String title;
	private String purpose;
	private int period; // 챌린지 기간
	private int time; // 평균 소요 시간(분)
	private int level; // 난이도
	private int participantsCount;
	private int currentParticipantsCount;
	private int successParticipantsCount;
	private int videoCount;
	private List<String> supplies = new ArrayList<>();
	private List<Video> videos = new ArrayList<>();

	// 첫번째 영상의 썸네일
	private String imageUrl;
	
	// 작성자 
	private String writer;
	
	public Challenge() {}

	public int getVideoCount() {
		return videoCount;
	}

	public void setVideoCount(int videoCount) {
		this.videoCount = videoCount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getParticipantsCount() {
		return participantsCount;
	}

	public void setParticipantsCount(int participantsCount) {
		this.participantsCount = participantsCount;
	}

	public int getCurrentParticipantsCount() {
		return currentParticipantsCount;
	}

	public void setCurrentParticipantsCount(int currentParticipantsCount) {
		this.currentParticipantsCount = currentParticipantsCount;
	}

	public int getSuccessParticipantsCount() {
		return successParticipantsCount;
	}

	public void setSuccessParticipantsCount(int successParticipantsCount) {
		this.successParticipantsCount = successParticipantsCount;
	}

	public List<String> getSupplies() {
		return supplies;
	}

	public void setSupplies(List<String> supplies) {
		this.supplies = supplies;
	}

	public List<Video> getVideos() {
		return videos;
	}

	public void setVideos(List<Video> videos) {
		this.videos = videos;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

}
