package com.expro.photogram.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.expro.photogram.domain.subscribe.SubscribeRepository;
import com.expro.photogram.handler.ex.CustomApiException;
import com.expro.photogram.web.dto.subscribe.SubscribeDto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class SubscribeService {
	
	private final SubscribeRepository subscribeRepository;
	private final EntityManager em;
	
	@Transactional
	public void memSubscribe(int fromUserId, int toUserId) {
		try {
			subscribeRepository.mSubscribe(fromUserId, toUserId);
		}catch(Exception e) {
			throw new CustomApiException("이미 구독을 하셨습니다.");
		}
	}
	
	@Transactional
	public void memUnSubscribe(int fromUserId, int toUserId) {
		subscribeRepository.mUnSubscribe(fromUserId, toUserId);
	}
	
	@Transactional(readOnly = true)
	public List<SubscribeDto> memSubscribeList(int principalId, int pageUserId) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT u.id, u.username, u.profileImageUrl, ");
		sb.append("if((SELECT 1 FROM subscribe WHERE fromUserId = ? AND toUserId = u.id), 1, 0) subscribeState, ");
		sb.append("if((?=u.id), 1, 0) equalUserState ");
		sb.append("FROM user u INNER JOIN subscribe s ");
		sb.append("ON u.id = s.toUserId ");
		sb.append("WHERE s.fromUserId = ?");
		
		Query query = em.createNativeQuery(sb.toString())
				.setParameter(1, principalId)
				.setParameter(2, principalId)
				.setParameter(3, pageUserId);
		
		JpaResultMapper result = new JpaResultMapper();
		List<SubscribeDto> subscribeDtos = result.list(query, SubscribeDto.class);
		
		return subscribeDtos;
	}
}
