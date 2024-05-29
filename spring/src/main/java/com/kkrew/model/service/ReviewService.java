package com.kkrew.model.service;

import java.util.List;

import com.kkrew.model.dto.Review;

public interface ReviewService {

	List<Review> getChallengeReview(int challengeId);
	
	int writeReview(Review review);
	
	int modifyReview(Review review);
	
	int removeReview(int reviewId);

	List<Review> searchReviewList(String string, String id);

	void modifyReviewWriter(Review review);
	
}
