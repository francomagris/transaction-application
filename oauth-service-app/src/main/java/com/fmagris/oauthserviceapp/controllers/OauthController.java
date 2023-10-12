package com.fmagris.oauthserviceapp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OauthController {
	
	@GetMapping("/oauth/sayHi")
	public String sayHi() {
		System.out.println("Say Hi Controller");
		return "Welcome to oauth service";
	}
	
	@GetMapping("/oauth/sayBy")
	public String sayBy() {
		System.out.println("Say by Controller");
		return "Hasta la vista Baby";
	}
}
