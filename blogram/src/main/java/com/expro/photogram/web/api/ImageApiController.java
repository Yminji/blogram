package com.expro.photogram.web.api;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expro.photogram.cofig.auth.PrincipalDetails;
import com.expro.photogram.domain.image.Image;
import com.expro.photogram.service.ImageService;
import com.expro.photogram.service.LikesService;
import com.expro.photogram.web.dto.CMRespDto;
import com.expro.photogram.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ImageApiController {

	private final ImageService imageService;
	private final LikesService likesService;
	
	@GetMapping("/api/image")
	public ResponseEntity<?> imageStory(@AuthenticationPrincipal PrincipalDetails principalDetails, @PageableDefault(size = 3) Pageable pageable){
		Page<Image> images = imageService.imageStory(principalDetails.getUser().getId(), pageable);
		
		return new ResponseEntity<>(new CMRespDto<>(1, "성공", images), HttpStatus.OK);
	}
	
	@DeleteMapping("/api/image/{imageId}")
	public ResponseEntity<?> deleteImage(@PathVariable int imageId, @AuthenticationPrincipal PrincipalDetails principalDetails){
		imageService.imageDelete(imageId, principalDetails.getUser().getId());
		
		return new ResponseEntity<>(new CMRespDto<>(1, "이미지 삭제 성공", null), HttpStatus.OK);
	}
	
	@PostMapping("/api/image/{imageId}/likes")
	public ResponseEntity<?> likes(@PathVariable int imageId, @AuthenticationPrincipal PrincipalDetails principalDetails){
		likesService.sLikes(imageId, principalDetails.getUser().getId());
		return new ResponseEntity<>(new CMRespDto<>(1, "좋아요 성공", null), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/api/image/{imageId}/likes")
	public ResponseEntity<?> unlikes(@PathVariable int imageId, @AuthenticationPrincipal PrincipalDetails principalDetails){
		likesService.sUnLikes(imageId, principalDetails.getUser().getId());
		return new ResponseEntity<>(new CMRespDto<>(1, "좋아요 취소 성공", null), HttpStatus.OK);
	}	
	
	@PutMapping("/api/image/board/{userId}/{imageId}")
	public ResponseEntity<?> ImageUpdate(@PathVariable int imageId, @PathVariable int userId, @Valid ImageUploadDto imageUploadDto, BindingResult bindingResult,
			@AuthenticationPrincipal PrincipalDetails principalDetails){
		if(principalDetails.getUser().getId() != userId) {
		Image imageEntity = imageService.mImageModify(imageId, imageUploadDto, principalDetails);
		return new ResponseEntity<>(new CMRespDto<>(1, "이미지 수정 성공", imageEntity), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(new CMRespDto<>(0, "해당 유저가 아님", null), HttpStatus.BAD_GATEWAY);
		}
	}
}
