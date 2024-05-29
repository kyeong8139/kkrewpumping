package com.kkrew.model.dto;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Krew {
	private int id;
	private String name;
	private Date endDate;
	private int challengeId;
	
	private List<KrewUser> users;
	
	public List<KrewUser> getUsers() {
		return users;
	}
	public void setUsers(List<KrewUser> users) {
		this.users = users;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getChallengeId() {
		return challengeId;
	}
	public void setChallengeId(int challengeId) {
		this.challengeId = challengeId;
	}
	@Override
	public String toString() {
		return "Krew [id=" + id + ", name=" + name + ", endDate=" + endDate + ", challengeId=" + challengeId
				+ ", users=" + users + "]";
	}
}
