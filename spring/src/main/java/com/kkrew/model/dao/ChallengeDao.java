package com.kkrew.model.dao;

import java.util.List;

import com.kkrew.model.dto.Challenge;
import com.kkrew.model.dto.Video;

public interface ChallengeDao {

	// 전체 챌린지 조회
	List<Challenge> selectAllChallenge();
	
	// 챌린지 상세정보
	Challenge selectOneChallenge(int id);
	
	// 챌린지 등록
	int insertChallenge(Challenge challenge);
	
	// 챌린지 수정
	int updateChallenge(Challenge challenge);
	
	// 챌린지 삭제
	int deleteChallenge(int id);
	
	// 챌린지에 속한 비디오 url
	List<Integer> selectChallengeVideoId(int id);
	
	int insertChallengeVideo(int challengeId, int videoId, int order);
	
	// 챌린지 준비물 테이블
	void insertSupplies(int id, String supply);
	
	List<String> selectSupplies(int id);
	
	void deleteSupplies(int id, String supply);

	// 검색
	List<Challenge> searchChallengeList(String key, String word);

	void updateChallengeWriter(Challenge challenge);

	int countChallengeByVideoId(int id);
}
