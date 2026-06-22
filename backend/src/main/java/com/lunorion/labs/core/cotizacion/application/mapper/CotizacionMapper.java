package com.lunorion.labs.core.cotizacion.application.mapper;

import com.lunorion.labs.core.cotizacion.application.dto.in.CreateCotizacionRequest;
import com.lunorion.labs.core.cotizacion.application.dto.in.CotizacionItemRequest;
import com.lunorion.labs.core.cotizacion.application.dto.out.CotizacionItemResponse;
import com.lunorion.labs.core.cotizacion.application.dto.out.CotizacionResponse;
import com.lunorion.labs.core.cotizacion.domain.entity.Cotizacion;
import com.lunorion.labs.core.cotizacion.domain.entity.CotizacionItem;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CotizacionMapper {

    public Cotizacion toDomain(CreateCotizacionRequest request) {
        Cotizacion cotizacion = Cotizacion.create(
                request.getTenantId(),
                request.getClienteId(),
                request.getVehiculoId(),
                java.time.LocalDate.now(),
                java.time.LocalDate.now().plusDays(15),
                request.getNotas()
        );
        if (request.getItems() != null) {
            for (CotizacionItemRequest itemReq : request.getItems()) {
                cotizacion.agregarItem(
                        itemReq.getDescripcion(),
                        itemReq.getCantidad(),
                        itemReq.getPrecioUnitario()
                );
            }
        }
        return cotizacion;
    }

    public void updateDomain(Cotizacion cotizacion, CreateCotizacionRequest request) {
        cotizacion.actualizar(
                request.getClienteId(),
                request.getVehiculoId(),
                request.getNotas()
        );
        cotizacion.limpiarItems();
        if (request.getItems() != null) {
            for (CotizacionItemRequest itemReq : request.getItems()) {
                cotizacion.agregarItem(
                        itemReq.getDescripcion(),
                        itemReq.getCantidad(),
                        itemReq.getPrecioUnitario()
                );
            }
        }
    }

    public CotizacionResponse toResponse(Cotizacion cotizacion) {
        CotizacionResponse response = new CotizacionResponse();
        response.setId(cotizacion.getId().toString());
        response.setTenantId(cotizacion.getTenantId());
        response.setClienteId(cotizacion.getClienteId());
        response.setVehiculoId(cotizacion.getVehiculoId());
        response.setFechaEmision(cotizacion.getFechaEmision());
        response.setFechaValidez(cotizacion.getFechaValidez());
        response.setEstado(cotizacion.getEstado());
        response.setSubtotal(cotizacion.getSubtotal());
        response.setIgv(cotizacion.getIgv());
        response.setTotal(cotizacion.getTotal());
        response.setNotas(cotizacion.getNotas());
        response.setActivo(cotizacion.isActivo());
        if (cotizacion.getItems() != null) {
            response.setItems(cotizacion.getItems().stream()
                    .map(this::toItemResponse)
                    .collect(Collectors.toList()));
        }
        return response;
    }

    private CotizacionItemResponse toItemResponse(CotizacionItem item) {
        CotizacionItemResponse resp = new CotizacionItemResponse();
        resp.setId(item.getId().toString());
        resp.setDescripcion(item.getDescripcion());
        resp.setCantidad(item.getCantidad());
        resp.setPrecioUnitario(item.getPrecioUnitario());
        resp.setSubtotal(item.getSubtotal());
        return resp;
    }
}
