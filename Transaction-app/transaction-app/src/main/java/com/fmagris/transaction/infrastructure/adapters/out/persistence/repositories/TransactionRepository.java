package com.fmagris.transaction.infrastructure.adapters.out.persistence.repositories;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fmagris.transaction.infrastructure.adapters.out.persistence.entities.TransactionEntity;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long>{
	@Query(value= "SELECT tr.* FROM TRANSACTION tr\r\n"
			+ "WHERE \r\n"
			+ "     tr.TR_AC_CBU_SENDER = :cbu \r\n"
			+ "  OR  tr.TR_AC_CBU_RECIPIENT = :cbu \r\n"
			+ "      AND tr.TR_DATE BETWEEN  :dateFrom AND :dateTo\r\n"
			+ "ORDER BY tr.TR_DATE", nativeQuery = true)
	public List<TransactionEntity> findTransactionByPeriod(@Param("cbu") Long cbu, 
										@Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo);
	
}
