package com.fmagris.oauthserviceapp.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fmagris.oauthserviceapp.feingClients.UserFeingClient;
import com.fmagris.oauthserviceapp.models.Client;
import com.fmagris.oauthserviceapp.models.UserDetailsImpl;


@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	UserFeingClient feignClient;
	ObjectMapper mapper = new ObjectMapper();

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			ResponseEntity<?> feignResponse = feignClient.findClient(username);

			if(feignResponse.getStatusCode() == HttpStatus.OK) {
				Client clientFeign = mapper.convertValue(feignResponse.getBody(), Client.class);
				return new UserDetailsImpl(clientFeign);
			}
			throw new UsernameNotFoundException("Username '" + username  +"' not found.");
		}catch(Exception ex) {
			ex.printStackTrace();
			throw new UsernameNotFoundException("Username '" + username  +"' not found.");
		}
	}

}
