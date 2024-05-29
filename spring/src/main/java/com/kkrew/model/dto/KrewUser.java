package com.kkrew.model.dto;

import java.sql.Timestamp;

public class KrewUser{
	private int krewId;
	private String userId;
	private int userChallengeId;
	private int currentCompletion;
	private Timestamp updateTimeStamp;
	
	public Timestamp getUpdateTimeStamp() {
		return updateTimeStamp;
	}
	public void setUpdateTimeStamp(Timestamp updateTimeStamp) {
		this.updateTimeStamp = updateTimeStamp;
	}
	public int getKrewId() {
		return krewId;
	}
	public void setKrewId(int krewId) {
		this.krewId = krewId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getUserChallengeId() {
		return userChallengeId;
	}
	public void setUserChallengeId(int userChallengeId) {
		this.userChallengeId = userChallengeId;
	}
	public int getCurrentCompletion() {
		return currentCompletion;
	}
	public void setCurrentCompletion(int currentCompletion) {
		this.currentCompletion = currentCompletion;
	}
}
