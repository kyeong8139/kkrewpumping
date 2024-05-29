package com.kkrew.model.dao;

import java.util.List;

import com.kkrew.model.dto.Video;

public interface VideoDao {
	
	// 비디오 테이블
	List<Video> searchVideo(String key, String word);
	
	int insertVideo(Video video);

	int updateVideo(Video video);

	int deleteVideo(int videoId);
	
	void updataViewCount(int videoId);
	
	// 비디오 준비물 테이블
	
	void insertSupplies(int videoId, String supply);
	
	List<String> selectSupplies(int videoId);
	
	void deleteSupplies(int videoId, String supply);

	void updateVideoWriter(Video video);
	
}
