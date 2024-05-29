package com.kkrew.model.service;

import java.util.List;

import com.kkrew.model.dto.UserChallenge;

public interface UserChallengeService {

	int registChallenge(UserChallenge userChallenge);

	UserChallenge getUserChallenge(int userChallengeId);

	int updateCurrentCompletion(UserChallenge userChallenge);

	int updateComplete(UserChallenge userChallenge);
	
	List<UserChallenge> getUserChallengeList(String id, int completion);
}
