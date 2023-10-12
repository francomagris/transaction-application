package com.fmagris.clients.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fmagris.clients.models.Client;
import com.fmagris.clients.services.ClientsService;

@RestController
@RequestMapping("/api-clients/")
public class ClientsController {
	@Autowired
	private ClientsService service;
	
	@GetMapping("find_{username}")
	@ResponseBody
	public ResponseEntity<?> findClient(@PathVariable(name="username") String username) {
		try {
			Client cli = service.getClientByUsername(username);
			return new ResponseEntity<Client>(cli, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	@PostMapping("create")
	public ResponseEntity<?> createClient(@RequestBody Client client) {
		try {
			Client cli = service.newClient(client);
			return new ResponseEntity<Client>(cli, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> updateClient(@RequestBody Client client) {
		try {
			Client cli = service.updateClient(client);
			return new ResponseEntity<Client>(cli, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/delete_{username}")
	public ResponseEntity<?> deleteClient(@PathVariable(name="username") String username) {
		try {
			String response = service.deleteClient(username);
			return new ResponseEntity<String>(response, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
