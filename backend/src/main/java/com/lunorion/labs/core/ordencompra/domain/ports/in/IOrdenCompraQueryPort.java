package com.lunorion.labs.core.ordencompra.domain.ports.in;

import com.lunorion.labs.core.ordencompra.application.dto.out.OrdenCompraResponse;
import com.lunorion.labs.core.ordencompra.application.dto.out.ReporteGastosResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IOrdenCompraQueryPort {
    Optional<OrdenCompraResponse> findById(String id);
    List<OrdenCompraResponse> findByTenantId(String tenantId);
    List<OrdenCompraResponse> findByProveedorId(String proveedorId);
    ReporteGastosResponse spendingReport(String tenantId, LocalDate desde, LocalDate hasta);
}
