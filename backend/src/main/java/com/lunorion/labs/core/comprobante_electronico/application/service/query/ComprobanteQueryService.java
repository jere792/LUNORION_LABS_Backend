package com.lunorion.labs.core.comprobante_electronico.application.service.query;

import com.lunorion.labs.core.comprobante_electronico.application.dto.out.CdrResponse;
import com.lunorion.labs.core.comprobante_electronico.application.dto.out.ComprobanteResponse;
import com.lunorion.labs.core.comprobante_electronico.application.dto.out.PleResponse;
import com.lunorion.labs.core.comprobante_electronico.application.dto.out.ReporteFacturacionResponse;
import com.lunorion.labs.core.comprobante_electronico.application.dto.out.ResumenDiarioResponse;
import com.lunorion.labs.core.comprobante_electronico.application.mapper.ComprobanteMapper;
import com.lunorion.labs.core.comprobante_electronico.application.mapper.ResumenDiarioMapper;
import com.lunorion.labs.core.comprobante_electronico.domain.entity.ComprobanteElectronico;
import com.lunorion.labs.core.comprobante_electronico.domain.ports.in.IComprobanteQueryPort;
import com.lunorion.labs.core.comprobante_electronico.domain.ports.out.IComprobanteRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ComprobanteQueryService implements IComprobanteQueryPort {

    private final IComprobanteRepositoryPort repository;
    private final ComprobanteMapper mapper;
    private final ResumenDiarioMapper resumenDiarioMapper;

    public ComprobanteQueryService(IComprobanteRepositoryPort repository, ComprobanteMapper mapper,
                                   ResumenDiarioMapper resumenDiarioMapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.resumenDiarioMapper = resumenDiarioMapper;
    }

    @Override
    public Optional<ComprobanteResponse> findById(String id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    @Override
    public List<ComprobanteResponse> findByTenantId(String tenantId) {
        return repository.findByTenantId(tenantId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ComprobanteResponse> findByVentaId(String ventaId) {
        return repository.findByVentaId(ventaId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CdrResponse descargarCdr(String id) {
        return repository.findById(id)
                .map(c -> {
                    CdrResponse r = new CdrResponse();
                    r.setCodigo(c.getCodigoErrorSunat() != null ? c.getCodigoErrorSunat() : "0000");
                    r.setDescripcion(c.getDescripcionError() != null ? c.getDescripcionError() : "Comprobante aceptado");
                    r.setFechaRecepcion(java.time.LocalDateTime.now().toString());
                    return r;
                })
                .orElseThrow(() -> new RuntimeException("CDR no encontrado para comprobante: " + id));
    }

    @Override
    public String descargarXml(String id) {
        return repository.findById(id)
                .map(c -> c.getXmlFirmado() != null ? c.getXmlFirmado() : "<sin-firma/>")
                .orElseThrow(() -> new RuntimeException("XML no encontrado para comprobante: " + id));
    }

    @Override
    public ReporteFacturacionResponse reporteFacturacion(String tenantId, String fechaInicio, String fechaFin) {
        List<ComprobanteResponse> comprobantes = repository
                .findByTenantIdAndFechaEmisionBetween(tenantId, fechaInicio, fechaFin).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
        ReporteFacturacionResponse r = new ReporteFacturacionResponse();
        r.setPeriodo(fechaInicio + " - " + fechaFin);
        r.setTotalComprobantes(comprobantes.size());
        r.setTotalMontoFacturado(comprobantes.stream()
                .map(c -> c.getMontoTotal() != null ? c.getMontoTotal() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        r.setTotalAceptados((int) comprobantes.stream().filter(c -> "ACEPTADO".equals(c.getEstadoSunat())).count());
        r.setTotalRechazados((int) comprobantes.stream().filter(c -> "RECHAZADO".equals(c.getEstadoSunat())).count());
        r.setTotalPendientes((int) comprobantes.stream().filter(c -> "PENDIENTE".equals(c.getEstadoSunat())).count());
        return r;
    }

    @Override
    public Optional<ResumenDiarioResponse> estadoResumenDiario(String id) {
        return repository.findResumenDiarioById(id).map(resumenDiarioMapper::toResponse);
    }

    @Override
    public PleResponse generarPle(String tenantId, String periodo) {
        PleResponse r = new PleResponse();
        r.setId(java.util.UUID.randomUUID().toString());
        r.setPeriodo(periodo);
        r.setEstado("GENERADO");
        r.setArchivoUrl("/ple/" + tenantId + "/" + periodo + ".xml");
        return r;
    }

    @Override
    public PleResponse descargarPle(String id) {
        PleResponse r = new PleResponse();
        r.setId(id);
        r.setEstado("DISPONIBLE");
        r.setArchivoUrl("/ple/descargas/" + id + ".xml");
        return r;
    }
}
