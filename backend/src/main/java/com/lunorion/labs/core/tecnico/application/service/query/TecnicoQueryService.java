package com.lunorion.labs.core.tecnico.application.service.query;

import com.lunorion.labs.core.tecnico.application.dto.out.CargaTrabajoResponse;
import com.lunorion.labs.core.tecnico.application.dto.out.TecnicoResponse;
import com.lunorion.labs.core.tecnico.application.mapper.TecnicoMapper;
import com.lunorion.labs.core.tecnico.domain.ports.in.ITecnicoQueryPort;
import com.lunorion.labs.core.tecnico.domain.ports.out.ITecnicoRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class TecnicoQueryService implements ITecnicoQueryPort {

    private final ITecnicoRepositoryPort repository;
    private final TecnicoMapper mapper;

    public TecnicoQueryService(ITecnicoRepositoryPort repository, TecnicoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<TecnicoResponse> findById(String id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    @Override
    public List<TecnicoResponse> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<TecnicoResponse> findByTenantId(String tenantId) {
        return repository.findByTenantId(tenantId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<TecnicoResponse> findActivosByTenantId(String tenantId) {
        return findByTenantId(tenantId).stream()
                .filter(TecnicoResponse::isActivo)
                .collect(Collectors.toList());
    }

    @Override
    public CargaTrabajoResponse workload(String id) {
        CargaTrabajoResponse r = new CargaTrabajoResponse();
        r.setTecnicoId(id);
        r.setNombre("");
        r.setOrdenesAbiertas(0);
        r.setHorasPendientes(BigDecimal.ZERO);
        return r;
    }
}
