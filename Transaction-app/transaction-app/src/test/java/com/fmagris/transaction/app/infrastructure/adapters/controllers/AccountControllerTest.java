package com.fmagris.transaction.app.infrastructure.adapters.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fmagris.transaction.domain.Account;
import com.fmagris.transaction.domain.ports.in.AccountPortIn;
import com.fmagris.transaction.domain.ports.in.MovementsPortIn;
import com.fmagris.transaction.infrastructure.adapters.controllers.AccountController;
import com.fmagris.transaction.infrastructure.adapters.out.persistence.entities.AccountEntity;
import com.fmagris.transaction.infrastructure.adapters.out.persistence.entities.AccountType;
import com.fmagris.transaction.infrastructure.adapters.out.persistence.entities.MovementsEntity;
import com.fmagris.transaction.infrastructure.adapters.out.persistence.entities.TransactionEntity;

import static models.DataLoader.*;

import models.DataLoader;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

	@MockBean
	private AccountPortIn accountPort;

	@MockBean
	private MovementsPortIn movementsPort;

	@Autowired
	private MockMvc mvc;

	@Test
	public void getAcountByCbu_shouldReturnAccount() throws Exception {
		Account account = Account.builder().balance(500.0).clientId("Franco-1b72-bs652-s33t5").cbu(555555555L).build();
		// Given
		when(accountPort.getAcountByCbu(account.getCbu())).thenReturn(account);

		// When
		mvc.perform(get("/api-homebanking/account/find_cbu_555555555")
				.contentType(MediaType.APPLICATION_JSON))
				// Then
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.balance").value(account.getBalance()))
				.andExpect(jsonPath("$.clientId").value("Franco-1b72-bs652-s33t5"));

		verify(accountPort, times(1)).getAcountByCbu(555555555L);
	}
	

	@Test
	public void getAccountMovements_shouldReturnMovements() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		when(movementsPort.getMovements(1112587458950000L, sdf.parse("20230501"), sdf.parse("20230505")))
				.thenReturn(Arrays.asList(createMovements_01().entityToDto()));
		
		when(movementsPort.getMovements(1112587458955555L, sdf.parse("20230501"), sdf.parse("20230505")))
		.thenReturn(Arrays.asList(createMovements_02().entityToDto()));
		
		mvc.perform(get("/api-homebanking/account/movements/1112587458950000/20230501/20230505")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$[0].amount").value(-25000d))
		.andExpect(jsonPath("$[0].account.clientId").value("Franco-9f920f9c-ac23"));
		
		mvc.perform(get("/api-homebanking/account/movements/1112587458955555/20230501/20230505")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$[0].amount").value(25000d))
		.andExpect(jsonPath("$[0].account.clientId").value("Angeles-9f920f9c-ac23"));
		
		
		verify(movementsPort, times(1)).getMovements(1112587458950000L, sdf.parse("20230501"), sdf.parse("20230505"));
		verify(movementsPort, times(1)).getMovements(1112587458955555L, sdf.parse("20230501"), sdf.parse("20230505"));
		
	}

}
