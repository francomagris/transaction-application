package com.fmagris.transaction.infrastructure.adapters.out.persistence.entities;
import com.fmagris.transaction.domain.Account;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="ACCOUNT")
public class AccountEntity {
	
	
	@Id
	@Column(name="AC_CBU")
	private Long cbu;
	
	@Column(name="AC_BALANCE")
	private Double balance;
	
	@Column(name="AC_CLI_ID")
	private String clientId;
	
	@ManyToOne
    @JoinColumn(name="AC_TYPE")
    private AccountType accountType;

	
	
	
	
	public static Account mapToDTO(AccountEntity entity) {
		Account account = Account.builder()
				.cbu(entity.getCbu())
				.accountType(entity.getAccountType())
				.balance(entity.getBalance())
				.clientId(entity.getClientId())
				.build();
		return account;	
	}
	
	public AccountEntity() {
		
	}
	
	public AccountEntity(Account dto) {
		this.cbu = dto.getCbu();
		this.accountType = dto.getAccountType();
		this.balance = dto.getBalance();
		this.clientId = dto.getClientId();
	}
}
