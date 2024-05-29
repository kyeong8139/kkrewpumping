package com.kkrew.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kkrew.model.dao.UserDao;
import com.kkrew.model.dto.User;
import com.kkrew.util.MailUtil;

@Service
public class UserServiceImpl implements UserService {

	private UserDao uDao;
	private MailUtil mUtil;
	
	public UserServiceImpl(UserDao uDao, MailUtil mUtil) {
		this.uDao = uDao;
		this.mUtil = mUtil;
	}

	@Override
	public int registUser(User user) {
		return uDao.insertUser(user);
	}

	@Override
	public User getUserInfo(String id) {
		return uDao.selectUserById(id);
	}

	@Override
	public String getNickname(String id) {
		return uDao.selectUserById(id).getNickname();
	}

	@Override
	public boolean checkPassword(User user) {
		User checker = uDao.selectUserById(user.getId());
		if (checker != null && checker.getPassword().equals(user.getPassword())) {
			return true;
		}
		
		return false;
	}

	@Override
	public boolean hasDuplicate(String key, String word) {
		int result = uDao.selectKeyCount(key, word);
		
		if (result > 0) {
			return true;
		}
		
		return false;
	}

	@Override
	public int modifyUserInfo(User user) {
		return uDao.updateUser(user);
	}

	@Override
	public int removeUserInfo(String id) {
		return uDao.deleteUser(id);
	}

	@Override
	public int updateUserExp(String id, int exp) {
		return uDao.updateUserExp(id, exp);
	}

	@Override
	public List<User> searchUserList(String key, String word) {
		return uDao.selectUserByNickname(key, word);
	}

	@Override
	public String sendCodeToEmail(String email) {
		String title = "kkrewpumping 이메일 인증 번호";
		String authCode = mUtil.createCode();
		
		mUtil.sendImegeMail(email, title, authCode);
		
		return authCode;
	}

	@Override
	public void updatePassword(User user) {
		uDao.updatePassword(user);
	}
}
