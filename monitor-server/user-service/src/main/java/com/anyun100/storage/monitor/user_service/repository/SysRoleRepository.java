package com.anyun100.storage.monitor.user_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.NoRepositoryBean;

import com.anyun100.storage.monitor.user_service.domain.SysRole;


public interface SysRoleRepository extends JpaRepository<SysRole,Long> {
	
    List<SysRole> findByValueContaining(String value);
    SysRole findByName(String name);
}
