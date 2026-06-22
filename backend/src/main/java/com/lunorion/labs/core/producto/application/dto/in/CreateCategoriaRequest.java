package com.lunorion.labs.core.producto.application.dto.in;

import java.util.UUID;

public class CreateCategoriaRequest {
    private String tenantId;
    private String nombre;
    private UUID categoriaPadreId;

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public UUID getCategoriaPadreId() { return categoriaPadreId; }
    public void setCategoriaPadreId(UUID categoriaPadreId) { this.categoriaPadreId = categoriaPadreId; }
}
