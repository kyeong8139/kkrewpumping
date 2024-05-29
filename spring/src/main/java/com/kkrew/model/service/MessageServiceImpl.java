package com.kkrew.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kkrew.model.dao.MessageDao;
import com.kkrew.model.dto.Message;

@Service
public class MessageServiceImpl implements MessageService {

	MessageDao mDao;
	public MessageServiceImpl(MessageDao mDao) {
		this.mDao = mDao;
	}

	@Override
	public List<Message> getMessageById(String id) {
		return mDao.selectMessageById(id);
	}
	
	@Override
	public int writeMessage(Message message) {
		return mDao.insertMessage(message);
	}

}
