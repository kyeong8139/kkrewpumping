package com.kkrew.controller;

import java.util.Collections;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kkrew.model.dto.Video;
import com.kkrew.model.service.ChallengeService;
import com.kkrew.model.service.VideoService;

@RestController
@RequestMapping("/video")
public class VideoController {
	
	VideoService vService;
	ChallengeService cService;
	
	public VideoController(VideoService vService, ChallengeService cService) {
		this.vService = vService;
		this.cService = cService;
	}

	@GetMapping("")
	public ResponseEntity<?> searchVideo(@RequestParam("key") String key, @RequestParam("word") String word) {
		List<Video> videos = vService.searchVideoList(key, word);
		
		if (videos == null || videos.size() == 0) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		Collections.shuffle(videos);
		
		return new ResponseEntity<List<Video>>(videos, HttpStatus.OK);
	}
	
	@GetMapping("/{video_id}")
	public ResponseEntity<?> getVideo(@PathVariable("video_id") int id) {
		Video video = vService.searchVideoById(id);
		
		if (video == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		vService.updateViewCount(id);
		return new ResponseEntity<>(video, HttpStatus.OK);
	}
	
	@PostMapping("")
	public ResponseEntity<?> createVideo(@RequestBody Video video) {
		System.out.println("createVideo");
		
		// 썸네일 정보 추가
		int index = video.getVideoUrl().indexOf("=");
		String distinctId = video.getVideoUrl().substring(index+1);		
		
		video.setImageUrl("https://img.youtube.com/vi/" + distinctId + "/0.jpg");
		
		System.out.println(video);
		
		// 비디오 등록
		int result = vService.createVideo(video);
		
		if (result == 1) {
			Map<String, Integer> data = new HashMap<>();
			data.put("videoId", video.getId());			
			
			return new ResponseEntity<>(data, HttpStatus.CREATED);
		}
		
		return new ResponseEntity<>(HttpStatus.CONFLICT);
	}
	
	@PutMapping("/{video_id}")
	public ResponseEntity<?> modifyVideo(@PathVariable("video_id") int id, @RequestBody Video video) {
		
		System.out.println(video);
		
		video.setId(id);
		int result = vService.modifyVideo(video);

		Map<String, Integer> data = new HashMap<>();
		data.put("videoId", id);
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@DeleteMapping("/{video_id}")
	public ResponseEntity<?> removeVideo(@PathVariable("video_id") int id) {
		
		boolean hasChallenge = cService.hasChallenge(id);
		if (hasChallenge) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		
		int result = vService.removeVideo(id);
		
		Map<String, Integer> data = new HashMap<>();
		data.put("videoId", id);
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
}
