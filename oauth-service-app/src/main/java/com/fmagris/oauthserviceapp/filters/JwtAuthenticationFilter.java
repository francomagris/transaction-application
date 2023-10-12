package com.fmagris.oauthserviceapp.filters;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fmagris.oauthserviceapp.models.AuthenticationCredentials;
import com.fmagris.oauthserviceapp.models.UserDetailsImpl;
import com.fmagris.oauthserviceapp.usecases.TokenUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;


public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, 
												HttpServletResponse response)throws AuthenticationException {
		System.out.println("JwtAuthenticationFilter -> attemptAuthentication");
		
		AuthenticationCredentials auth = new AuthenticationCredentials();
		try {
			auth = new ObjectMapper().readValue(request.getReader(), AuthenticationCredentials.class);
		} catch (IOException e) {
		}
		UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(
				auth.getUsername(), 
				auth.getPassword(), 
				Collections.emptyList());
		return getAuthenticationManager().authenticate(upat);
	}

	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, 
											HttpServletResponse response, 
											FilterChain chain,
											Authentication authResult) throws IOException, ServletException {
		System.out.println("JwtAuthenticationFilter -> successfulAuthentication");
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
		
		String token = TokenUtils.createToken(userDetails);
		
		response.setHeader("Authorization", "Bearer " + token);
		response.getWriter().flush();
		
		super.successfulAuthentication(request, response, chain, authResult);
	}
	
}
