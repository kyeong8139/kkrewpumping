package com.kkrew.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kkrew.model.dao.VideoDao;
import com.kkrew.model.dto.Video;

@Service
public class VideoServiceImpl implements VideoService {
	
	VideoDao vDao;
	
	public VideoServiceImpl(VideoDao vDao) {
		this.vDao = vDao;
	}

	@Override
	public List<Video> searchVideoList(String key, String word) {
		if (key == null) {
			key = "none";
		}
		
		if (word == null) {
			word = "";
		}
		
		List<Video> videos = vDao.searchVideo(key, word);
		if (videos != null) {
			for (Video video : videos) {
				video.setSupplies(getSupplies(video.getId()));
			}
		}
		
		return videos;
	}

	@Override
	public Video searchVideoById(int id) {
		String key = "id";
		String word = String.valueOf(id);
		
		List<Video> videos = this.searchVideoList(key, word);
		
		if (videos != null) {
			Video video = videos.get(0);
			video.setSupplies(getSupplies(video.getId()));
			
			return video;
		}
		
		return null;
	}
	
	public List<String> getSupplies(int id) {
		List<String> supplies = vDao.selectSupplies(id);
		return supplies;
	}

	@Override
	public int createVideo(Video video) {
		
		// 중복 체크
		String videoUrl = video.getVideoUrl();
		List<Video> conflictCheck = searchVideoList("video_url", videoUrl);
		if (conflictCheck != null && conflictCheck.size() > 0) {
			return 0;
		}
		
		// 데이터 추가
		int result = vDao.insertVideo(video);
		if (result == 1) {
			// 준비물 입력
			for (String supply : video.getSupplies()) {
				vDao.insertSupplies(video.getId(), supply);
			}			
		}
		
		return result;
	}

	@Override
	public int modifyVideo(Video video) {
		// 준비물 수정
		
		List<String> prev = vDao.selectSupplies(video.getId());
		
		for (String supply : video.getSupplies()) {
			if (!prev.contains(supply)) {
				vDao.insertSupplies(video.getId(), supply);				
			} else {
				prev.remove(supply);				
			}
		}
		
		for (String supply : prev) {
			vDao.deleteSupplies(video.getId(), supply);
		}
		
		return vDao.updateVideo(video);
	}

	@Override
	public int removeVideo(int id) {
		
		// 준비물 삭제
		List<String> prev = vDao.selectSupplies(id);
		
		for (String supply : prev) {
			vDao.deleteSupplies(id, supply);
		}
		
		return vDao.deleteVideo(id);
	}

	@Override
	public void updateViewCount(int id) {
		vDao.updataViewCount(id);
	}

	@Override
	public void modifyVideoWriter(Video video) {
		vDao.updateVideoWriter(video);
	}
}
