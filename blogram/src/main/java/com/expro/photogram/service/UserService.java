package com.expro.photogram.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.expro.photogram.domain.user.User;
import com.expro.photogram.domain.user.UserRepository;
import com.expro.photogram.handler.ex.CustomException;
import com.expro.photogram.handler.ex.CustomValidationApiException;
import com.expro.photogram.web.dto.user.UserProfileDto;
import com.expro.photogram.web.dto.user.UserUpdateDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Transactional(readOnly=true)
	public UserProfileDto memberProfile(int pageUserId, int principalId) {
		UserProfileDto dto = new UserProfileDto();
		
		User userEntity = userRepository.findById(pageUserId).orElseThrow(() -> {
			throw new CustomException("해당 프로필 페이지는 없는 페이지입니다.");
		});
		
		dto.setUser(userEntity);
		dto.setPageOwnerState(pageUserId == principalId);
		
		return dto;
	}
	
	@Transactional
	public User memberModify(int id, User user) {
		User userEntity = userRepository.findById(id).orElseThrow(()->{
			throw new CustomValidationApiException("찾을 수 없는 Id입니다.");
		});
		
		
		userEntity.setName(user.getName());
		
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		userEntity.setPassword(user.getPassword());
		
		userEntity.setBio(user.getBio());
		userEntity.setWebsite(user.getWebsite());
		userEntity.setPhone(user.getPhone());
		userEntity.setGender(user.getGender());
		
		if(userEntity.getPassword() == null) {
			return userEntity;
		}else {
			userEntity.setPassword(encPassword);
			return userEntity;
		}
	}
}
