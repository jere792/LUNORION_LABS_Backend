package com.lunorion.labs.core.dashboard.infrastructure.adapters.in.http;

import com.lunorion.labs.core.dashboard.application.dto.out.KpiResponse;
import com.lunorion.labs.core.dashboard.application.dto.out.RentabilidadResponse;
import com.lunorion.labs.core.dashboard.domain.ports.in.IDashboardQueryPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final IDashboardQueryPort dashboardQueryPort;

    public DashboardController(IDashboardQueryPort dashboardQueryPort) {
        this.dashboardQueryPort = dashboardQueryPort;
    }

    @GetMapping("/kpis")
    public ResponseEntity<KpiResponse> obtenerKpis(@RequestParam("tenantId") String tenantId) {
        KpiResponse response = dashboardQueryPort.obtenerKpis(tenantId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/rentabilidad")
    public ResponseEntity<List<RentabilidadResponse>> obtenerRentabilidad(
            @RequestParam("tenantId") String tenantId) {
        List<RentabilidadResponse> response = dashboardQueryPort.obtenerRentabilidad(tenantId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/exportar")
    public ResponseEntity<byte[]> exportarReporte(
            @RequestParam("tenantId") String tenantId,
            @RequestParam("formato") String formato,
            @RequestParam("fechaInicio") String fechaInicio,
            @RequestParam("fechaFin") String fechaFin,
            @RequestParam("tipo") String tipo) {
        byte[] reporte = dashboardQueryPort.exportarReporte(tenantId, formato, fechaInicio, fechaFin, tipo);
        HttpHeaders headers = new HttpHeaders();
        String filename = "reporte." + (formato.equalsIgnoreCase("PDF") ? "pdf" : "xlsx");
        headers.setContentDispositionFormData("attachment", filename);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return ResponseEntity.ok().headers(headers).body(reporte);
    }
}
