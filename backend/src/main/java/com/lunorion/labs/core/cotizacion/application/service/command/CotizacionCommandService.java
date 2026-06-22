package com.lunorion.labs.core.cotizacion.application.service.command;

import com.lunorion.labs.core.cotizacion.application.dto.in.CreateCotizacionRequest;
import com.lunorion.labs.core.cotizacion.application.dto.out.CotizacionResponse;
import com.lunorion.labs.core.cotizacion.application.mapper.CotizacionMapper;
import com.lunorion.labs.core.cotizacion.domain.entity.Cotizacion;
import com.lunorion.labs.core.cotizacion.domain.ports.in.ICotizacionCommandPort;
import com.lunorion.labs.core.cotizacion.domain.ports.out.ICotizacionRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CotizacionCommandService implements ICotizacionCommandPort {

    private final ICotizacionRepositoryPort repository;
    private final CotizacionMapper mapper;

    public CotizacionCommandService(ICotizacionRepositoryPort repository, CotizacionMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public CotizacionResponse create(CreateCotizacionRequest request) {
        Cotizacion cotizacion = mapper.toDomain(request);
        Cotizacion saved = repository.save(cotizacion);
        return mapper.toResponse(saved);
    }

    @Override
    public CotizacionResponse update(String id, CreateCotizacionRequest request) {
        Cotizacion cotizacion = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cotizacion no encontrada: " + id));
        mapper.updateDomain(cotizacion, request);
        Cotizacion saved = repository.save(cotizacion);
        return mapper.toResponse(saved);
    }

    @Override
    public void enviar(String id) {
        repository.findById(id).ifPresent(cotizacion -> {
            cotizacion.enviar();
            repository.save(cotizacion);
        });
    }

    @Override
    public void aprobar(String id) {
        repository.findById(id).ifPresent(cotizacion -> {
            cotizacion.aprobar();
            repository.save(cotizacion);
        });
    }

    @Override
    public void rechazar(String id) {
        repository.findById(id).ifPresent(cotizacion -> {
            cotizacion.rechazar();
            repository.save(cotizacion);
        });
    }

    @Override
    public void convertirEnOt(String id) {
        repository.findById(id).ifPresent(cotizacion -> {
            cotizacion.convertirEnOt();
            repository.save(cotizacion);
        });
    }
}
