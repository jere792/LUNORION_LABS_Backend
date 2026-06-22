package com.lunorion.labs.core.tecnico.infrastructure.adapters.in.http;

import com.lunorion.labs.core.tecnico.application.dto.in.ActualizarTarifaRequest;
import com.lunorion.labs.core.tecnico.application.dto.in.CreateTecnicoRequest;
import com.lunorion.labs.core.tecnico.application.dto.out.CargaTrabajoResponse;
import com.lunorion.labs.core.tecnico.application.dto.out.TecnicoResponse;
import com.lunorion.labs.core.tecnico.domain.ports.in.ITecnicoCommandPort;
import com.lunorion.labs.core.tecnico.domain.ports.in.ITecnicoQueryPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tecnicos")
public class TecnicoController {

    private final ITecnicoCommandPort commandService;
    private final ITecnicoQueryPort queryService;

    public TecnicoController(ITecnicoCommandPort commandService, ITecnicoQueryPort queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    public ResponseEntity<TecnicoResponse> create(@RequestBody CreateTecnicoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commandService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TecnicoResponse> findById(@PathVariable String id) {
        return queryService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<TecnicoResponse>> findAll() {
        return ResponseEntity.ok(queryService.findAll());
    }

    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<List<TecnicoResponse>> findByTenant(@PathVariable String tenantId) {
        return ResponseEntity.ok(queryService.findByTenantId(tenantId));
    }

    @PostMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivar(@PathVariable String id) {
        commandService.desactivar(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/tarifa")
    public ResponseEntity<Void> actualizarTarifa(@PathVariable String id, @RequestBody ActualizarTarifaRequest request) {
        commandService.actualizarTarifa(id, request.getTarifa());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/carga-trabajo")
    public ResponseEntity<CargaTrabajoResponse> workload(@PathVariable String id) {
        return ResponseEntity.ok(queryService.workload(id));
    }
}
