package com.kkrew.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.kkrew.model.dao.ChallengeDao;
import com.kkrew.model.dto.Challenge;
import com.kkrew.model.dto.Video;

@Service
public class ChallengeServiceImpl implements ChallengeService {

	ChallengeDao cDao;
	VideoService vService;

	public ChallengeServiceImpl(ChallengeDao cDao, VideoService vService) {
		this.cDao = cDao;
		this.vService = vService;
	}

	@Override
	public List<Challenge> getChallengeList() {
		return cDao.selectAllChallenge();
	}

	@Override
	public Challenge getChallengeInfo(int id) {
		
		Challenge challenge = cDao.selectOneChallenge(id);
		
		List<Integer> videosId = cDao.selectChallengeVideoId(id);
		List<Video> videos = new ArrayList<>();
		for (int vId : videosId) {
			videos.add(vService.searchVideoById(vId));
		}
		
		challenge.setVideos(videos);
		challenge.setSupplies(cDao.selectSupplies(id));
		
		return challenge;
	}

	@Override
	public int addChallenge(Challenge challenge) {
		
		int result = cDao.insertChallenge(challenge);
		if (result == 1) {
			int id = challenge.getId();
			
			for (String supply : challenge.getSupplies()) {
				cDao.insertSupplies(id, supply);
			}
			
			for (int i = 0; i < challenge.getVideos().size(); i++) {			
				cDao.insertChallengeVideo(id, challenge.getVideos().get(i).getId(), i+1);
			}
		} 
		
		return result;
	}

	@Override
	public int updateChallenge(Challenge challenge) {
		List<String> prev = cDao.selectSupplies(challenge.getId());
		
		for (String supply : challenge.getSupplies()) {
			if (!prev.contains(supply)) {
				cDao.insertSupplies(challenge.getId(), supply);				
			} else {
				prev.remove(supply);				
			}
		}
		
		for (String supply : prev) {
			cDao.deleteSupplies(challenge.getId(), supply);
		}
		
		return cDao.updateChallenge(challenge);
	}

	@Override
	public int deleteChallenge(int id) {
		return cDao.deleteChallenge(id);
	}

	@Override
	public List<Challenge> searchChallengeList(String key, String word) {
		return cDao.searchChallengeList(key, word);
	}

	@Override
	public int addParticipants(int challengeId) {
		Challenge challenge = this.getChallengeInfo(challengeId);
		challenge.setParticipantsCount(challenge.getParticipantsCount() + 1);
		challenge.setCurrentParticipantsCount(challenge.getCurrentParticipantsCount() + 1);
		return cDao.updateChallenge(challenge);
	}

	@Override
	public int addSuccessParticipants(int challengeId) {
		Challenge challenge = this.getChallengeInfo(challengeId);
		challenge.setCurrentParticipantsCount(challenge.getCurrentParticipantsCount() - 1);
		challenge.setSuccessParticipantsCount(challenge.getSuccessParticipantsCount() + 1);
		
		return cDao.updateChallenge(challenge);
	}

	@Override
	public void updateChallengeWriter(Challenge challenge) {
		cDao.updateChallengeWriter(challenge);
	}
	
    @Override
    public boolean hasChallenge(int id) {
        int result = cDao.countChallengeByVideoId(id);
        
        return result > 0;
    }
}
