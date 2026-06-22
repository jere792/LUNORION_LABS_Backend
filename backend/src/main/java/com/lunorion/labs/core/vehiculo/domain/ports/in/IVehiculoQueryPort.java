package com.lunorion.labs.core.vehiculo.domain.ports.in;

import com.lunorion.labs.core.vehiculo.application.dto.out.VehiculoResponse;
import java.util.List;
import java.util.Optional;

public interface IVehiculoQueryPort {
    Optional<VehiculoResponse> findById(String id);
    List<VehiculoResponse> findByTenantId(String tenantId);
    List<VehiculoResponse> findByClienteId(String clienteId);
    List<VehiculoResponse> findAll();
}
