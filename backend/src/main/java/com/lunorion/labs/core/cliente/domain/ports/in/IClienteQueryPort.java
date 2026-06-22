package com.lunorion.labs.core.cliente.domain.ports.in;

import com.lunorion.labs.core.cliente.application.dto.out.ClienteResponse;
import com.lunorion.labs.core.cliente.application.dto.out.HistorialCompraResponse;
import com.lunorion.labs.core.cliente.application.dto.out.HistorialTrabajoResponse;
import com.lunorion.labs.core.cliente.application.dto.out.RentabilidadClienteResponse;

import java.util.List;
import java.util.Optional;

public interface IClienteQueryPort {
    Optional<ClienteResponse> findById(String id);
    Optional<ClienteResponse> findByNumeroDocumento(String numeroDocumento);
    List<ClienteResponse> findByTenantId(String tenantId);
    List<ClienteResponse> findAll();
    List<HistorialTrabajoResponse> workHistory(String id);
    List<HistorialCompraResponse> purchaseHistory(String id);
    RentabilidadClienteResponse profitability(String id);
}
