package com.lunorion.labs.core.planilla.domain.ports.in;

import com.lunorion.labs.core.planilla.application.dto.in.CreateComisionConfigRequest;
import com.lunorion.labs.core.planilla.application.dto.in.RegistrarAsistenciaRequest;
import com.lunorion.labs.core.planilla.application.dto.out.AsistenciaResponse;
import com.lunorion.labs.core.planilla.application.dto.out.BoletaPagoResponse;
import com.lunorion.labs.core.planilla.application.dto.out.ComisionConfigResponse;
import com.lunorion.labs.core.planilla.application.dto.out.PlameResponse;

public interface IPlanillaCommandPort {
    AsistenciaResponse registrarAsistencia(RegistrarAsistenciaRequest request);
    BoletaPagoResponse generarBoleta(String tecnicoId, String periodo);
    ComisionConfigResponse createComisionConfig(CreateComisionConfigRequest request);
    PlameResponse generarPlame(String tenantId, String periodo);
}
