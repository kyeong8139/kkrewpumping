package com.kkrew.model.service;

import java.util.List;

import com.kkrew.model.dto.Challenge;

public interface ChallengeService {

	// 전체 챌린지 조회
	List<Challenge> getChallengeList();
	
	// 챌린지 상세정보
	Challenge getChallengeInfo(int id);
	
	// 챌린지 등록
	int addChallenge(Challenge challenge);
	
	// 챌린지 수정
	int updateChallenge(Challenge challenge);
	
	// 챌린지 삭제
	int deleteChallenge(int id);

	List<Challenge> searchChallengeList(String key, String word);

	int addParticipants(int challengeId);

	int addSuccessParticipants(int challengeId);

	void updateChallengeWriter(Challenge challenge);

	boolean hasChallenge(int id);
	
}
