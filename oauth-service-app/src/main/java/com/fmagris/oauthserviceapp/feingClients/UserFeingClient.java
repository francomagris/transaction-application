package com.fmagris.oauthserviceapp.feingClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(name="client-service")
public interface UserFeingClient {

	@GetMapping("/api-clients/find_{username}")
	@ResponseBody
	public ResponseEntity<?> findClient(@PathVariable(name="username") String username);
}
