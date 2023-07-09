package com.fmagris.transaction.infrastructure.adapters.out.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fmagris.transaction.infrastructure.adapters.out.persistence.entities.AccountType;

@Repository
public interface AccountTypeRepository extends JpaRepository<AccountType, Long>{

	AccountType findFirstByType(String type);

}
