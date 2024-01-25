package com.expro.photogram.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.expro.photogram.domain.user.User;
import com.expro.photogram.domain.user.UserRepository;
import com.expro.photogram.handler.ex.CustomApiException;
import com.expro.photogram.handler.ex.CustomException;
import com.expro.photogram.handler.ex.CustomValidationApiException;
import com.expro.photogram.web.dto.user.UserProfileDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Value("${file.path}")
	private String uploadFolder;
	
	@Transactional
	public User memberProfileModify(int principalId, MultipartFile profileImageFile) {
		UUID uuid = UUID.randomUUID();
		System.out.println(profileImageFile);
		String imageFileName = uuid + "-"+ profileImageFile.getOriginalFilename();
		
		Path imageFilePath = Paths.get(uploadFolder+imageFileName);
		
		try {
			Files.write(imageFilePath, profileImageFile.getBytes());
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		User userEntity = userRepository.findById(principalId).orElseThrow(()->{
			throw new CustomApiException("유저를 찾을 수 없습니다.");
		});
		
		userEntity.setProfileImageUrl(imageFileName);
		
		return userEntity;
	}
	
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
