package com.anyun100.storage.monitor.metadata_service.repository;


import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.jpa.repository.Query;

import com.anyun100.storage.monitor.metadata_service.domain.SGWStatus;

public interface SGWStatusRepository extends JpaRepository<SGWStatus, Long> {
	@Query("select c from SGWStatus c where c.sgwId = ?1 and c.ldt between ?2 and ?3")
	List<SGWStatus> findSGWStatus(Long sgwId, Long start, Long end);
	
	@Query("select c.sgwId from SGWStatus c where c.regionId = ?1")
	Set<Long> findAllSGWSIds(Long regionId);
}
