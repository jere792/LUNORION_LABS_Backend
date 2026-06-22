package com.lunorion.labs.core.venta.domain.ports.in;

import com.lunorion.labs.core.venta.application.dto.out.VentaResponse;

import java.util.List;
import java.util.Optional;

public interface IVentaQueryPort {
    Optional<VentaResponse> findById(String id);
    List<VentaResponse> findByTenantId(String tenantId);
    List<VentaResponse> findByClienteId(String clienteId);
    List<VentaResponse> findAll();
}
