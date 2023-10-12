package com.fmagris.clients.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name="CLIENTS")
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
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		name="CLIENT_ROLES",
		joinColumns = @JoinColumn(name="CLI_ID"),
		inverseJoinColumns = @JoinColumn(name="ROLE_ID")
		)
	List<Roles> roles;
		
	
}
