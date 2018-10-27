package com.anyun100.storage.monitor.client_config_service.repository;



import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.jpa.repository.Query;

import com.anyun100.storage.monitor.client_config_service.domain.Location;

public interface LocationRepository extends JpaRepository<Location, String> {
	@Query("select c.locationName from Location c where c.locationId = ?1 and c.level = 2")
	String getCityName(String locationId);
	
	@Query("select c.locationName from Location c where c.locationId = ?1 and c.level = 1")
	String getProvinceName(String locationId);
	
	@Query("select c from Location c where c.locationName = ?1")
	Location findOneByName(String locationName);
	
}
