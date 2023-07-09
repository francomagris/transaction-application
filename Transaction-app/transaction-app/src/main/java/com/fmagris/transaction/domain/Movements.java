package com.fmagris.transaction.domain;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Movements {
	private Long id;
	private Account account;
	private Double amount;
	private Date date;
}
