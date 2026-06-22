package com.lunorion.labs.core.producto.domain.ports.in;

import com.lunorion.labs.core.producto.application.dto.out.CategoriaResponse;
import com.lunorion.labs.core.producto.application.dto.out.ProductoResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IProductoQueryPort {
    Optional<ProductoResponse> findById(String id);
    Optional<ProductoResponse> findByCodigo(String codigo);
    List<ProductoResponse> findByTenantId(String tenantId);
    List<ProductoResponse> findByCategoriaId(UUID categoriaId);
    List<ProductoResponse> findStockCritico(String tenantId);
    List<ProductoResponse> reporteRotacion(String tenantId);
    List<CategoriaResponse> findAllCategorias();
    Optional<CategoriaResponse> findCategoriaById(String id);
}
