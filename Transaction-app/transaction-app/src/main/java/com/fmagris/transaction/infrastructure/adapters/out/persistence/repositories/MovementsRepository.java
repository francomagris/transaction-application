package com.fmagris.transaction.infrastructure.adapters.out.persistence.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fmagris.transaction.domain.Movements;
import com.fmagris.transaction.infrastructure.adapters.out.persistence.entities.MovementsEntity;
import com.fmagris.transaction.infrastructure.adapters.out.persistence.entities.TransactionEntity;

@Repository
public interface MovementsRepository extends JpaRepository<MovementsEntity, Long>{
	@Query(value= "SELECT mv.* FROM MOVEMENT mv \n"
			+ " WHERE \n"
			+ "     mv.MV_AC_CBU = :cbu \n"
			+ " AND mv.MV_DATE BETWEEN  :dateFrom AND :dateTo\r\n"
			+ " ORDER BY mv.MV_DATE", nativeQuery = true)
	public List<MovementsEntity> findMovementsByPeriod(@Param("cbu") Long cbu, 
										@Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo);

}
