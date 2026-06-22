package com.lunorion.labs.core.vehiculo.infrastructure.adapters.in.http;

import com.lunorion.labs.core.vehiculo.application.dto.in.CreateVehiculoRequest;
import com.lunorion.labs.core.vehiculo.application.dto.out.VehiculoResponse;
import com.lunorion.labs.core.vehiculo.domain.ports.in.IVehiculoCommandPort;
import com.lunorion.labs.core.vehiculo.domain.ports.in.IVehiculoQueryPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {

    private final IVehiculoCommandPort commandService;
    private final IVehiculoQueryPort queryService;

    public VehiculoController(IVehiculoCommandPort commandService, IVehiculoQueryPort queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    public ResponseEntity<VehiculoResponse> create(@RequestBody CreateVehiculoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commandService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<VehiculoResponse>> findAll() {
        return ResponseEntity.ok(queryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehiculoResponse> findById(@PathVariable String id) {
        return queryService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<List<VehiculoResponse>> findByTenantId(@PathVariable String tenantId) {
        return ResponseEntity.ok(queryService.findByTenantId(tenantId));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<VehiculoResponse>> findByClienteId(@PathVariable String clienteId) {
        return ResponseEntity.ok(queryService.findByClienteId(clienteId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehiculoResponse> update(@PathVariable String id, @RequestBody CreateVehiculoRequest request) {
        return ResponseEntity.ok(commandService.update(id, request));
    }

    @PostMapping("/{id}/asignar-cliente")
    public ResponseEntity<Void> asignarCliente(@PathVariable String id, @RequestBody Map<String, String> body) {
        commandService.asignarCliente(id, body.get("clienteId"));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivar(@PathVariable String id) {
        commandService.desactivar(id);
        return ResponseEntity.ok().build();
    }
}
