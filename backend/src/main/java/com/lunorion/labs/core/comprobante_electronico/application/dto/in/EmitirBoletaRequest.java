package com.lunorion.labs.core.comprobante_electronico.application.dto.in;

import java.math.BigDecimal;

public class EmitirBoletaRequest {
    private String tenantId;
    private String ventaId;
    private String serie;
    private int numero;
    private String fechaEmision;
    private String horaEmision;
    private BigDecimal montoTotal;
    private String rucCliente;
    private String razonSocialCliente;
    private String enviadoPorId;

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
    public String getVentaId() { return ventaId; }
    public void setVentaId(String ventaId) { this.ventaId = ventaId; }
    public String getSerie() { return serie; }
    public void setSerie(String serie) { this.serie = serie; }
    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }
    public String getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(String fechaEmision) { this.fechaEmision = fechaEmision; }
    public String getHoraEmision() { return horaEmision; }
    public void setHoraEmision(String horaEmision) { this.horaEmision = horaEmision; }
    public BigDecimal getMontoTotal() { return montoTotal; }
    public void setMontoTotal(BigDecimal montoTotal) { this.montoTotal = montoTotal; }
    public String getRucCliente() { return rucCliente; }
    public void setRucCliente(String rucCliente) { this.rucCliente = rucCliente; }
    public String getRazonSocialCliente() { return razonSocialCliente; }
    public void setRazonSocialCliente(String razonSocialCliente) { this.razonSocialCliente = razonSocialCliente; }
    public String getEnviadoPorId() { return enviadoPorId; }
    public void setEnviadoPorId(String enviadoPorId) { this.enviadoPorId = enviadoPorId; }
}
