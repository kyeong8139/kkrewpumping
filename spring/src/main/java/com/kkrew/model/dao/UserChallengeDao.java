package com.kkrew.model.dao;

import java.util.List;

import com.kkrew.model.dto.UserChallenge;

public interface UserChallengeDao {

	List<UserChallenge> selectUserChallengeList(String id, int completion);

	int insertUserChallenge(UserChallenge userChallenge);

	UserChallenge selectOneUserChallenge(int userChallengeId);

	int updateCurrentCompletion(UserChallenge userChallenge);

	int updateComplete(UserChallenge userChallenge);

}
