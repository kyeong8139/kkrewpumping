package com.kkrew.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kkrew.model.dto.Challenge;
import com.kkrew.model.dto.Message;
import com.kkrew.model.dto.Review;
import com.kkrew.model.dto.SocialRequest;
import com.kkrew.model.dto.User;
import com.kkrew.model.dto.Video;
import com.kkrew.model.service.ChallengeService;
import com.kkrew.model.service.MessageService;
import com.kkrew.model.service.ReviewService;
import com.kkrew.model.service.SearchService;
import com.kkrew.model.service.UserService;
import com.kkrew.model.service.VideoService;
import com.kkrew.util.EncryptionUtil;
import com.kkrew.util.JwtUtil;
import com.kkrew.util.KaKaoUtil;

@RestController
@RequestMapping("/user")
public class UserController {
	
	UserService uService;
	ChallengeService cService;
	MessageService mService;
	SearchService searchService;
	VideoService vService;
	ReviewService rService;
	
	JwtUtil jwtUtil;
	EncryptionUtil encryptionUtil;
	KaKaoUtil kakaoUtil;
	
	public UserController(UserService uService, ChallengeService cService, MessageService mService,
			SearchService searchService, VideoService vService, ReviewService rService, JwtUtil jwtUtil,
			EncryptionUtil encryptionUtil, KaKaoUtil kakaoUtil) {
		this.uService = uService;
		this.cService = cService;
		this.mService = mService;
		this.searchService = searchService;
		this.vService = vService;
		this.rService = rService;
		this.jwtUtil = jwtUtil;
		this.encryptionUtil = encryptionUtil;
		this.kakaoUtil = kakaoUtil;
	}

	@GetMapping("/check")
	public ResponseEntity<?> checkDuplicate(@RequestParam String key, @RequestParam String word) {
		boolean result = uService.hasDuplicate(key, word);
		
		if (!result) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.CONFLICT);
	}
	
	@PostMapping("/regist")
	public ResponseEntity<?> regist(@RequestBody User user) {
		
		String securePW = encryptionUtil.getSecureCode(user.getPassword());
		user.setPassword(securePW);
		
		int result = uService.registUser(user);
		
		Message message = new Message();
		message.setFromUser("kkrewpumping");
		message.setContent("가입을 축하드립니다!");
		message.setToUser(user.getId());
		mService.writeMessage(message);
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user) {

		String securePW = encryptionUtil.getSecureCode(user.getPassword());
		user.setPassword(securePW);
		
		// 카카오 유저일 경우 이 방법으로는 로그인 되지 않음
		if (uService.getUserInfo(user.getId()).getIsKakaoUser() == 1) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		// 비밀번호 체크
		boolean result = uService.checkPassword(user);
		if (!result) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		// 비밀번호 일치시 토큰을 포함한 정보 반환		
		Map<String, String> data = new HashMap<>();
		data.put("id", user.getId());
		data.put("nickname", uService.getNickname(user.getId()));
		data.put("access-token", jwtUtil.createToken(user.getId(), uService.getNickname(user.getId())));
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@GetMapping("/logout")
	public ResponseEntity<?> doLogout(@RequestParam("id") String id) {
		User user = uService.getUserInfo(id);
		if (user == null || user.getIsKakaoUser() == 0) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
		kakaoUtil.kakaoLogout(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/{user_id}")
	public ResponseEntity<?> getUserInfo(@PathVariable("user_id") String id) {
		User user = uService.getUserInfo(id);
		user.setPassword("");
		
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@PostMapping("/{user_id}")
	public ResponseEntity<Void> checkPassword(@RequestBody User user) {
		user.setPassword(encryptionUtil.getSecureCode(user.getPassword()));
		
		boolean result = uService.checkPassword(user);
		
		if (!result) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping("/{user_id}")
	public ResponseEntity<Void> modifyUserInfo(@RequestBody User user) {
		
		System.out.println(user.toString());
		
		User origin = uService.getUserInfo(user.getId());		
		String securePW = encryptionUtil.getSecureCode(user.getPassword());
		origin.setPassword(securePW);
		origin.setIntroduction(user.getIntroduction());
		
		int result = uService.modifyUserInfo(origin);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping("/setPassword")
	public ResponseEntity<?> setPassword(@RequestBody User user) {
		System.out.println(user.toString());
		String securePW = encryptionUtil.getSecureCode(user.getPassword());
		user.setPassword(securePW);
		
		uService.updatePassword(user);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/{user_id}")
	public ResponseEntity<Void> removeUser(@PathVariable("user_id") String id) {
		
		User user = uService.getUserInfo(id);
		
		// 등록한 비디오, 챌린지, 리뷰에 대해서 작성자를 더미데이터로 변경
		List videos = searchService.searchMyData(id, "video");
		for (Object o : videos) {
			Video video = (Video) o;
			video.setWriter("(알 수 없음)");
			vService.modifyVideoWriter(video);
		}
		
		List challenges = searchService.searchMyData(id, "challenge");
		for (Object o : challenges) {
			Challenge challenge = (Challenge) o;
			challenge.setWriter("(알 수 없음)");
			cService.updateChallengeWriter(challenge);
		}
		
		List reviews = searchService.searchMyData(id, "review");
		for (Object o : reviews) {
			Review review = (Review) o;
			review.setWriter("(알 수 없음)");
			rService.modifyReviewWriter(review);
		}
		
		if (user.getIsKakaoUser() == 1) {
			kakaoUtil.unlinkWithKakao(user.getId());
		}
		
		
		int result = uService.removeUserInfo(id);			
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/sendemail") 
    public ResponseEntity<?> sendMessage(@RequestParam("email") String email) {
		System.out.println(email);
        if (uService.hasDuplicate("id", email) == false) {
        	return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
		
		String code = uService.sendCodeToEmail(email);

        Map<String, String> data = new HashMap<>();
        data.put("code", code);
        
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
	
	@PostMapping("/login/kakao")
	public ResponseEntity<?> doKakaoLogin(@RequestBody SocialRequest request) {
		String accessToken = kakaoUtil.getAccessToken(request.getCode());
		Map<String, Object> userInfo = kakaoUtil.getUserInfo(accessToken);
		Map<String, Object> kakaoAccount = (Map<String, Object>) userInfo.get("kakao_account");
		Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
		
		// 회원가입 또는 로그인
		User user = new User();
		user.setId(Long.toString((long) userInfo.get("id")));
		user.setNickname((String) profile.get("nickname"));		
		user.setPassword(String.valueOf(UUID.randomUUID()));
		user.setIsKakaoUser(1);
		
		boolean result = uService.hasDuplicate("id", user.getId());
		if (!result) {
			uService.registUser(user);
			
			Message message = new Message();
			message.setFromUser("kkrewpumping");
			message.setContent("가입을 축하드립니다!");
			message.setToUser(user.getId());
			mService.writeMessage(message);
		}
		
		Map<String, String> data = new HashMap<>();
		
		data.put("id", user.getId());
		data.put("nickname", uService.getNickname(user.getId()));
		data.put("access-token", jwtUtil.createToken(user.getId(), user.getNickname()));
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
}
