package com.fmagris.transaction.infrastructure.adapters.out.persistence.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fmagris.transaction.infrastructure.adapters.out.persistence.entities.AccountEntity;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long>{
	Optional<AccountEntity> findFirstByCbu(Long cbu);
}
