package com.anyun100.storage.monitor.client_config_service.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.jpa.repository.Query;

import com.anyun100.storage.monitor.client_config_service.domain.ClientConfig;
import com.anyun100.storage.monitor.client_config_service.domain.RegionCenter;

public interface ClientConfigRepository extends JpaRepository<ClientConfig, Long> {
	
	@Query("select c.siteId from ClientConfig c where c.regionCenter = ?1")
	List<Long> getSiteIdsByRegionCenter(RegionCenter regionCenter);
	
	@Query("select c from ClientConfig c where c.regionCenter = ?1")
	List<ClientConfig> findClientConfigByRegionCenter(RegionCenter regionCenter);
}
