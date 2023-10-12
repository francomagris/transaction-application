package com.fmagris.clients.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fmagris.clients.models.Client;
import com.fmagris.clients.models.exceptions.ClientNotFoundException;
import com.fmagris.clients.repository.ClientRepository;

import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;

@Service
public class ClientServiceImpl implements ClientsService{
	
	@Autowired
	ClientRepository repo;

	@Override
	@Transactional
	public Client getClientByUsername(String username) throws ClientNotFoundException{
		return repo.findClientByUsername(username).orElseThrow(()-> new ClientNotFoundException("Client username not found"));
	}

	@Override
	public Client newClient(Client client) {
		try {
			getClientByUsername(client.getUsername());
		}catch(ClientNotFoundException ex) {
			return repo.save(client);
		}catch(Exception ex) {
			throw new InternalError("An error has ocurred");
		}
		throw new IllegalArgumentException("This username is not available");	
	}

	@Override
	public String deleteClient(String username) throws ClientNotFoundException {
		try{
			Client cli = getClientByUsername(username);
			repo.deleteById(cli.getId());
			return "Client deleted";
		}catch(Exception e) {
			if(e instanceof ClientNotFoundException)
				throw e;
			else
				throw new PersistenceException("An error has ocurred by deleting the client");
		}
	}

	@Override
	public Client updateClient(Client client) throws ClientNotFoundException {
		repo.findById(client.getId()).orElseThrow(()-> new ClientNotFoundException("Client username not found"));
		return repo.save(client);
	}

}
