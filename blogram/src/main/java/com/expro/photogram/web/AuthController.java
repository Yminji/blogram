package com.expro.photogram.web;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.expro.photogram.domain.user.User;
import com.expro.photogram.service.AuthService;
import com.expro.photogram.web.dto.auth.SignupDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@Slf4j
public class AuthController {

	private static final Logger log = LoggerFactory.getLogger(AuthController.class);
	
	private final AuthService authService;
	
	@GetMapping("auth/signin")
	public String signinForm() {
		return "auth/signin";
	}
	
	@GetMapping("auth/signup")
	public String signupForm() {
		return "auth/signup";
	}
	
	
	@PostMapping("auth/signup")
	public String signup(@Valid SignupDto signupDto, BindingResult bindingResult) {
			
		User user = signupDto.toEntity();
		authService.joinMembers(user);
		log.info(user.toString());
		
		return "auth/signin";
	}
}
