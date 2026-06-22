package com.lunorion.labs.core.planilla.domain.ports.in;

import com.lunorion.labs.core.planilla.application.dto.out.AsistenciaResponse;
import com.lunorion.labs.core.planilla.application.dto.out.BoletaPagoResponse;
import com.lunorion.labs.core.planilla.application.dto.out.ComisionConfigResponse;
import com.lunorion.labs.core.planilla.application.dto.out.ProductividadResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IPlanillaQueryPort {
    List<AsistenciaResponse> consultarAsistencia(String tecnicoId, LocalDate desde, LocalDate hasta);
    Optional<BoletaPagoResponse> consultarBoleta(String tecnicoId, String periodo);
    List<BoletaPagoResponse> consultarBoletasByPeriodo(String tenantId, String periodo);
    ProductividadResponse productividad(String tenantId, LocalDate desde, LocalDate hasta);
    List<ComisionConfigResponse> getComisionesConfig(String tenantId);
    byte[] descargarPdf(String id);
}
