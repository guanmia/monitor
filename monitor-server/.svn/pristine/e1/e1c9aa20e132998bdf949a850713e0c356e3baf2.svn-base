package com.anyun100.storage.monitor.user_service.repository;

import com.anyun100.storage.monitor.user_service.domain.SysUser;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

//@NoRepositoryBean
public interface SysUserRepository extends JpaRepository<SysUser, Long> {
	
    Optional<SysUser> findOneWithRolesByUsername(String username);
    
    Optional<SysUser> findOneByEmail(String email);
    
    @Query("select c from SysUser c where c.enabled=1")
    Page<SysUser> findAllPages(Pageable pgeable);
    
    @Query("select c from SysUser c where c.enabled=1 and c.username  in (:users)")
    Page<SysUser> findAllPagesByUsername(Pageable pgeable, @Param(value = "users") List<String> users);
}
