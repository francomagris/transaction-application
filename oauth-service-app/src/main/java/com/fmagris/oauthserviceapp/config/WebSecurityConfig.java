package com.fmagris.oauthserviceapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fmagris.oauthserviceapp.filters.JwtAuthenticationFilter;
import com.fmagris.oauthserviceapp.filters.JwtAuthorizationFilter;


@Configuration
public class WebSecurityConfig {
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	private JwtAuthorizationFilter authorizationFilter;
	
	
	@Bean
	public SecurityFilterChain filterchain(HttpSecurity http, AuthenticationManager authManager) throws Exception {
		
		JwtAuthenticationFilter authenticationFilter = new JwtAuthenticationFilter();
		authenticationFilter.setAuthenticationManager(authManager);
		authenticationFilter.setFilterProcessesUrl("/login");
		
		return http
				.csrf().disable()
				.authorizeHttpRequests((auth) -> auth
						.requestMatchers("/oauth/sayHi").permitAll()
						.requestMatchers("/oauth/sayBy").hasRole("ADMIN")
						.anyRequest()
						.authenticated())
				.httpBasic()
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.addFilter(authenticationFilter)
				.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}
	
	@Bean
	public AuthenticationManager authManager(HttpSecurity http) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class)
				.userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder())
				.and().build();
	}
	
//	@Bean
//	public UserDetailsService userDetailsService() {
//		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//		manager.createUser(User.withUsername("admin")
//				.password(passwordEncoder().encode("admin"))
//				.roles()
//				.build());
//		return manager;
//	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println("contraseña: " +encoder.encode("123456") + "Franco");
		return new BCryptPasswordEncoder();
	}
}
