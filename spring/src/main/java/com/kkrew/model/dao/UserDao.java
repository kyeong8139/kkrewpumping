package com.kkrew.model.dao;

import java.util.List;

import com.kkrew.model.dto.User;

public interface UserDao {

	int insertUser(User user);

	User selectUserById(String id);

	int selectKeyCount(String key, String word);

	int updateUser(User user);

	int deleteUser(String id);
	
	int updateUserExp(String id, int exp);

	List<User> selectUserByNickname(String key, String word);

	void updatePassword(User user);
}
