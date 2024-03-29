package com.expro.photogram.web;

import java.util.List;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.expro.photogram.cofig.auth.PrincipalDetails;
import com.expro.photogram.domain.image.Image;
import com.expro.photogram.handler.ex.CustomValidationException;
import com.expro.photogram.service.ImageService;
import com.expro.photogram.util.Script;
import com.expro.photogram.web.dto.image.ImageBoardDto;
import com.expro.photogram.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ImageController {
	
	private final ImageService imageService;
	
	@GetMapping({"/", "/image/story"})
	public String story() {
		return "image/story";
	}
	
	@GetMapping({"/image/upload"})
	public String upload() {
		return "image/upload";
	}
	
	@PostMapping("/image")
	public String imageUpload(ImageUploadDto imageUploadDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		if(imageUploadDto.getFile().isEmpty()) {
			throw new CustomValidationException("이미지가 첨부되지 않았습니다.", null);
		}
		
		imageService.imageUploadService(imageUploadDto, principalDetails);
		
		return "redirect:/user/"+principalDetails.getUser().getId();
	}
	
	@GetMapping("/image/popular")
	public String popular(Model model) {
		List<Image> images = imageService.popularImage();
		
		model.addAttribute("images", images);
		
		return "image/popular";
	}
	
	@GetMapping("/image/board/{userId}/{imageId}")
	public String board(@PathVariable int imageId, @PathVariable int userId, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		if(principalDetails.getUser().getId() == userId) {
			ImageBoardDto dto = imageService.mImageBoard(principalDetails.getUser().getId(), imageId);
			model.addAttribute("dto", dto);
			return "image/board";
		}else {
			
			return Script.back("이용할 수 없는 유저입니다.");
		}
		
	}
}
