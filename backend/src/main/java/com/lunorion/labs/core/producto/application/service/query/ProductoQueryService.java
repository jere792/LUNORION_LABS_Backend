package com.lunorion.labs.core.producto.application.service.query;

import com.lunorion.labs.core.producto.application.dto.out.CategoriaResponse;
import com.lunorion.labs.core.producto.application.dto.out.ProductoResponse;
import com.lunorion.labs.core.producto.application.mapper.ProductoMapper;
import com.lunorion.labs.core.producto.domain.ports.in.IProductoQueryPort;
import com.lunorion.labs.core.producto.domain.ports.out.IProductoRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ProductoQueryService implements IProductoQueryPort {

    private final IProductoRepositoryPort repository;
    private final ProductoMapper mapper;

    public ProductoQueryService(IProductoRepositoryPort repository, ProductoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<ProductoResponse> findById(String id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    @Override
    public Optional<ProductoResponse> findByCodigo(String codigo) {
        return repository.findByCodigo(codigo).map(mapper::toResponse);
    }

    @Override
    public List<ProductoResponse> findByTenantId(String tenantId) {
        return repository.findByTenantId(tenantId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductoResponse> findByCategoriaId(UUID categoriaId) {
        return repository.findByCategoriaId(categoriaId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductoResponse> findStockCritico(String tenantId) {
        return repository.findStockCritico(tenantId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductoResponse> reporteRotacion(String tenantId) {
        return repository.findByTenantId(tenantId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoriaResponse> findAllCategorias() {
        return repository.findAllCategorias().stream()
                .map(mapper::toCategoriaResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CategoriaResponse> findCategoriaById(String id) {
        return repository.findCategoriaById(id).map(mapper::toCategoriaResponse);
    }
}
