package com.lunorion.labs.core.orden_trabajo.domain.ports.in;

import com.lunorion.labs.core.orden_trabajo.application.dto.out.KanbanResponse;
import com.lunorion.labs.core.orden_trabajo.application.dto.out.OrdenTrabajoResponse;

import java.util.List;
import java.util.Optional;

public interface IOrdenTrabajoQueryPort {
    Optional<OrdenTrabajoResponse> findById(String id);
    List<OrdenTrabajoResponse> findByTenantId(String tenantId);
    List<OrdenTrabajoResponse> findAll();
    List<OrdenTrabajoResponse> findByEstado(String estado, String tenantId);
    List<KanbanResponse> kanban(String tenantId);
}
