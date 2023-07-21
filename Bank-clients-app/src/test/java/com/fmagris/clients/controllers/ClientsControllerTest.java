package com.fmagris.clients.controllers;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fmagris.clients.models.Client;
import com.fmagris.clients.models.exceptions.ClientNotFoundException;
import com.fmagris.clients.services.ClientsService;

@WebMvcTest(ClientsController.class)
class ClientsControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private ClientsService service;
	
	private Client client;
	private ObjectMapper mapper;
	
	@BeforeEach
	public void setUp() {
		client = new Client();
		client.setId(1L);
		client.setName("Franco");
		client.setLastName("Magris");
		client.setUsername("franco.magris");
		client.setPassword("1234_fm");
		
		mapper = new ObjectMapper();
	}

	
	@Test
	public void findClient_shouldReturnCLient() throws Exception {
		when(service.getClientByUsername("franco.magris")).thenReturn(client);
		
		mvc.perform(get("/api-clients/find_franco.magris")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.name").value("Franco"))
		.andExpect(jsonPath("$.username").value("franco.magris"));
		
	}
	
	@Test
	public void findClient_shouldReturnCLientNotFoundException() throws Exception {
		when(service.getClientByUsername(any())).thenThrow(new ClientNotFoundException("Client username not found"));
				
		mvc.perform(get("/api-clients/find_franco_magris")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isInternalServerError())
		.andExpect(content().string("Client username not found"));
		
	}
	
	@Test
	public void createClient_shouldReturnClient() throws Exception {
		when(service.newClient(any())).thenReturn(client);
		
		mvc.perform(post("/api-clients/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(client)))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.name").value("Franco"))
		.andExpect(jsonPath("$.username").value("franco.magris"));
		
	}
	
	@Test
	public void createClient_shouldReturnUsernameNotAvailable() throws Exception{
		when(service.newClient(any())).thenThrow(new IllegalArgumentException("This username is not available"));
				
		mvc.perform(post("/api-clients/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(client)))
		.andExpect(status().isInternalServerError())
		.andExpect(content().string("This username is not available"));		
	}
	
	@Test
	public void deleteClient_succesfull() throws Exception {
		when(service.deleteClient(any())).thenReturn("Client deleted");
		
		mvc.perform(delete("/api-clients/delete_franco_magris")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(client)))
		.andExpect(status().isOk())
		.andExpect(content().string("Client deleted"));	
		
	}
	
	
}
