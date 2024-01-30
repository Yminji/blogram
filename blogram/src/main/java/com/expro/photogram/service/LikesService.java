package com.expro.photogram.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.expro.photogram.domain.likes.LikesRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LikesService {
	private final LikesRepository likesRepository;
	
	@Transactional
	public void sLikes(int imageId, int principalId) {
		likesRepository.mLikes(imageId, principalId);
	}
	
	@Transactional
	public void sUnLikes(int imageId,  int principalId) {
		likesRepository.mUnLikes(imageId, principalId);
	}
}
