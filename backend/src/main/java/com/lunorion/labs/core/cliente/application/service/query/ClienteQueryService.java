package com.lunorion.labs.core.cliente.application.service.query;

import com.lunorion.labs.core.cliente.application.dto.out.ClienteResponse;
import com.lunorion.labs.core.cliente.application.dto.out.HistorialCompraResponse;
import com.lunorion.labs.core.cliente.application.dto.out.HistorialTrabajoResponse;
import com.lunorion.labs.core.cliente.application.dto.out.RentabilidadClienteResponse;
import com.lunorion.labs.core.cliente.application.mapper.ClienteMapper;
import com.lunorion.labs.core.cliente.domain.ports.in.IClienteQueryPort;
import com.lunorion.labs.core.cliente.domain.ports.out.IClienteRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ClienteQueryService implements IClienteQueryPort {

    private final IClienteRepositoryPort repository;
    private final ClienteMapper mapper;

    public ClienteQueryService(IClienteRepositoryPort repository, ClienteMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<ClienteResponse> findById(String id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    @Override
    public Optional<ClienteResponse> findByNumeroDocumento(String numeroDocumento) {
        return repository.findByNumeroDocumento(numeroDocumento).map(mapper::toResponse);
    }

    @Override
    public List<ClienteResponse> findByTenantId(String tenantId) {
        return repository.findByTenantId(tenantId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClienteResponse> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<HistorialTrabajoResponse> workHistory(String id) {
        return Collections.emptyList();
    }

    @Override
    public List<HistorialCompraResponse> purchaseHistory(String id) {
        return Collections.emptyList();
    }

    @Override
    public RentabilidadClienteResponse profitability(String id) {
        RentabilidadClienteResponse r = new RentabilidadClienteResponse();
        r.setClienteId(id);
        r.setTotalFacturado(BigDecimal.ZERO);
        r.setTotalCostos(BigDecimal.ZERO);
        r.setMargen(BigDecimal.ZERO);
        r.setOrdenesCompletadas(0);
        return r;
    }
}
