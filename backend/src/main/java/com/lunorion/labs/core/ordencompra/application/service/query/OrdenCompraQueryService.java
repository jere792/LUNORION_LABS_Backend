package com.lunorion.labs.core.ordencompra.application.service.query;

import com.lunorion.labs.core.ordencompra.application.dto.out.OrdenCompraItemResponse;
import com.lunorion.labs.core.ordencompra.application.dto.out.OrdenCompraResponse;
import com.lunorion.labs.core.ordencompra.application.dto.out.ReporteGastosResponse;
import com.lunorion.labs.core.ordencompra.application.mapper.OrdenCompraMapper;
import com.lunorion.labs.core.ordencompra.domain.ports.in.IOrdenCompraQueryPort;
import com.lunorion.labs.core.ordencompra.domain.ports.out.IOrdenCompraItemRepositoryPort;
import com.lunorion.labs.core.ordencompra.domain.ports.out.IOrdenCompraRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class OrdenCompraQueryService implements IOrdenCompraQueryPort {

    private final IOrdenCompraRepositoryPort repository;
    private final IOrdenCompraItemRepositoryPort itemRepository;
    private final OrdenCompraMapper mapper;

    public OrdenCompraQueryService(IOrdenCompraRepositoryPort repository,
                                   IOrdenCompraItemRepositoryPort itemRepository,
                                   OrdenCompraMapper mapper) {
        this.repository = repository;
        this.itemRepository = itemRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<OrdenCompraResponse> findById(String id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    @Override
    public List<OrdenCompraResponse> findByTenantId(String tenantId) {
        return repository.findByTenantId(tenantId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrdenCompraResponse> findByProveedorId(String proveedorId) {
        return repository.findByProveedorId(proveedorId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<OrdenCompraItemResponse> findItemsByOrdenCompraId(String ordenCompraId) {
        return itemRepository.findByOrdenCompraId(ordenCompraId).stream()
                .map(mapper::toItemResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ReporteGastosResponse spendingReport(String tenantId, LocalDate desde, LocalDate hasta) {
        List<OrdenCompraResponse> ordenes = repository.findByTenantId(tenantId).stream()
                .filter(oc -> oc.getFechaEmision() != null
                        && !oc.getFechaEmision().isBefore(desde)
                        && !oc.getFechaEmision().isAfter(hasta))
                .map(mapper::toResponse)
                .collect(Collectors.toList());

        ReporteGastosResponse report = new ReporteGastosResponse();
        report.setTenantId(tenantId);
        report.setDesde(desde);
        report.setHasta(hasta);
        report.setTotalGastos(ordenes.stream()
                .map(OrdenCompraResponse::getTotal)
                .filter(t -> t != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        report.setGastos(ordenes.stream().map(oc -> {
            ReporteGastosResponse.GastoItem item = new ReporteGastosResponse.GastoItem();
            item.setOrdenCompraId(oc.getId());
            item.setNumeroOrden(oc.getNumeroOrden());
            item.setProveedorId(oc.getProveedorId());
            item.setFechaEmision(oc.getFechaEmision());
            item.setTotal(oc.getTotal());
            item.setEstado(oc.getEstado());
            return item;
        }).collect(Collectors.toList()));
        return report;
    }
}
