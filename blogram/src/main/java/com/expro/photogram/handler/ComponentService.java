package com.expro.photogram.handler;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ComponentService {
	
	@Value("${oauth2.client.registration.facebook.client-secret}")
	private String facebookClientSecret;
	
	@Value("${oauth2.client.registration.kakao.client-secret")
	private String kakaoClientSecret;
	
	@Value("${oauth2.client.registration.naver.client-secret")
	private String naverClientSecret;
}
