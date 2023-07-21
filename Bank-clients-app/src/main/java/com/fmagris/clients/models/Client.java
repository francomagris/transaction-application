package com.fmagris.clients.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Client {
	
	@Id
	@Column(name="CLI_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="CLI_USERNAME", unique = true)
	private String username;
	
	@Column(name="CLI_NAME")
	private String name;
	
	@Column(name="CLI_LASTNAME")
	private String lastName;
	
	@Column(name="CLI_PASSWORD")
	private String password;

	
		
}
