package com.expro.photogram.handler;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ComponentService {
	
	@Value("${spring.security.oauth2.client.registration.facebook.client-secret}")
	private String facebookSecretKey;
	
	@Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
	private String kakaoSecretKey;
	
	@Value("${spring.security.oauth2.client.registration.naver.client-secret}")
	private String naverSecretKey;
}
