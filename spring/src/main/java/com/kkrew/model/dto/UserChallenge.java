package com.kkrew.model.dto;

import java.sql.Date;
import java.sql.Timestamp;

public class UserChallenge {
	private int id;
	private String userId;
	private int challengeId;
	private int crewId = -1;
	private Date startDate;
	private Date endDate;
	private int videoCount;
	private int currentCompletion;
	private Challenge challenge;
	private Timestamp updateTimeStamp;

	public UserChallenge() {}
	
	public Timestamp getUpdateTimeStamp() {
		return updateTimeStamp;
	}
	
	public void setUpdateTimeStamp(Timestamp updateTimeStamp) {
		this.updateTimeStamp = updateTimeStamp;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getCurrentCompletion() {
		return currentCompletion;
	}

	public void setCurrentCompletion(int currentCompletion) {
		this.currentCompletion = currentCompletion;
	}

	public int getChallengeId() {
		return challengeId;
	}

	public void setChallengeId(int challengeId) {
		this.challengeId = challengeId;
	}

	public int getCrewId() {
		return crewId;
	}

	public void setCrewId(int crewId) {
		this.crewId = crewId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getVideoCount() {
		return videoCount;
	}

	public void setVideoCount(int videoCount) {
		this.videoCount = videoCount;
	}
	
	public Challenge getChallenge() {
		return challenge;
	}

	public void setChallenge(Challenge challenge) {
		this.challenge = challenge;
	}

	public UserChallenge(int id, String userId, int challengeId, int crewId, Date startDate, Date endDate,
			int videoCount, int currentCompletion, Challenge challenge, Timestamp updateTimeStamp) {
		super();
		this.id = id;
		this.userId = userId;
		this.challengeId = challengeId;
		this.crewId = crewId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.videoCount = videoCount;
		this.currentCompletion = currentCompletion;
		this.challenge = challenge;
		this.updateTimeStamp = updateTimeStamp;
	}
	
}
