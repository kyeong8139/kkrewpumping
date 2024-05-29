package com.kkrew.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class KaKaoUtil {
	
	private String kakaoApiKey = "카카오 API키";
	private String SERVICE_APP_ADMIN_KEY = "카카오 admin 키";
	
	public String getAccessToken(String code) {
		String accessToken = "";
		String requestUrl = "https://kauth.kakao.com/oauth/token";
	
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
	    params.add("client_id", kakaoApiKey);
	    params.add("code", code);
		
		WebClient webClient = WebClient.builder().build();
		
		String response = webClient.post()
			.uri(requestUrl)
			.header("Content-type", "application/x-www-form-urlencoded")
			.body(BodyInserters.fromFormData(params))
			.retrieve()
			.bodyToMono(String.class)
			.block();
		
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Map<String, String> jsonMap = objectMapper.readValue(response, new TypeReference<Map<String, String>>() {
			});
			
			accessToken = jsonMap.get("access_token");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return accessToken;
	}

	public Map<String, Object> getUserInfo(String accessToken) {
		String requestUrl = "https://kapi.kakao.com/v2/user/me";
		
		Map<String, Object> userInfo = new HashMap<>();
		
		WebClient webClient = WebClient.builder().build();
		
		String response = webClient.get()
				.uri(requestUrl)
				.header("Authorization", ("Bearer " + accessToken))
				.retrieve()
				.bodyToMono(String.class)
				.block();
						
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			userInfo = objectMapper.readValue(response, new TypeReference<Map<String, Object>>() {
			});
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return userInfo;
	}

	public void kakaoLogout(String id) {
		String requestUrl = "https://kapi.kakao.com/v1/user/logout";
		
		WebClient webClient = WebClient.builder().defaultHeader("Content-Type", "application/x-www-form-urlencoded").build();
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("target_id_type", "user_id");
	    params.add("target_id", id);
	    
		String response = webClient.post()
				.uri(requestUrl)
				.header("Authorization", ("KakaoAK " + SERVICE_APP_ADMIN_KEY))
				.body(BodyInserters.fromFormData(params))
				.retrieve()
				.bodyToMono(String.class)
				.block();
	}

	public void unlinkWithKakao(String id) {
		String requestUrl = "https://kapi.kakao.com/v1/user/unlink";
		
		WebClient webClient = WebClient.builder().build();
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("target_id_type", "user_id");
	    params.add("target_id", id);
	    
		String response = webClient.post()
				.uri(requestUrl)
				.header("Authorization", ("KakaoAK " + SERVICE_APP_ADMIN_KEY))
				.body(BodyInserters.fromFormData(params))
				.retrieve()
				.bodyToMono(String.class)
				.block();
	}
}
