package models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fmagris.transaction.infrastructure.adapters.out.persistence.entities.AccountEntity;
import com.fmagris.transaction.infrastructure.adapters.out.persistence.entities.AccountType;
import com.fmagris.transaction.infrastructure.adapters.out.persistence.entities.MovementsEntity;
import com.fmagris.transaction.infrastructure.adapters.out.persistence.entities.TransactionEntity;

import lombok.Data;

@Data
public class DataLoader {
	
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	
	 public static AccountType createAccountTypeAmericanUsd() {
	    	AccountType usdAccountType = new AccountType(1L, "AMERICAN_USD");
	        return usdAccountType;
	    }
	        // Create Account Type
	    public static AccountType createAccountTypeArgPesos() {
	    	AccountType usdAccountType = new AccountType(2L, "ARG_PESOS");
	        return usdAccountType;
	    }   
	        
	// Create Account
	    //Franco-USD
	    public static AccountEntity createAccountFrancoUSD() {
	        AccountEntity franco_usd = new AccountEntity();
	        franco_usd.setCbu(1112587458950000L); 
	        franco_usd.setBalance(100.0);
	        franco_usd.setClientId("Franco-9f920f9c-ac23");
	        franco_usd.setAccountType(createAccountTypeAmericanUsd());
	        return franco_usd;
	    }
	    
	    //Angeles-USD
	    public static AccountEntity createAccountAngelesUSD() {
	    	AccountEntity angeles_usd = new AccountEntity();
	        angeles_usd.setCbu(1116988514521111L); 
	        angeles_usd.setBalance(100.0);
	        angeles_usd.setClientId("Angeles-9f920f9c-ac23");
	        angeles_usd.setAccountType(createAccountTypeAmericanUsd());
	        return angeles_usd;
	    }
	    
	    //Franco-Pesos
	    public static AccountEntity createAccountFrancoPesos() {
	    	AccountEntity franco_pesos = new AccountEntity();
	        franco_pesos.setCbu(2222479631450000L); 
	        franco_pesos.setBalance(35000.0);
	        franco_pesos.setClientId("Franco-9f920f9c-ac23");
	        franco_pesos.setAccountType(createAccountTypeArgPesos());
	        return franco_pesos;
	    }
	    
	    //Angeles pesos
	    public static AccountEntity createAccountAngelesPesos() {
	    	AccountEntity angeles_pesos = new AccountEntity();
	        angeles_pesos.setCbu(2229874587411111L); 
	        angeles_pesos.setBalance(35000.0);
	        angeles_pesos.setClientId("Angeles-9f920f9c-ac23");
	        angeles_pesos.setAccountType(createAccountTypeArgPesos()); 
	        return angeles_pesos;
	    }
	    
	    ///Transactions
	    public static TransactionEntity createTransaction() {
	    	TransactionEntity transaction = new TransactionEntity();
	        transaction.setSenderCBU(createAccountFrancoPesos().getCbu());
	        transaction.setRecipientCBU(createAccountAngelesPesos().getCbu());
	        transaction.setAmount(25000.0);
	        transaction.setDate(new Date());
	        return transaction;
	    }    

	    public static MovementsEntity createMovements_01() throws ParseException {
	    	 MovementsEntity debit = new MovementsEntity();
	         debit.setAccount(createAccountFrancoPesos());
	         debit.setAmount(-25000d);
	         debit.setDate(sdf.parse("20230501"));
	         debit.setTransaction(createTransaction());
	         return debit;
	    }
	    
	    public static MovementsEntity createMovements_02() throws ParseException {
	    	MovementsEntity credit = new MovementsEntity();
	        credit.setAccount(createAccountAngelesPesos());
	        credit.setAmount(25000d);
	        credit.setDate(sdf.parse("20230501"));
	        credit.setTransaction(createTransaction());
	        return credit;
	   }
	       
}
