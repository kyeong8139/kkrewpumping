package com.kkrew.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kkrew.model.service.ChallengeService;
import com.kkrew.model.service.ReviewService;
import com.kkrew.model.service.SearchService;
import com.kkrew.model.service.UserService;
import com.kkrew.model.service.VideoService;

@RestController
@RequestMapping("/search")
public class SearchController {
	
	SearchService searchService;
	
	public SearchController(SearchService searchService) {
		this.searchService = searchService;
	}

	@GetMapping("")
	public ResponseEntity<?> searchData(@RequestParam("key") String key, @RequestParam("word") String word) {
		
		List result = searchService.searchData(key, word);
		
		if (result == null || result.size() == 0) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/{user_id}")
	public ResponseEntity<?> searchMyData(@PathVariable("user_id") String id, @RequestParam("table") String table) {
		
		List result = searchService.searchMyData(id, table);
		
		if (result == null || result.size() == 0) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
}
