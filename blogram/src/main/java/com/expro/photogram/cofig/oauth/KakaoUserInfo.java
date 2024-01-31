package com.expro.photogram.cofig.oauth;

import java.util.Map;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class KakaoUserInfo implements OAuth2UserInfo{
	private Map<String, Object> attributes;

	@Override
	public String getProviderId() {
		return attributes.get("id").toString();
	}

	@Override
	public String getProvider() {
		return "kakao";
	}

	@Override
	public String getEmail() {
		return null;
	}

	@Override
	public String getName() {
		//kakao_account라는 Map에서 추출
		return (String)((Map)attributes.get("properties")).get("nickname");
	}
}
