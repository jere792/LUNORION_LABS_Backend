package com.lunorion.labs.core.orden_trabajo.domain.ports.in;

import com.lunorion.labs.core.orden_trabajo.application.dto.in.*;
import com.lunorion.labs.core.orden_trabajo.application.dto.out.CierreOtResponse;
import com.lunorion.labs.core.orden_trabajo.application.dto.out.OrdenTrabajoResponse;

public interface IOrdenTrabajoCommandPort {
    OrdenTrabajoResponse create(CreateOrdenTrabajoRequest request);
    OrdenTrabajoResponse update(String id, CreateOrdenTrabajoRequest request);
    void delete(String id);
    OrdenTrabajoResponse cambiarEstado(String id, CambioEstadoRequest request);
    OrdenTrabajoResponse addInsumo(String id, AddInsumoRequest request);
    void removeInsumo(String id, String insumoId);
    OrdenTrabajoResponse addLabor(String id, RegistroLaborRequest request);
    OrdenTrabajoResponse updateLabor(String id, String laborId, RegistroLaborRequest request);
    CierreOtResponse close(String id);
    OrdenTrabajoResponse reopen(String id);
}
