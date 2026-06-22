package com.lunorion.labs.core.cita.application.service.command;

import com.lunorion.labs.core.cita.application.dto.in.CreateCitaRequest;
import com.lunorion.labs.core.cita.application.dto.in.NotificacionesConfigRequest;
import com.lunorion.labs.core.cita.application.dto.in.ReprogramarCitaRequest;
import com.lunorion.labs.core.cita.application.dto.out.CitaResponse;
import com.lunorion.labs.core.cita.application.dto.out.NotificacionesConfigResponse;
import com.lunorion.labs.core.cita.application.mapper.CitaMapper;
import com.lunorion.labs.core.cita.domain.entity.Cita;
import com.lunorion.labs.core.cita.domain.ports.in.ICitaCommandPort;
import com.lunorion.labs.core.cita.domain.ports.out.ICitaRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class CitaCommandService implements ICitaCommandPort {

    private final ICitaRepositoryPort repository;
    private final CitaMapper mapper;

    public CitaCommandService(ICitaRepositoryPort repository, CitaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public CitaResponse crear(CreateCitaRequest request) {
        Cita domain = mapper.toDomain(request);
        Cita saved = repository.save(domain);
        return mapper.toResponse(saved);
    }

    @Override
    public CitaResponse reprogramar(String id, ReprogramarCitaRequest request) {
        Cita cita = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita not found with id: " + id));
        cita.reprogramar(LocalDateTime.parse(request.getFechaHora()));
        Cita saved = repository.save(cita);
        return mapper.toResponse(saved);
    }

    @Override
    public void confirmar(String id) {
        repository.findById(id).ifPresent(c -> {
            c.confirmar();
            repository.save(c);
        });
    }

    @Override
    public void cancelar(String id) {
        repository.findById(id).ifPresent(c -> {
            c.cancelar();
            repository.save(c);
        });
    }

    @Override
    public void cambiarEstado(String id, String estado) {
        repository.findById(id).ifPresent(c -> {
            c.cambiarEstado(estado);
            repository.save(c);
        });
    }

    @Override
    public NotificacionesConfigResponse updateNotificacionesConfig(NotificacionesConfigRequest request) {
        NotificacionesConfigResponse response = new NotificacionesConfigResponse();
        response.setTenantId(request.getTenantId());
        response.setNotificarWhatsapp(request.isNotificarWhatsapp());
        response.setRecordatorioAutomatico(request.isRecordatorioAutomatico());
        response.setAnticipacionMinutos(request.getAnticipacionMinutos());
        return response;
    }
}
