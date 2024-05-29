package com.kkrew.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.kkrew.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {
	
	JwtUtil jwtUtil;
	
	public LoginInterceptor(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		if (!request.getMethod().equalsIgnoreCase("POST")) {
            return true;
        } 
		
		String accessToken = request.getHeader("access-token");
		System.out.println(accessToken);
		if (accessToken != null) {
			try {
				jwtUtil.validate(accessToken);				
			} catch (Exception e) {
				 response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않은 액세스 토큰");
				 return false;
			}
			return true;
		}
		
		return false;
	}
}
