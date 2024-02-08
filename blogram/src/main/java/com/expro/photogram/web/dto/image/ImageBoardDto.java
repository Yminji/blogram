package com.expro.photogram.web.dto.image;

import com.expro.photogram.domain.image.Image;
import com.expro.photogram.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImageBoardDto {
	private User user;
	private Image image;
}
