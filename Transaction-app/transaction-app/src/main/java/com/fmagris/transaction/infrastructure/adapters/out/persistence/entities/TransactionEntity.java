package com.fmagris.transaction.infrastructure.adapters.out.persistence.entities;

import java.util.Date;

import com.fmagris.transaction.domain.Transaction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name="TRANSACTION")
@Data
public class TransactionEntity {
	
	@Id
	@Column(name="TR_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="TR_AC_CBU_SENDER")
	private Long senderCBU;
	
	@Column(name="TR_AC_CBU_RECIPIENT")
	private Long recipientCBU;
	
	@Column(name="TR_AMOUNT")
	private Double amount;
	
	@Column(name="TR_DATE")
	private Date date;
	

	
	
	public Transaction entityToDto() {
		return Transaction.builder()
				.id(id)
				.senderCBU(senderCBU)
				.recipientCBU(recipientCBU)
				.amount(amount)
				.date(date)
				.build();
	}
	
	public TransactionEntity (Transaction dto) {
		this.id = dto.getId();
		this.senderCBU = dto.getSenderCBU();
		this.recipientCBU = dto.getRecipientCBU();
		this.amount = dto.getAmount();
		this.date = dto.getDate();			
	}
	
	public TransactionEntity () {		
	}
	
}
