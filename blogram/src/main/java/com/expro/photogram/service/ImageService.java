package com.expro.photogram.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.expro.photogram.cofig.auth.PrincipalDetails;
import com.expro.photogram.domain.image.Image;
import com.expro.photogram.domain.image.ImageRepository;
import com.expro.photogram.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class ImageService {
	private final ImageRepository imageRepository;
	
	@Value("${file.path}")
	private String uploadFolder;
	
	@Transactional
	public void imageUploadService(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {
		UUID uuid = UUID.randomUUID();
		String imageFileName = uuid + "-" + imageUploadDto.getFile().getOriginalFilename();
		
		Path imageFilePath = Paths.get(uploadFolder + imageFileName);
		
		try {
			Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		Image image = imageUploadDto.toEntity(imageFileName, principalDetails.getUser());
		imageRepository.save(image);
	}
	
	//삭제할 때 이미지 경로, 세션과 같은 유저
	@Transactional
	public void imageDelete(int imageId, int principalId) {
			
		Optional<Image> optionalImage = imageRepository.findById(imageId);
		
		if(optionalImage.isPresent()) {
			Image image = optionalImage.get();
			
			imageRepository.mDeleteImage(imageId, principalId);
			
			Path imageFilePath = Paths.get(uploadFolder + image.getPostImageUrl());
			
			try {
				Files.delete(imageFilePath);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else {
			log.error("이미지를 찾을 수 없습니다.");
		}
	}
	
	@Transactional(readOnly = true)
	public Page<Image> imageStory(int principalId, Pageable pageable){
		Page<Image> images = imageRepository.mStory(principalId, pageable);
		
		images.forEach((image)->{
			image.getLikes().forEach((like)->{
				image.setLikeCount(image.getLikes().size());
				
				if(like.getUser().getId() == principalId) {
					image.setLikeState(true);
				}
			});
		});
		
		return images;
	}
	
	@Transactional(readOnly = true)
	public List<Image> popularImage(){
		return imageRepository.mPopular();
	}
	
	
}
