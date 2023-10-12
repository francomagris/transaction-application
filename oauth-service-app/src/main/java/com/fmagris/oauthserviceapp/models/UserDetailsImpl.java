package com.fmagris.oauthserviceapp.models;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class UserDetailsImpl implements UserDetails{
	
	private Client client;

	public UserDetailsImpl(Client client) {
		super();
		System.out.println("UserDetailsImpl -> constructor");
		this.client = client;
	}
	

	public UserDetailsImpl() {
		super();
		System.out.println("UserDetailsImpl -> constructor()");
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return client.getRoles()
				.stream()
				.map(rol -> new SimpleGrantedAuthority(rol.getRole()))
				.peek(rol -> System.out.println(rol.getAuthority()))
				.collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return client.getPassword();
	}

	@Override
	public String getUsername() {
		return client.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public String getName() {
		return client.getName();
	}
	
}