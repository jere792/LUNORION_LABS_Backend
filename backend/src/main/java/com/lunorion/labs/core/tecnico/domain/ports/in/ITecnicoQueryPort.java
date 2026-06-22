package com.lunorion.labs.core.tecnico.domain.ports.in;

import com.lunorion.labs.core.tecnico.application.dto.out.CargaTrabajoResponse;
import com.lunorion.labs.core.tecnico.application.dto.out.TecnicoResponse;

import java.util.List;
import java.util.Optional;

public interface ITecnicoQueryPort {
    Optional<TecnicoResponse> findById(String id);
    List<TecnicoResponse> findAll();
    List<TecnicoResponse> findByTenantId(String tenantId);
    List<TecnicoResponse> findActivosByTenantId(String tenantId);
    CargaTrabajoResponse workload(String id);
}
