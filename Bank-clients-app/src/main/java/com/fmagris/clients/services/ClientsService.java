package com.fmagris.clients.services;

import com.fmagris.clients.models.Client;
import com.fmagris.clients.models.exceptions.ClientNotFoundException;

public interface ClientsService {
	public Client getClientByUsername(String username) throws ClientNotFoundException;
	public Client newClient(Client client);
	public String deleteClient(String username) throws ClientNotFoundException;
	public Client updateClient(Client client) throws ClientNotFoundException; 
}
