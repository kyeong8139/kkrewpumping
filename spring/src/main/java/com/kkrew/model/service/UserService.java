package com.kkrew.model.service;

import java.util.List;
import java.util.Map;

import com.kkrew.model.dto.User;

public interface UserService {

	int registUser(User user);

	User getUserInfo(String id);

	String getNickname(String id);

	boolean checkPassword(User user);
	
	boolean hasDuplicate(String key, String word);

	int modifyUserInfo(User user);

	int updateUserExp(String id, int exp);

	List<User> searchUserList(String key, String word);

	int removeUserInfo(String id);

	String sendCodeToEmail(String email);

	void updatePassword(User user);
}
