package com.expro.photogram.cofig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.expro.photogram.cofig.oauth.OAuth2DetailsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig{
	
	private final OAuth2DetailsService oauth2DetailsService;
	
	@Bean
	BCryptPasswordEncoder encode() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	SecurityFilterChain configure(HttpSecurity http) throws Exception{
		http.csrf().disable(); 
		
		http.authorizeRequests()
			.antMatchers("/", "/user/**", "/image/**", "/subscribe/**", "/comment/**","/api/**").authenticated()
			.anyRequest().permitAll()	
			.and()
			.formLogin() 
			.loginPage("/auth/signin") 
			.loginProcessingUrl("/auth/signin") 
			.defaultSuccessUrl("/")
			.and()
			.oauth2Login()
			.userInfoEndpoint()
			.userService(oauth2DetailsService);
			
					
		return http.build();
	}
}
