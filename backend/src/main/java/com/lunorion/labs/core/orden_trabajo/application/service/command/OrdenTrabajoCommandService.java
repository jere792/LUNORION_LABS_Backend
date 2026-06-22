package com.lunorion.labs.core.orden_trabajo.application.service.command;

import com.lunorion.labs.core.orden_trabajo.application.dto.in.*;
import com.lunorion.labs.core.orden_trabajo.application.dto.out.CierreOtResponse;
import com.lunorion.labs.core.orden_trabajo.application.dto.out.OrdenTrabajoResponse;
import com.lunorion.labs.core.orden_trabajo.application.mapper.OrdenTrabajoMapper;
import com.lunorion.labs.core.orden_trabajo.domain.entity.OrdenTrabajo;
import com.lunorion.labs.core.orden_trabajo.domain.entity.OtInsumo;
import com.lunorion.labs.core.orden_trabajo.domain.entity.OtManoObra;
import com.lunorion.labs.core.orden_trabajo.domain.ports.in.IOrdenTrabajoCommandPort;
import com.lunorion.labs.core.orden_trabajo.domain.ports.out.IOrdenTrabajoRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class OrdenTrabajoCommandService implements IOrdenTrabajoCommandPort {

    private final IOrdenTrabajoRepositoryPort repository;
    private final OrdenTrabajoMapper mapper;

    public OrdenTrabajoCommandService(IOrdenTrabajoRepositoryPort repository, OrdenTrabajoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public OrdenTrabajoResponse create(CreateOrdenTrabajoRequest request) {
        OrdenTrabajo ot = mapper.toDomain(request);
        OrdenTrabajo saved = repository.save(ot);

        List<OtInsumo> insumos = mapper.toInsumosDomain(saved.getId().toString(), request.getInsumos());
        repository.saveAllInsumos(insumos);

        List<OtManoObra> manosObra = mapper.toManosObraDomain(saved.getId().toString(), request.getManosObra());
        repository.saveAllManoObra(manosObra);

        OrdenTrabajoResponse response = mapper.toResponse(saved);
        response.setInsumos(mapper.toInsumosResponse(insumos));
        response.setManosObra(mapper.toManosObraResponse(manosObra));
        return response;
    }

    @Override
    public OrdenTrabajoResponse update(String id, CreateOrdenTrabajoRequest request) {
        OrdenTrabajo ot = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden de trabajo no encontrada: " + id));
        ot.actualizar(
                request.getClienteId(),
                request.getVehiculoId(),
                request.getTecnicoId(),
                request.getMotivoIngreso(),
                request.getKilometrajeIngreso(),
                request.getKilometrajeSalida(),
                request.getFechaPrometida()
        );
        OrdenTrabajo saved = repository.save(ot);
        return mapper.toResponse(saved);
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    public OrdenTrabajoResponse cambiarEstado(String id, CambioEstadoRequest request) {
        OrdenTrabajo ot = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden de trabajo no encontrada: " + id));
        ot.setEstado(request.getEstado());
        OrdenTrabajo saved = repository.save(ot);
        return mapper.toResponse(saved);
    }

    @Override
    public OrdenTrabajoResponse addInsumo(String id, AddInsumoRequest request) {
        OrdenTrabajo ot = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden de trabajo no encontrada: " + id));
        BigDecimal subtotal = request.getCantidad().multiply(request.getPrecioUnitario());
        OtInsumo insumo = OtInsumo.create(id, request.getProductoId(), request.getCantidad(), request.getPrecioUnitario(), subtotal);
        repository.saveInsumo(insumo);
        ot.setTotalRepuestos(ot.getTotalRepuestos().add(subtotal));
        ot.setTotal(ot.getTotal().add(subtotal));
        repository.save(ot);
        OrdenTrabajoResponse response = mapper.toResponse(ot);
        response.setInsumos(mapper.toInsumosResponse(repository.findInsumosByOrdenTrabajoId(id)));
        return response;
    }

    @Override
    public void removeInsumo(String id, String insumoId) {
        OrdenTrabajo ot = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden de trabajo no encontrada: " + id));
        OtInsumo insumo = repository.findInsumoById(insumoId)
                .orElseThrow(() -> new RuntimeException("Insumo no encontrado: " + insumoId));
        ot.setTotalRepuestos(ot.getTotalRepuestos().subtract(insumo.getSubtotal()));
        ot.setTotal(ot.getTotal().subtract(insumo.getSubtotal()));
        repository.deleteInsumoById(insumoId);
        repository.save(ot);
    }

    @Override
    public OrdenTrabajoResponse addLabor(String id, RegistroLaborRequest request) {
        OrdenTrabajo ot = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden de trabajo no encontrada: " + id));
        BigDecimal subtotal = request.getHoras().multiply(request.getTarifaHora());
        OtManoObra labor = OtManoObra.create(id, request.getTecnicoId(), request.getServicioDescripcion(),
                request.getHoras(), request.getTarifaHora(), subtotal);
        repository.saveManoObra(labor);
        ot.setTotalManoObra(ot.getTotalManoObra().add(subtotal));
        ot.setTotal(ot.getTotal().add(subtotal));
        repository.save(ot);
        OrdenTrabajoResponse response = mapper.toResponse(ot);
        response.setManosObra(mapper.toManosObraResponse(repository.findManosObraByOrdenTrabajoId(id)));
        return response;
    }

    @Override
    public OrdenTrabajoResponse updateLabor(String id, String laborId, RegistroLaborRequest request) {
        OrdenTrabajo ot = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden de trabajo no encontrada: " + id));
        OtManoObra labor = repository.findManoObraById(laborId)
                .orElseThrow(() -> new RuntimeException("Labor no encontrada: " + laborId));
        BigDecimal subtotal = request.getHoras().multiply(request.getTarifaHora());
        OtManoObra updated = new OtManoObra(labor.getId(), id, request.getTecnicoId(), request.getServicioDescripcion(),
                request.getHoras(), request.getTarifaHora(), subtotal);
        repository.saveManoObra(updated);
        ot.setTotalManoObra(ot.getTotalManoObra().subtract(labor.getSubtotal()).add(subtotal));
        ot.setTotal(ot.getTotal().subtract(labor.getSubtotal()).add(subtotal));
        repository.save(ot);
        OrdenTrabajoResponse response = mapper.toResponse(ot);
        response.setManosObra(mapper.toManosObraResponse(repository.findManosObraByOrdenTrabajoId(id)));
        return response;
    }

    @Override
    public CierreOtResponse close(String id) {
        OrdenTrabajo ot = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden de trabajo no encontrada: " + id));
        BigDecimal totalRepuestos = ot.getTotalRepuestos() != null ? ot.getTotalRepuestos() : BigDecimal.ZERO;
        BigDecimal totalManoObra = ot.getTotalManoObra() != null ? ot.getTotalManoObra() : BigDecimal.ZERO;
        BigDecimal total = totalRepuestos.add(totalManoObra);
        ot.setTotal(total);
        ot.cerrar("SISTEMA");
        repository.save(ot);
        CierreOtResponse response = new CierreOtResponse();
        response.setId(ot.getId().toString());
        response.setTotalRepuestos(totalRepuestos);
        response.setTotalManoObra(totalManoObra);
        response.setTotal(total);
        response.setPuedeFacturar(true);
        return response;
    }

    @Override
    public OrdenTrabajoResponse reopen(String id) {
        OrdenTrabajo ot = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden de trabajo no encontrada: " + id));
        ot.reabrir();
        OrdenTrabajo saved = repository.save(ot);
        return mapper.toResponse(saved);
    }
}
