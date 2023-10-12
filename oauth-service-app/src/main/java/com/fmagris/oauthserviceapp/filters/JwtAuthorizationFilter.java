package com.fmagris.oauthserviceapp.filters;

import java.io.IOException;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fmagris.oauthserviceapp.usecases.TokenUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		System.out.println("JwtAuthorizationFilter -> doFilterInternal");
		
		String bearerToken = request.getHeader("Authorization");
			
			try {
				if(bearerToken != null && bearerToken.contains("Bearer ")) {
					String token = bearerToken.replace("Bearer ", "");
					UsernamePasswordAuthenticationToken upat = TokenUtils.decodeToken(token);
					SecurityContextHolder.getContext().setAuthentication(upat);
				}
				filterChain.doFilter(request, response);
			}catch(Exception e) {
				String errorMessage = "Authenticatin error: " + e.getMessage();
				response.setStatus(HttpStatus.SC_UNAUTHORIZED);
				response.getWriter().write("{\"error\": \"" + errorMessage + "\"}");
				response.setContentType("application/json");
			}
		
	}

}
