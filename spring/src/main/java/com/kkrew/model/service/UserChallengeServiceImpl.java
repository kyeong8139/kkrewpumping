package com.kkrew.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kkrew.model.dao.ChallengeDao;
import com.kkrew.model.dao.UserChallengeDao;
import com.kkrew.model.dto.UserChallenge;
import com.kkrew.model.dto.Video;

@Service
public class UserChallengeServiceImpl implements UserChallengeService {
	
	UserChallengeDao ucDao;
	ChallengeService cService;

	public UserChallengeServiceImpl(UserChallengeDao ucDao, ChallengeService cService) {
		this.ucDao = ucDao;
		this.cService = cService;
	}

	@Override
	public List<UserChallenge> getUserChallengeList(String id, int completion) {
		return ucDao.selectUserChallengeList(id, completion);
	}
	
	@Override
	public int registChallenge(UserChallenge userChallenge) {
		
		cService.addParticipants(userChallenge.getChallengeId());
		return ucDao.insertUserChallenge(userChallenge);
	}

	@Override
	public UserChallenge getUserChallenge(int userChallengeId) {
		
		UserChallenge userChallenge = ucDao.selectOneUserChallenge(userChallengeId);
		
		if (userChallenge == null) return null;
		
		userChallenge.setChallenge(cService.getChallengeInfo(userChallenge.getChallengeId()));
		
		int currentCompletion = userChallenge.getCurrentCompletion();
		
		// 비트마스킹을 통해 각 비디오의 운동완료 여부를 설정함
		List<Video> videos = userChallenge.getChallenge().getVideos();
		for (int i = 0; i < videos.size(); i++) {
			if ((currentCompletion & (1 << i)) != 0) {
				videos.get(i).setIsComplete(1);
			}
		}
		
		// 현재 운동을 몇개 완료 했는지 정수값으로 리턴함
		int completion = Integer.bitCount(userChallenge.getCurrentCompletion());
		userChallenge.setCurrentCompletion(completion);
		
		return userChallenge;
	}

	@Override
	public int updateCurrentCompletion(UserChallenge userChallenge) {
		return ucDao.updateCurrentCompletion(userChallenge);
	}
	@Override
	public int updateComplete(UserChallenge userChallenge) {
		cService.addSuccessParticipants(userChallenge.getChallengeId());
		return ucDao.updateComplete(userChallenge);
	}
}
