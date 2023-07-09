package com.fmagris.transaction.application.usecases;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fmagris.transaction.domain.Account;
import com.fmagris.transaction.domain.Movements;
import com.fmagris.transaction.domain.Transaction;
import com.fmagris.transaction.domain.TransactionCurrencyType;
import com.fmagris.transaction.domain.exceptions.ExternalServiceException;
import com.fmagris.transaction.domain.exceptions.TransactionException;
import com.fmagris.transaction.domain.ports.in.AccountPortIn;
import com.fmagris.transaction.domain.ports.in.SaveTransactionPortIn;
import com.fmagris.transaction.domain.ports.out.AccountPortOut;
import com.fmagris.transaction.domain.ports.out.MovementsPortOut;
import com.fmagris.transaction.domain.ports.out.TransactionPortOut;

import jakarta.transaction.Transactional;


// 1- Check if sender and recipient CBU exists
// 2- Check if Account has enough money 
// 3- Get Transaction currency types 
// 3- Check if amount is grater than zero
// 4- Save Transaction
// 5- Generate Transaction according Currency exchange
// 6- Update Accounts balance

@Transactional
@Service
public class TransactionUsecases implements SaveTransactionPortIn{

	@Autowired
	TransactionPortOut transactionPort;
	
	@Autowired
	TransactionUtils transactionUtils;
	
	@Autowired
	AccountPortOut accountPort;
	
	@Autowired
	MovementsPortOut movementsPort;

	
	@Override
	public void saveTransaction(Transaction transaction) throws Exception{
//		Transaction saved_transaction = null;
		Account senderAccount, recipientAccount;
		senderAccount = recipientAccount = null;

		
		Double transactionAmount = transaction.getAmount();
		
		// Check transaction amount > 0
		if(transactionAmount <= 0) 
			throw new TransactionException("The amount of the transaction must be greater than zero");
		
		// Check if accounts exists
		senderAccount = transactionUtils.checkIfAccountExist(transaction.getSenderCBU(), true);
		
		// Sender balance > transaction amount
		if(senderAccount.getBalance() < transactionAmount)
			throw new TransactionException("Account balance is not enough to carry out this transaction.");
		
		recipientAccount = transactionUtils.checkIfAccountExist(transaction.getRecipientCBU(), false);
		
		// Check Transaction type
		TransactionCurrencyType	transactionType = transactionUtils.getTransactionCurrencies(senderAccount.getAccountType().getType(), 
																	recipientAccount.getAccountType().getType());
		
		try {
			saveTransactionPersistence(senderAccount, recipientAccount, transaction, transactionType);		
			
		}catch(Exception e) {
			e.printStackTrace();
			if(e instanceof ExternalServiceException)
				throw e;
			throw new TransactionException("An error has occurred during the transaction");
		}
	}
	

	
	private void saveTransactionPersistence(Account sender, Account recipient, 
								Transaction transaction, TransactionCurrencyType transactionType) throws Exception{
		// Save Transaction
		transaction.setDate(new Date());
		transaction  = transactionPort.saveTransaction(transaction);
		
		System.out.println("Transaction succesfull Amout:" + transaction.getAmount());
		
		// Update sender account balance
		sender.debit(transaction.getAmount());
		accountPort.updateAccount(sender);
		
		// Update recipient account balance
		recipient.credit( transactionUtils.calculateTranasctionAmount(transactionType, transaction.getAmount()) );
		accountPort.updateAccount(recipient);
		
		// Add sender movement
		movementsPort.saveMovements(Movements.builder()
											.account(sender)
											.amount( - transaction.getAmount())
											.date(new Date())
											.build());
		// Add recipient movement
		movementsPort.saveMovements(Movements.builder()
											.account(recipient)
											.amount(transaction.getAmount())
											.date(new Date())
											.build());
	
	}

}
