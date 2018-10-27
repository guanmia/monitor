package com.anyun100.storage.monitor.metadata_service.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.jpa.repository.Query;

import com.anyun100.storage.monitor.metadata_service.domain.MetaDataStatus;

public interface MetaDataStatusRepository extends JpaRepository<MetaDataStatus, Long> {
	@Query("select c from MetaDataStatus c where c.regionId = ?1 and c.ldt between ?2 and ?3")
	List<MetaDataStatus> findMetadataStatus(Long regionId, Long start, Long end);
}
