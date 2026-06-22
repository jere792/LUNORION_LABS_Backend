package com.lunorion.labs.core.tenant.application.mapper;

import com.lunorion.labs.core.tenant.application.dto.in.CreateTenantRequest;
import com.lunorion.labs.core.tenant.application.dto.out.TenantResponse;
import com.lunorion.labs.core.tenant.domain.entity.Tenant;
import org.springframework.stereotype.Component;

@Component
public class TenantMapper {

    public Tenant toDomain(CreateTenantRequest request) {
        return Tenant.create(
            request.getRuc(),
            request.getRazonSocial(),
            request.getNombreComercial(),
            request.getDomicilioFiscal(),
            request.getEmail(),
            request.getTelefono()
        );
    }

    public TenantResponse toResponse(Tenant tenant) {
        TenantResponse response = new TenantResponse();
        response.setId(tenant.getId().toString());
        response.setRuc(tenant.getRuc());
        response.setRazonSocial(tenant.getRazonSocial());
        response.setNombreComercial(tenant.getNombreComercial());
        response.setDomicilioFiscal(tenant.getDomicilioFiscal());
        response.setEmail(tenant.getEmail());
        response.setTelefono(tenant.getTelefono());
        response.setLogoUrl(tenant.getLogoUrl());
        response.setColorPrimario(tenant.getColorPrimario());
        response.setColorSecundario(tenant.getColorSecundario());
        response.setPlan(tenant.getPlan());
        response.setEstado(tenant.getEstado());
        return response;
    }
}
