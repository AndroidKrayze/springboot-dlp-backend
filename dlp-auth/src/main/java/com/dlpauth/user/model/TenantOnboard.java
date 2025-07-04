package com.dlpauth.user.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "tenant_onboard", schema = "dlp")
public class TenantOnboard {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "subdomain")
    private String subdomain;

    @Column(name = "tenant_id")
    private String tenantId;

    @Column(name = "access_token", columnDefinition = "TEXT")
    private String accessToken;

    @Column(name = "refresh_token", columnDefinition = "TEXT")
    private String refreshToken;

    @Column(name = "org_name", columnDefinition = "TEXT")
    private String orgName;

    // Getters and setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getSubdomain() { return subdomain; }
    public void setSubdomain(String subdomain) { this.subdomain = subdomain; }
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
    public String getOrgName() { return orgName; }
    public void setOrgName(String orgName) { this.orgName = orgName; }
}

