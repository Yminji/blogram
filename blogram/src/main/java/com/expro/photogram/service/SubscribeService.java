package com.expro.photogram.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.expro.photogram.domain.subscribe.SubscribeRepository;
import com.expro.photogram.handler.ex.CustomApiException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class SubscribeService {
	
	private SubscribeRepository subscribeRepository;
	
	@Transactional
	public void subscribeService(int fromUserId, int toUserId) {
		try {
			subscribeRepository.mSubscribe(fromUserId, toUserId);
		}catch(Exception e) {
			throw new CustomApiException("이미 구독을 하셨습니다.");
		}
	}
	
	@Transactional
	public void unSubscribeService(int fromUserId, int toUserId) {
		subscribeRepository.mUnSubscribe(fromUserId, toUserId);
	}
}
