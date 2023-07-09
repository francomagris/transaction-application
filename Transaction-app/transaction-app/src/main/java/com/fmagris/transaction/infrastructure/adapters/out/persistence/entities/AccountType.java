package com.fmagris.transaction.infrastructure.adapters.out.persistence.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name="ACCOUNT_TYPE")
public class AccountType {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="AT_ID")
	private Long id;
	
	@Column(name = "AT_TYPE")
	private String type;
}
