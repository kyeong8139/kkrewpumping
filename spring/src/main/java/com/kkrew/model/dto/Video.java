package com.kkrew.model.dto;

import java.util.ArrayList;
import java.util.List;

public class Video {
	private int id;
	private String title;
	private String purpose;
	private String imageUrl;
	private String videoUrl;
	private String viewCount;
	private String description;
	private int isComplete;
	private List<String> supplies = new ArrayList<>();
	
	// video 게시물의 작성자의 아이디
	private String writer;	

	public Video() {}
	
	public Video(int id, String title, String purpose, String imageUrl, String videoUrl, String viewCount,
			List<String> supplies, String writer) {
		this.id = id;
		this.title = title;
		this.purpose = purpose;
		this.imageUrl = imageUrl;
		this.videoUrl = videoUrl;
		this.viewCount = viewCount;
		this.supplies = supplies;
		this.writer = writer;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getIsComplete() {
		return isComplete;
	}

	public void setIsComplete(int isComplete) {
		this.isComplete = isComplete;
	}

	public List<String> getSupplies() {
		return supplies;
	}

	public void setSupplies(List<String> supplies) {
		this.supplies = supplies;
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

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getViewCount() {
		return viewCount;
	}

	public void setViewCount(String viewCount) {
		this.viewCount = viewCount;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	@Override
	public String toString() {
		return "Video [id=" + id + ", title=" + title + ", purpose=" + purpose + ", imageUrl=" + imageUrl
				+ ", videoUrl=" + videoUrl + ", viewCount=" + viewCount + ", supplies=" + supplies + ", writer="
				+ writer + "]";
	}
}
