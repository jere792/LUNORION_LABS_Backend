package com.lunorion.labs.core.vehiculo.application.service.query;

import com.lunorion.labs.core.vehiculo.application.dto.out.VehiculoResponse;
import com.lunorion.labs.core.vehiculo.application.mapper.VehiculoMapper;
import com.lunorion.labs.core.vehiculo.domain.ports.in.IVehiculoQueryPort;
import com.lunorion.labs.core.vehiculo.domain.ports.out.IVehiculoRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class VehiculoQueryService implements IVehiculoQueryPort {

    private final IVehiculoRepositoryPort repository;
    private final VehiculoMapper mapper;

    public VehiculoQueryService(IVehiculoRepositoryPort repository, VehiculoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<VehiculoResponse> findById(String id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    @Override
    public List<VehiculoResponse> findByTenantId(String tenantId) {
        return repository.findByTenantId(tenantId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<VehiculoResponse> findByClienteId(String clienteId) {
        return repository.findByClienteId(clienteId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<VehiculoResponse> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }
}
