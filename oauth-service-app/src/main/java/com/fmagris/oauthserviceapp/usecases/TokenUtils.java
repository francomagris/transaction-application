package com.fmagris.oauthserviceapp.usecases;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.Data;

@Data
public class TokenUtils {

	private static final String SECURITY_KEY = "this_is_my_security_Key_from_my_app";
	private static final int TOKEN_VALIDITY = 3_600_000 * 24;

	public static String createToken(UserDetails userDetails) {
		Date expirationDate = new Date(System.currentTimeMillis() + TOKEN_VALIDITY);
		Map<String, Object> claims = new HashMap<>();

		claims.put("name", userDetails.getUsername());
		claims.put("company", "Amazone");
		claims.put("expirationDate", expirationDate);
		claims.put("roles", userDetails.getAuthorities()
				.stream()
				.map(auth -> "ROLE_" + auth.getAuthority())
				.collect(Collectors.toList()));

		return Jwts.builder()
				.setSubject(userDetails.getUsername())
				.setExpiration(expirationDate)
				.addClaims(claims)
				.signWith(Keys.hmacShaKeyFor(SECURITY_KEY.getBytes())).compact();
	}

	public static UsernamePasswordAuthenticationToken decodeToken(String token) throws Exception {
		try {
			Claims claims = Jwts
					.parserBuilder()
					.setSigningKey(SECURITY_KEY.getBytes())
					.build()
					.parseClaimsJws(token)
					.getBody();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			System.out.println("Expiration Date ->>" + sdf.format(claims.getExpiration()));
			System.out.println("Subject ->>" + claims.getSubject());
			System.out.println(claims.get("roles").toString());
			
			List<GrantedAuthority> authorities = ((List<String>) claims.get("roles"))
																		.stream()
																		.map(SimpleGrantedAuthority::new)
																		.collect(Collectors.toList());
			
			return new UsernamePasswordAuthenticationToken(claims.getSubject(), null, authorities);
		} catch (SignatureException e) {
			throw new SignatureException("Invalid JWT signature ");
		} catch (MalformedJwtException e) {
			throw new MalformedJwtException("Invalid JWT token");
		} catch (ExpiredJwtException e) {
			throw new Exception("JWT token is expired ");
		} catch (UnsupportedJwtException e) {
			throw new UnsupportedJwtException("JWT token is unsupported ");
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("JWT claims string is empty ");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}

}
