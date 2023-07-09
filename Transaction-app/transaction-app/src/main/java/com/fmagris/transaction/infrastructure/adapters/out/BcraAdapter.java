package com.fmagris.transaction.infrastructure.adapters.out;



import java.util.Arrays;
import java.util.List;

import org.hibernate.mapping.Array;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fmagris.transaction.domain.ports.out.BcraExternalServicePort;
import com.fmagris.transaction.infrastructure.adapters.out.persistence.entities.BcraDolarValueDTO;


@Component
public class BcraAdapter implements BcraExternalServicePort{
	
	private final String BEARER_TOKEN = "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9."
			+ "eyJleHAiOjE3MTgyOTA2NTEsInR5cGUiOiJleHRlcm5hbCIsInVzZXIiOiJmcmFuY29tYWdyaXNAZ21haWwuY29tIn0."
			+ "P3hVPRCADpkABsgRvcWFS4iX8fKJfytXM2uXMlfvgJs7sN4vctLxisplQyRZGb67bjD_Y33mZYBNnvU4mLKuqQ";
	
	private final String BCRA_API_URL = "https://api.estadisticasbcra.com/usd";
	private final RestTemplate restTemplate = new RestTemplate();

	@Override
	public Double getDollarPrice() {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(BEARER_TOKEN);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		
		ResponseEntity<BcraDolarValueDTO[]> response = restTemplate.exchange(BCRA_API_URL, HttpMethod.GET, entity, BcraDolarValueDTO[].class);
		BcraDolarValueDTO[] historicDolarPrice = response.getBody();
		
		Double dolarPrice = 0.0;
		if(historicDolarPrice.length > 0) {
			List<BcraDolarValueDTO> list = Arrays.asList(historicDolarPrice);
			dolarPrice = list.get(list.size() -1 ).getV();
			System.out.println("Dolar price : U$D: " + dolarPrice);
		}
				
		return dolarPrice;
	}
	

}
