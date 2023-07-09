package com.fmagris.transaction.domain.ports.in;

import java.util.Date;
import java.util.List;

import com.fmagris.transaction.domain.Movements;
import com.fmagris.transaction.domain.exceptions.AccountNotFoundException;

public interface MovementsPortIn {
	List<Movements> getMovements(Long cbu, Date dateFrom, Date dateTo) throws Exception;
}
