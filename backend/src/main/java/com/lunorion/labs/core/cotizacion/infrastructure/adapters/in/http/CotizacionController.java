package com.lunorion.labs.core.cotizacion.infrastructure.adapters.in.http;

import com.lunorion.labs.core.cotizacion.application.dto.in.CreateCotizacionRequest;
import com.lunorion.labs.core.cotizacion.application.dto.out.CotizacionResponse;
import com.lunorion.labs.core.cotizacion.domain.ports.in.ICotizacionCommandPort;
import com.lunorion.labs.core.cotizacion.domain.ports.in.ICotizacionQueryPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cotizaciones")
public class CotizacionController {

    private final ICotizacionCommandPort commandService;
    private final ICotizacionQueryPort queryService;

    public CotizacionController(ICotizacionCommandPort commandService, ICotizacionQueryPort queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    public ResponseEntity<CotizacionResponse> create(@RequestBody CreateCotizacionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commandService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<CotizacionResponse>> findAll() {
        return ResponseEntity.ok(queryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CotizacionResponse> findById(@PathVariable String id) {
        return queryService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<List<CotizacionResponse>> findByTenantId(@PathVariable String tenantId) {
        return ResponseEntity.ok(queryService.findByTenantId(tenantId));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<CotizacionResponse>> findByClienteId(@PathVariable String clienteId) {
        return ResponseEntity.ok(queryService.findByClienteId(clienteId));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<CotizacionResponse>> findByEstado(@PathVariable String estado) {
        return ResponseEntity.ok(queryService.findByEstado(estado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CotizacionResponse> update(@PathVariable String id, @RequestBody CreateCotizacionRequest request) {
        return ResponseEntity.ok(commandService.update(id, request));
    }

    @PostMapping("/{id}/enviar")
    public ResponseEntity<Void> enviar(@PathVariable String id) {
        commandService.enviar(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/aprobar")
    public ResponseEntity<Void> aprobar(@PathVariable String id) {
        commandService.aprobar(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/rechazar")
    public ResponseEntity<Void> rechazar(@PathVariable String id) {
        commandService.rechazar(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/convertir-ot")
    public ResponseEntity<Void> convertirEnOt(@PathVariable String id) {
        commandService.convertirEnOt(id);
        return ResponseEntity.ok().build();
    }
}
