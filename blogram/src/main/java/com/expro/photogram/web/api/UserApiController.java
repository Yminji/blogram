package com.expro.photogram.web.api;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.expro.photogram.cofig.auth.PrincipalDetails;
import com.expro.photogram.domain.user.User;
import com.expro.photogram.service.UserService;
import com.expro.photogram.web.dto.CMRespDto;
import com.expro.photogram.web.dto.user.UserUpdateDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserApiController {
	
	private final UserService userService;
	
	@PutMapping("/api/user/{id}")
	public CMRespDto<?> update(@PathVariable int id, @Valid UserUpdateDto userUpdateDto,
			BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetails principalDetails){
		User userEntity = userService.memberModify(id, userUpdateDto.toEntity());
		principalDetails.setUser(userEntity);
		return new CMRespDto<>(1, "회원수정완료", userEntity);
	}
	
	@PutMapping("/api/user/{principalId}/profileImageUrl")
	public ResponseEntity<?> profileImageUrlUpdate(@PathVariable int principalId, MultipartFile profileImageFile,
			@AuthenticationPrincipal PrincipalDetails principalDetails){
		User userEntity = userService.memberProfileModify(principalId, profileImageFile);
		principalDetails.setUser(userEntity);
		return new ResponseEntity<>(new CMRespDto<>(1, "프로필사진변경 선공", null), HttpStatus.OK);
	}
}