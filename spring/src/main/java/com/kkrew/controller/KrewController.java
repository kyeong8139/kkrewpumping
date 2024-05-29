package com.kkrew.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kkrew.model.dto.Challenge;
import com.kkrew.model.dto.Krew;
import com.kkrew.model.dto.KrewUser;
import com.kkrew.model.dto.Message;
import com.kkrew.model.dto.User;
import com.kkrew.model.dto.UserChallenge;
import com.kkrew.model.service.ChallengeService;
import com.kkrew.model.service.KrewService;
import com.kkrew.model.service.MessageService;
import com.kkrew.model.service.UserChallengeService;
import com.kkrew.util.DateUtil;

@RestController
@RequestMapping("/krew")
public class KrewController {
	
	KrewService kService;
	ChallengeService cService;
	UserChallengeService ucService;
	MessageService mService;
	
	DateUtil dateUtil;

	public KrewController(KrewService kService, ChallengeService cService, UserChallengeService ucService,
			MessageService mService, DateUtil dateUtil) {
		this.kService = kService;
		this.cService = cService;
		this.ucService = ucService;
		this.mService = mService;
		this.dateUtil = dateUtil;
	}

	@GetMapping("/{challenge_id}")
	public ResponseEntity<?> getChallengeKrew(@PathVariable("challenge_id") int challengeId) {
		List<Krew> krews = kService.getChallengeKrew(challengeId);
		
		if (krews == null || krews.size() == 0) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<>(krews, HttpStatus.OK);
	}
	
	@GetMapping("/krew/{krew_id}")
	public ResponseEntity<?> getKrewUser(@PathVariable("krew_id") int id) {
		Krew krew = kService.getKrewInfo(id);
		krew.setUsers(kService.getKrewUsers(id));
		
		return new ResponseEntity<>(krew, HttpStatus.OK);
	}
	
	@PostMapping("/{challenge_id}")
	public ResponseEntity<?> createKrew(@PathVariable("challenge_id") int challengeId, @RequestBody Krew krew) {
		int period = cService.getChallengeInfo(challengeId).getPeriod();
		krew.setChallengeId(challengeId);
		krew.setEndDate(dateUtil.getEndDate(period));
		
		kService.createKrew(krew);
	
		Map<String, String> data = new HashMap<>();
		data.put("krewId", String.valueOf(krew.getId()));
		
		return new ResponseEntity<>(data, HttpStatus.CREATED);
	}

	
	@PutMapping("/{krew_id}")
	public ResponseEntity<?> addKrewUser(@PathVariable("krew_id") int krewId, @RequestBody User user) {
		// 챌린지에 등록
		UserChallenge userChallenge = new UserChallenge();
		Krew krew = kService.getKrewInfo(krewId);
		Challenge challenge = cService.getChallengeInfo(krew.getChallengeId());		
		
		// 아이디 정보 입력
		userChallenge.setCrewId(krewId);
		userChallenge.setUserId(user.getId());
		userChallenge.setChallengeId(challenge.getId());		

		// 시작 날짜 및 종료 날짜 연산
		userChallenge.setStartDate(dateUtil.getStartDate());
		userChallenge.setEndDate(krew.getEndDate());
		
		// 비디오 갯수 입력
		userChallenge.setVideoCount(challenge.getVideoCount());
		
		ucService.registChallenge(userChallenge);
		
		// 크루유저로서 등록
		
		KrewUser krewUser = new KrewUser();
		krewUser.setKrewId(krewId);
		krewUser.setUserId(user.getId());
		krewUser.setUserChallengeId(userChallenge.getId());
		int result = kService.addKrewUser(krewUser);
		
		Message message = new Message();
		message.setToUser(user.getId());
		message.setContent("'" +krew.getName() + "' 크루와 함께 끝까지 달려봐요!");
		mService.writeMessage(message);
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}
