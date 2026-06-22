package com.lunorion.labs.core.cita.domain.ports.in;

import com.lunorion.labs.core.cita.application.dto.in.CreateCitaRequest;
import com.lunorion.labs.core.cita.application.dto.in.NotificacionesConfigRequest;
import com.lunorion.labs.core.cita.application.dto.in.ReprogramarCitaRequest;
import com.lunorion.labs.core.cita.application.dto.out.CitaResponse;
import com.lunorion.labs.core.cita.application.dto.out.NotificacionesConfigResponse;

public interface ICitaCommandPort {
    CitaResponse crear(CreateCitaRequest request);
    CitaResponse reprogramar(String id, ReprogramarCitaRequest request);
    void confirmar(String id);
    void cancelar(String id);
    void cambiarEstado(String id, String estado);
    NotificacionesConfigResponse updateNotificacionesConfig(NotificacionesConfigRequest request);
}
