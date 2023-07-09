package com.fmagris.transaction.app.infrastructure.adapters.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fmagris.transaction.domain.exceptions.ExternalServiceException;
import com.fmagris.transaction.domain.exceptions.TransactionException;
import com.fmagris.transaction.domain.ports.in.SaveTransactionPortIn;
import com.fmagris.transaction.infrastructure.adapters.controllers.TransacrionController;

import models.DataLoader;

@WebMvcTest(TransacrionController.class)
class TransacrionControllerTest {
	
	@MockBean
	private SaveTransactionPortIn transactionPort;
	
	@Autowired
	private MockMvc mvc;
	ObjectMapper mapper;
	
	@BeforeEach
	void setUp() {
		mapper = new ObjectMapper();
	}
	
	@Test
	public void saveTransaction_Error()  throws Exception {
		doThrow(new ExternalServiceException()).when(transactionPort)
	    .saveTransaction(any());
		
		mvc.perform(post("/api-homebanking/transaction")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(DataLoader.createTransaction())))
		.andExpect(status().isInternalServerError())
		.andExpect(content().string("There was a problem with the transaction"));
		
		doThrow(new  TransactionException("An error has occurred during the transaction")).when(transactionPort)
	    .saveTransaction(any());
		
		mvc.perform(post("/api-homebanking/transaction")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(DataLoader.createTransaction())))
		.andExpect(status().isInternalServerError())
		.andExpect(content().string("There was a problem with the transaction"));
		
	}
	
	@Test
	public void saveTransaction_Successful()  throws Exception {
		doNothing().when(transactionPort).saveTransaction(any());
		
		mvc.perform(post("/api-homebanking/transaction")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(DataLoader.createTransaction())))
		.andExpect(status().isOk())
		.andExpect(content().string("Transaction succesfull"));		
	}
	
}
