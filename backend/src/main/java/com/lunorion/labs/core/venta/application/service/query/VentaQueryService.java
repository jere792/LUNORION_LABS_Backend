package com.lunorion.labs.core.venta.application.service.query;

import com.lunorion.labs.core.venta.application.dto.out.VentaResponse;
import com.lunorion.labs.core.venta.application.mapper.VentaMapper;
import com.lunorion.labs.core.venta.domain.ports.in.IVentaQueryPort;
import com.lunorion.labs.core.venta.domain.ports.out.IVentaRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class VentaQueryService implements IVentaQueryPort {

    private final IVentaRepositoryPort repository;
    private final VentaMapper mapper;

    public VentaQueryService(IVentaRepositoryPort repository, VentaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<VentaResponse> findById(String id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    @Override
    public List<VentaResponse> findByClienteId(String clienteId) {
        return repository.findByClienteId(clienteId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<VentaResponse> findByTenantId(String tenantId) {
        return repository.findByTenantId(tenantId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<VentaResponse> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }
}
