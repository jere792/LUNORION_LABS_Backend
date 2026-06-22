package com.lunorion.labs.core.comprobante_electronico.domain.ports.in;

import com.lunorion.labs.core.comprobante_electronico.application.dto.out.CdrResponse;
import com.lunorion.labs.core.comprobante_electronico.application.dto.out.ComprobanteResponse;
import com.lunorion.labs.core.comprobante_electronico.application.dto.out.PleResponse;
import com.lunorion.labs.core.comprobante_electronico.application.dto.out.ReporteFacturacionResponse;
import com.lunorion.labs.core.comprobante_electronico.application.dto.out.ResumenDiarioResponse;

import java.util.List;
import java.util.Optional;

public interface IComprobanteQueryPort {
    Optional<ComprobanteResponse> findById(String id);
    List<ComprobanteResponse> findByTenantId(String tenantId);
    List<ComprobanteResponse> findByVentaId(String ventaId);
    CdrResponse descargarCdr(String id);
    String descargarXml(String id);
    ReporteFacturacionResponse reporteFacturacion(String tenantId, String fechaInicio, String fechaFin);
    Optional<ResumenDiarioResponse> estadoResumenDiario(String id);
    PleResponse generarPle(String tenantId, String periodo);
    PleResponse descargarPle(String id);
}
