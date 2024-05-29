package com.kkrew.model.service;

import java.util.List;

import com.kkrew.model.dto.Video;

public interface VideoService {
	List<Video> searchVideoList(String key, String word);

	Video searchVideoById(int id);

	int createVideo(Video video);

	int modifyVideo(Video video);

	int removeVideo(int id);
	
	void updateViewCount(int id);

	void modifyVideoWriter(Video video);
}
