package com.fmagris.transaction.infrastructure.adapters.controllers;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fmagris.transaction.domain.Account;
import com.fmagris.transaction.domain.Movements;
import com.fmagris.transaction.domain.exceptions.AccountNotFoundException;
import com.fmagris.transaction.domain.ports.in.AccountPortIn;
import com.fmagris.transaction.domain.ports.in.MovementsPortIn;



@RestController
@RequestMapping("/api-homebanking/account/")
public class AccountController {
	
	@Autowired
	private AccountPortIn accountPortIn;
	
	@Autowired
	private MovementsPortIn movementsPortIn;
	
	
	@GetMapping(value="find_cbu_{accountCbu}")
	public ResponseEntity<Account> getAcountByCbu(@PathVariable(name = "accountCbu") Long accountCbu) throws Exception{
		
		Account account = accountPortIn.getAcountByCbu(accountCbu);
		return new ResponseEntity<Account>(account, HttpStatus.OK);
	}
	
	
	
	@GetMapping(value="movements/{cbu}/{dateFrom}/{dateTo}")
	public ResponseEntity<List<Movements>> getAccountMovements(@PathVariable(value="cbu") Long cbu,
														@PathVariable(name = "dateFrom") Long dateFrom,
														@PathVariable(name="dateTo") Long dateTo) throws Exception{
		
		try {
			DateFormat df = new SimpleDateFormat("yyyyMMdd");
			Date parseDateTo = df.parse(dateTo.toString());
			Date parseDateFrom = df.parse(dateFrom.toString());
			
			List<Movements> movements = movementsPortIn.getMovements(cbu, parseDateFrom, parseDateTo);
			return new ResponseEntity<List<Movements>>(movements, HttpStatus.OK);
			
		}catch(AccountNotFoundException e) {
			throw e;
		}catch(Exception ex) {
			ex.printStackTrace();
			throw new InternalError("Application Error");
		}
	}
}
