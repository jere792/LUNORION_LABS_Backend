package com.lunorion.labs.core.venta.infrastructure.adapters.in.http;

import com.lunorion.labs.core.venta.application.dto.in.CreateVentaRequest;
import com.lunorion.labs.core.venta.application.dto.out.VentaResponse;
import com.lunorion.labs.core.venta.domain.ports.in.IVentaCommandPort;
import com.lunorion.labs.core.venta.domain.ports.in.IVentaQueryPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    private final IVentaCommandPort commandService;
    private final IVentaQueryPort queryService;

    public VentaController(IVentaCommandPort commandService, IVentaQueryPort queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    public ResponseEntity<VentaResponse> create(@RequestBody CreateVentaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commandService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaResponse> findById(@PathVariable String id) {
        return queryService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<VentaResponse>> findAll() {
        return ResponseEntity.ok(queryService.findAll());
    }

    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<List<VentaResponse>> findByTenant(@PathVariable String tenantId) {
        return ResponseEntity.ok(queryService.findByTenantId(tenantId));
    }

    @GetMapping("/clientes/{clienteId}/historial")
    public ResponseEntity<List<VentaResponse>> customerHistory(@PathVariable String clienteId) {
        return ResponseEntity.ok(queryService.findByClienteId(clienteId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        commandService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
