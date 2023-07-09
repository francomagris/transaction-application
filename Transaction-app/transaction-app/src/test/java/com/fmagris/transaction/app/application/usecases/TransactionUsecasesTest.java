package com.fmagris.transaction.app.application.usecases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fmagris.transaction.application.usecases.TransactionUsecases;
import com.fmagris.transaction.application.usecases.TransactionUtils;
import com.fmagris.transaction.domain.Account;
import com.fmagris.transaction.domain.Movements;
import com.fmagris.transaction.domain.Transaction;
import com.fmagris.transaction.domain.TransactionCurrencyType;
import com.fmagris.transaction.domain.exceptions.AccountNotFoundException;
import com.fmagris.transaction.domain.exceptions.ExternalServiceException;
import com.fmagris.transaction.domain.exceptions.TransactionException;
import com.fmagris.transaction.domain.ports.out.AccountPortOut;
import com.fmagris.transaction.domain.ports.out.BcraExternalServicePort;
import com.fmagris.transaction.domain.ports.out.MovementsPortOut;
import com.fmagris.transaction.domain.ports.out.TransactionPortOut;
import com.fmagris.transaction.infrastructure.adapters.out.persistence.entities.AccountType;


@SpringBootTest
class TransactionUsecasesTest {
	
	@MockBean 
	TransactionPortOut transactionPort;
	
	@MockBean
	AccountPortOut accountPort;
	
	@MockBean
	MovementsPortOut movementsPort;
	
	@MockBean
	BcraExternalServicePort bcraPort;
	
	
	
	@Autowired
	TransactionUsecases transactionUsecases;
	@Autowired
	TransactionUtils transactionUtils;
	
	private Transaction transaction;
	private Account sender;
	private Account recipient;
	
	
	@BeforeEach
	public void setUp() {
		transaction = Transaction.builder()
				.id(1L)
				.recipientCBU(12345L)
				.senderCBU(99345L)
				.date(new Date())
				.amount(500.0).build();
		
		sender = Account.builder()
				.cbu(12345L)
				.balance(500.0)
				.clientId("fmagris-45s55d2258f1g4g85h25")
				.accountType(new AccountType(1L, "ARG_PESOS"))
				.build();
		
		recipient = Account.builder()
				.cbu(99345L)
				.balance(300.0)
				.clientId("arusconi-45s55d2258f1g4g85h25")
				.accountType(new AccountType(2L, "AMERICAN_USD"))
				.build();
		
	}
	
	@Test
	void saveTransaction_shouldRetournAmountNotEnoughException(){
		transaction.setAmount(0.0);
		
		Throwable exeption = assertThrows(TransactionException.class, () ->{
			transactionUsecases.saveTransaction(transaction);
		});
		
		assertTrue(exeption.getMessage().contains("The amount of the transaction must be greater than zero"));
	}
	
	@Test
	void saveTransaction_shouldRetournAccountNotFoundException() throws Exception{
		
		when(accountPort.getAcountByCbu(12345L)).thenThrow(AccountNotFoundException.class);
		
		Throwable exeption =  assertThrows(TransactionException.class, ()->{
			transactionUtils.checkIfAccountExist(12345L, true);
		});
		
		assertTrue(exeption.getMessage().contains("Sender CBU is wrong, or does not exist."));
		
		exeption =  assertThrows(TransactionException.class, ()->{
			transactionUtils.checkIfAccountExist(12345L, false);
		});
		
		assertTrue(exeption.getMessage().contains("Recipient CBU is wrong, or does not exist."));
	}
	
	
	@Test	
	void saveTransaction_shouldRetournAccountBalanceNotEnoughMoney()throws Exception {
		transaction.setAmount(1000.0);
		
		when(transactionUtils.checkIfAccountExist(transaction.getSenderCBU(), true)).thenReturn(sender);
				
		Throwable exeption = assertThrows(TransactionException.class, () ->{
			transactionUsecases.saveTransaction(transaction);
		});
		
		assertTrue(exeption.getMessage().contains("Account balance is not enough to carry out this transaction."));
		
	}
	
	@Test	
	public void testGetTransactionCurrencies_ValidCurrencies() throws Exception{
		
		TransactionCurrencyType currencyType = transactionUtils.getTransactionCurrencies(sender.getAccountType().getType(),recipient.getAccountType().getType());
		assertEquals(TransactionCurrencyType.ARG_PESOS_TO_AMERICAN_USD, currencyType);		
	}

	
	@Test
	public void saveTransaction_shouldSaveTransactionAndUpdateAccountBalances() throws Exception {
		recipient.getAccountType().setType("ARG_PESOS");
		
		when(accountPort.getAcountByCbu(transaction.getSenderCBU())).thenReturn(sender);
        when(accountPort.getAcountByCbu(transaction.getRecipientCBU())).thenReturn(recipient);
        when(transactionPort.saveTransaction(transaction)).thenReturn(transaction);
        
        transactionUsecases.saveTransaction(transaction);
        
        verify(transactionPort).saveTransaction(transaction);
        verify(accountPort).updateAccount(sender);
        verify(accountPort).updateAccount(recipient);
        verify(movementsPort, times(2)).saveMovements(any(Movements.class));
        
        assertEquals(sender.getBalance(), 0.0);
        assertEquals(recipient.getBalance(), 800.0);
	}
	
	
	@Test
	public void saveTransactionPersistence_throwsExceptionOnExternalServiceFailure() throws Exception {
		
		when(bcraPort.getDollarPrice()).thenThrow(new RuntimeException("Error en el servicio externo"));
		when(accountPort.getAcountByCbu(transaction.getSenderCBU())).thenReturn(sender);
        when(accountPort.getAcountByCbu(transaction.getRecipientCBU())).thenReturn(recipient);
        when(transactionPort.saveTransaction(transaction)).thenReturn(transaction);
		
		assertThrows(ExternalServiceException.class, ()->{
			transactionUsecases.saveTransaction(transaction);
		});
		
		verify(accountPort, times(0)).updateAccount(recipient);		
		verify(movementsPort, times(0)).saveMovements(any(Movements.class));		
	}
	
	
}
