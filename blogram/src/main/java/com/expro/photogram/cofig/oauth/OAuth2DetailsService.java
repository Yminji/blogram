package com.expro.photogram.cofig.oauth;

import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.expro.photogram.cofig.auth.PrincipalDetails;
import com.expro.photogram.domain.user.User;
import com.expro.photogram.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class OAuth2DetailsService extends DefaultOAuth2UserService{
	private final UserRepository userRepository;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
		OAuth2User oauth2User = super.loadUser(userRequest);
		log.info("getAttributes: {}", oauth2User.getAttributes());
		
		OAuth2UserInfo oAuth2UserInfo = null;
		
		String provider = userRequest.getClientRegistration().getRegistrationId();
		
		if(provider.equals("kakao")) {
			log.info("카카오 로그인 요청");
			oAuth2UserInfo = new KakaoUserInfo((Map)oauth2User.getAttributes());
		}else if(provider.equals("facebook")) {
			log.info("페이스북 로그인 요청");
			oAuth2UserInfo = new FacebookUserInfo(oauth2User.getAttributes());
		}else if(provider.equals("naver")) {
			log.info("네이버 로그인 요청");
			oAuth2UserInfo = new NaverUserInfo((Map)oauth2User.getAttributes().get("response"));
		}
		
		String providerId = oAuth2UserInfo.getProviderId();
		String password = new BCryptPasswordEncoder().encode(UUID.randomUUID().toString());
		String email = oAuth2UserInfo.getEmail();
		if(email == null) {
			email = " ";
		}
		String username = provider + "_" + providerId;
		String name = oAuth2UserInfo.getName();
		
		if(userRepository.existsByName(name)) {
			name = makeUniqueName(name);
		}
		
		User userEntity = userRepository.findByUsername(username);
		
		log.info(username);
		if(userEntity == null) { //최초 로그인
			User user = User.builder()
				.username(username)
				.password(password)
				.email(email)
				.name(name)
				.role("ROLE_USER")
				.build();
			
			return new PrincipalDetails(userRepository.save(user), oauth2User.getAttributes());
		}else {
			return new PrincipalDetails(userEntity, oauth2User.getAttributes());
		}

	}
	
	private String makeUniqueName(String name) {
		String uniqueName = name;
		Random random = new Random();
		
		String randomString = random.ints(48, 123)
				.filter(i -> (i < 58 || (i >= 65 && i < 91) || (i >= 97 && i < 123)))
				.limit(3)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
				.toString();
		
		uniqueName += "-" + randomString;
		
		if(userRepository.existsByName(uniqueName))
			return makeUniqueName(name);
		else
			return uniqueName;
	}
}
