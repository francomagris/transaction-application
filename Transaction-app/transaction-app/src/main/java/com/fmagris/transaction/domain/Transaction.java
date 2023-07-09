package com.fmagris.transaction.domain;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Transaction {
	private Long id;
	private Long senderCBU;
	private Long recipientCBU;
	private Double amount;
	private Date date;
}
