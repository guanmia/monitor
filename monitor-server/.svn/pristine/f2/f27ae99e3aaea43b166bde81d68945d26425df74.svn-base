package com.anyun100.storage.monitor.metadata_service.repository;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.jpa.repository.Query;

import com.anyun100.storage.monitor.metadata_service.domain.ClientStatus;

public interface ClientStatusRepository extends JpaRepository<ClientStatus, Long> {
	@Query("select c from ClientStatus c where c.siteId = ?1 and c.dt between ?2 and ?3")
	List<ClientStatus> findClientStatus(Long siteId, LocalDateTime start, LocalDateTime end);
}
