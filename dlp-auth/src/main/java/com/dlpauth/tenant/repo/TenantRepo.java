package com.dlpauth.tenant.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dlpauth.tenant.model.Tenant;

public interface TenantRepo extends JpaRepository<Tenant, Long> {
	
	Optional<Tenant> findByTenantId(String tenantId);
	
    boolean existsByTenantId(String tenantId);

}
