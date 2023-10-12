package com.fmagris.oauthserviceapp.models;

import java.util.List;
import lombok.Data;

@Data
public class Client {
	
	private Long id;
	private String username;
	private String name;
	private String lastName;
	private String password;
	private List<Roles> roles;

}
