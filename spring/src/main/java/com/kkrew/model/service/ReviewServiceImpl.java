package com.kkrew.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kkrew.model.dao.ReviewDao;
import com.kkrew.model.dto.Review;

@Service
public class ReviewServiceImpl implements ReviewService{

	private ReviewDao rDao;
	
	public ReviewServiceImpl(ReviewDao rDao) {
		this.rDao = rDao;
	}

	@Override
	public List<Review> getChallengeReview(int challengeId) {
		return rDao.selectReview(challengeId);
	}

	@Override
	public int writeReview(Review review) {
		System.out.println(review.toString());
		return rDao.insertReview(review);
	}

	@Override
	public int modifyReview(Review review) {
		return rDao.updateReview(review);
	}

	@Override
	public int removeReview(int reviewId) {
		return rDao.deleteReview(reviewId);
	}

	@Override
	public List<Review> searchReviewList(String key, String word) {
		return rDao.searchReviewList(key, word);
	}

	@Override
	public void modifyReviewWriter(Review review) {
		rDao.upDateReviewWriter(review);
	}

}
