package com.lunorion.labs.core.dashboard.domain.ports.in;

import com.lunorion.labs.core.dashboard.application.dto.out.KpiResponse;
import com.lunorion.labs.core.dashboard.application.dto.out.RentabilidadResponse;

import java.util.List;

public interface IDashboardQueryPort {

    KpiResponse obtenerKpis(String tenantId);

    List<RentabilidadResponse> obtenerRentabilidad(String tenantId);

    byte[] exportarReporte(String tenantId, String formato, String fechaInicio, String fechaFin, String tipo);
}
