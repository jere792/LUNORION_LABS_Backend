package com.lunorion.labs.core.producto.infrastructure.adapters.in.http;

import com.lunorion.labs.core.producto.application.dto.in.CreateProductoRequest;
import com.lunorion.labs.core.producto.application.dto.out.ProductoResponse;
import com.lunorion.labs.core.producto.domain.ports.in.IProductoCommandPort;
import com.lunorion.labs.core.producto.domain.ports.in.IProductoQueryPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final IProductoCommandPort commandService;
    private final IProductoQueryPort queryService;

    public ProductoController(IProductoCommandPort commandService, IProductoQueryPort queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    public ResponseEntity<ProductoResponse> create(@RequestBody CreateProductoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commandService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponse> update(@PathVariable String id, @RequestBody CreateProductoRequest request) {
        return ResponseEntity.ok(commandService.update(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponse> findById(@PathVariable String id) {
        return queryService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<ProductoResponse> findByCodigo(@PathVariable String codigo) {
        return queryService.findByCodigo(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<ProductoResponse>> findByCategoriaId(@PathVariable UUID categoriaId) {
        return ResponseEntity.ok(queryService.findByCategoriaId(categoriaId));
    }

    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<List<ProductoResponse>> findByTenantId(@PathVariable String tenantId) {
        return ResponseEntity.ok(queryService.findByTenantId(tenantId));
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<Void> updateStock(@PathVariable String id, @RequestBody Integer cantidad) {
        commandService.updateStock(id, cantidad);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/movimientos")
    public ResponseEntity<List<?>> findMovimientosByProducto(@PathVariable String id) {
        return ResponseEntity.ok(List.of());
    }

    @GetMapping("/stock-alertas")
    public ResponseEntity<List<ProductoResponse>> findStockCritico(@RequestParam String tenantId) {
        return ResponseEntity.ok(queryService.findStockCritico(tenantId));
    }

    @GetMapping("/reporte-rotacion")
    public ResponseEntity<List<ProductoResponse>> reporteRotacion(@RequestParam String tenantId) {
        return ResponseEntity.ok(queryService.reporteRotacion(tenantId));
    }

    @PostMapping("/creacion-rapida")
    public ResponseEntity<ProductoResponse> quickCreate(@RequestBody CreateProductoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commandService.quickCreate(request));
    }
}
