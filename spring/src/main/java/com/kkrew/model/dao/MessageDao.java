package com.kkrew.model.dao;

import java.util.List;

import com.kkrew.model.dto.Message;

public interface MessageDao {

	List<Message> selectMessageById(String id);

	int insertMessage(Message message);

}
