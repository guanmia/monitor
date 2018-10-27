package com.anyun100.storage.monitor.audit_service.repository;


import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anyun100.storage.monitor.audit_service.domain.SysLog;
public interface SysLogRepository extends JpaRepository<SysLog, Long> {
	 @Query("select c from SysLog c where c.lastModifiedBy in (:users) and c.lastModifiedDate Between :startDate and :endDate")
	    Page<SysLog> findAllPages(Pageable pgeable,  @Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate ,@Param(value = "users") List<String> users);
}
