package com.fmagris.transaction.domain.ports.out;

import java.util.Date;
import java.util.List;

import com.fmagris.transaction.domain.Movements;
import com.fmagris.transaction.domain.exceptions.AccountNotFoundException;

public interface MovementsPortOut {
	List<Movements> getAccountMovements(Long cbu, Date dateFrom, Date dateTo) throws Exception;
	void saveMovements(Movements movement);
}
