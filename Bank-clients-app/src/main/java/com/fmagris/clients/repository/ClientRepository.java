package com.fmagris.clients.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fmagris.clients.models.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{
	Optional<Client> findClientByUsername(String username);	
}
