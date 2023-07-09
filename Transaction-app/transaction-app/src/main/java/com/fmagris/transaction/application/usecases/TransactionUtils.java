package com.fmagris.transaction.application.usecases;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fmagris.transaction.domain.Account;
import com.fmagris.transaction.domain.TransactionCurrencyType;
import com.fmagris.transaction.domain.exceptions.AccountNotFoundException;
import com.fmagris.transaction.domain.exceptions.ExternalServiceException;
import com.fmagris.transaction.domain.exceptions.TransactionException;
import com.fmagris.transaction.domain.ports.out.AccountPortOut;
import com.fmagris.transaction.domain.ports.out.BcraExternalServicePort;

@Component
public class TransactionUtils {
	
	@Autowired
	AccountPortOut accountPort;
	
	@Autowired
	BcraExternalServicePort bcraServicePort;
	
	// 1- Check if accounts exist
	public Account checkIfAccountExist(Long cbu, boolean isSenderAccount) throws TransactionException{
		Account account = null;
		try{
			account = accountPort.getAcountByCbu(cbu);
		}catch(AccountNotFoundException e) {
			throw new TransactionException((isSenderAccount  ? "Sender" : "Recipient") + " CBU is wrong, or does not exist.");
		}

		return account;
	}
	
	
	// get TransactionCurrencyTypes
	public TransactionCurrencyType getTransactionCurrencies(String sender , String recipient) throws TransactionException{
		String sender_to_recipient = sender +"_TO_" + recipient;
		for(TransactionCurrencyType c : TransactionCurrencyType.values()) {
			if(sender_to_recipient.equals(c.toString())) 
				return c;
		}
		throw new TransactionException("Error retrieving transaction currency types");
	}
	
	// Get Currency price and calculate the exchange between currencies.
	public Double calculateTranasctionAmount(TransactionCurrencyType transactionType, Double amount) throws ExternalServiceException{
		try{
			switch (transactionType) {
			case AMERICAN_USD_TO_ARG_PESOS: {
				return amount * bcraServicePort.getDollarPrice();				
			}
			case ARG_PESOS_TO_AMERICAN_USD:{
				return amount / bcraServicePort.getDollarPrice();					
			}
			default:
				return amount;
			}			
		}catch(Exception ex) {
			throw new ExternalServiceException(ex.getMessage());
		}
	}
}
