package com.expro.photogram.web.dto.image;

import org.springframework.web.multipart.MultipartFile;

import com.expro.photogram.domain.image.Image;
import com.expro.photogram.domain.user.User;

import lombok.Data;

@Data
public class ImageUploadDto {
	private MultipartFile file;
	private String caption;
	
	public Image toEntity(String postImageUrl, User user) {
		return Image.builder()
				.caption(caption)
				.postImageUrl(postImageUrl)
				.user(user)
				.build();
	}
}
