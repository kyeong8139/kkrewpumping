package com.kkrew.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kkrew.model.dto.Challenge;
import com.kkrew.model.dto.Message;
import com.kkrew.model.dto.UserChallenge;
import com.kkrew.model.dto.Video;
import com.kkrew.model.service.ChallengeService;
import com.kkrew.model.service.MessageService;
import com.kkrew.model.service.UserChallengeService;
import com.kkrew.model.service.UserService;
import com.kkrew.util.DateUtil;

@RestController
@RequestMapping("/userchallenge")
public class UserChallengeController {

	UserChallengeService ucService;
	UserService uService;
	ChallengeService cService;
	MessageService mService;

	DateUtil dateUtil;

	public UserChallengeController(UserChallengeService ucService, UserService uService, ChallengeService cService,
			MessageService mService, DateUtil dateUtil) {
		this.ucService = ucService;
		this.uService = uService;
		this.cService = cService;
		this.mService = mService;
		this.dateUtil = dateUtil;
	}
	
	@GetMapping("/{user_id}/{user_challenge_id}")
	public ResponseEntity<?> getUserChallengeInfo(@PathVariable("user_id") String id,
			@PathVariable("user_challenge_id") int userChallengeId) {
		UserChallenge userChallenge = ucService.getUserChallenge(userChallengeId);
		
		// 해당하는 유저 챌린지가 없을 경우 잘못된 경로로 접근함
		if (userChallenge == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(userChallenge, HttpStatus.OK);
	}


	@GetMapping("/{user_id}/current")
	public ResponseEntity<?> getCurrentUserChallenge(@PathVariable("user_id") String id) {
		List<UserChallenge> userChallenges = ucService.getUserChallengeList(id, 0);

		if (userChallenges == null || userChallenges.size() == 0) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		for (UserChallenge userChallenge : userChallenges) {
			userChallenge.setChallenge(cService.getChallengeInfo(userChallenge.getChallengeId()));

			int completion = Integer.bitCount(userChallenge.getCurrentCompletion());
			userChallenge.setCurrentCompletion(completion);
		}

		return new ResponseEntity<>(userChallenges, HttpStatus.OK);
	}

	@GetMapping("/{user_id}/finish")
	public ResponseEntity<?> getFinishedUserChallenge(@PathVariable("user_id") String id) {
		List<UserChallenge> userChallenges = ucService.getUserChallengeList(id, 1);

		if (userChallenges == null || userChallenges.size() == 0) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		for (UserChallenge userChallenge : userChallenges) {
			userChallenge.setChallenge(cService.getChallengeInfo(userChallenge.getChallengeId()));
		}
		return new ResponseEntity<>(userChallenges, HttpStatus.OK);
	}

	// 혼자 챌린지 도전하기
	@PostMapping("/{user_id}/{challenge_id}")
	public ResponseEntity<?> registChallenge(@PathVariable("user_id") String id,
			@PathVariable("challenge_id") int challengeId) {
		UserChallenge userChallenge = new UserChallenge();
		Challenge challenge = cService.getChallengeInfo(challengeId);

		// 아이디 정보 입력
		userChallenge.setUserId(id);
		userChallenge.setChallengeId(challengeId);

		// 시작 날짜 및 종료 날짜 연산
		userChallenge.setStartDate(dateUtil.getStartDate());
		userChallenge.setEndDate(dateUtil.getEndDate(challenge.getPeriod()));

		// 비디오 갯수 입력
		userChallenge.setVideoCount(challenge.getVideoCount());

		int result = ucService.registChallenge(userChallenge);

		// 메세지 전송
		Message message = new Message();
		message.setToUser(id);
		message.setContent("'" + challenge.getTitle() + "'챌린지에 등록하셨어요! 꾸준히 운동해봐요!");
		mService.writeMessage(message);

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PutMapping("/{user_id}/{user_challenge_id}/{order}")
	public ResponseEntity<?> updateCurrentCompletion(@PathVariable("user_id") String id,
			@PathVariable("user_challenge_id") int userChallengeId, @PathVariable("order") int order) {
		UserChallenge userChallenge = ucService.getUserChallenge(userChallengeId);

		int completion = userChallenge.getCurrentCompletion();
		completion |= (1 << order);

		userChallenge.setCurrentCompletion(completion);

		int result = ucService.updateCurrentCompletion(userChallenge);
		uService.updateUserExp(id, 5);
		Message message = new Message();
		message.setToUser(id);
		message.setContent("오늘의 운동을 완료하셨어요. 보상으로 5exp를 드릴게요.");
		mService.writeMessage(message);

		int target = (1 << userChallenge.getVideoCount()) - 1;

		if (userChallenge.getCurrentCompletion() == target) {
			ucService.updateComplete(userChallenge);
			uService.updateUserExp(id, 10);

			message = new Message();
			String title = cService.getChallengeInfo(userChallenge.getChallengeId()).getTitle();
			message.setToUser(id);
			message.setContent("축하합니다! '" + title + "' 챌린지를 완료하셨어요. 보상으로 10exp를 드릴게요.");

			mService.writeMessage(message);
		}

		return new ResponseEntity<>(userChallenge, HttpStatus.OK);
	}
}
