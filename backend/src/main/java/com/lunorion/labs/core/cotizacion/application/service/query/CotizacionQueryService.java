package com.lunorion.labs.core.cotizacion.application.service.query;

import com.lunorion.labs.core.cotizacion.application.dto.out.CotizacionResponse;
import com.lunorion.labs.core.cotizacion.application.mapper.CotizacionMapper;
import com.lunorion.labs.core.cotizacion.domain.ports.in.ICotizacionQueryPort;
import com.lunorion.labs.core.cotizacion.domain.ports.out.ICotizacionRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class CotizacionQueryService implements ICotizacionQueryPort {

    private final ICotizacionRepositoryPort repository;
    private final CotizacionMapper mapper;

    public CotizacionQueryService(ICotizacionRepositoryPort repository, CotizacionMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<CotizacionResponse> findById(String id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    @Override
    public List<CotizacionResponse> findByTenantId(String tenantId) {
        return repository.findByTenantId(tenantId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CotizacionResponse> findByClienteId(String clienteId) {
        return repository.findByClienteId(clienteId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CotizacionResponse> findByEstado(String estado) {
        return repository.findByEstado(estado).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CotizacionResponse> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }
}
