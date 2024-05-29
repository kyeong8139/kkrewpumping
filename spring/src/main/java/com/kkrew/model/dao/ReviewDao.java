package com.kkrew.model.dao;

import java.util.List;

import com.kkrew.model.dto.Review;

public interface ReviewDao {
	
	List<Review> selectReview(int challengeId);
	
	int insertReview(Review review);
	
	int updateReview(Review review);
	
	int deleteReview(int reviewId);

	List<Review> searchReviewList(String key, String word);

	void upDateReviewWriter(Review review);
}
