package com.lunorion.labs.core.cita.application.service.query;

import com.lunorion.labs.core.cita.application.dto.out.CitaResponse;
import com.lunorion.labs.core.cita.application.dto.out.DisponibilidadResponse;
import com.lunorion.labs.core.cita.application.dto.out.NotificacionesConfigResponse;
import com.lunorion.labs.core.cita.application.mapper.CitaMapper;
import com.lunorion.labs.core.cita.domain.ports.in.ICitaQueryPort;
import com.lunorion.labs.core.cita.domain.ports.out.ICitaRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class CitaQueryService implements ICitaQueryPort {

    private final ICitaRepositoryPort repository;
    private final CitaMapper mapper;

    public CitaQueryService(ICitaRepositoryPort repository, CitaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<CitaResponse> findById(String id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    @Override
    public List<CitaResponse> findByTenantId(String tenantId) {
        return repository.findByTenantId(tenantId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CitaResponse> findByClienteId(String clienteId) {
        return repository.findByClienteId(clienteId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CitaResponse> findByTecnicoId(String tecnicoId) {
        return repository.findByTecnicoId(tecnicoId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CitaResponse> calendario(String tenantId, LocalDate desde, LocalDate hasta) {
        return repository.findByFechaHoraBetween(desde, hasta).stream()
                .filter(c -> c.getTenantId().equals(tenantId))
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<DisponibilidadResponse> disponibilidad(LocalDate fecha, String tecnicoId) {
        List<CitaResponse> citas = repository.findByTecnicoIdAndFechaHoraBetween(tecnicoId, fecha, fecha).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());

        List<DisponibilidadResponse> slots = new ArrayList<>();
        LocalTime hora = LocalTime.of(8, 0);
        LocalTime fin = LocalTime.of(18, 0);

        while (hora.isBefore(fin)) {
            String horaStr = hora.toString();
            boolean ocupado = citas.stream()
                    .anyMatch(c -> c.getFechaHora() != null && c.getFechaHora().startsWith(horaStr));
            slots.add(new DisponibilidadResponse(horaStr, !ocupado));
            hora = hora.plusMinutes(30);
        }
        return slots;
    }

    @Override
    public NotificacionesConfigResponse getNotificacionesConfig(String tenantId) {
        NotificacionesConfigResponse response = new NotificacionesConfigResponse();
        response.setTenantId(tenantId);
        response.setNotificarWhatsapp(true);
        response.setRecordatorioAutomatico(true);
        response.setAnticipacionMinutos(60);
        return response;
    }
}
