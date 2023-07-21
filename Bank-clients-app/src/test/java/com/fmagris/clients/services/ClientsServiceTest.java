package com.fmagris.clients.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fmagris.clients.models.Client;
import com.fmagris.clients.models.exceptions.ClientNotFoundException;
import com.fmagris.clients.repository.ClientRepository;

import jakarta.persistence.PersistenceException;

@SpringBootTest
class ClientsServiceTest {
	
	@Autowired
	private ClientsService service;
	
	@MockBean
	private ClientRepository repo;
	
	private Client client;
	
	@BeforeEach
	public void setUp() {
		client = new Client();
		client.setId(1L);
		client.setName("Franco");
		client.setLastName("Magris");
		client.setUsername("franco.magris");
		client.setPassword("1234_fm");
	}
	

	@Test
	void getClientByUsername() throws Exception {
		
		when(repo.findClientByUsername("franco.magris")).thenReturn(Optional.of(client));
		
		Client result = service.getClientByUsername("franco.magris");
		
		assertAll( ()-> assertNotNull(result),
				   ()-> assertEquals("Franco", result.getName()),
				   ()-> assertEquals("Magris", result.getLastName()),
				   ()-> verify(repo, times(1)).findClientByUsername("franco.magris")
		);
	}
	
	@Test
	void getClientByUsername_shouldReturnClientNotFournEx() throws Exception {
		
		when(repo.findClientByUsername("franco.magris")).thenReturn(Optional.empty());
		
		assertThrows(ClientNotFoundException.class, () -> service.getClientByUsername("franco.magris"));
	}
	
	@Test
	void deleteClient_shouldReturnError() throws ClientNotFoundException {
		
		
		
		
		when(repo.findClientByUsername("franco_magris")).thenReturn(Optional.empty());
		assertThrows(ClientNotFoundException.class, ()->{
			service.deleteClient("franco_magris");
		});
		
		when(repo.findClientByUsername("francomagris")).thenThrow(PersistenceException.class);
		assertThrows(PersistenceException.class, ()->{
			service.deleteClient("francomagris");
		});
		
		when(repo.findClientByUsername("franco.magris")).thenReturn(Optional.of(client));
		String result = service.deleteClient("franco.magris");
		
		assertAll( ()-> assertNotNull(result),
				   ()-> assertEquals("Client deleted", "Client deleted"),
				   ()-> verify(repo,times(1)).deleteById(any())
		);
		
	}
	
	@Test
	void newClient(){
		when(repo.save(client)).thenReturn(client);
		
		Client result = service.newClient(client);
		
		assertAll( ()-> assertNotNull(result),
				   ()-> assertEquals("Franco", result.getName()),
				   ()-> assertEquals("Magris", result.getLastName())
		);
	}
	
	@Test
	void newClient_ShouldReturnErrorByNotAvailableUsername(){
		when(repo.findClientByUsername(client.getUsername())).thenReturn(Optional.of(client));		
			
		assertThrows(IllegalArgumentException.class, ()->{
			service.newClient(client);
		});
	}
	
		
	@Test
	void updateClient() throws ClientNotFoundException {
		client.setLastName("Perez");
		client.setPassword("Juan");
		
		when(repo.findById(1L)).thenReturn(Optional.of(client));
		when(repo.save(client)).thenReturn(client);
		
		Client result = service.updateClient(client);
		assertEquals(result, client);
		
		when(repo.findById(3L)).thenReturn(Optional.empty());
		
		assertThrows(ClientNotFoundException.class,() ->{
			client.setId(3L);
			service.updateClient(client);
		});
				
	}

}
