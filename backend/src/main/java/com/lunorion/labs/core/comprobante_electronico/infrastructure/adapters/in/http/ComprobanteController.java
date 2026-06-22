package com.lunorion.labs.core.comprobante_electronico.infrastructure.adapters.in.http;

import com.lunorion.labs.core.comprobante_electronico.application.dto.in.*;
import com.lunorion.labs.core.comprobante_electronico.application.dto.out.*;
import com.lunorion.labs.core.comprobante_electronico.domain.ports.in.IComprobanteCommandPort;
import com.lunorion.labs.core.comprobante_electronico.domain.ports.in.IComprobanteQueryPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comprobantes")
public class ComprobanteController {

    private final IComprobanteCommandPort commandService;
    private final IComprobanteQueryPort queryService;

    public ComprobanteController(IComprobanteCommandPort commandService, IComprobanteQueryPort queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    public ResponseEntity<ComprobanteResponse> create(@RequestBody CreateComprobanteRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commandService.create(request));
    }

    @PostMapping("/recibo")
    public ResponseEntity<ComprobanteResponse> emitirBoleta(@RequestBody EmitirBoletaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commandService.emitirBoleta(request));
    }

    @PostMapping("/nota-credito")
    public ResponseEntity<ComprobanteResponse> emitirNotaCredito(@RequestBody NotaCreditoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commandService.emitirNotaCredito(request));
    }

    @PostMapping("/nota-debito")
    public ResponseEntity<ComprobanteResponse> emitirNotaDebito(@RequestBody NotaDebitoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commandService.emitirNotaDebito(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComprobanteResponse> findById(@PathVariable String id) {
        return queryService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<List<ComprobanteResponse>> findByTenant(@PathVariable String tenantId) {
        return ResponseEntity.ok(queryService.findByTenantId(tenantId));
    }

    @GetMapping("/venta/{ventaId}")
    public ResponseEntity<List<ComprobanteResponse>> findByVenta(@PathVariable String ventaId) {
        return ResponseEntity.ok(queryService.findByVentaId(ventaId));
    }

    @PostMapping("/{id}/firmar")
    public ResponseEntity<Void> firmar(@PathVariable String id) {
        commandService.firmar(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/enviar")
    public ResponseEntity<Void> enviarSunat(@PathVariable String id) {
        commandService.enviarSunat(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/aceptar")
    public ResponseEntity<Void> aceptar(@PathVariable String id) {
        commandService.aceptar(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/rechazar")
    public ResponseEntity<Void> rechazar(@PathVariable String id, @RequestParam String error) {
        commandService.rechazar(id, error);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/cdr")
    public ResponseEntity<CdrResponse> descargarCdr(@PathVariable String id) {
        return ResponseEntity.ok(queryService.descargarCdr(id));
    }

    @GetMapping("/{id}/xml")
    public ResponseEntity<String> descargarXml(@PathVariable String id) {
        return ResponseEntity.ok(queryService.descargarXml(id));
    }

    @PostMapping("/{id}/reenviar")
    public ResponseEntity<Void> reenviar(@PathVariable String id) {
        commandService.reenviar(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/resumen-diario")
    public ResponseEntity<ResumenDiarioResponse> generarResumenDiario(@RequestBody CreateResumenDiarioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commandService.generarResumenDiario(request));
    }

    @GetMapping("/resumen-diario/{id}")
    public ResponseEntity<ResumenDiarioResponse> estadoResumenDiario(@PathVariable String id) {
        return queryService.estadoResumenDiario(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/reporte-facturacion")
    public ResponseEntity<ReporteFacturacionResponse> reporteFacturacion(
            @RequestParam String tenantId,
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin) {
        return ResponseEntity.ok(queryService.reporteFacturacion(tenantId, fechaInicio, fechaFin));
    }

    @GetMapping("/ple")
    public ResponseEntity<PleResponse> generarPle(@RequestParam String tenantId, @RequestParam String periodo) {
        return ResponseEntity.ok(queryService.generarPle(tenantId, periodo));
    }

    @GetMapping("/ple/descargar")
    public ResponseEntity<PleResponse> descargarPle(@RequestParam String id) {
        return ResponseEntity.ok(queryService.descargarPle(id));
    }
}
