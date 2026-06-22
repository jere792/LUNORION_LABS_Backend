package com.lunorion.labs.core.orden_trabajo.application.mapper;

import com.lunorion.labs.core.orden_trabajo.application.dto.in.AddInsumoRequest;
import com.lunorion.labs.core.orden_trabajo.application.dto.in.CreateOrdenTrabajoRequest;
import com.lunorion.labs.core.orden_trabajo.application.dto.in.RegistroLaborRequest;
import com.lunorion.labs.core.orden_trabajo.application.dto.out.OrdenTrabajoResponse;
import com.lunorion.labs.core.orden_trabajo.application.dto.out.OrdenTrabajoResponse.OtInsumoResponse;
import com.lunorion.labs.core.orden_trabajo.application.dto.out.OrdenTrabajoResponse.OtManoObraResponse;
import com.lunorion.labs.core.orden_trabajo.domain.entity.OrdenTrabajo;
import com.lunorion.labs.core.orden_trabajo.domain.entity.OtInsumo;
import com.lunorion.labs.core.orden_trabajo.domain.entity.OtManoObra;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrdenTrabajoMapper {

    public OrdenTrabajo toDomain(CreateOrdenTrabajoRequest request) {
        return OrdenTrabajo.create(
            request.getTenantId(),
            request.getClienteId(),
            request.getVehiculoId(),
            request.getTecnicoId(),
            request.getNumeroOt(),
            request.getEstado(),
            request.getMotivoIngreso(),
            request.getUsuarioCreoId()
        );
    }

    public OtInsumo toInsumoDomain(String ordenTrabajoId, CreateOrdenTrabajoRequest.CreateOtInsumoRequest request) {
        return OtInsumo.create(
            ordenTrabajoId,
            request.getProductoId(),
            request.getCantidad(),
            request.getPrecioUnitario(),
            request.getSubtotal()
        );
    }

    public List<OtInsumo> toInsumosDomain(String ordenTrabajoId, List<CreateOrdenTrabajoRequest.CreateOtInsumoRequest> requests) {
        if (requests == null) return Collections.emptyList();
        return requests.stream()
                .map(r -> toInsumoDomain(ordenTrabajoId, r))
                .collect(Collectors.toList());
    }

    public OtManoObra toManoObraDomain(String ordenTrabajoId, CreateOrdenTrabajoRequest.CreateOtManoObraRequest request) {
        return OtManoObra.create(
            ordenTrabajoId,
            request.getTecnicoId(),
            request.getServicioDescripcion(),
            request.getHoras(),
            request.getTarifaHora(),
            request.getSubtotal()
        );
    }

    public List<OtManoObra> toManosObraDomain(String ordenTrabajoId, List<CreateOrdenTrabajoRequest.CreateOtManoObraRequest> requests) {
        if (requests == null) return Collections.emptyList();
        return requests.stream()
                .map(r -> toManoObraDomain(ordenTrabajoId, r))
                .collect(Collectors.toList());
    }

    public OrdenTrabajoResponse toResponse(OrdenTrabajo ot) {
        OrdenTrabajoResponse response = new OrdenTrabajoResponse();
        response.setId(ot.getId().toString());
        response.setTenantId(ot.getTenantId());
        response.setClienteId(ot.getClienteId());
        response.setVehiculoId(ot.getVehiculoId());
        response.setTecnicoId(ot.getTecnicoId());
        response.setNumeroOt(ot.getNumeroOt());
        response.setEstado(ot.getEstado());
        response.setMotivoIngreso(ot.getMotivoIngreso());
        response.setKilometrajeIngreso(ot.getKilometrajeIngreso());
        response.setKilometrajeSalida(ot.getKilometrajeSalida());
        response.setFechaPrometida(ot.getFechaPrometida());
        response.setFechaCierre(ot.getFechaCierre());
        response.setTotalRepuestos(ot.getTotalRepuestos());
        response.setTotalManoObra(ot.getTotalManoObra());
        response.setTotal(ot.getTotal());
        response.setPuedeFacturar("CERRADO".equals(ot.getEstado()));
        response.setOtOrigenId(ot.getOtOrigenId());
        response.setUsuarioCreoId(ot.getUsuarioCreoId());
        response.setUsuarioCerroId(ot.getUsuarioCerroId());
        return response;
    }

    public OtInsumoResponse toInsumoResponse(OtInsumo insumo) {
        OtInsumoResponse r = new OtInsumoResponse();
        r.setId(insumo.getId().toString());
        r.setProductoId(insumo.getProductoId());
        r.setCantidad(insumo.getCantidad());
        r.setPrecioUnitario(insumo.getPrecioUnitario());
        r.setSubtotal(insumo.getSubtotal());
        return r;
    }

    public List<OtInsumoResponse> toInsumosResponse(List<OtInsumo> insumos) {
        if (insumos == null) return Collections.emptyList();
        return insumos.stream().map(this::toInsumoResponse).collect(Collectors.toList());
    }

    public OtManoObraResponse toManoObraResponse(OtManoObra mo) {
        OtManoObraResponse r = new OtManoObraResponse();
        r.setId(mo.getId().toString());
        r.setTecnicoId(mo.getTecnicoId());
        r.setServicioDescripcion(mo.getServicioDescripcion());
        r.setHoras(mo.getHoras());
        r.setTarifaHora(mo.getTarifaHora());
        r.setSubtotal(mo.getSubtotal());
        return r;
    }

    public List<OtManoObraResponse> toManosObraResponse(List<OtManoObra> manosObra) {
        if (manosObra == null) return Collections.emptyList();
        return manosObra.stream().map(this::toManoObraResponse).collect(Collectors.toList());
    }

    public OtInsumo toInsumoDomain(String ordenTrabajoId, AddInsumoRequest request) {
        BigDecimal subtotal = request.getCantidad().multiply(request.getPrecioUnitario());
        return OtInsumo.create(ordenTrabajoId, request.getProductoId(), request.getCantidad(), request.getPrecioUnitario(), subtotal);
    }

    public OtManoObra toManoObraDomain(String ordenTrabajoId, RegistroLaborRequest request) {
        BigDecimal subtotal = request.getHoras().multiply(request.getTarifaHora());
        return OtManoObra.create(ordenTrabajoId, request.getTecnicoId(), request.getServicioDescripcion(),
                request.getHoras(), request.getTarifaHora(), subtotal);
    }
}
