package com.kkrew.model.service;

import java.util.List;

import com.kkrew.model.dto.Message;

public interface MessageService {

	List<Message> getMessageById(String id);

	int writeMessage(Message message);

}
