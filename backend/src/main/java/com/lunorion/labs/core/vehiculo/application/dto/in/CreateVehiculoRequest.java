package com.lunorion.labs.core.vehiculo.application.dto.in;

public class CreateVehiculoRequest {
    private String tenantId;
    private String placa;
    private String marca;
    private String modelo;
    private Integer anio;
    private String color;
    private String numeroVin;
    private String clienteId;

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public Integer getAnio() { return anio; }
    public void setAnio(Integer anio) { this.anio = anio; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public String getNumeroVin() { return numeroVin; }
    public void setNumeroVin(String numeroVin) { this.numeroVin = numeroVin; }
    public String getClienteId() { return clienteId; }
    public void setClienteId(String clienteId) { this.clienteId = clienteId; }
}
