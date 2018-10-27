package com.anyun100.storage.monitor.client_config_service.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.anyun100.storage.monitor.client_config_service.domain.RegionCenter;

public interface RegionCenterRepository extends JpaRepository<RegionCenter, Long> {
	@Query("select c from RegionCenter c where c.serverHostName = ?1 and c.enabled= ?2")
	Optional<RegionCenter> findOneWithEnabled(String regionHostName, boolean enabled);
	
	@Query("select c from RegionCenter c where c.regionName = ?1")
	Optional<RegionCenter> findOneWithRegionName(String regionName);
}
