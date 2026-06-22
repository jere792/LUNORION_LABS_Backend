package com.lunorion.labs.core.cotizacion.domain.ports.in;

import com.lunorion.labs.core.cotizacion.application.dto.out.CotizacionResponse;

import java.util.List;
import java.util.Optional;

public interface ICotizacionQueryPort {
    Optional<CotizacionResponse> findById(String id);
    List<CotizacionResponse> findByTenantId(String tenantId);
    List<CotizacionResponse> findByClienteId(String clienteId);
    List<CotizacionResponse> findByEstado(String estado);
    List<CotizacionResponse> findAll();
}
