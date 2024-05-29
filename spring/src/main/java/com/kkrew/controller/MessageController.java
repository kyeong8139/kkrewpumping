package com.kkrew.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kkrew.model.dto.Message;
import com.kkrew.model.service.MessageService;
import com.kkrew.util.DateUtil;

@RestController
@RequestMapping("/message")
public class MessageController {
	
	MessageService mService;
	
	public MessageController(MessageService mService) {
		this.mService = mService;
	}

	@GetMapping("/{user_id}")
	public ResponseEntity<?> getMessage(@PathVariable("user_id") String id) {
		List<Message> messages = mService.getMessageById(id);
		
		return new ResponseEntity<>(messages, HttpStatus.OK);
	}
}
