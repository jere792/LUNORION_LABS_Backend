package com.lunorion.labs.core.cita.domain.ports.in;

import com.lunorion.labs.core.cita.application.dto.out.CitaResponse;
import com.lunorion.labs.core.cita.application.dto.out.DisponibilidadResponse;
import com.lunorion.labs.core.cita.application.dto.out.NotificacionesConfigResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ICitaQueryPort {
    Optional<CitaResponse> findById(String id);
    List<CitaResponse> findByTenantId(String tenantId);
    List<CitaResponse> findByClienteId(String clienteId);
    List<CitaResponse> findByTecnicoId(String tecnicoId);
    List<CitaResponse> calendario(String tenantId, LocalDate desde, LocalDate hasta);
    List<DisponibilidadResponse> disponibilidad(LocalDate fecha, String tecnicoId);
    NotificacionesConfigResponse getNotificacionesConfig(String tenantId);
}
