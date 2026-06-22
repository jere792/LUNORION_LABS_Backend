package com.lunorion.labs.core.planilla.infrastructure.adapters.in.http;

import com.lunorion.labs.core.planilla.application.dto.in.CreateComisionConfigRequest;
import com.lunorion.labs.core.planilla.application.dto.in.RegistrarAsistenciaRequest;
import com.lunorion.labs.core.planilla.application.dto.out.AsistenciaResponse;
import com.lunorion.labs.core.planilla.application.dto.out.BoletaPagoResponse;
import com.lunorion.labs.core.planilla.application.dto.out.ComisionConfigResponse;
import com.lunorion.labs.core.planilla.application.dto.out.PlameResponse;
import com.lunorion.labs.core.planilla.application.dto.out.ProductividadResponse;
import com.lunorion.labs.core.planilla.domain.ports.in.IPlanillaCommandPort;
import com.lunorion.labs.core.planilla.domain.ports.in.IPlanillaQueryPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/planilla")
public class PlanillaController {

    private final IPlanillaCommandPort commandService;
    private final IPlanillaQueryPort queryService;

    public PlanillaController(IPlanillaCommandPort commandService, IPlanillaQueryPort queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping("/asistencias")
    public ResponseEntity<AsistenciaResponse> registrarAsistencia(@RequestBody RegistrarAsistenciaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commandService.registrarAsistencia(request));
    }

    @PostMapping("/boletas/generar")
    public ResponseEntity<BoletaPagoResponse> generarBoleta(@RequestParam String tecnicoId, @RequestParam String periodo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commandService.generarBoleta(tecnicoId, periodo));
    }

    @GetMapping("/asistencias")
    public ResponseEntity<List<AsistenciaResponse>> consultarAsistencia(
            @RequestParam String tecnicoId,
            @RequestParam LocalDate desde,
            @RequestParam LocalDate hasta) {
        return ResponseEntity.ok(queryService.consultarAsistencia(tecnicoId, desde, hasta));
    }

    @GetMapping("/boletas")
    public ResponseEntity<BoletaPagoResponse> consultarBoleta(
            @RequestParam String tecnicoId, @RequestParam String periodo) {
        return queryService.consultarBoleta(tecnicoId, periodo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/boletas/periodo")
    public ResponseEntity<List<BoletaPagoResponse>> consultarBoletasByPeriodo(
            @RequestParam String tenantId, @RequestParam String periodo) {
        return ResponseEntity.ok(queryService.consultarBoletasByPeriodo(tenantId, periodo));
    }

    @GetMapping("/productividad")
    public ResponseEntity<ProductividadResponse> productividad(
            @RequestParam String tenantId,
            @RequestParam LocalDate desde,
            @RequestParam LocalDate hasta) {
        return ResponseEntity.ok(queryService.productividad(tenantId, desde, hasta));
    }

    @GetMapping("/comisiones/config")
    public ResponseEntity<List<ComisionConfigResponse>> getComisionesConfig(@RequestParam String tenantId) {
        return ResponseEntity.ok(queryService.getComisionesConfig(tenantId));
    }

    @PostMapping("/comisiones/config")
    public ResponseEntity<ComisionConfigResponse> createComisionConfig(@RequestBody CreateComisionConfigRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commandService.createComisionConfig(request));
    }

    @GetMapping("/boletas/{id}/pdf")
    public ResponseEntity<byte[]> descargarPdf(@PathVariable String id) {
        byte[] pdf = queryService.descargarPdf(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "boleta_" + id + ".pdf");
        return ResponseEntity.ok().headers(headers).body(pdf);
    }

    @PostMapping("/plame")
    public ResponseEntity<PlameResponse> generarPlame(@RequestParam String tenantId, @RequestParam String periodo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commandService.generarPlame(tenantId, periodo));
    }
}
