package com.fmagris.transaction.infrastructure.adapters.out.persistence.entities;

import java.util.Date;

import lombok.Data;

@Data
public class BcraDolarValueDTO {
	private Date d;
	private Double v;

}
