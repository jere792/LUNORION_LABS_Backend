package com.lunorion.labs.core.usuario.infrastructure.adapters.in.http;

import com.lunorion.labs.core.usuario.application.dto.in.AsignarPermisosRequest;
import com.lunorion.labs.core.usuario.application.dto.in.CreateUsuarioRequest;
import com.lunorion.labs.core.usuario.application.dto.out.PermisoResponse;
import com.lunorion.labs.core.usuario.application.dto.out.UsuarioResponse;
import com.lunorion.labs.core.usuario.domain.ports.in.IUsuarioCommandPort;
import com.lunorion.labs.core.usuario.domain.ports.in.IUsuarioQueryPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final IUsuarioCommandPort commandService;
    private final IUsuarioQueryPort queryService;

    public UsuarioController(IUsuarioCommandPort commandService, IUsuarioQueryPort queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> create(@RequestBody CreateUsuarioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commandService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> findById(@PathVariable String id) {
        return queryService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<List<UsuarioResponse>> findByTenant(@PathVariable String tenantId) {
        return ResponseEntity.ok(queryService.findByTenantId(tenantId));
    }

    @PostMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivar(@PathVariable String id) {
        commandService.desactivar(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/permisos")
    public ResponseEntity<Void> asignarPermisos(@PathVariable String id, @RequestBody AsignarPermisosRequest request) {
        commandService.asignarPermisos(id, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/permisos")
    public ResponseEntity<List<PermisoResponse>> listarPermisos(@RequestParam String tenantId) {
        return ResponseEntity.ok(queryService.listarPermisos(tenantId));
    }
}
