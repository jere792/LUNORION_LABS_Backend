package com.lunorion.labs.core.planilla.application.dto.in;

import java.math.BigDecimal;

public class CreateComisionConfigRequest {
    private String tenantId;
    private String nombre;
    private String tipo;
    private BigDecimal porcentaje;
    private BigDecimal montoFijo;
    private boolean activo;

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public BigDecimal getPorcentaje() { return porcentaje; }
    public void setPorcentaje(BigDecimal porcentaje) { this.porcentaje = porcentaje; }
    public BigDecimal getMontoFijo() { return montoFijo; }
    public void setMontoFijo(BigDecimal montoFijo) { this.montoFijo = montoFijo; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}
