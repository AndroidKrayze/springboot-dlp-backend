package com.dlpauth.user.repository;

import com.dlpauth.user.model.TenantOnboard;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TenantOnboardRepository extends JpaRepository<TenantOnboard, UUID> {
    // Additional query methods if needed
}

