package com.lunorion.labs.core.producto.infrastructure.adapters.in.http;

import com.lunorion.labs.core.producto.application.dto.in.CreateCategoriaRequest;
import com.lunorion.labs.core.producto.application.dto.out.CategoriaResponse;
import com.lunorion.labs.core.producto.domain.ports.in.IProductoCommandPort;
import com.lunorion.labs.core.producto.domain.ports.in.IProductoQueryPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final IProductoCommandPort commandService;
    private final IProductoQueryPort queryService;

    public CategoriaController(IProductoCommandPort commandService, IProductoQueryPort queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponse>> findAll() {
        return ResponseEntity.ok(queryService.findAllCategorias());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponse> findById(@PathVariable String id) {
        return queryService.findCategoriaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CategoriaResponse> create(@RequestBody CreateCategoriaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commandService.createCategoria(request));
    }
}
