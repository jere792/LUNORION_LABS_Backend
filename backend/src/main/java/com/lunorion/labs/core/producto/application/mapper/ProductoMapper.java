package com.lunorion.labs.core.producto.application.mapper;

import com.lunorion.labs.core.producto.application.dto.in.CreateCategoriaRequest;
import com.lunorion.labs.core.producto.application.dto.in.CreateProductoRequest;
import com.lunorion.labs.core.producto.application.dto.out.CategoriaResponse;
import com.lunorion.labs.core.producto.application.dto.out.ProductoResponse;
import com.lunorion.labs.core.producto.domain.entity.CategoriaProducto;
import com.lunorion.labs.core.producto.domain.entity.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {

    public Producto toDomain(CreateProductoRequest request) {
        return Producto.create(
            request.getTenantId(),
            request.getCategoriaId(),
            request.getCodigo(),
            request.getCodigoBarra(),
            request.getNombre(),
            request.getDescripcion(),
            request.getMarca(),
            request.getModelo(),
            request.getUnidadMedida(),
            request.getPrecioCompra(),
            request.getPrecioVenta(),
            request.getStockActual(),
            request.getStockMinimo(),
            request.getUbicacion(),
            request.getTipo()
        );
    }

    public ProductoResponse toResponse(Producto producto) {
        ProductoResponse response = new ProductoResponse();
        response.setId(producto.getId().toString());
        response.setCategoriaId(producto.getCategoriaId());
        response.setCodigo(producto.getCodigo());
        response.setCodigoBarra(producto.getCodigoBarra());
        response.setNombre(producto.getNombre());
        response.setDescripcion(producto.getDescripcion());
        response.setMarca(producto.getMarca());
        response.setModelo(producto.getModelo());
        response.setUnidadMedida(producto.getUnidadMedida());
        response.setPrecioCompra(producto.getPrecioCompra());
        response.setPrecioVenta(producto.getPrecioVenta());
        response.setStockActual(producto.getStockActual());
        response.setStockMinimo(producto.getStockMinimo());
        response.setUbicacion(producto.getUbicacion());
        response.setTipo(producto.getTipo());
        response.setActivo(producto.isActivo());
        return response;
    }

    public CategoriaProducto toCategoriaDomain(CreateCategoriaRequest request) {
        return CategoriaProducto.create(
            request.getTenantId(),
            request.getNombre(),
            request.getCategoriaPadreId()
        );
    }

    public CategoriaResponse toCategoriaResponse(CategoriaProducto categoria) {
        CategoriaResponse response = new CategoriaResponse();
        response.setId(categoria.getId().toString());
        response.setTenantId(categoria.getTenantId());
        response.setNombre(categoria.getNombre());
        response.setCategoriaPadreId(categoria.getCategoriaPadreId());
        return response;
    }
}
