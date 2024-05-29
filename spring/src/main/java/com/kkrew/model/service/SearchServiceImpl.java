package com.kkrew.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class SearchServiceImpl implements SearchService {

	VideoService vService;
	ChallengeService cService;
	UserService uService;
	ReviewService rService;
	
	public SearchServiceImpl(VideoService vService, ChallengeService cService, UserService uService,
			ReviewService rService) {
		this.vService = vService;
		this.cService = cService;
		this.uService = uService;
		this.rService = rService;
	}
	
	@Override
	public List searchData(String key, String word) {
		List result = null;

		switch (key) {
		case "video":
			result = vService.searchVideoList("title", word);
			break;
		case "challenge":
			result = cService.searchChallengeList("title", word);
			break;
		case "user":
			result = uService.searchUserList("nickname", word);
			break;
		}
		
		return result;
	}

	@Override
	public List searchMyData(String id, String table) {
		List result = null;
		
		switch(table) {
		case "video":
			result = vService.searchVideoList("writer", id);
			System.out.println(result);
			break;
		case "challenge":
			result = cService.searchChallengeList("writer", id);
			break;
		case "review":
			result = rService.searchReviewList("writer", id);
			break;
		}
		
		return result;
	}

}
