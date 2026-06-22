package com.lunorion.labs.core.orden_trabajo.infrastructure.adapters.in.http;

import com.lunorion.labs.core.orden_trabajo.application.dto.in.*;
import com.lunorion.labs.core.orden_trabajo.application.dto.out.CierreOtResponse;
import com.lunorion.labs.core.orden_trabajo.application.dto.out.KanbanResponse;
import com.lunorion.labs.core.orden_trabajo.application.dto.out.OrdenTrabajoResponse;
import com.lunorion.labs.core.orden_trabajo.domain.ports.in.IOrdenTrabajoCommandPort;
import com.lunorion.labs.core.orden_trabajo.domain.ports.in.IOrdenTrabajoQueryPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordenes-trabajo")
public class OrdenTrabajoController {

    private final IOrdenTrabajoCommandPort commandService;
    private final IOrdenTrabajoQueryPort queryService;

    public OrdenTrabajoController(IOrdenTrabajoCommandPort commandService, IOrdenTrabajoQueryPort queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    public ResponseEntity<OrdenTrabajoResponse> create(@RequestBody CreateOrdenTrabajoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commandService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdenTrabajoResponse> findById(@PathVariable String id) {
        return queryService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<OrdenTrabajoResponse>> findAll() {
        return ResponseEntity.ok(queryService.findAll());
    }

    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<List<OrdenTrabajoResponse>> findByTenant(@PathVariable String tenantId) {
        return ResponseEntity.ok(queryService.findByTenantId(tenantId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        commandService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdenTrabajoResponse> update(@PathVariable String id, @RequestBody CreateOrdenTrabajoRequest request) {
        return ResponseEntity.ok(commandService.update(id, request));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<OrdenTrabajoResponse> changeStatus(@PathVariable String id, @RequestBody CambioEstadoRequest request) {
        return ResponseEntity.ok(commandService.cambiarEstado(id, request));
    }

    @PostMapping("/{id}/insumos")
    public ResponseEntity<OrdenTrabajoResponse> addInsumo(@PathVariable String id, @RequestBody AddInsumoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commandService.addInsumo(id, request));
    }

    @DeleteMapping("/{id}/insumos/{insumoId}")
    public ResponseEntity<Void> removeInsumo(@PathVariable String id, @PathVariable String insumoId) {
        commandService.removeInsumo(id, insumoId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/labor")
    public ResponseEntity<OrdenTrabajoResponse> addLabor(@PathVariable String id, @RequestBody RegistroLaborRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commandService.addLabor(id, request));
    }

    @PutMapping("/{id}/labor/{laborId}")
    public ResponseEntity<OrdenTrabajoResponse> updateLabor(@PathVariable String id, @PathVariable String laborId,
                                                            @RequestBody RegistroLaborRequest request) {
        return ResponseEntity.ok(commandService.updateLabor(id, laborId, request));
    }

    @PostMapping("/{id}/cerrar")
    public ResponseEntity<CierreOtResponse> close(@PathVariable String id) {
        return ResponseEntity.ok(commandService.close(id));
    }

    @PostMapping("/{id}/reabrir")
    public ResponseEntity<OrdenTrabajoResponse> reopen(@PathVariable String id) {
        return ResponseEntity.ok(commandService.reopen(id));
    }

    @GetMapping("/kanban")
    public ResponseEntity<List<KanbanResponse>> kanban(@RequestParam String tenantId) {
        return ResponseEntity.ok(queryService.kanban(tenantId));
    }
}
