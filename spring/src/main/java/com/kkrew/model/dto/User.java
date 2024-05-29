package com.kkrew.model.dto;

public class User {
	
	private String id;
	private String nickname;
	private String password;
	private String introduction;
	private String imageUrl;
	private int isKakaoUser;
	
	private int exp;
	public String getId() {
		return id;
	}
	
	public User() {}
	
	public User(String id, String nickname, String password, String introduction, String imageUrl, int exp) {
		this.id = id;
		this.nickname = nickname;
		this.password = password;
		this.introduction = introduction;
		this.imageUrl = imageUrl;
		this.exp = exp;
	}

	public int getIsKakaoUser() {
		return isKakaoUser;
	}

	public void setIsKakaoUser(int isKakaoUser) {
		this.isKakaoUser = isKakaoUser;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public int getExp() {
		return exp;
	}
	public void setExp(int exp) {
		this.exp = exp;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", nickname=" + nickname + ", password=" + password + ", introduction=" + introduction
				+ ", imageUrl=" + imageUrl + ", isKakaoUser=" + isKakaoUser + ", exp=" + exp + "]";
	}
}
