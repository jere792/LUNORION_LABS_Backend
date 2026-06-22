package com.lunorion.labs.core.caja.infrastructure.adapters.in.http;

import com.lunorion.labs.core.caja.application.dto.in.AbrirCajaRequest;
import com.lunorion.labs.core.caja.application.dto.in.CerrarCajaRequest;
import com.lunorion.labs.core.caja.application.dto.in.RegistrarMovimientoRequest;
import com.lunorion.labs.core.caja.application.dto.out.CierreCajaResponse;
import com.lunorion.labs.core.caja.application.dto.out.EstadoCajaResponse;
import com.lunorion.labs.core.caja.application.dto.out.MovimientoCajaResponse;
import com.lunorion.labs.core.caja.application.service.CajaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/caja")
public class CajaController {

    private final CajaService cajaService;

    public CajaController(CajaService cajaService) {
        this.cajaService = cajaService;
    }

    @PostMapping("/abrir")
    public ResponseEntity<CierreCajaResponse> abrir(@RequestBody AbrirCajaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cajaService.abrir(request));
    }

    @PostMapping("/{id}/cerrar")
    public ResponseEntity<CierreCajaResponse> cerrar(@PathVariable String id, @RequestBody CerrarCajaRequest request) {
        CierreCajaResponse response = cajaService.cerrar(id, request);
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @PostMapping("/movimientos")
    public ResponseEntity<MovimientoCajaResponse> registrarMovimiento(@RequestBody RegistrarMovimientoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cajaService.registrarMovimiento(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CierreCajaResponse> findById(@PathVariable String id) {
        return cajaService.findCierreById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/abierto/{tenantId}")
    public ResponseEntity<CierreCajaResponse> findAbierto(@PathVariable String tenantId) {
        return cajaService.findCierreAbierto(tenantId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<List<CierreCajaResponse>> findByTenant(@PathVariable String tenantId) {
        return ResponseEntity.ok(cajaService.findCierresByTenant(tenantId));
    }

    @GetMapping("/{cierreCajaId}/movimientos")
    public ResponseEntity<List<MovimientoCajaResponse>> findMovimientos(@PathVariable String cierreCajaId) {
        return ResponseEntity.ok(cajaService.findMovimientosByCierre(cierreCajaId));
    }

    @GetMapping("/actual")
    public ResponseEntity<EstadoCajaResponse> currentState(@RequestParam String tenantId) {
        EstadoCajaResponse estado = cajaService.obtenerEstadoActual(tenantId);
        return estado != null ? ResponseEntity.ok(estado) : ResponseEntity.notFound().build();
    }
}
