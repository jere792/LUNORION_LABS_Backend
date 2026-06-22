package com.lunorion.labs.core.dashboard.application.service;

import com.lunorion.labs.core.dashboard.application.dto.out.KpiResponse;
import com.lunorion.labs.core.dashboard.application.dto.out.RentabilidadResponse;
import com.lunorion.labs.core.dashboard.domain.ports.in.IDashboardQueryPort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
public class DashboardService implements IDashboardQueryPort {

    @Override
    public KpiResponse obtenerKpis(String tenantId) {
        return new KpiResponse(
                new BigDecimal("15800.00"),
                12,
                5,
                8,
                new BigDecimal("345000.00"),
                new BigDecimal("198000.00")
        );
    }

    @Override
    public List<RentabilidadResponse> obtenerRentabilidad(String tenantId) {
        return Arrays.asList(
                new RentabilidadResponse("Mantenimiento Preventivo", new BigDecimal("85000"),
                        new BigDecimal("34000"), new BigDecimal("51000"), 60.0),
                new RentabilidadResponse("Reparación General", new BigDecimal("120000"),
                        new BigDecimal("72000"), new BigDecimal("48000"), 40.0),
                new RentabilidadResponse("Diagnóstico", new BigDecimal("45000"),
                        new BigDecimal("15000"), new BigDecimal("30000"), 66.67),
                new RentabilidadResponse("Instalación", new BigDecimal("95000"),
                        new BigDecimal("57000"), new BigDecimal("38000"), 40.0)
        );
    }

    @Override
    public byte[] exportarReporte(String tenantId, String formato, String fechaInicio,
                                  String fechaFin, String tipo) {
        String contenido = "Reporte " + tipo + " del " + fechaInicio + " al " + fechaFin +
                " en formato " + formato;
        return contenido.getBytes();
    }
}
