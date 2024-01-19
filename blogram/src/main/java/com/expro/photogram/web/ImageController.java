package com.expro.photogram.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.expro.photogram.service.ImageService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ImageController {
	
//	private final ImageService imageSerive;
	
	@GetMapping({"/", "/image/story"})
	public String story() {
		return "image/story";
	}
	
	
}
