package com.fmagris.transaction.infrastructure.adapters.out;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fmagris.transaction.domain.Movements;
import com.fmagris.transaction.domain.ports.out.MovementsPortOut;
import com.fmagris.transaction.infrastructure.adapters.out.persistence.entities.MovementsEntity;
import com.fmagris.transaction.infrastructure.adapters.out.persistence.repositories.MovementsRepository;

import jakarta.transaction.Transactional;

@Component
public class MovementsAdapter extends AccountAdapter implements MovementsPortOut {
	
	@Autowired
	private MovementsRepository movementsRepo;

	@Override
	public List<Movements> getAccountMovements(Long cbu, Date dateFrom, Date dateTo) throws Exception {
		getAcountByCbu(cbu);
		return movementsRepo.findMovementsByPeriod(cbu, dateFrom, dateTo)
				.stream()
				.map(m -> {
						return m.entityToDto();
				}).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void saveMovements(Movements movement) {
		movementsRepo.save(new MovementsEntity(movement));
	}

}
