package com.francesca.pascalau.config.datasource;

import com.francesca.pascalau.config.TenantContext;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * It tells hibernate which is the current tenant configured.
 * If no Tenant_Id is found then the Public schema is used as default tenant.
 */
@Component
public class TenantSchemaResolver implements CurrentTenantIdentifierResolver {

    private static final String defaultTenant = "public";

    @Override
    public String resolveCurrentTenantIdentifier() {
        String currentTenant = TenantContext.getCurrentTenant();

        return Objects.requireNonNullElse(currentTenant, defaultTenant);
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}