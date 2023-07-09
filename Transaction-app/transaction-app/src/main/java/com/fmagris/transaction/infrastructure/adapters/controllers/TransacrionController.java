package com.fmagris.transaction.infrastructure.adapters.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fmagris.transaction.domain.Transaction;
import com.fmagris.transaction.domain.ports.in.SaveTransactionPortIn;


@RestController
@RequestMapping("api-homebanking/transaction")
public class TransacrionController {
	@Autowired
	private SaveTransactionPortIn transactionPort;
	
	
	@PostMapping()
	public ResponseEntity<?> saveTransaction(@RequestBody Transaction transaction){
		try {
			transactionPort.saveTransaction(transaction);
			return new ResponseEntity<String>("Transaction succesfull" , HttpStatus.OK );
		}catch(Exception e) {
			return new ResponseEntity<String>("There was a problem with the transaction", HttpStatus.INTERNAL_SERVER_ERROR );
		}
	}
	

}
