package com.kkrew.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kkrew.model.dto.Challenge;
import com.kkrew.model.dto.Review;
import com.kkrew.model.dto.Video;
import com.kkrew.model.service.ChallengeService;
import com.kkrew.model.service.ReviewService;
import com.kkrew.model.service.VideoService;

@RestController
@RequestMapping("/challenge")
public class ChallengeController {
	
	ChallengeService cService;
	ReviewService rService;
	VideoService vService;
	
	public ChallengeController(ChallengeService cService, ReviewService rService, VideoService vService) {
		this.cService = cService;
		this.rService = rService;
		this.vService = vService;
	}

	@GetMapping("")
	public ResponseEntity<?> getChallengeList() {
		
		List<Challenge> challenges = cService.getChallengeList();
		
		if (challenges == null || challenges.size() == 0) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<>(challenges, HttpStatus.OK);
	}
	
	@GetMapping("/{challenge_id}")
	public ResponseEntity<?> getChallengeInfo(@PathVariable("challenge_id") int id) {
		Challenge challenge = cService.getChallengeInfo(id);
		
		if (challenge == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(challenge, HttpStatus.OK);
	}
	
	@PostMapping("")
	public ResponseEntity<?> addChallenge(@RequestBody Challenge challenge) {
		
		// 썸네일 정보 추가	
		int videoId = challenge.getVideos().get(0).getId();
		Video video = vService.searchVideoById(videoId);
		
		String url = video.getVideoUrl();
		int index = url.indexOf("=");
		String distinctId = url.substring(index+1);
				
		challenge.setImageUrl("https://img.youtube.com/vi/" + distinctId + "/0.jpg");
		
		// 챌린지 비디오 갯수 추가
		challenge.setVideoCount(challenge.getVideos().size());
		
		int result = cService.addChallenge(challenge);
		
		// 데이터 추가 
		Map<String, Integer> data = new HashMap<>();
		data.put("challengeId", challenge.getId());
		
		return new ResponseEntity<>(data, HttpStatus.CREATED);
	}
	
	@PutMapping("/{challenge_id}")
	public ResponseEntity<?> modifyVideo(@PathVariable("challenge_id") int id, @RequestBody Challenge challenge) {
		challenge.setId(id);
		int result = cService.updateChallenge(challenge);
		
		if (result == 0) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		
		Map<String, Integer> data = new HashMap<>();
		data.put("challengeId", challenge.getId());
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@DeleteMapping("/{challenge_id}")
	public ResponseEntity<?> removeChallenge(@PathVariable("challenge_id") int id) {
		int result = cService.deleteChallenge(id);
		
		if (result == 1) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	}
	
	
	// 챌린지 리뷰 CRUD
	@GetMapping("/{challenge_id}/review")
	public ResponseEntity<?> getChallengeReview(@PathVariable("challenge_id") int id) {
		List<Review> reviews = rService.getChallengeReview(id);
		
		if (reviews == null || reviews.size() == 0) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<>(reviews, HttpStatus.OK);
	}
	
	@PostMapping("/{challenge_id}/review")
	public ResponseEntity<?> writeChallengeReview(@PathVariable("challenge_id") int cId, @RequestBody Review review) {
		review.setChallengeId(cId);
		int result = rService.writeReview(review);
		
		Map<String, Integer> data = new HashMap<>();
		data.put("challengeId", cId);
		return new ResponseEntity<>(data, HttpStatus.CREATED);
	}
	
	@PutMapping("/{challenge_id}/review/{review_id}")
	public ResponseEntity<?> updateChallengeReview(@PathVariable("challenge_id") int cId, @PathVariable("review_id") int rId, @RequestBody Review review) {
		review.setChallengeId(cId);
		review.setId(rId);
		
		int result = rService.modifyReview(review);
		
		Map<String, Integer> data = new HashMap<>();
		data.put("challengeId", cId);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@DeleteMapping("/{challenge_id}/review/{review_id}")
	public ResponseEntity<?> removeChallengeReview(@PathVariable("challenge_id") int cId, @PathVariable("review_id") int rId) {
		int result = rService.removeReview(rId);
		
		Map<String, Integer> data = new HashMap<>();
		data.put("challengeId", cId);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
}
