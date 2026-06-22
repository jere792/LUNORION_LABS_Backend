package com.lunorion.labs.core.cotizacion.application.dto.in;

import java.util.List;

public class CreateCotizacionRequest {
    private String tenantId;
    private String clienteId;
    private String vehiculoId;
    private String notas;
    private List<CotizacionItemRequest> items;

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
    public String getClienteId() { return clienteId; }
    public void setClienteId(String clienteId) { this.clienteId = clienteId; }
    public String getVehiculoId() { return vehiculoId; }
    public void setVehiculoId(String vehiculoId) { this.vehiculoId = vehiculoId; }
    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }
    public List<CotizacionItemRequest> getItems() { return items; }
    public void setItems(List<CotizacionItemRequest> items) { this.items = items; }
}
