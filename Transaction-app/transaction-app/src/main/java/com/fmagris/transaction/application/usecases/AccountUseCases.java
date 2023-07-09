package com.fmagris.transaction.application.usecases;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.fmagris.transaction.domain.Account;
import com.fmagris.transaction.domain.Movements;
import com.fmagris.transaction.domain.Transaction;
import com.fmagris.transaction.domain.exceptions.AccountNotFoundException;
import com.fmagris.transaction.domain.ports.in.AccountPortIn;
import com.fmagris.transaction.domain.ports.in.MovementsPortIn;
import com.fmagris.transaction.domain.ports.out.AccountPortOut;
import com.fmagris.transaction.domain.ports.out.MovementsPortOut;


@Service
public class AccountUseCases implements AccountPortIn, MovementsPortIn{
	
	@Autowired
	private AccountPortOut accountPort;
	
	@Autowired
	private MovementsPortOut movementsPort;


	@Override
	public List<Movements> getMovements(Long cbu, Date dateFrom, Date dateTo) throws Exception {
		return movementsPort.getAccountMovements(cbu, dateFrom, dateTo);
	}

	@Override
	public Account getAcountByCbu(Long cbu) throws AccountNotFoundException {
		return accountPort.getAcountByCbu(cbu);
	}



	

}
