package com.fmagris.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.fmagris.clients.models.Client;
import com.fmagris.clients.repository.ClientRepository;

@Component
public class DataLoader implements CommandLineRunner{
	@Autowired
	ClientRepository repo;

	@Override
	public void run(String... args) throws Exception {
		Client cli = new Client();
		cli.setName("Franco");
		cli.setLastName("Magris");
		cli.setUsername("franco_magris");
		cli.setPassword("123456");
		
		Client c = repo.save(cli);
		
		System.out.println("Client added: " + c.getName() + " " +c.getLastName());
		
	}

}
