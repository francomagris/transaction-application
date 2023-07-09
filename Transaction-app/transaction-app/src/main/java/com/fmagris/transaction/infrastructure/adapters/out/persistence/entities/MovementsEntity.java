package com.fmagris.transaction.infrastructure.adapters.out.persistence.entities;

import java.util.Date;

import org.hibernate.annotations.GeneratorType;

import com.fmagris.transaction.domain.Movements;
import com.fmagris.transaction.domain.Transaction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "MOVEMENT")
public class MovementsEntity {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="MV_ID")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="MV_AC_CBU", referencedColumnName = "AC_CBU")
	private AccountEntity account;
	
	@ManyToOne
    @JoinColumn(name = "MV_TR_ID")
    private TransactionEntity transaction;
	
	@Column(name="MV_AMOUNT")
	private Double amount;
	
	@Column(name="MV_DATE")
	private Date date;
	
	
	
	public Movements entityToDto() {
		return Movements.builder()
				.id(id)
				.account(AccountEntity.mapToDTO(account))
				.amount(amount)
				.date(date)
				.build();
		
	}
	
	public MovementsEntity(Movements mov) {		
		this.setAccount(new AccountEntity(mov.getAccount()));
		this.setAmount(mov.getAmount());
		this.setDate(mov.getDate());
	}
	
	public MovementsEntity() {			
	}
}
