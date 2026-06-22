package com.lunorion.labs.core.cliente.infrastructure.adapters.in.http;

import com.lunorion.labs.core.cliente.application.dto.in.CreateClienteRequest;
import com.lunorion.labs.core.cliente.application.dto.out.ClienteResponse;
import com.lunorion.labs.core.cliente.application.dto.out.HistorialCompraResponse;
import com.lunorion.labs.core.cliente.application.dto.out.HistorialTrabajoResponse;
import com.lunorion.labs.core.cliente.application.dto.out.RentabilidadClienteResponse;
import com.lunorion.labs.core.cliente.domain.ports.in.IClienteCommandPort;
import com.lunorion.labs.core.cliente.domain.ports.in.IClienteQueryPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final IClienteCommandPort commandService;
    private final IClienteQueryPort queryService;

    public ClienteController(IClienteCommandPort commandService, IClienteQueryPort queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    public ResponseEntity<ClienteResponse> create(@RequestBody CreateClienteRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commandService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> update(@PathVariable String id, @RequestBody CreateClienteRequest request) {
        return ResponseEntity.ok(commandService.update(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> findById(@PathVariable String id) {
        return queryService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponse>> findAll() {
        return ResponseEntity.ok(queryService.findAll());
    }

    @GetMapping("/documento/{numero}")
    public ResponseEntity<ClienteResponse> findByNumeroDocumento(@PathVariable String numero) {
        return queryService.findByNumeroDocumento(numero)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<List<ClienteResponse>> findByTenantId(@PathVariable String tenantId) {
        return ResponseEntity.ok(queryService.findByTenantId(tenantId));
    }

    @PostMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivar(@PathVariable String id) {
        commandService.desactivar(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/historial-trabajos")
    public ResponseEntity<List<HistorialTrabajoResponse>> workHistory(@PathVariable String id) {
        return ResponseEntity.ok(queryService.workHistory(id));
    }

    @GetMapping("/{id}/historial-compras")
    public ResponseEntity<List<HistorialCompraResponse>> purchaseHistory(@PathVariable String id) {
        return ResponseEntity.ok(queryService.purchaseHistory(id));
    }

    @GetMapping("/{id}/rentabilidad")
    public ResponseEntity<RentabilidadClienteResponse> profitability(@PathVariable String id) {
        return ResponseEntity.ok(queryService.profitability(id));
    }
}
